import database.Database;

public class Main {
    public static void main(String[] args) {
        String dbPath = Main.class.getResource("BoardingPassesDEV.db").getPath();
        Database db = new Database("jdbc:sqlite:" + dbPath);

        // Add some code here!
    }
}
