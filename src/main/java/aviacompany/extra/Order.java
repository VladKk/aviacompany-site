package aviacompany.extra;


import aviacompany.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

// Додатковий клас для обробки замовлення
public class Order {
    private final List<Ticket> tickets;
    private double total;

    public Order() {
        tickets = new ArrayList<>();
        total = 0.0;
    }

    public synchronized List<Ticket> getTickets() {
        return tickets;
    }

    //    Додати новий квиток до замовлення
    public synchronized void addNewItemToOrder(Ticket ticket, boolean hasAddService) {
        boolean newItem = true;

//        Якщо новий квиток уже існує, то не додавати його до замовлення
        for (Ticket item : tickets) {
            if (item.getId() == ticket.getId()) {
                newItem = false;
                break;
            }
        }

        if (newItem) {
            tickets.add(ticket);

//            Якщо квиток має обрані додаткові послуги, то додати його повну ціну, інакше додати лише ціну самого квитка
            if (hasAddService)
                total += ticket.getFullPrice();
            else
                total += ticket.getPrice();
        }
    }

    //    Видалити квиток з замовлення
    public synchronized void deleteItemInOrder(Ticket ticket) {
        for (Ticket item : tickets) {
            if (item.getId() == ticket.getId()) {
                total -= item.getPrice();
                tickets.remove(item);
                break;
            }
        }
    }

    //    Очитсити замовлення
    public synchronized void clearOrder() {
        tickets.clear();
        total = 0.0;
    }

    public synchronized double getTotal() {
        return total;
    }
}
