package dao;

import database.DBConnection;
import model.Patient;
import model.Severity;

import java.sql.*;
import java.util.ArrayList;
//vidhi(2510990452)
public class PatientDAO {

    // 1. Add Patient
    public int addPatient(Patient patient) {

    String query = "INSERT INTO patients (name, age, gender, severity, status, arrival_time) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            System.out.println("Database connection failed!");
            return -1;
        }

        PreparedStatement ps = con.prepareStatement(
            query, Statement.RETURN_GENERATED_KEYS
        );

        ps.setString(1, patient.getName());
        ps.setInt(2, patient.getAge());
        ps.setString(3, patient.getGender());
        ps.setString(4, patient.getSeverity().name());
        ps.setString(5, patient.getStatus());
        ps.setTimestamp(6, Timestamp.valueOf(patient.getArrivalTime()));

        int rows = ps.executeUpdate();

        if (rows > 0) {
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return -1;
}

    // 2. View All Patients
    public ArrayList<Patient> getAllPatients() {

        ArrayList<Patient> patients = new ArrayList<>();

        String query = "SELECT * FROM patients";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Patient patient = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        Severity.valueOf(rs.getString("severity"))
                );
                Timestamp timestamp = rs.getTimestamp("arrival_time");

if (timestamp != null) {
    patient.setArrivalTime(timestamp.toLocalDateTime());
}

                patient.setStatus(rs.getString("status"));
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    // 3. Search Patient By ID
    public Patient getPatientById(int patientId) {

        String query = "SELECT * FROM patients WHERE patient_id = ?";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Patient patient = new Patient(
                            rs.getInt("patient_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            Severity.valueOf(rs.getString("severity"))
                    );
                    Timestamp timestamp = rs.getTimestamp("arrival_time");

if (timestamp != null) {
    patient.setArrivalTime(timestamp.toLocalDateTime());
}

                    patient.setStatus(rs.getString("status"));
                    return patient;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 4. Update Patient Status
    public boolean updatePatientStatus(int patientId, String status) {

        String query = "UPDATE patients SET status = ? WHERE patient_id = ?";

        try (Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, patientId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 5. Delete Patient
    // 5. Delete Patient
public boolean deletePatient(int patientId) {

    String query = "DELETE FROM patients WHERE patient_id = ?";

    try (Connection con = DBConnection.getConnection()) {

        if (con == null) {
            System.out.println("Database connection failed!");
            return false;
        }

        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, patientId);

            return ps.executeUpdate() > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
//vidhi(2510990452)
