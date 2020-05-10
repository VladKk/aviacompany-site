package aviacompany.controllers;

import aviacompany.entity.Client;
import aviacompany.entity.User;
import aviacompany.extra.UserService;
import aviacompany.repository.ClientRepository;
import aviacompany.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserRoleController {
    private final UserService userService;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserRoleController(UserService userService, ClientRepository clientRepository, UserRepository userRepository) {
        this.userService = userService;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("label", "Login");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("label", "Register");
        model.addAttribute("user", new User());
        model.addAttribute("client", new Client());
        return "register";
    }

    @PostMapping("register")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          @Valid Client client,
                          BindingResult bindingResult1,
                          Model model) {
        model.addAttribute("label", "Register");
        if (bindingResult.hasErrors() || bindingResult1.hasErrors())
            return "register";

        if (!user.getPassword().equals(user.getConfirmPass())) {
            model.addAttribute("passwordError", "Passwords dont match");
            return "register";
        }

        if (!userService.saveUser(user)) {
            model.addAttribute("loginError", "The user is already exists");
            return "register";
        }

        User user1 = userRepository.findByLogin(user.getUsername());

        client.setUser(user1);
        clientRepository.save(client);

        return "redirect:/home";
    }
}
