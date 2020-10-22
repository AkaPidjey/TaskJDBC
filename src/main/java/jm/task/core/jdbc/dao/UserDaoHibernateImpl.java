package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {}

    @Override   // * Creating a user table.
    public void createUsersTable() {
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR (30) NOT NULL, " +
                    "lastName VARCHAR (30) NOT NULL, " +
                    "age int NOT NULL" +
                    ") ENGINE=InnoDB;").executeUpdate();
            transaction.commit();
            System.out.println("База данных [user] создана!");
        } catch (HibernateException he) {
            System.out.println("Ошибка создания базы данных [user]");
            he.printStackTrace();
        }
    }

    @Override   // * Deleting a user table.
    public void dropUsersTable() {
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("База данных [user] удалена");
        } catch (HibernateException he) {
            System.out.println("Ошибка удаления базы данных [user]");
            he.printStackTrace();
        }
    }

    @Override   // * Adding a user to a table.
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("Пользователь с именем – " + name + " добавлен в базу данных [user] ");
        } catch (HibernateException he) {
            System.out.println("Ошибка сохранения пользователья в базе данных [user]");
            he.printStackTrace();
        }
    }

    @Override   // * Deleting a user in a table.
    public void removeUserById(long id) {
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь [id = " + id + "] удален из базы данных [user]!");
        } catch (HibernateException he) {
            System.out.println("Ошибка удаления пользователя из базы данных [user]");
            he.printStackTrace();
        }
    }

    @Override   // * Getting all users from a table.
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            users = session.createQuery("FROM User").list();
            transaction.commit();
            System.out.println("Вывод списка всех пользователей базы данных [user]");
        } catch (HibernateException he) {
            System.out.println("Ошибка получения списка всех пользователей из базы данных [user]");
            he.printStackTrace();
        }
        return users;
    }

    @Override   // * Clearing contents of a table.
    public void cleanUsersTable() {
        try(Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
//            session.createQuery("DELETE FROM users").executeUpdate();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("База данных [user] очищена!");
        } catch (HibernateException he) {
            System.out.println("Ошибка очистки базы данных [user]");
            he.printStackTrace();
        }
    }
}
