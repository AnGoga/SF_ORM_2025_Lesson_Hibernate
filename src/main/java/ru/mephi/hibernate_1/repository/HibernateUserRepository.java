package ru.mephi.hibernate_1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mephi.hibernate_1.exceptions.AppException;
import ru.mephi.hibernate_1.model.User;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@Primary
public class HibernateUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return getSession().createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        User user = getSession().find(User.class, id);
        User user1 = getSession().find(User.class, id);
        User user2 = getSession().find(User.class, id);
        User user3 = getSession().find(User.class, id);
        User user4 = getSession().find(User.class, id);

        user.setEmail("wert");

        return Optional.ofNullable(user);
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
        for (int i = 0; i < users.size(); i++) {
            session.persist(users.get(i));
            if (i % 1000 == 0) session.flush();
        }
        return users;
    }

    @Override
    @Transactional
    public User update(User user) {
        return getSession().merge(user);
    }

//    @Override
    @Transactional
    public User update2(User updateUser) {
        Session session = getSession();
        User user = session.find(User.class, updateUser.getId());
        if (user != null) {
            user.setUsername(updateUser.getUsername());
            user.setAge(updateUser.getAge());
            user.setEmail(updateUser.getEmail());
            return user;
        } else {
            return save(updateUser);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Session session = getSession();
        User user = session.find(User.class, id);
        if (user != null) {
            session.remove(user);
            return true;
        }
        return false;
    }


    @Autowired private EntityManagerFactory emf;
    SessionFactory sessionFactory() {
        return emf.unwrap(SessionFactory.class);
    }
}