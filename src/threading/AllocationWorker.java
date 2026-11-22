// Surbhi - 2510991335

// Surbhi - 2510991335
package threading;

import allocation.AllocationService;
import allocation.WaitingQueue;
import model.Patient;

public class AllocationWorker implements Runnable {

    private final AllocationService allocationService;

    public AllocationWorker(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        WaitingQueue queue = allocationService.getWaitingQueue();

        while (!Thread.currentThread().isInterrupted()) {

            try {

                Patient patient = queue.getNextPatient();

                boolean allocated =
                    allocationService.allocatePatient(patient);

                if (!allocated) {

                    queue.addPatient(patient);

                    allocationService.waitForBed();
                }

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}

// Surbhi - 2510991335