package pl.edu.wszib.what.todo.notes.dao.impl.memory;

import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.model.Note;

import java.util.*;

public class NoteRepository implements INoteDAO {

    List<Note> notes = new ArrayList<>();

    private final IdSequence idSequence;

    public NoteRepository(IdSequence idSequence) {
        this.idSequence = new IdSequence();
        this.notes.add(new Note((long) this.idSequence.getId(),
                "Przykładowa Notatka", "Przykładowy opis w notatce", "WAŻNEEE"));
    }

    @Override
    public Optional<Note> getById(final Long id) {
        return this.notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .map(this::copy);
    }

    @Override
    public List<Note> getAll() {
        return this.notes.stream().map(this::copy).toList();
    }

    @Override
    public List<Note> getByPattern(final String pattern) {
        return this.notes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(pattern.toLowerCase()) ||
                        note.getStatus().contains(pattern.toLowerCase()))
                .map(this::copy)
                .toList();
    }

    @Override
    public void save(Note note) {
        note.setId((long) this.idSequence.getId());
        notes.add(note);
    }

    @Override
    public void delete(Long id) {
        this.notes.removeIf(note -> note.getId().equals(id));
    }

    @Override
    public void update(final Note note) {
            this.notes.stream()
                    .filter(b -> Objects.equals(b.getId(), note.getId()))
                    .findAny()
                    .ifPresent(b -> {
                b.setTitle(note.getTitle());
                b.setContent(note.getContent());
                b.setStatus(note.getStatus());
            });
    }

    // Funkcja kopiująca powtarzalny fragment dla getById i getByPattern
    private Note copy(Note note) {
        Note copy = new Note();
        copy.setId(note.getId());
        copy.setTitle(note.getTitle());
        copy.setContent(note.getContent());
        copy.setStatus(note.getStatus());
        return copy;
    }
}
