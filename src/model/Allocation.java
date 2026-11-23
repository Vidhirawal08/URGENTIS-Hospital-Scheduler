package model;

import java.time.LocalDateTime;

public class Allocation {

    private int allocationId;
    private int patientId;
    private int doctorId;
    private int bedId;
    private LocalDateTime allocationTime;
    private LocalDateTime dischargeTime;
    private String status;

    public Allocation(int allocationId, int patientId,
                    int doctorId, int bedId,
                    LocalDateTime allocationTime,
                    LocalDateTime dischargeTime,
                    String status) {

        this.allocationId = allocationId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.bedId = bedId;
        this.allocationTime = allocationTime;
        this.dischargeTime = dischargeTime;
        this.status = status;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getBedId() {
        return bedId;
    }

    public LocalDateTime getAllocationTime() {
        return allocationTime;
    }

    public LocalDateTime getDischargeTime() {
        return dischargeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDischargeTime(LocalDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
    }
}
