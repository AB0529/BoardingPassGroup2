import database.Database;
import database.models.BoardingPass;

public class Main {
    public static void main(String[] args) {
        String dbPath = Main.class.getResource("BoardingPasses.db").getPath();
        Database db = new Database("jdbc:sqlite:" + dbPath);

        db.createTable();
        BoardingPass pass = db.findPassByNumber(1);

        System.out.println(pass);

        System.out.println("Hello");
    }
}
