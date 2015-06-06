package com.hackathon.ultimate.hackers.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hackathon.ultimate.hackers.User;
import com.hackathon.ultimate.hackers.utility.PasswordHasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * JustBuy Identity Manager to manage user operations.
 *
 * @author asif
 */
@Singleton
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Inject))
public class IdentityManager {

    /**
     * Hibernate Session Factory.
     */
    private final SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public Integer createUser(final User user) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            /*
             * Check for duplicate email.
			 */
            List<User> users = session
                    .createQuery("From User where email=:email")
                    .setString("email", user.getEmail()).list();
            if (!users.isEmpty()) {
                throw new Exception("User already exists with emailId "
                        + user.getEmail());
            }
            int id = (int) session.save(user);
            tx.commit();
            log.debug("Successfully created user {}", user);
            return id;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public User getUser(final Integer userId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, userId);
            tx.commit();
            log.debug("Successfully queried user {}", user);
            return user;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public User getUser(final String email) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<User> users = session
                    .createQuery("From User where email=:email")
                    .setString("email", email).list();
            User user;
            if (users.isEmpty()) {
                user = null;
            } else {
                user = users.get(0);
            }
            tx.commit();
            log.debug("Successfully queried user {}", user);
            return user;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void changePassword(final Integer userId, final String newPassword) {

    }

    public boolean authenticate(final Integer userId, final String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        return PasswordHasher.validatePassword(password, getUser(userId).getPassword());
    }
}
