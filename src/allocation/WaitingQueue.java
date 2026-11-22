
// Surbhi - 2510991335
package allocation;

import java.util.Comparator;
import java.util.PriorityQueue;
import model.Patient;

public class WaitingQueue {

    private final PriorityQueue<Patient> queue =
        new PriorityQueue<>(
            Comparator.comparingInt(
                (Patient p) -> p.getSeverity().getPriority()
            ).thenComparing(Patient::getArrivalTime)
        );

    public synchronized void addPatient(Patient patient) {
        queue.add(patient);
        notifyAll();
    }

    public synchronized Patient getNextPatient()
            throws InterruptedException {

        while (queue.isEmpty()) {
            wait();
        }

        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }
}

// Surbhi - 2510991335