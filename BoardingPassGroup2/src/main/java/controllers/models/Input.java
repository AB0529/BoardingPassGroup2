package controllers.models;

public class Input {
    private int boardingPassNo;
    private final String departureDate;
    private final String departureTime;
    private final String originCode;
    private final String destinationCode;
    private Long etaHr, ticketPrice;
    private final String name;
    private final String email;
    private final String phoneNo;
    private final String Gender;
    private final String age;


    public Input(String departureDate, String departureTime, String originCode, String destinationCode, String name, String email, String phoneNo, String gender, String age) {
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.age = age;
        Gender = gender;
    }

    private void setBoardingPassNo(int boardingPassNo) {
        this.boardingPassNo = boardingPassNo;
    }

    private void setEtaHr(Long etaHr) {
        this.etaHr = etaHr;
    }

    private void setTicketPrice(Long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getAge() {
        return age;
    }

    public int getBoardingPassNo() {
        return boardingPassNo;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getOriginCode() {
        return originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public Long getEtaHr() {
        return etaHr;
    }

    public Long getTicketPrice() {
        return ticketPrice;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return Gender;
    }


    @Override
    public String toString() {
        return "controllers.models.Input{" +
                "boardingPassNo=" + boardingPassNo +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", originCode='" + originCode + '\'' +
                ", destinationCode='" + destinationCode + '\'' +
                ", etaHr=" + etaHr +
                ", ticketPrice=" + ticketPrice +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", Gender='" + Gender + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
