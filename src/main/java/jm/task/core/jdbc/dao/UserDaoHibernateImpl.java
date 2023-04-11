package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mortbay.jetty.servlet.AbstractSessionManager;

import java.sql.Connection;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS  User "
                    + "(id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,"
                    + "name varchar(255),"
                    + "lastname varchar(255) not null,"
                    + "age tinyint not null)";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS User";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try  (Session session = Util.getSessionFactory().openSession()) {
            transaction =session.beginTransaction();
            session.createQuery("delete User  where id = :id").setParameter("id",id).executeUpdate();
            transaction.commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User",User.class)
                    .getResultList();
            transaction.commit();
            } catch (NullPointerException e) {
            e.printStackTrace();
            transaction.rollback();
        }
        return users ;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "truncate table User";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
