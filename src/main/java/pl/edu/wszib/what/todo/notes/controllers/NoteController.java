package pl.edu.wszib.what.todo.notes.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.what.todo.notes.exceptions.NoteValidationExemption;
import pl.edu.wszib.what.todo.notes.model.Note;
import pl.edu.wszib.what.todo.notes.services.INoteService;
import pl.edu.wszib.what.todo.notes.validators.NoteValidator;

import java.util.Optional;

@Controller
public class NoteController {

    private final INoteService noteService;

    private final HttpSession httpSession;

    public NoteController(INoteService noteService, HttpSession httpSession) {
        this.noteService = noteService;
        this.httpSession = httpSession;
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("noteModel", new Note());
        return "noteForm";
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.POST)
    public String add2(@ModelAttribute Note note) {
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExemption e) {
            e.printStackTrace();
            return "redirect:/note/add";
        }
        this.noteService.save(note);
        return "redirect:/";
    }

    @RequestMapping(path = "/note/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        Optional<Note> noteBox = this.noteService.getById(id);
        if (noteBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("noteModel", noteBox.get());
        }
        return "noteForm";
    }

    @RequestMapping(path = "/note/edit/{id}", method = RequestMethod.POST)
    public String edit2(@ModelAttribute Note note, @PathVariable int id) {
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExemption e) {
            e.printStackTrace();
            return "redirect:/note/edit/";
        }
        this.noteService.update(note,id);
        return "redirect:/";
    }

    // NIE DZIALA ZROBIC
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public String deleteNoteForm(@ModelAttribute Note note) {
        if (this.httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        this.noteService.delete(note);
        return "redirect:/";
    }
}
