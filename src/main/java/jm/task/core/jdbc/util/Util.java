package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;


public class Util {
    // реализуйте настройку соеденения с БД

    private static final String USERNAME = "root";
    private static final String PASSWORD = "12019050";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?useSSL=false";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }


    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                Configuration configuration = new Configuration();
                Properties prop = new Properties();
                prop.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                prop.put(Environment.URL, "jdbc:mysql://localhost:3306/hibernate?useSSL=false");
                prop.put(Environment.USER, "root");
                prop.put(Environment.PASS, "12019050");
                prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                prop.put(Environment.SHOW_SQL, "true");
                prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                prop.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(prop);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
