package database.models;

public class BoardingPass {
    private int number;
    private String date;
    private String origin;
    private String destination;
    private String name;
    private String email;
    private String phoneNumber;
    private String gender;
    private double eta;
    private String departureTime;
    private double price;

    public BoardingPass() {}

    public BoardingPass(int number, String date, String origin, String destination, String name, String email, String phoneNumber, String gender, double eta, String departureTime, double price) {
        this.number = number;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.eta = eta;
        this.departureTime = departureTime;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getEta() {
        return eta;
    }

    public double getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
