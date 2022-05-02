package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    /*
    * Handles database connection and queries
    * */
    private Connection connection = null;
    private String url;

    public Database(String url) {
        this.url = url;

        try {
            connection = DriverManager.getConnection(url);

            System.out.println("Connected to driver: " + connection.getMetaData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() {
        return connection;
    }
}
