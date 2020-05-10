package aviacompany.controllers;

import aviacompany.extra.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRoleController {
    private final UserService userService;

    @Autowired
    public AdminRoleController(UserService userService) {
        this.userService = userService;
    }

    //    Додати мапінг для сторінки для адміністратора
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("label", "Admin");
//        До моделі додати список користувачів та дані про поточного користувача
        model.addAttribute("user_list", userService.allUsers());
        model.addAttribute("user", userService.getUser());
        return "admin";
    }

    //    Видалити коритсувача
    @PostMapping("admin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") long id,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(id);
        }

        return "redirect:/admin";
    }

    //    Отримати дані про певного користувача
    @GetMapping("/admin/get/{id}")
    public String getUser(@PathVariable("id") long id,
                          Model model) {
        model.addAttribute("all_users", userService.getUserList(id));
        return "admin";
    }
}
