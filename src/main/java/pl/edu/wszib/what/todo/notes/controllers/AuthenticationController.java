package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.wszib.what.todo.notes.exceptions.RegisterValidationExemption;
import pl.edu.wszib.what.todo.notes.services.IAuthenticationService;
import pl.edu.wszib.what.todo.notes.session.SessionConstants;
import pl.edu.wszib.what.todo.notes.validators.RegisterValidator;

@Controller
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    @Autowired
    public HttpSession httpSession;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "/login")
    public String login(Model model) {
        model.addAttribute("loginInfo", this.authenticationService.getLoginInfo());
        return "login";
    }

    @PostMapping(path = "/login")
    public String login2(@RequestParam String login, @RequestParam String password) {
        this.authenticationService.login(login, password);
        if (this.httpSession.getAttribute(SessionConstants.USER_KEY) != null) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("registerInfo", this.authenticationService.getRegisterInfo());
        return "/register";
    }

    @PostMapping(path = "/register")
    public String register2(@RequestParam String name, @RequestParam String surname,
                            @RequestParam String login, @RequestParam String password) {
        try {
            RegisterValidator.validateName(name);
            RegisterValidator.validateSurname(surname);
            RegisterValidator.validateLogin(login);
            RegisterValidator.validatePassword(password);
        } catch (RegisterValidationExemption e) {
            e.printStackTrace();
            return "redirect:/register?error=" + e.getMessage();
        }
        this.authenticationService.register(name, surname, login, password);
        if (this.httpSession.getAttribute(SessionConstants.USER_KEY) != null) {
            return "redirect:/";
        }
        return "redirect:/register";
    }

    @GetMapping(path = "/logout")
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/";
    }
}
