package discharge;

import allocation.AllocationService;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DischargeService {

    private final AllocationService allocationService;

    // Constructor
    public DischargeService(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    public boolean dischargePatient(int patientId) {

        String findAllocation =
                "SELECT allocation_id, doctor_id, bed_id FROM allocations "
              + "WHERE patient_id = ? AND status = 'ACTIVE'";

        String completeAllocation =
                "UPDATE allocations SET status = 'COMPLETED', "
              + "discharge_time = CURRENT_TIMESTAMP "
              + "WHERE allocation_id = ?";

        String updatePatient =
                "UPDATE patients SET status = 'DISCHARGED' "
              + "WHERE patient_id = ?";

        String updateBed =
                "UPDATE beds SET status = 'AVAILABLE' "
              + "WHERE bed_id = ?";

        String updateDoctor =
                "UPDATE doctors SET availability = TRUE "
              + "WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            // Start transaction
            con.setAutoCommit(false);

            try {

                int allocationId;
                int doctorId;
                int bedId;

                // 1. Find active allocation
                try (PreparedStatement ps =
                        con.prepareStatement(findAllocation)) {

                    ps.setInt(1, patientId);

                    ResultSet rs = ps.executeQuery();

                    if (!rs.next()) {
                        System.out.println("No active allocation found!");
                        con.rollback();
                        return false;
                    }

                    allocationId = rs.getInt("allocation_id");
                    doctorId = rs.getInt("doctor_id");
                    bedId = rs.getInt("bed_id");
                }

                // 2. Complete allocation
                try (PreparedStatement ps =
                        con.prepareStatement(completeAllocation)) {

                    ps.setInt(1, allocationId);

                    if (ps.executeUpdate() == 0) {
                        throw new Exception("Allocation update failed!");
                    }
                }

                // 3. Update patient status
                try (PreparedStatement ps =
                        con.prepareStatement(updatePatient)) {

                    ps.setInt(1, patientId);

                    if (ps.executeUpdate() == 0) {
                        throw new Exception("Patient status update failed!");
                    }
                }

                // 4. Make bed available
                try (PreparedStatement ps =
                        con.prepareStatement(updateBed)) {

                    ps.setInt(1, bedId);

                    if (ps.executeUpdate() == 0) {
                        throw new Exception("Bed update failed!");
                    }
                }

                // 5. Make doctor available
                try (PreparedStatement ps =
                        con.prepareStatement(updateDoctor)) {

                    ps.setInt(1, doctorId);

                    if (ps.executeUpdate() == 0) {
                        throw new Exception("Doctor update failed!");
                    }
                }

                // 6. Save all database changes
                con.commit();

                System.out.println("\nPatient discharged successfully!");
                System.out.println("Patient ID: " + patientId);
                System.out.println("Bed ID: " + bedId + " is now AVAILABLE");
                System.out.println("Doctor ID: " + doctorId + " is now AVAILABLE");

                // 7. Notify waiting allocation worker
                allocationService.notifyBedAvailable();

                System.out.println("Waiting queue notified!");

                return true;

            } catch (Exception e) {

                // Undo all changes if something fails
                con.rollback();

                System.out.println("Discharge failed: " + e.getMessage());

                return false;
            }

        } catch (Exception e) {

            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
}