package pl.edu.wszib.what.todo.notes.services;

import pl.edu.wszib.what.todo.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface INoteService {
    void save(Note note);
    Optional<Note> getById(Long id);
    void update(Note note, Long id);
    void delete(Note note);
    List<Note> getAll();
    List<Note> getByPattern(String pattern);
}
