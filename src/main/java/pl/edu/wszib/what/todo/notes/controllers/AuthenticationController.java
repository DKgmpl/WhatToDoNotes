package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.what.todo.notes.exceptions.RegisterValidationExemption;
import pl.edu.wszib.what.todo.notes.services.IAuthenticationService;
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

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginInfo", this.authenticationService.getLoginInfo());
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login2(@RequestParam String login, @RequestParam String password) {
        // WALIDACJA LOGINU (DANE WEJSCIOWE)
        this.authenticationService.login(login, password);
        if (this.httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("registerInfo", this.authenticationService.getRegisterInfo());
        return "/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
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
        if (this.httpSession.getAttribute("user") != null) {
            return "redirect:/login";
        }
        return "redirect:/register";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/";
    }
}
