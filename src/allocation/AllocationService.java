package allocation;

import dao.AllocationDAO;
import hospital.BedService;
import hospital.DoctorService;
import model.Bed;
import model.Doctor;
import model.Patient;
import patient.PatientService;

public class AllocationService {

    private final WaitingQueue waitingQueue = new WaitingQueue();

    private final BedService bedService = new BedService();

    private final DoctorService doctorService = new DoctorService();

    private final PatientService patientService = new PatientService();

    private final AllocationDAO allocationDAO = new AllocationDAO();


    // 1. Add Patient to Waiting Queue
    public void addPatient(Patient patient) {
        waitingQueue.addPatient(patient);
    }


    // 2. Allocate Patient
    public synchronized boolean allocatePatient(Patient patient) {

        // Find available beds
        if (bedService.getAvailableBeds().isEmpty()) {
            System.out.println("No bed available for " + patient.getName());
            return false;
        }

        // Find available doctor
        Doctor availableDoctor = null;

        for (Doctor doctor : doctorService.getAllDoctors()) {

            if (doctor.isAvailable()) {
                availableDoctor = doctor;
                break;
            }
        }

        if (availableDoctor == null) {
            System.out.println("No doctor available for " + patient.getName());
            return false;
        }

        // Select first available bed
        Bed bed = bedService.getAvailableBeds().get(0);

        int bedId = bed.getBedId();
        int doctorId = availableDoctor.getDoctorId();
        int patientId = patient.getPatientId();

        // 1. Occupy bed
        boolean bedUpdated =
                bedService.updateBedStatus(bedId, "OCCUPIED");

        if (!bedUpdated) {
            return false;
        }

        // 2. Mark doctor unavailable
        boolean doctorUpdated =
                doctorService.updateDoctorAvailability(doctorId, false);

        if (!doctorUpdated) {

            bedService.updateBedStatus(bedId, "AVAILABLE");

            return false;
        }

        // 3. Update patient status
        boolean patientUpdated =
                patientService.updatePatientStatus(patientId, "ALLOCATED");

        if (!patientUpdated) {

            bedService.updateBedStatus(bedId, "AVAILABLE");

            doctorService.updateDoctorAvailability(doctorId, true);

            return false;
        }

        // 4. Save allocation in database
        int allocationId =
                allocationDAO.addAllocation(patientId, doctorId, bedId);

        if (allocationId == -1) {

            bedService.updateBedStatus(bedId, "AVAILABLE");

            doctorService.updateDoctorAvailability(doctorId, true);

            patientService.updatePatientStatus(patientId, "WAITING");

            return false;
        }

        // Update patient object
        patient.setStatus("ALLOCATED");

        System.out.println("\nPatient Allocated Successfully!");
        System.out.println("Patient: " + patient.getName());
        System.out.println("Severity: " + patient.getSeverity());
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Bed ID: " + bedId);
        System.out.println("Allocation ID: " + allocationId);

        return true;
    }


    // 3. Wait Until Bed Becomes Available
    public synchronized void waitForBed()
            throws InterruptedException {

        while (bedService.getAvailableBeds().isEmpty()
                || doctorService.getAllDoctors().stream()
                .noneMatch(Doctor::isAvailable)) {

            wait();
        }
    }


    // 4. Notify Worker When Resources Become Available
    public synchronized void notifyBedAvailable() {
        notifyAll();
    }


    // 5. Get Waiting Queue
    public WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }
}