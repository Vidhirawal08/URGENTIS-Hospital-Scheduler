
// Surbhi - 2510991335
package allocation;

import hospital.BedService;
import model.Bed;
import model.Patient;
import patient.PatientService;

public class AllocationService {

    private final WaitingQueue waitingQueue = new WaitingQueue();
    private final BedService bedService = new BedService();
    private final PatientService patientService = new PatientService();

    // Add patient to queue
    public void addPatient(Patient patient) {
        waitingQueue.addPatient(patient);
    }

    // Allocate an available bed
    public synchronized boolean allocatePatient(Patient patient) {

        if (bedService.getAvailableBeds().isEmpty()) {
            System.out.println("No bed available for " + patient.getName());
            return false;
        }

        Bed bed = bedService.getAvailableBeds().get(0);

        boolean bedUpdated = bedService.updateBedStatus(
            bed.getBedId(), "OCCUPIED"
        );

        if (!bedUpdated) {
            return false;
        }

        boolean patientUpdated = patientService.updatePatientStatus(
            patient.getPatientId(), "ALLOCATED"
        );

        if (!patientUpdated) {
            bedService.updateBedStatus(bed.getBedId(), "AVAILABLE");
            return false;
        }

        patient.setStatus("ALLOCATED");

        System.out.println("\nPatient Allocated Successfully!");
        System.out.println("Patient: " + patient.getName());
        System.out.println("Severity: " + patient.getSeverity());
        System.out.println("Bed ID: " + bed.getBedId());

        return true;
    }

    // Wait until a bed becomes available
    public synchronized void waitForBed()
            throws InterruptedException {

        while (bedService.getAvailableBeds().isEmpty()) {
            wait();
        }
    }

    // Notify worker when a bed becomes available
    public synchronized void notifyBedAvailable() {
        notifyAll();
    }

    public WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }
}
// Surbhi - 2510991335
