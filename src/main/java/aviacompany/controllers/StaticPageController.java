package aviacompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("label", "Home");
        return "home";
    }

    @GetMapping("/employee_login")
    public String employee_login(Model model) {
        model.addAttribute("label", "Employee Login");
        return "employee_login";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("label", "About");
        return "about";
    }

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("label", "News");
        return "news";
    }

    @GetMapping("/thanks")
    public String thanks(Model model) {
        model.addAttribute("label", "Thanks");
        return "thanks";
    }
}
