package ru.mephi.hibernate_1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernate_1.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public class HqlUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return getSession().createQuery("FROM User", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        List<User> users = getSession().createQuery(
                "FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList();

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    @Transactional
    public User save(User user) {
        getSession().persist(user);
        return user;
    }

    @Override
    @Transactional
    public List<User> saveAll(List<User> users) {
        Session session = getSession();
        for (User user : users) {
            session.persist(user);
        }
        return users;
    }

    @Override
    @Transactional
    public User update(User user) {
        getSession().createQuery(
                "UPDATE User u SET u.username = :username, u.email = :email, u.age = :age WHERE u.id = :id")
                .setParameter("username", user.getUsername())
                .setParameter("email", user.getEmail())
                .setParameter("age", user.getAge())
                .setParameter("id", user.getId())
                .executeUpdate();

        return getSession().createQuery(
                "FROM User u WHERE u.id = :id", User.class)
                .setParameter("id", user.getId())
                .getSingleResult();
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        int deletedCount = getSession().createQuery(
                "DELETE FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        return deletedCount > 0;
    }
}