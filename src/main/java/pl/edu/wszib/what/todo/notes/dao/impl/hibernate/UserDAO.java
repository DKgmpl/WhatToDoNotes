package pl.edu.wszib.what.todo.notes.dao.impl.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.what.todo.notes.dao.impl.IUserDAO;
import pl.edu.wszib.what.todo.notes.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements IUserDAO {
    private final SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Optional<User> getById(Long id) {
        Session session = this.sessionFactory.openSession();
        Query<User> query =
                session.createQuery("FROM pl.edu.wszib.what.todo.notes.model.User WHERE id = :id", User.class);
        query.setParameter("id", id);
        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query =
                session.createQuery("FROM pl.edu.wszib.what.todo.notes.model.User WHERE login = :login", User.class);
        query.setParameter("login", login);
        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.edu.wszib.what.todo.notes.model.User", User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void save(User user) {
        Session session = this.sessionFactory.openSession();
        try {
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new User(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(User user) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
