package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {

    private static final String URL =
            "jdbc:mysql://localhost:3306/mydbtest?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        getSessionFactory();
    }

    /// Connection with JDBC
   public static Connection getMySQLConnection() {
       try {
           Class.forName(DRIVER);
           connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           System.out.println("БД успешно подключена");
       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       }
        return connection;
   }

   ////// Connection with Hibernate
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect");
                properties.setProperty("hibernate.connection.driver_class", DRIVER);
                properties.setProperty("hibernate.connection.url",URL);
                properties.setProperty("hibernate.connection.username",USERNAME);
                properties.setProperty("hibernate.connection.password", PASSWORD);
                properties.setProperty("show_sql", "true");
                properties.setProperty("hibernate.format_sql", "true");
                properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

                sessionFactory = new Configuration()
                        .addPackage("jm.task.core.jdbc.model.User")
                        .addProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();

                System.out.println("БД успешно подключена");
                return sessionFactory;

            } catch (HibernateException he) {
                System.out.println("Ошибка подключения БД");
                he.getStackTrace();
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getConnection() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url",URL)
                    .setProperty("hibernate.connection.username",USERNAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("show_sql", "true")
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("БД успешно подключена");
        } catch (Throwable e) {
            System.out.println("Ошибка подключения БД");
            e.printStackTrace();
        }
        return sessionFactory;
    }
}




