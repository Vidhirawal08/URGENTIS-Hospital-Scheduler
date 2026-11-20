package model;
//vidhi(2510990452)
import java.time.LocalDateTime;

public class Patient {

    private int patientId;
    private String name;
    private int age;
    private String gender;
    private Severity severity;
    private String status;
    private LocalDateTime arrivalTime;

    // Constructor
    public Patient(int patientId, String name, int age,
                String gender, Severity severity) {

        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.severity = severity;
        this.status = "WAITING";
        this.arrivalTime = LocalDateTime.now();
    }

    // Getters

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    public void setPatientId(int patientId) {
    this.patientId = patientId;
}

public void setArrivalTime(LocalDateTime arrivalTime) {
    this.arrivalTime = arrivalTime;
}

    // Setter

    public void setStatus(String status) {
        this.status = status;
    }

    // Display patient information

    @Override
    public String toString() {

        return "Patient ID: " + patientId +
            "\nName: " + name +
            "\nAge: " + age +
            "\nGender: " + gender +
            "\nSeverity: " + severity +
            "\nStatus: " + status +
            "\nArrival Time: " + arrivalTime;
    }
}
//vidhi(2510990452)