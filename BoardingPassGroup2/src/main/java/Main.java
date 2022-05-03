import controllers.DatabaseController;
import controllers.GUIController;
import controllers.models.Input;

public class Main {
    public static void main(String[] args) {
        // Initialize database
        String dbPath = Main.class.getResource("BoardingPassesDEV.db").getPath();
        DatabaseController db = new DatabaseController("jdbc:sqlite:" + dbPath);

        db.createTable();

        // Initialize GUI
        // TODO: Move everything inside here
        new GUIController();
    }
}
