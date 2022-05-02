import database.Database;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String dbPath = Main.class.getResource("BoardingPasses.db").getPath();
        Database db = new Database("jdbc:sqlite:" + dbPath);

        db.createTable();

        System.out.println("Hello");
    }
}
