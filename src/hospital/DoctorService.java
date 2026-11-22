
package hospital;

import dao.DoctorDAO;
import model.Doctor;

import java.util.ArrayList;

public class DoctorService {

    private DoctorDAO doctorDAO = new DoctorDAO();

    // 1. Register Doctor
    public int registerDoctor(String name, String specialization) {

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Doctor name cannot be empty!");
            return -1;
        }

        if (specialization == null || specialization.trim().isEmpty()) {
            System.out.println("Specialization cannot be empty!");
            return -1;
        }

        Doctor doctor = new Doctor(
                0,
                name.trim(),
                specialization.trim(),
                true
        );

        return doctorDAO.addDoctor(doctor);
    }

    // 2. View All Doctors
    public ArrayList<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }

    // 3. Search Doctor By ID
    public Doctor getDoctorById(int doctorId) {

        if (doctorId <= 0) {
            System.out.println("Invalid Doctor ID!");
            return null;
        }

        return doctorDAO.getDoctorById(doctorId);
    }

    // 4. Check Doctor Availability
    public boolean isDoctorAvailable(int doctorId) {

        Doctor doctor = doctorDAO.getDoctorById(doctorId);

        return doctor != null && doctor.isAvailable();
    }

    // 5. Update Doctor Availability
    public boolean updateDoctorAvailability(int doctorId, boolean available) {

        if (doctorId <= 0) {
            System.out.println("Invalid Doctor ID!");
            return false;
        }

        return doctorDAO.updateDoctorAvailability(doctorId, available);
    }

    // 6. Update Doctor Details
    public boolean updateDoctor(Doctor doctor) {

        if (doctor == null || doctor.getDoctorId() <= 0) {
            System.out.println("Invalid doctor details!");
            return false;
        }

        if (doctor.getName() == null || doctor.getName().trim().isEmpty()
                || doctor.getSpecialization() == null
                || doctor.getSpecialization().trim().isEmpty()) {
            System.out.println("Name and specialization cannot be empty!");
            return false;
        }

        return doctorDAO.updateDoctor(doctor);
    }

    // 7. Delete Doctor
    public boolean deleteDoctor(int doctorId) {

        if (doctorId <= 0) {
            System.out.println("Invalid Doctor ID!");
            return false;
        }

        return doctorDAO.deleteDoctor(doctorId);
    }
}

