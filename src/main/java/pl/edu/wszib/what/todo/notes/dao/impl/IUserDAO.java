package pl.edu.wszib.what.todo.notes.dao.impl;

import pl.edu.wszib.what.todo.notes.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getById(Long id);
    Optional<User> getByLogin(String login);

    List<User> getAll();

    void save(User user);
    void delete(Long id);
    void update(User user);
}
