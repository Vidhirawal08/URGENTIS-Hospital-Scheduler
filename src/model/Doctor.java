
package model;

public class Doctor {

    private int doctorId;
    private String name;
    private String specialization;
    private boolean available;

    // Constructor
    public Doctor(int doctorId, String name, String specialization, boolean available) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.available = available;
    }

    // Getters

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean isAvailable() {
        return available;
    }

    // Setters

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Display Doctor Information

    @Override
    public String toString() {
        return "Doctor ID: " + doctorId +
                "\nName: " + name +
                "\nSpecialization: " + specialization +
                "\nAvailability: " + (available ? "Available" : "Not Available");
    }
}
