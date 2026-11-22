
package model;

public class Bed {

    private int bedId;
    private String bedType;
    private String status;

    // Constructor
    public Bed(int bedId, String bedType, String status) {
        this.bedId = bedId;
        this.bedType = bedType;
        this.status = status;
    }

    // Getters

    public int getBedId() {
        return bedId;
    }

    public String getBedType() {
        return bedType;
    }

    public String getStatus() {
        return status;
    }

    // Setters

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public void setStatus(String status) {
        if (status.equals("AVAILABLE") || status.equals("OCCUPIED")) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid bed status");
        }
    }

    // Display Bed Information

    @Override
    public String toString() {
        return "Bed ID: " + bedId +
                "\nBed Type: " + bedType +
                "\nStatus: " + status;
    }
}

