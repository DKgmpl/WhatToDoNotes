package pl.edu.wszib.what.todo.notes.dao.impl.memory;

import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import pl.edu.wszib.what.todo.notes.dao.impl.IUserDAO;
import pl.edu.wszib.what.todo.notes.exceptions.LoginAlreadyExistExemption;
import pl.edu.wszib.what.todo.notes.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements IUserDAO {

    private final List<User> users = new ArrayList<>();

    private final IdSequence idSequence;

    public UserRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
        this.users.add(new User(this.idSequence.getId(), "Master","Admin","admin",DigestUtils.md5DigestAsHex
                ("admin123".getBytes()), User.Role.ADMIN));
        this.users.add(new User(this.idSequence.getId(), "Jan","Kowalski","jan", DigestUtils.md5DigestAsHex
                ("jan123".getBytes()), User.Role.USER));
    }

    @Override
    public Optional<User> getById(final int id) {
        return this.users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(this::copy);
    }

    @Override
    public Optional<User> getByLogin(final String login) {
        return this.users.stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .map(this::copy);
    }

    @Override
    public List<User> getAll() {
        return this.users.stream().map(this::copy).toList();
    }

    @Override
    public void save(User user) {
        user.setId(this.idSequence.getId());
        this.getByLogin(user.getLogin()).ifPresent(u -> {
            throw new LoginAlreadyExistExemption();
        });
        this.users.add(user);
    }

    @Override
    public void delete(final int id) {
        this.users.removeIf(user -> user.getId() == id);
    }

    @Override
    public void update(User user) {
        this.users.stream()
                .filter(u -> u.getId() == user.getId())
                .findAny()
                .ifPresent(u -> {
            u.setName(user.getName());
            u.setSurname(user.getSurname());
            u.setLogin(user.getLogin());
            u.setPassword(user.getPassword());
            u.setRole(user.getRole());
        });
    }

    private User copy (User user) {
        User u = new User();
        u.setId(user.getId());
        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setLogin(user.getLogin());
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        return u;
    }
}
