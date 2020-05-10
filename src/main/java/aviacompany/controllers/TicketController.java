package aviacompany.controllers;

import aviacompany.entity.Ticket;
import aviacompany.extra.Order;
import aviacompany.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TicketController {
    TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    //    Додати мапінг для сторінки зі списком квитків
    @GetMapping("/tickets")
    public String tickets(Model model) {
        model.addAttribute("label", "Tickets");
//        До моделі додати список квитків
        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets";
    }

    //    Додати мапінг для сторінки окремого квитка
    @GetMapping("/tickets/{id}")
    public String ticketId(@PathVariable("id") String id,
                           Model model) {
        model.addAttribute("label", "Ticket " + id);

//        Знайти потрібний квиток та показати його сторінку, інакше перейти до домашньої сторінки
        for (Ticket item : ticketRepository.findAll()) {
            if (String.valueOf(item.getId()).equals(id)) {
                model.addAttribute("ticket", item);
                model.addAttribute("addServices", item.getAdd_services());
                return "ticket_page";
            }
        }

        return "redirect:/home";
    }

    //    Додати мапінг для сторінки з замовленням
    @GetMapping("/order")
    public String order(HttpServletRequest request, Model model) {
        model.addAttribute("label", "Order");
        HttpSession session = request.getSession();

//        З поточної сесії отримати замовлення та додатковий атрибут для перевірки наявності обраних додаткових послуг
        Order order = (Order) session.getAttribute("order");
        String hasAddServices = (String) session.getAttribute("hasAddServices");

//        Додати всі дані до моделі
        model.addAttribute("order", order.getTickets());
        model.addAttribute("total", order.getTotal());
        model.addAttribute("hasAddServices", hasAddServices);

        return "order";
    }

    //    Пост-запит для отримання замовлення
    @PostMapping("/order")
    public String getOrder(@RequestParam("id") Ticket ticket,
                           @RequestParam("hasAddService") String hasAddService,
                           HttpServletRequest request, Model model) {
        model.addAttribute("label", "Order");
//        З поточної сесії отримати замовлення
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");

//        Якщо замовлення немає, то створити його новий об'єкт та додати до сесії
        if (order == null) {
            order = new Order();
            session.setAttribute("order", order);
        }

//        Додати новий квиток до замовлення
        order.addNewItemToOrder(ticket, hasAddService.equals("1"));
        session.setAttribute("order", order);
        session.setAttribute("hasAddServices", hasAddService);

//        До моделі додати список квитків в замовленні та загальну суму
        model.addAttribute("order", order.getTickets());
        model.addAttribute("total", order.getTotal());

        return "order";
    }

    //    Пост-запит на видалення певного квитка з замовлення
    @PostMapping("deleteTicket")
    public String deleteTicket(@RequestParam("id") Ticket ticket,
                               HttpServletRequest request) {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        order.deleteItemInOrder(ticket);
        session.setAttribute("order", order);
        return "redirect:/order";
    }

    //    Пост-запит на повне очищення замовлення
    @PostMapping("clearAll")
    public String clearAll(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        Order order = (Order) session.getAttribute("order");
        order.clearOrder();
        session.setAttribute("order", order);

        return "redirect:/order";
    }
}
