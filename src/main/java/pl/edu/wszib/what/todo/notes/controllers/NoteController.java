package pl.edu.wszib.what.todo.notes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(path = "/add")
    public String add(Model model) {
        model.addAttribute("noteModel", new Note());
        return "noteForm";
    }

    @PostMapping(path = "/add")
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

    @GetMapping(path = "/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Note> noteBox = this.noteService.getById(id);
        if (noteBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("noteModel", noteBox.get());
        }
        return "noteForm";
    }

    @PostMapping(path = "/edit/{id}")
    public String edit2(@ModelAttribute Note note, @PathVariable Long id) {
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
