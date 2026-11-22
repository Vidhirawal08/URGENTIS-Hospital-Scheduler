
package dao;

import database.DBConnection;
import model.Doctor;

import java.sql.*;
import java.util.ArrayList;

public class DoctorDAO {

    // 1. Add Doctor
    public int addDoctor(Doctor doctor) {

        String query = "INSERT INTO doctors (name, specialization, availability) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return -1;
            }

            try (PreparedStatement ps = con.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, doctor.getName());
                ps.setString(2, doctor.getSpecialization());
                ps.setBoolean(3, doctor.isAvailable());

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // 2. View All Doctors
    public ArrayList<Doctor> getAllDoctors() {

        ArrayList<Doctor> doctors = new ArrayList<>();

        String query = "SELECT * FROM doctors";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return doctors;
            }

            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Doctor doctor = new Doctor(
                            rs.getInt("doctor_id"),
                            rs.getString("name"),
                            rs.getString("specialization"),
                            rs.getBoolean("availability")
                    );

                    doctors.add(doctor);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    // 3. Search Doctor By ID
    public Doctor getDoctorById(int doctorId) {

        String query = "SELECT * FROM doctors WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, doctorId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        return new Doctor(
                                rs.getInt("doctor_id"),
                                rs.getString("name"),
                                rs.getString("specialization"),
                                rs.getBoolean("availability")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 4. Update Doctor Availability
    public boolean updateDoctorAvailability(int doctorId, boolean available) {

        String query = "UPDATE doctors SET availability = ? WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setBoolean(1, available);
                ps.setInt(2, doctorId);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Update Doctor Details
    public boolean updateDoctor(Doctor doctor) {

        String query = "UPDATE doctors SET name = ?, specialization = ?, availability = ? WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, doctor.getName());
                ps.setString(2, doctor.getSpecialization());
                ps.setBoolean(3, doctor.isAvailable());
                ps.setInt(4, doctor.getDoctorId());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Delete Doctor
    public boolean deleteDoctor(int doctorId) {

        String query = "DELETE FROM doctors WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, doctorId);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
