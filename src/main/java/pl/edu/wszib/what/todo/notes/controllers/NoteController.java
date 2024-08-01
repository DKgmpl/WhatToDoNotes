package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.exceptions.NoteValidationExemption;
import pl.edu.wszib.what.todo.notes.model.Note;
import pl.edu.wszib.what.todo.notes.validators.NoteValidator;

import java.util.Optional;

@Controller
public class NoteController {

    private final INoteDAO noteDAO;

    private final HttpSession httpSession;

    public NoteController(INoteDAO noteDAO, HttpSession httpSession) {
        this.noteDAO = noteDAO;
        this.httpSession = httpSession;
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.GET)
    public String addNoteForm(Model model) {
        model.addAttribute("noteModel", new Note());
        return "noteForm";
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.POST)
    public String addBookForm2(@ModelAttribute Note note) {
        if (this.httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExemption e) {
            e.printStackTrace();
            return "redirect:/note/add";
        }
        this.noteDAO.save(note);
        return "redirect:/";
    }

    @RequestMapping(path = "/note/edit/{id}", method = RequestMethod.GET)
    public String editNoteForm(@PathVariable int id, Model model) {
        if (this.httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        Optional<Note> noteBox = this.noteDAO.getById(id);
        if (noteBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("noteModel", noteBox.get());
        }
        return "noteForm";
    }

    @RequestMapping(path = "/note/edit/{id}", method = RequestMethod.POST)
    public String editNoteForm2(@ModelAttribute Note note, @PathVariable int id) {
        if (this.httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExemption e) {
            e.printStackTrace();
            return "redirect:/note/edit/";
        }
        note.setId(id);
        this.noteDAO.update(note);
        return "redirect:/";
    }

    // NIE DZIALA ZROBIC
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public String deleteNoteForm(@ModelAttribute Note note) {
        if (this.httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        this.noteDAO.delete(note.getId());
        return "redirect:/";
    }
}
