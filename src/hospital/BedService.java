
package hospital;

import dao.BedDAO;
import model.Bed;

import java.util.ArrayList;

public class BedService {

    private BedDAO bedDAO = new BedDAO();

    // 1. Register Bed
    public int registerBed(String bedType) {

        if (bedType == null || bedType.trim().isEmpty()) {
            System.out.println("Bed type cannot be empty!");
            return -1;
        }

        Bed bed = new Bed(
                0,
                bedType.trim(),
                "AVAILABLE"
        );

        return bedDAO.addBed(bed);
    }

    // 2. View All Beds
    public ArrayList<Bed> getAllBeds() {
        return bedDAO.getAllBeds();
    }

    // 3. Search Bed By ID
    public Bed getBedById(int bedId) {

        if (bedId <= 0) {
            System.out.println("Invalid Bed ID!");
            return null;
        }

        return bedDAO.getBedById(bedId);
    }

    // 4. View Available Beds
    public ArrayList<Bed> getAvailableBeds() {
        return bedDAO.getAvailableBeds();
    }

    // 5. Update Bed Status
    public boolean updateBedStatus(int bedId, String status) {

        if (bedId <= 0) {
            System.out.println("Invalid Bed ID!");
            return false;
        }

        if (!"AVAILABLE".equals(status) && !"OCCUPIED".equals(status)) {
            System.out.println("Invalid bed status!");
            return false;
        }

        return bedDAO.updateBedStatus(bedId, status);
    }

    // 6. Delete Bed
    public boolean deleteBed(int bedId) {

        if (bedId <= 0) {
            System.out.println("Invalid Bed ID!");
            return false;
        }

        return bedDAO.deleteBed(bedId);
    }
}

