package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;

@Controller
public class CommonController {

    private final INoteDAO noteDAO;

    @Autowired
    HttpSession httpSession;
//    private final IUserDAO userDAO;

    public CommonController(INoteDAO noteDAO, HttpSession httpSession) {
        this.noteDAO = noteDAO;
//        this.userDAO = userDAO;
        this.httpSession = httpSession;
    }


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required = false) String pattern) {
        if (pattern == null) {
            model.addAttribute("notes", noteDAO.getAll());
            model.addAttribute("pattern", "");
        } else {
            model.addAttribute("notes", noteDAO.getByPattern(pattern));
            model.addAttribute("pattern", pattern);
        }
        return "index";
    }
}
