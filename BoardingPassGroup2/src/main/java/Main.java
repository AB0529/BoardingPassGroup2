import database.Database;

public class Main {
    public static void main(String[] args) {
        Database db = new Database("jdbc:sqlite:src/main/resources/BoardingPasses.db");

        System.out.println("Hello");
    }
}
