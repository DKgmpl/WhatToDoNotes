package pl.edu.wszib.what.todo.notes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.exceptions.NoteValidationExeption;
import pl.edu.wszib.what.todo.notes.model.Note;
import pl.edu.wszib.what.todo.notes.validators.NoteValidator;

import java.util.Optional;

@Controller
public class NoteController {

    private final INoteDAO noteDAO;

    public NoteController(INoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.GET)
    public String addNoteForm(Model model) {
        model.addAttribute("noteModel", new Note());
        return "addNote";
    }

    @RequestMapping(path = "/note/add", method = RequestMethod.POST)
    public String addBookForm2(@ModelAttribute Note note) {
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExeption e) {
            return "redirect:/note/add?error=" + e.getMessage();
        }
        this.noteDAO.save(note);
        return "redirect:/";
    }

    @RequestMapping(path = "/note/edit/{id}", method = RequestMethod.GET)
    public String editNoteForm(@PathVariable int id, Model model) {
        Optional<Note> noteBox = this.noteDAO.getById(id);
        if (noteBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("noteModel", noteBox.get());
        }
        return "addNote";
    }

    @RequestMapping(path = "/note/edit/{id}", method = RequestMethod.POST)
    public String editNoteForm2(@ModelAttribute Note note, @PathVariable int id) {
        try {
            NoteValidator.validateTitle(note.getTitle());
            NoteValidator.validateContent(note.getContent());
            NoteValidator.validateStatus(note.getStatus());
        } catch (NoteValidationExeption e) {
            return "redirect:/note/edit/" + id + "?error=" + e.getMessage();
        }
        note.setId(id);
        this.noteDAO.update(note);
        return "redirect:/";
    }

}
