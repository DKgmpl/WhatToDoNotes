package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.what.todo.notes.dao.impl.IUserDAO;
import pl.edu.wszib.what.todo.notes.exceptions.RegisterValidationExemption;
import pl.edu.wszib.what.todo.notes.model.User;
import pl.edu.wszib.what.todo.notes.validators.RegisterValidator;

import java.util.Optional;

@Controller
public class AuthenticationController {

    private final IUserDAO userDAO;

    @Autowired
    public HttpSession httpSession;

    @Autowired
    public AuthenticationController(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginInfo", this.httpSession.getAttribute("loginInfo"));
        this.httpSession.removeAttribute("loginInfo");
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login2(@RequestParam String login, @RequestParam String password) {
        Optional<User> user = this.userDAO.getByLogin(login);
        if (user.isPresent() &&
                DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.get().getPassword())) {
            httpSession.setAttribute("user", user.get());
            System.out.println("Logged in");
            return "redirect:/";
        }
        this.httpSession.setAttribute("loginInfo", "Złe dane");
        System.out.println("ERR.. Not logged in, try again");
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("registerInfo", this.httpSession.getAttribute("registerInfo"));
        this.httpSession.removeAttribute("registerInfo");
        return "/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register2(@RequestParam String login, @RequestParam String password) {
        Optional<User> user = this.userDAO.getByLogin(login);
        User newUser = new User();
            if (user.isEmpty()) {
                newUser.setLogin(login);
                newUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
                newUser.setRole(User.Role.USER);
                try {
                    RegisterValidator.validateLogin(login);
                    RegisterValidator.validatePassword(password);
                } catch (RegisterValidationExemption e) {
                    e.printStackTrace();
                    this.httpSession.setAttribute("registerInfo", "Złe dane");
                    System.out.println("ERR.. Not registered new user, try again");
                    return "redirect:/register?error=" + e.getMessage();
                }
                this.userDAO.save(newUser);
                System.out.println("Registered new user");
            }
            return "redirect:/register";

    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.httpSession.removeAttribute("user");
        return "redirect:/";
    }
}
