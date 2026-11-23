package dao;

import database.DBConnection;
import model.Allocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Allocation;

public class AllocationDAO {

    public int addAllocation(int patientId, int doctorId, int bedId) {

        String sql = "INSERT INTO allocations (patient_id, doctor_id, bed_id, status) "
                + "VALUES (?, ?, ?, 'ACTIVE')";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                sql, PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setInt(3, bedId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            System.out.println("Error saving allocation: " + e.getMessage());
        }

        return -1;
    }
    public Allocation getActiveAllocationByPatientId(int patientId) {

    String sql = "SELECT * FROM allocations "
            + "WHERE patient_id = ? AND status = 'ACTIVE'";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, patientId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            return new Allocation(
                rs.getInt("allocation_id"),
                rs.getInt("patient_id"),
                rs.getInt("doctor_id"),
                rs.getInt("bed_id"),
                rs.getTimestamp("allocation_time").toLocalDateTime(),
                rs.getTimestamp("discharge_time") == null
                    ? null
                    : rs.getTimestamp("discharge_time").toLocalDateTime(),
                rs.getString("status")
            );
        }

    } catch (Exception e) {
        System.out.println("Error finding allocation: " + e.getMessage());
    }

    return null;
}
public boolean completeAllocation(int allocationId) {

    String sql = "UPDATE allocations "
               + "SET status = 'COMPLETED', discharge_time = CURRENT_TIMESTAMP "
               + "WHERE allocation_id = ? AND status = 'ACTIVE'";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {

        ps.setInt(1, allocationId);

        int rows = ps.executeUpdate();

        return rows > 0;

    } catch (Exception e) {
        System.out.println("Error completing allocation: " + e.getMessage());
    }

    return false;
}
}
