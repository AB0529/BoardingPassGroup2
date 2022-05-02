import controllers.DatabaseController;
public class Main {
    public static void main(String[] args) {
        String dbPath = Main.class.getResource("BoardingPassesDEV.db").getPath();
        DatabaseController db = new DatabaseController("jdbc:sqlite:" + dbPath);

        db.createTable();

        // Add some code here!
    }
}
