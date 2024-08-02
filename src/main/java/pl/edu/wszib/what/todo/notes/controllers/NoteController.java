package pl.edu.wszib.what.todo.notes.controllers;

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
@RequestMapping(path = "/note")
public class NoteController {

    private final INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("noteModel", new Note());
        return "noteForm";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
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

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable int id, Model model) {
        Optional<Note> noteBox = this.noteService.getById(id);
        if (noteBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("noteModel", noteBox.get());
        }
        return "noteForm";
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.POST)
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
    public String delete(@ModelAttribute Note note) {
        this.noteService.delete(note);
        return "redirect:/";
    }
}
