package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Aleksey", "Alekseev", (byte)20);
        userService.saveUser("Vasiliy", "Vasin", (byte)25);
        userService.saveUser("Nikolay", "Nikolaev", (byte)30);
        userService.saveUser("Egor", "Egorov", (byte)35);

        userService.removeUserById(2);

        List<User> users = userService.getAllUsers();
        for (User element : users) {
            System.out.println(element.toString());
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
