package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    private static final String URL =
            "jdbc:mysql://localhost:3306/mydbtest?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static Connection connection;

    public static void main(String[] args) {
        getMySQLConnection();
    }

   public static Connection getMySQLConnection() {
       try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           System.out.println("БД успешно подключена");
       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       }
        return connection;

   }

}




