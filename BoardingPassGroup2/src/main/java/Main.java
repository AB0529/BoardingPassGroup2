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
        GUIController guiController = new GUIController();

        // Grab the user input
        // TODO: fix this and make it work
        Input input = guiController.getUserInput();

        System.out.println(input);

        if (input != null)
            System.out.println(input);
    }
}
