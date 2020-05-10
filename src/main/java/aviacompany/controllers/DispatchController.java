package aviacompany.controllers;

import aviacompany.extra.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DispatchController {
    private final DispatchService dispatchService;

    @Autowired
    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    //    Додати мапінг для входу в систему для диспетчера
    @GetMapping("/employee_login/dispatch_login")
    public String employee_login(Model model) {
        model.addAttribute("employee", "dispatch");
        model.addAttribute("label", "Employee Login");
        return "employee_login";
    }

    //    Додати мапінг для входу в систему для диспетчера
    @PostMapping("employee_login/dispatch_login")
    public String emp_log(@RequestParam("id") long id,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        if (dispatchService.getLogin(id, username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("employee", id);

            return "redirect:/home";
        } else
            return "redirect:/about";
    }
}