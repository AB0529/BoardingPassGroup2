package database;

import database.models.BoardingPass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

// TODO: Implement adding data
public class Database {
    /*
    * Handles database connection and queries
    * */
    private String url;

    public Database(String url) {
        this.url = url;

        try {
            Connection connection = DriverManager.getConnection(url);

            System.out.println("Connected to driver: " + connection.getMetaData().getDriverName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Will create the table if it doesn't exist in the database
     */
    public void createTable() {
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS `passes` (\n" +
                    "\t`boarding_pass_number`INT NOT NULL,\n" +
                    "\t`date` VARCHAR(255) NOT NULL,\n" +
                    "\t`origin` VARCHAR(255) NOT NULL,\n" +
                    "\t`destination` VARCHAR(255) NOT NULL,\n" +
                    "\t`name` VARCHAR(255) NOT NULL,\n" +
                    "\t`email` VARCHAR(255),\n" +
                    "\t`phone_number` VARCHAR(255),\n" +
                    "\t`gender` VARCHAR(255),\n" +
                    "\t`eta` INT NOT NULL,\n" +
                    "\t`departure_time` VARCHAR(255),\n" +
                    "\t`price` DOUBLE NOT NULL,\n" +
                    "\tPRIMARY KEY (`boarding_pass_number`)\n" +
                    ");");
            stmt.close();

            System.out.println("Table created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a list of all boarding passes in the database
     * TODO: implement this
     * @return list of all boarding passes in database
     */
    public List<BoardingPass> getAllPasses() {
        return null;
    }

    /**
     * Gets a boarding pass from an id
     * TODO: implement this
     * @param id the id to look for
     * @return the found boarding pass or null
     */
    public BoardingPass findByID(int id) {
        return null;
    }

    public String getUrl() {
        return url;
    }
}
