package database;

import database.models.BoardingPass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    Database db;
    BoardingPass pass;

    @BeforeEach
    void setUp() {
        db = new Database("jdbc:sqlite:src/test/resources/BoardingPassesTEST.db");
        pass = new BoardingPass(
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
                420.69);

        db.createTable();
    }

    @Test
    void getAllPasses() {
        try {
            db.insertPass(pass);
            assertEquals(1, db.getAllPasses().size(), "Length was not excepted");
        } catch (Exception ignored) {}
    }

    @Test
    void findPassByNumber() {
        assertEquals(pass.getNumber(), db.findPassByNumber(1).getNumber(), "Passes were not the same");
    }

    @Test
    void updatePassByNumber() {
        BoardingPass pass2 = pass;
        pass2.setNumber(2);

        try {
            db.insertPass(pass2);
            db.updatePassByNumber(pass2.getNumber(), pass2);
            BoardingPass pass3 = db.findPassByNumber(pass2.getNumber());

            assertEquals(pass2.getNumber(), pass3.getNumber(), "Passes were not the same");

        } catch (Exception ignored) {}
    }

    @Test
    void updatePassNumberByNumber() {
        db.updatePassNumberByNumber(2, 3);

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals(3, uPass.getNumber(), "Pass number did not change");
    }

    @Test
    void updatePassDateByNumber() {
        db.updatePassDateByNumber(3, "a");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("a", uPass.getDate(), "Pass did not change");
    }

    @Test
    void updatePassOriginByNumber() {
        db.updatePassOriginByNumber(3, "b");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("b", uPass.getOrigin(), "Pass did not change");
    }

    @Test
    void updatePassDestinationByNumber() {
        db.updatePassDestinationByNumber(3, "gr");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("gr", uPass.getDestination(), "Pass did not change");
    }

    @Test
    void updatePassNameByNumber() {
        db.updatePassNameByNumber(3, "Wow");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("Wow", uPass.getName(), "Pass did not change");
    }

    @Test
    void updatePassEmailByNumber() {
        db.updatePassEmailByNumber(3, "gr@gmail.com");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("gr@gmail.com", uPass.getEmail(), "Pass did not change");
    }

    @Test
    void updatePassPhoneByNumber() {
        db.updatePassPhoneByNumber(3, "555");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("555", uPass.getPhoneNumber(), "Pass did not change");
    }

    @Test
    void updatePassGenderByNumber() {
        db.updatePassGenderByNumber(3, "female");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("female", uPass.getGender(), "Pass did not change");
    }

    @Test
    void updatePassETAByNumber() {
        db.updatePassETAByNumber(3, 1.5);

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals(1.5, uPass.getEta(), "Pass did not change");
    }

    @Test
    void updatePassDepartureByNumber() {
        db.updatePassDepartureByNumber(3, "Later");

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals("Later", uPass.getDepartureTime(), "Pass did not change");
    }

    @Test
    void updatePassPriceByNumber() {
        db.updatePassPriceByNumber(3, 0);

        BoardingPass uPass = db.findPassByNumber(3);

        assertEquals(0, uPass.getPrice(), "Pass did not change");
    }
}