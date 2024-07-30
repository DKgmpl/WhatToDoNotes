package pl.edu.wszib.what.todo.notes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.exceptions.NoteValidationExeption;
import pl.edu.wszib.what.todo.notes.model.Note;
import pl.edu.wszib.what.todo.notes.validators.NoteValidator;

import java.awt.print.Book;

@Controller
public class NoteController {

    private final INoteDAO noteDAO;

    public NoteController(INoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.GET)
    public String addNoteForm(Model model) {
        model.addAttribute("singularNote", new Note());
        return "addNote";
    }

    @RequestMapping(path = "/book/add", method = RequestMethod.POST)
    public String addBookForm2(@ModelAttribute Note note) {
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExeption e) {
//            e.printStackTrace();
            return "redirect:/book/add?error=" + e.getMessage();
        }
        this.noteDAO.save(note);
        return "/redirect:/";
    }
}
