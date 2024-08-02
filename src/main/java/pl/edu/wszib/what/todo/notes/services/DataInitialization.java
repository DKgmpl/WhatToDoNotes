package pl.edu.wszib.what.todo.notes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.dao.impl.IUserDAO;
import pl.edu.wszib.what.todo.notes.model.Note;
import pl.edu.wszib.what.todo.notes.model.User;

@Component
@RequiredArgsConstructor
public class DataInitialization implements CommandLineRunner {

    private final IUserDAO userDAO;
    private final INoteDAO noteDAO;

    @Override
    public void run(String... args) throws Exception {
        this.userDAO.save(new User(null, "Master","Admin","admin", DigestUtils.md5DigestAsHex
                ("admin123".getBytes()), User.Role.ADMIN));
        this.userDAO.save(new User(null, "Jan","Kowalski","jan", DigestUtils.md5DigestAsHex
                ("jan123".getBytes()), User.Role.USER));

        this.noteDAO.save(new Note(null,
                "Przykładowa Notatka", "Przykładowy opis w notatce", "WAŻNEEE"));
    }
}
