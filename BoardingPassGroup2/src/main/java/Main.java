import database.Database;
import database.models.BoardingPass;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String dbPath = Main.class.getResource("BoardingPassesPROD.db").getPath();
        Database db = new Database("jdbc:sqlite:" + dbPath);

        db.createTable();
        try {
            db.insertPass(new BoardingPass(
                    1,
                    "May 19th",
                    "Texas",
                    "Ohio",
                    "AMOGUS",
                    "amogus@sus.com",
                    "1234",
                    "male",
                    2.5,
                    "NOW!!",
                    420.69));
        } catch (Exception e) {
            System.out.println("Cannot add duplicate pass numbers");
        }

        BoardingPass pass = db.findPassByNumber(1);

        System.out.println(pass);

        db.updatePassByNumber(1, new BoardingPass(
                1,
                "March 20th",
                "Texas",
                "Ohio",
                "AMOGUS",
                "amogus@sus.com",
                "1234",
                "male",
                2.5,
                "NOW!!",
                420.69));

        pass = db.findPassByNumber(1);
        System.out.println(pass);

        System.out.println("Hello");
    }
}
