package controllers;

import controllers.models.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GUIController {
    JFrame frame;
    JButton etrBtn;
    JLabel[] labels;
    JComboBox[] comboBoxes;
    JTextField[] fields;
    JDialog d;
    JButton[] button = new JButton[49];

    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";




    public GUIController(){
        String departureDate, departureTime, originCode, destinationCode, name, email, phoneNo, gender;
        int age;

        frame  = new JFrame("Customer Details");


        String fileName = "BoardingPassGroup2/src/main/resources/FieldNames";

        GridLayout gl = new GridLayout(0,3);

        frame.setLayout(new GridLayout(0,1));


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

            String[] genderOption = {"Male", "Female", "Transgender", "Non-binary", "Prefer not to respond"};





            //ComboBoxes or TextFields (4,5,6,8)

            for(int i=0;i< labelTitles.size();i++){

                JPanel panel = new JPanel();
                switch (i){
                    case 0:  //departure date
                        labels[i] = new JLabel(labelTitles.get(0));
                        fields[i] = new JTextField(10);
                        JButton b = new JButton("Pick Date");
                        panel.setLayout(gl);
                        panel.add(labels[i]);
                        panel.add(fields[i]);
                        panel.add(b);
                        frame.add(panel);
                        b.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent ae) {
                                fields[0].setText(DatePicker(frame));
                            }
                        });
                        break;
                    case 1: //departure time
                    case 2: //departure city
                    case 3: //destination city
                    case 7: //gender
                        labels[i] = new JLabel(labelTitles.get(i));
                        String[] optionBuf = metaOptions.get(i);
                        comboBoxes[i] =new JComboBox<>(optionBuf);
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

                /*
                if((i==4)||(i==5)||(i==6)||(i==8)) {
                    labels[i] = new JLabel(labelTitles.get(i));
                    fields[i] = new JTextField(16);
                    JPanel panel = new JPanel();
                    panel.setLayout(gl);
                    panel.add(labels[i]);
                    panel.add(fields[i]);
                    frame.add(panel);
                }else{

                     labels[i] = new JLabel(labelTitles.get(i));
                     comboBoxes[i] =new JComboBox<>();


                }
                */
                //   panel.setBounds(50,60*(1+i),300,50);


            }



            frame.add(btnPanel);

            // btnPanel.setBounds(50,550,300,50);
            frame.setSize(400,680);
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

            String[] inputs = new String[labels.length];




            departureDate=fields[0].getText();
            departureTime = comboBoxes[1].getSelectedItem().toString();
            originCode = comboBoxes[2].getSelectedItem().toString();
            destinationCode= comboBoxes[3].getSelectedItem().toString();
            name= fields[4].getText();
            email= fields[5].getText();
            phoneNo= fields[6].getText();
            gender= comboBoxes[7].getSelectedItem().toString();
            age= fields[8].getText();


            //Clayton santizes the input

            if(checkParseInteger(age)){
                Input i =  new Input(departureDate, departureTime, originCode, destinationCode,  name, email,  phoneNo,  gender, age);
                System.out.println(i.toString());

            }




            // Kristian tranforms i to boarding pass and inputs to db



        }
    }

    //Pop Up Date Picker
    public String DatePicker(JFrame parent) {
        d = new JDialog();
        d.setModal(true);
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6)
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        d.dispose();
                    }
                });
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(Color.red);
            }
            p1.add(button[x]);
        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<< Previous");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        p2.add(previous);
        p2.add(l);
        JButton next = new JButton("Next >>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
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

    private boolean checkParseInteger(String current){
        boolean isParsable = false;
        try{
            Integer.parseInt(current);
            isParsable=true;
        }catch (NumberFormatException currentError){
            JOptionPane.showMessageDialog(frame, "Please enter age as a number");
        }


        return isParsable;
    }



    private ArrayList<String[]> getComboBoxOptions(){
        ArrayList<String[]> options = new ArrayList<>();

        for (int i=0;i<comboBoxes.length;i++){
            options.add(null);
        }


        for(int i=0;i<comboBoxes.length;i++){
            String fileName;
            switch (i){


                case 1: //departure times
                    fileName = "BoardingPassGroup2/src/main/resources/departure times";
                    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                       // List<String> genderList = stream.collect(Collectors.toList());
                        String[] buf = stream.toArray(String[]::new);
                        options.set(i,buf);
                    }catch (IOException ioException){
                        System.out.println(ioException.getMessage());
                    }
                    break;
                case 2: //cities
                case 3: //cities
                    fileName = "BoardingPassGroup2/src/main/resources/cities";
                    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                       // List<String> genderList = stream.collect(Collectors.toList());
                        String[] buf = stream.toArray(String[]::new);
                        options.set(i,buf);
                    }catch (IOException ioException){
                        System.out.println(ioException.getMessage());
                    }
                    break;
                case 7:
                    fileName = "BoardingPassGroup2/src/main/resources/genderOptions";
                    try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                        //List<String> genderList = stream.collect(Collectors.toList());
                        String[] buf = stream.toArray(String[]::new);
                        options.set(i,buf);
                    }catch (IOException ioException){
                        System.out.println(ioException.getMessage());
                    }
                    break;
                default:
                    break;

            }


        }




        return options;


    }

}
