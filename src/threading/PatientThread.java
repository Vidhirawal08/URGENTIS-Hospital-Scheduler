
// Surbhi - 2510991335
package threading;

import allocation.AllocationService;
import model.Patient;

public class PatientThread implements Runnable {

    private final Patient patient;
    private final AllocationService allocationService;

    public PatientThread(Patient patient, AllocationService allocationService) {
        this.patient = patient;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        System.out.println(
            patient.getName() + " has entered the waiting queue."
        );

        allocationService.addPatient(patient);
    }
}

// Surbhi - 2510991335
