package patient;

import model.Patient;
import model.Severity;
import dao.PatientDAO;

import java.util.ArrayList;
//vidhi(2510990452)
public class PatientService {

    private final PatientDAO patientDAO = new PatientDAO();

    // Register Patient
    public Patient registerPatient(String name, int age,
                                String gender, Severity severity) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be empty");
        }

        if (age <= 0 || age > 120) {
            throw new IllegalArgumentException("Invalid patient age");
        }

        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be empty");
        }

        if (severity == null) {
            throw new IllegalArgumentException("Severity is required");
        }

        Patient patient = new Patient(0, name, age, gender, severity);

        int generatedId = patientDAO.addPatient(patient);

        if (generatedId != -1) {

            patient.setPatientId(generatedId);

            System.out.println("Patient registered successfully!");

            return patient;
}

System.out.println("Patient registration failed!");
return null;
    }

    // View All Patients
    public ArrayList<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    // Search Patient
    public Patient getPatientById(int patientId) {

        if (patientId <= 0) {
            throw new IllegalArgumentException("Invalid Patient ID");
        }

        return patientDAO.getPatientById(patientId);
    }

public boolean updatePatientStatus(int patientId, String status) {

    if (patientId <= 0) {
        throw new IllegalArgumentException("Invalid Patient ID");
    }

    if (status == null || status.trim().isEmpty()) {
        throw new IllegalArgumentException("Status cannot be empty");
    }

    status = status.trim().toUpperCase();

    if (!status.equals("WAITING") &&
        !status.equals("ALLOCATED") &&
        !status.equals("DISCHARGED")) {

        throw new IllegalArgumentException("Invalid Patient Status!");
    }

    return patientDAO.updatePatientStatus(patientId, status);
}
    // Delete Patient
public boolean deletePatient(int patientId) {

    if (patientId <= 0) {
        throw new IllegalArgumentException("Invalid Patient ID");
    }

    return patientDAO.deletePatient(patientId);
}
//vidhi(2510990452)
}