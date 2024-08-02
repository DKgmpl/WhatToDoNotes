package pl.edu.wszib.what.todo.notes.dao.impl;

import pl.edu.wszib.what.todo.notes.model.Note;

import java.util.List;
import java.util.Optional;

public interface INoteDAO {

    Optional<Note> getById(Long id);
    List<Note> getAll();
    List<Note> getByPattern(String pattern);

    void save(Note note);
    void delete(Long id);
    void update(Note note);
}
