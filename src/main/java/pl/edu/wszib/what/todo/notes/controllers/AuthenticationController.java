package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.what.todo.notes.dao.impl.IUserDAO;
import pl.edu.wszib.what.todo.notes.model.User;

import java.util.Optional;

@Controller
public class AuthenticationController {

    private final IUserDAO userDAO;

    @Autowired
    HttpSession httpSession;

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
        this.httpSession.setAttribute("loginInfo", "złe dane");
        System.out.println("Not logged in, try again");
        return "redirect:/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register() {
        return "/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register2(@RequestParam String login, @RequestParam String password, @RequestParam User.Role role) {
        Optional<User> user = this.userDAO.getByLogin(login);
        if (user.isPresent()) {
            System.out.println("Not registered new user, login already exists");
            return "redirect:/register";
        }
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        newUser.setRole(role);
        this.userDAO.save(newUser);
        System.out.println("Registered new user");
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.httpSession.removeAttribute("user");
        return "redirect:/";
    }
}
