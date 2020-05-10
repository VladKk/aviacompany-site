package aviacompany.controllers;

import aviacompany.extra.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/employee_login/admin_login")
    public String employee_login(Model model) {
        model.addAttribute("employee", "admin");
        model.addAttribute("label", "Employee Login");
        return "employee_login";
    }

    @PostMapping("employee_login/admin_login")
    public String emp_log(@RequestParam("id") long id,
                          @RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        if (adminService.getLogin(id, username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("employee", id);

            return "redirect:/home";
        } else
            return "redirect:/about";
    }
}
