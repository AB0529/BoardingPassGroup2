package database;

import database.models.BoardingPass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
     * @return list of all boarding passes in database
     */
    public List<BoardingPass> getAllPasses() {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM passes");
            ResultSet res = s.executeQuery();
            List<BoardingPass> passes = new ArrayList<>();

            // Did not find
            if (!res.next())
                return passes;

            // Add each pass to passes list
            while (res.next()) {
                passes.add(new BoardingPass(
                                res.getInt("boarding_pass_number"),
                                res.getString("date"),
                                res.getString("origin"),
                                res.getString("destination"),
                                res.getString("name"),
                                res.getString("email"),
                                res.getString("phone_number"),
                                res.getString("gender"),
                                res.getDouble("eta"),
                                res.getString("departure_time"),
                                res.getDouble("price")
                        ));
            }

            return passes;

        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }    }

    /**
     * Gets a boarding pass from an id
     * @param id the id to look for
     * @return the found boarding pass or null
     */
    public BoardingPass findPassByNumber(int id) {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM passes WHERE boarding_pass_number = ?");

            s.setInt(1, id);
            ResultSet res = s.executeQuery();

            // Did not find
            if (!res.next())
                return null;

            return new BoardingPass(
                    res.getInt("boarding_pass_number"),
                    res.getString("date"),
                    res.getString("origin"),
                    res.getString("destination"),
                    res.getString("name"),
                    res.getString("email"),
                    res.getString("phone_number"),
                    res.getString("gender"),
                    res.getDouble("eta"),
                    res.getString("departure_time"),
                    res.getDouble("price"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a pass with a new one
     * @param id the boarding pass number to update
     * @param newPass the new pass which will replace old one
     */
    public void updatePassByNumber(int id, BoardingPass newPass) {
        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement s = conn.prepareStatement("UPDATE passes SET " +
                            "date = ?," +
                            "origin = ?," +
                            "destination = ?," +
                            "name = ?," +
                            "email = ?," +
                            "phone_number = ?," +
                            "gender = ?," +
                            "eta = ?," +
                            "departure_time = ?," +
                            "price = ?" +
                            " WHERE boarding_pass_number = ?");

                s.setString(1, newPass.getDate());
                s.setString(2, newPass.getOrigin());
                s.setString(3, newPass.getDestination());
                s.setString(4, newPass.getName());
                s.setString(5, newPass.getEmail());
                s.setString(6, newPass.getPhoneNumber());
                s.setString(7, newPass.getGender());
                s.setDouble(8, newPass.getEta());
                s.setString(9, newPass.getDepartureTime());
                s.setDouble(10, newPass.getPrice());
                s.setInt(11, id);

                s.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inserts a boarding pass object into the database
     * @param pass the boarding pass to insert
     */
    public void insertPass(BoardingPass pass) throws Exception {
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement s = conn.prepareStatement("INSERT INTO passes(" +
                "boarding_pass_number," +
                "date," +
                "origin," +
                "destination," +
                "name," +
                "email," +
                "phone_number," +
                "gender," +
                "eta," +
                "departure_time," +
                "price) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        s.setInt(1, pass.getNumber());
        s.setString(2, pass.getDate());
        s.setString(3, pass.getOrigin());
        s.setString(4, pass.getDestination());
        s.setString(5, pass.getName());
        s.setString(6, pass.getEmail());
        s.setString(7, pass.getPhoneNumber());
        s.setString(8, pass.getGender());
        s.setDouble(9, pass.getEta());
        s.setString(10, pass.getDepartureTime());
        s.setDouble(11, pass.getPrice());

        s.execute();
    }

    public String getUrl() {
        return url;
    }
}
