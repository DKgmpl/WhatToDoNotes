package pl.edu.wszib.what.todo.notes.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.model.Note;
import pl.edu.wszib.what.todo.notes.services.INoteService;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService implements INoteService {

    private final INoteDAO noteDAO;

    public NoteService(INoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    public void save(Note note) {
        this.noteDAO.save(note);

    }

    @Override
    public Optional<Note> getById(Long id) {
        return this.noteDAO.getById(id);
    }

    @Override
    public void update(Note note, Long id) {
        note.setId(id);
        this.noteDAO.update(note);
    }

    @Override
    public void delete(Note note) {
        this.noteDAO.delete(note.getId());
    }

    @Override
    public List<Note> getAll() {
        return this.noteDAO.getAll();
    }

    @Override
    public List<Note> getByPattern(String pattern) {
        return this.noteDAO.getByPattern(pattern);
    }
}
