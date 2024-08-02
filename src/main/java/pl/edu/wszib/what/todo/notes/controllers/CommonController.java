package pl.edu.wszib.what.todo.notes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.what.todo.notes.services.INoteService;

@Controller
public class CommonController {

    private final INoteService noteService;

    public CommonController(INoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required = false) String pattern) {
        if (pattern == null) {
            model.addAttribute("notes", this.noteService.getAll());
            model.addAttribute("pattern", "");
        } else {
            model.addAttribute("notes", this.noteService.getByPattern(pattern));
            model.addAttribute("pattern", pattern);
        }
        return "index";
    }
}
