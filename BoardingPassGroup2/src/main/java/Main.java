import database.Database;
import database.models.BoardingPass;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String dbPath = Main.class.getResource("BoardingPassesDEV.db").getPath();
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
        System.out.println(db.findPassByNumber(1));

        db.updatePassDestinationByNumber(1, "Florida");
        System.out.println(db.findPassByNumber(1));
    }
}
