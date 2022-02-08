package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {}


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS users";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();

        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

             User us = session.load(User.class, id);
             session.delete(us);

            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> l = null;
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
             l = session.createQuery("from User", User.class).list();
             transaction.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.clear();

            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
