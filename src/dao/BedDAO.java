
package dao;

import database.DBConnection;
import model.Bed;

import java.sql.*;
import java.util.ArrayList;

public class BedDAO {

    // 1. Add Bed
    public int addBed(Bed bed) {

        String query = "INSERT INTO beds (bed_type, status) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return -1;
            }

            try (PreparedStatement ps = con.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, bed.getBedType());
                ps.setString(2, bed.getStatus());

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

    // 2. View All Beds
    public ArrayList<Bed> getAllBeds() {

        ArrayList<Bed> beds = new ArrayList<>();

        String query = "SELECT * FROM beds";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return beds;
            }

            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Bed bed = new Bed(
                            rs.getInt("bed_id"),
                            rs.getString("bed_type"),
                            rs.getString("status")
                    );

                    beds.add(bed);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beds;
    }

    // 3. Search Bed By ID
    public Bed getBedById(int bedId) {

        String query = "SELECT * FROM beds WHERE bed_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, bedId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        return new Bed(
                                rs.getInt("bed_id"),
                                rs.getString("bed_type"),
                                rs.getString("status")
                        );
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // 4. View Available Beds
    public ArrayList<Bed> getAvailableBeds() {

        ArrayList<Bed> beds = new ArrayList<>();

        String query = "SELECT * FROM beds WHERE status = 'AVAILABLE'";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return beds;
            }

            try (PreparedStatement ps = con.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Bed bed = new Bed(
                            rs.getInt("bed_id"),
                            rs.getString("bed_type"),
                            rs.getString("status")
                    );

                    beds.add(bed);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return beds;
    }

    // 5. Update Bed Status
    public boolean updateBedStatus(int bedId, String status) {

        if (!"AVAILABLE".equals(status) && !"OCCUPIED".equals(status)) {
            System.out.println("Invalid bed status!");
            return false;
        }

        String query = "UPDATE beds SET status = ? WHERE bed_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, status);
                ps.setInt(2, bedId);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. Delete Bed
    public boolean deleteBed(int bedId) {

        String query = "DELETE FROM beds WHERE bed_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                System.out.println("Database connection failed!");
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, bedId);

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
