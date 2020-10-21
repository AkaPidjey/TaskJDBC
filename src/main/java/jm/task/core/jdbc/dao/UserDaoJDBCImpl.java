package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getMySQLConnection;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = getMySQLConnection();

    public UserDaoJDBCImpl() {}

    @Override   // * Creating a user table.
    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLUse.CREATE_USER_TABLE.QUERY);
            System.out.println("База данных [user] создана!");
        } catch (SQLException e) {
            System.out.println("Ошибка создания базы данных [user]");
            e.printStackTrace();
        }
    }

    @Override   // * Deleting a user table.
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLUse.DELETE_USER_TABLE.QUERY);
            System.out.println("База данных [user] удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления базы данных [user]");
            e.printStackTrace();
        }
    }

    @Override   // * Adding a user to a table.
    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLUse.INSERT_USER.QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с именем – " + name + " добавлен в базу данных ");
        } catch (SQLException e) {
            System.out.println("Ошибка сохранения пользователья в базе данных [user]");
            e.printStackTrace();
        }
    }

    @Override   // * Deleting a user in a table.
    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQLUse.DELETE_USER.QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь [id = " + id + "] удален из базы данных [user]!");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя из базы данных [user]");
            e.printStackTrace();
        }
    }

    @Override   // * Getting all users from a table.
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(ResultSet resultSet = connection.createStatement().executeQuery(SQLUse.SELECT_ALL_USERS.QUERY)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastname = resultSet.getString(3);
                byte age = resultSet.getByte(4);

                users.add(new User(id, name, lastname, age));
            }
            System.out.println("Вывод списка всех пользователей базы данных [user]");
        } catch (SQLException e) {
            System.out.println("Ошибка получения списка всех пользователей из базы данных [user]");
            e.printStackTrace();
        }
        return users;
    }

    @Override   // * Clearing contents of a table.
    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQLUse.CLEAR_TABLE.QUERY);
            System.out.println("База данных [user] очищена!");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки базы данных [user]");
            e.printStackTrace();
        }
    }

    enum SQLUse {   // * SQL queries for users table.
        CREATE_USER_TABLE("CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR (30) NOT NULL, " +
                "lastName VARCHAR (30) NOT NULL, " +
                "age int NOT NULL" +
                ") ENGINE=InnoDB;"),
        DELETE_USER_TABLE("DROP TABLE IF EXISTS users"),
        INSERT_USER("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);"),
        DELETE_USER("DELETE FROM users WHERE id = ?"),
        SELECT_ALL_USERS("SELECT * FROM users"),
        CLEAR_TABLE("DELETE FROM users");

        private String QUERY;

        SQLUse(String QUERY) {
            this.QUERY = QUERY;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
