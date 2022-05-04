package controllers;

import controllers.models.BoardingPass;
import controllers.models.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUIController {
    private DatabaseController databaseController;

    JFrame frame;
    JButton etrBtn;
    JLabel[] labels;
    JComboBox[] comboBoxes;
    JTextField[] fields;
    JDialog d;
    JButton[] button = new JButton[49];

    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";


    public GUIController() {
        String fileName = getClass().getClassLoader().getResource("FieldNames").getPath();
        GridLayout gl = new GridLayout(0, 3);

        frame = new JFrame("Customer Details");
        frame.setLayout(new GridLayout(0, 1));


        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            List<String> labelTitles = stream.collect(Collectors.toList());

            labels = new JLabel[labelTitles.size()];
            fields = new JTextField[labelTitles.size()];
            comboBoxes = new JComboBox[labelTitles.size()];

            //Submit Button

            etrBtn = new JButton("submit");
            etrBtn.addActionListener(this::actionPerformed);
            JPanel btnPanel = new JPanel();
            btnPanel.add(etrBtn);


            ArrayList<String[]> metaOptions = getComboBoxOptions();


            //ComboBoxes or TextFields (4,5,6,8)

            for (int i = 0; i < labelTitles.size(); i++) {

                JPanel panel = new JPanel();
                switch (i) {
                    case 0:  //departure date
                        labels[i] = new JLabel(labelTitles.get(0));
                        fields[i] = new JTextField(10);
                        JButton b = new JButton("Pick Date");
                        panel.setLayout(gl);
                        panel.add(labels[i]);
                        panel.add(fields[i]);
                        panel.add(b);
                        frame.add(panel);
                        b.addActionListener(ae -> fields[0].setText(DatePicker(frame)));
                        break;
                    case 1: //departure time
                    case 2: //departure city
                    case 3: //destination city
                    case 7: //gender
                        labels[i] = new JLabel(labelTitles.get(i));
                        String[] optionBuf = metaOptions.get(i);
                        comboBoxes[i] = new JComboBox<>(optionBuf);
                        panel.setLayout(gl);
                        panel.add(labels[i]);
                        panel.add(comboBoxes[i]);
                        frame.add(panel);
                        break;
                    case 4:  //name
                    case 5: //email
                    case 6: //phone number
                    case 8: //age
                        labels[i] = new JLabel(labelTitles.get(i));
                        fields[i] = new JTextField(16);
                        panel.setLayout(gl);
                        panel.add(labels[i]);
                        panel.add(fields[i]);
                        frame.add(panel);
                        break;
                }
            }

            frame.add(btnPanel);

            // btnPanel.setBounds(50,550,300,50);
            frame.setSize(400, 680);
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Submit Button Pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("submit")) {
            String departureDate, departureTime, originCode, destinationCode, name, email, phoneNo, gender, age;

            departureDate = fields[0].getText();
            departureTime = comboBoxes[1].getSelectedItem().toString();
            originCode = comboBoxes[2].getSelectedItem().toString();
            destinationCode = comboBoxes[3].getSelectedItem().toString();
            name = fields[4].getText();
            email = fields[5].getText();
            phoneNo = fields[6].getText();
            gender = comboBoxes[7].getSelectedItem().toString();
            age = fields[8].getText();

            Input input = new Input(departureDate, departureTime, originCode, destinationCode, name, email, phoneNo, gender, age);

            //Clayton santizes the input

            if (checkParseInteger(input.getAge())) {
                System.out.println(input);

                BoardingPass pass = new BoardingPass();
                List<BoardingPass> allPasses = databaseController.getAllPasses();

                // Kristian transforms i to boarding pass and inputs to db
                // Put logic here!

                int passETA = 0;
                // Put logic here! This must be unique!!

                int passNumber = 0;
                // Put logic here!
                double price = 0;
                int ageInt = Integer.valueOf(input.getAge());
                if(gender=="female"){
                    price= price*.75;
                }
                if(ageInt <= 12){
                    price = price*.5;
                }
                else if (ageInt >=60){
                    price  = price * .40;
                }



                pass.setNumber(passNumber);
                pass.setDate(departureDate);
                pass.setDestination(destinationCode);
                pass.setName(name);
                pass.setEmail(email);
                pass.setPhoneNumber(phoneNo);
                pass.setDepartureTime(departureTime);
                pass.setGender(gender);
                pass.setEta(passETA);
                pass.setPrice(price);
                pass.setOrigin(originCode);

                try {
                    databaseController.insertPass(pass);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                // Make user friendly format
                String friendlyFormatPath = getClass().getClassLoader().getResource("passes").getPath();

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(friendlyFormatPath + pass.getNumber() + ".md"));
                    writer.write(
                            String.format("# Boarding Pass `%d`" + "Name: %s\nEmail: %s\nPhone: %s\nPrice Payed: %f\n---\n# Flight Details\n`%s -> %s` (%f hours)\nDeparture Date: %s\nDeparture Time: %s",
                                    pass.getNumber(),
                                    pass.getName(),
                                    pass.getEmail(),
                                    pass.getPhoneNumber(),
                                    pass.getPrice(),
                                    pass.getOrigin(),
                                    pass.getDestination(),
                                    pass.getEta(),
                                    pass.getDate(),
                                    pass.getDepartureTime()));

                    System.out.println("File " + friendlyFormatPath + pass.getNumber() + ".md" + " wrote!");
                } catch (IOException ex) {
                    System.out.println("Error while writing to file: " + ex);
                }

            }
        }
    }

    //Pop Up Date Picker
    public String DatePicker(JFrame parent) {
        d = new JDialog();
        d.setModal(true);
        String[] header = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6)
                button[x].addActionListener(ae -> {
                    day = button[selection].getActionCommand();
                    d.dispose();
                });
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.red);
            }
            p1.add(button[x]);
        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<< Previous");
        previous.addActionListener(ae -> {
            month--;
            displayDate();
        });
        p2.add(previous);
        p2.add(l);
        JButton next = new JButton("Next >>");
        next.addActionListener(ae -> {
            month++;
            displayDate();
        });
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate();
        d.setVisible(true);
        return setPickedDate();
    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            button[x].setText("" + day);

        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Date Picker");

    }

    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }

    private boolean checkParseInteger(String current) {
        boolean isParsable = false;
        try {
            Integer.parseInt(current);
            isParsable = true;
        } catch (NumberFormatException currentError) {
            JOptionPane.showMessageDialog(frame, "Please enter age as a number");
        }


        return isParsable;
    }


    private ArrayList<String[]> getComboBoxOptions() {
        ArrayList<String[]> options = new ArrayList<>();

        for (int i = 0; i < comboBoxes.length; i++) {
            options.add(null);
        }


        for (int i = 0; i < comboBoxes.length; i++) {
            String fileName;
            switch (i) {


                case 1: //departureTimes
                    fileName = getClass().getClassLoader().getResource("departureTimes").getPath();
                    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                        // List<String> genderList = stream.collect(Collectors.toList());
                        String[] buf = stream.toArray(String[]::new);
                        options.set(i, buf);
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                    break;
                case 2: //cities
                case 3: //cities
                    fileName = getClass().getClassLoader().getResource("cities").getPath();
                    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                        // List<String> genderList = stream.collect(Collectors.toList());
                        String[] buf = stream.toArray(String[]::new);
                        options.set(i, buf);
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                    break;
                case 7:
                    fileName = getClass().getClassLoader().getResource("genderOptions").getPath();
                    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                        //List<String> genderList = stream.collect(Collectors.toList());
                        String[] buf = stream.toArray(String[]::new);
                        options.set(i, buf);
                    } catch (IOException ioException) {
                        System.out.println(ioException.getMessage());
                    }
                    break;
                default:
                    break;

            }


        }


        return options;


    }

    public void setDatabaseController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }
}
