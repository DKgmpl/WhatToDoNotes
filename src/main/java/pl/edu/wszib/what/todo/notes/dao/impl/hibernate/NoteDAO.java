package pl.edu.wszib.what.todo.notes.dao.impl.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.what.todo.notes.dao.impl.INoteDAO;
import pl.edu.wszib.what.todo.notes.model.Note;

import java.util.List;
import java.util.Optional;

@Repository
public class NoteDAO implements INoteDAO {

    private final SessionFactory sessionFactory;

    public NoteDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Note> getById(Long id) {
        Session session = sessionFactory.openSession();
        Query<Note> query = session.createQuery("FROM pl.edu.wszib.what.todo.notes.model.Note WHERE id = :id", Note.class);
        query.setParameter("id", id);
        try {
            Note note = query.getSingleResult();
            return Optional.of(note);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Note> getAll() {
        Session session = sessionFactory.openSession();
        Query<Note> query = session.createQuery("FROM pl.edu.wszib.what.todo.notes.model.Note", Note.class);
        List<Note> note = query.getResultList();
        session.close();
        return note;
    }

    @Override
    public List<Note> getByPattern(String pattern) {
        Session session = sessionFactory.openSession();
        Query<Note> query =
                session.createQuery("FROM pl.edu.wszib.what.todo.notes.model.Note WHERE title LIKE :pattern OR content LIKE :pattern", Note.class);
        query.setParameter("pattern", "%" + pattern + "%");
        List<Note> note = query.getResultList();
        session.close();
        return note;
    }

    @Override
    public void save(Note note) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(note);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new Note(id));
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Note note) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(note);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
