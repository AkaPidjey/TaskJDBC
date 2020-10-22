package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
//    private final UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
    private final UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
    /**
     * Creating a user table.
     */
    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    /**
     * Deleting a user table.
     */
    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    /**
     * Adding a user to a table.
     */
    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    /**
     * Deleting a user in a table.
     */
    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    /**
     * Getting all users from a table.
     */
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Clearing contents of a table.
     */
    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }


}
