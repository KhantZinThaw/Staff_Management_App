package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Database Connector
public class DBConfig {
    // Database url, user and password
    String DB_URL = "jdbc:mysql://localhost:3306/buddies";
    String DB_USER = "root";
    String DB_PASSWORD = "";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
