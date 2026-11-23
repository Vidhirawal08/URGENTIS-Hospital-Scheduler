import allocation.AllocationService;
import discharge.DischargeService;
import java.util.Scanner;
import model.Patient;
import model.Severity;
import patient.PatientService;
import threading.AllocationWorker;
import threading.PatientThread;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PatientService service = new PatientService();

        // Create Allocation Service
        AllocationService allocationService = new AllocationService();

        // Create Discharge Service
        DischargeService dischargeService =
                new DischargeService(allocationService);

        // Recover previously waiting patients from database
        for (Patient waitingPatient : service.getAllPatients()) {

            if ("WAITING".equalsIgnoreCase(waitingPatient.getStatus())) {

                allocationService.addPatient(waitingPatient);

                System.out.println(
                    waitingPatient.getName()
                    + " recovered from database and added to queue."
                );
            }
        }

        // Start Allocation Worker Thread
        Thread worker = new Thread(
                new AllocationWorker(allocationService)
        );

        worker.setDaemon(true);
        worker.start();

        int choice = 0;

        do {

            System.out.println("\n================================");
            System.out.println("       URGENTIS HOSPITAL");
            System.out.println("      PATIENT MANAGEMENT");
            System.out.println("================================");

            System.out.println("1. Register New Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient Status");
            System.out.println("5. Delete Patient");
            System.out.println("6. Discharge Patient");
            System.out.println("7. Exit");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    // REGISTER PATIENT
                    case 1:

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();

                        System.out.print(
                            "Enter Severity (CRITICAL/HIGH/MODERATE/LOW): "
                        );

                        Severity severity = Severity.valueOf(
                            sc.nextLine().trim().toUpperCase()
                        );

                        Patient p = service.registerPatient(
                            name, age, gender, severity
                        );

                        if (p != null) {

                            System.out.println(
                                "Patient ID: " + p.getPatientId()
                            );

                            // Start Patient Thread
                            Thread patientThread = new Thread(
                                new PatientThread(p, allocationService)
                            );

                            patientThread.start();
                        }

                        break;

                    // VIEW ALL PATIENTS
                    case 2:

                        System.out.println("\n--- ALL PATIENTS ---");

                        for (Patient patient : service.getAllPatients()) {

                            System.out.println(patient);
                            System.out.println("--------------------");
                        }

                        break;

                    // SEARCH PATIENT
                    case 3:

                        System.out.print("Enter Patient ID: ");

                        int searchId = Integer.parseInt(sc.nextLine());

                        Patient searched = service.getPatientById(searchId);

                        if (searched != null) {
                            System.out.println(searched);
                        } else {
                            System.out.println("Patient Not Found!");
                        }

                        break;

                    // UPDATE PATIENT STATUS
                    case 4:

                        System.out.print("Enter Patient ID: ");

                        int updateId = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter New Status: ");

                        String status = sc.nextLine();

                        boolean updated = service.updatePatientStatus(
                            updateId, status
                        );

                        System.out.println(
                            updated ? "Status Updated!" : "Update Failed!"
                        );

                        break;

                    // DELETE PATIENT
                    case 5:

                        System.out.print("Enter Patient ID: ");

                        int deleteId = Integer.parseInt(sc.nextLine());

                        boolean deleted = service.deletePatient(deleteId);

                        System.out.println(
                            deleted ? "Patient Deleted!" : "Patient Not Found!"
                        );

                        break;

                    // DISCHARGE PATIENT
                    case 6:

                        System.out.print("Enter Patient ID: ");

                        int dischargeId = Integer.parseInt(sc.nextLine());

                        boolean discharged =
                            dischargeService.dischargePatient(dischargeId);

                        if (discharged) {
                            System.out.println(
                                "Discharge Process Completed Successfully!"
                            );
                        } else {
                            System.out.println(
                                "Patient Discharge Failed!"
                            );
                        }

                        break;

                    // EXIT
                    case 7:

                        System.out.println(
                            "Thank you for using URGENTIS!"
                        );

                        break;

                    default:

                        System.out.println("Invalid Choice! Try Again.");
                }

            } catch (IllegalArgumentException e) {

                System.out.println("Invalid Input: " + e.getMessage());

            } catch (Exception e) {

                System.out.println(
                    "Something went wrong: " + e.getMessage()
                );
            }

        } while (choice != 7);

        sc.close();
    }
}