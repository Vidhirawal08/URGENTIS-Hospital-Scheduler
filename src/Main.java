// import patient.PatientService;
// import model.Patient;
// import model.Severity;
// import database.DBConnection;
// import java.sql.Connection;
// import java.sql.SQLException;

// public class Main {

//     public static void main(String[] args) {

//         PatientService service = new PatientService();

//         // Register patients
//         Patient p1 = service.registerPatient(
//             "Rahul", 45, "Male", Severity.CRITICAL
//         );

//         Patient p2 = service.registerPatient(
//             "Priya", 30, "Female", Severity.HIGH
//         );

//         // Display all patients
//         System.out.println("\n----- PATIENT RECORDS -----");

//         for (Patient p : service.getAllPatients()) {
//             System.out.println(p);
//             System.out.println("--------------------");
//         }

//         // Search patient
//         System.out.println("\n----- SEARCH PATIENT -----");

//         Patient searched = service.getPatientById(101);

//         if (searched != null) {
//             System.out.println(searched);
//         }

//         // Update status
//         service.updatePatientStatus(101, "ALLOCATED");

//         System.out.println("\nUpdated Status: "
//                         + p1.getStatus());
        
//         Connection con = DBConnection.getConnection();

//         if (con != null) {

//     System.out.println("URGENTIS MySQL Connection Successful!");

//     try {
//         con.close();
//         System.out.println("Connection Closed Successfully!");
//     }
//     catch (SQLException e) {
//         e.printStackTrace();
//     }
// }
//     }
// }

// import patient.PatientService;
// import model.Patient;
// import model.Severity;

// public class Main {

//     public static void main(String[] args) {

//         PatientService service = new PatientService();

//         Patient p1 = service.registerPatient(
//                 "Rahul", 45, "Male", Severity.CRITICAL
//         );

//         Patient p2 = service.registerPatient(
//                 "Priya", 30, "Female", Severity.HIGH
//         );
//     }
// }

// import patient.PatientService;
// import model.Patient;
// import model.Severity;

// public class Main {

//     public static void main(String[] args) {

//         PatientService service = new PatientService();

//         // View All Patients
//         System.out.println("\n--- ALL PATIENTS ---");

//         for (Patient p : service.getAllPatients()) {
//             System.out.println(p);
//             System.out.println("----------------");
//         }

//         // Search Patient
//         System.out.println("\n--- SEARCH PATIENT ---");

//         Patient searched = service.getPatientById(101);

//         if (searched != null) {
//             System.out.println(searched);
//         } else {
//             System.out.println("Patient not found!");
//         }

//         // Update Status
//         System.out.println("\n--- UPDATE STATUS ---");

//         boolean updated = service.updatePatientStatus(101, "ALLOCATED");

//         if (updated) {
//             System.out.println("Patient status updated!");
//         } else {
//             System.out.println("Patient not found!");
//         }
//     }
// }

// import patient.PatientService;
// import model.Patient;
// import model.Severity;

// public class Main {

//     public static void main(String[] args) {

//         PatientService service = new PatientService();

//         // Register Patient
//         Patient p1 = service.registerPatient(
//                 "Aman", 45, "Male", Severity.CRITICAL
//         );

//         // Search using actual generated ID
//         if (p1 != null) {

//             System.out.println("\n--- SEARCH PATIENT ---");

//             Patient searched =
//                     service.getPatientById(p1.getPatientId());

//             if (searched != null) {
//                 System.out.println(searched);
//             }

//             // Update Status
//             System.out.println("\n--- UPDATE STATUS ---");

//             boolean updated = service.updatePatientStatus(
//                     p1.getPatientId(), "ALLOCATED"
//             );

//             System.out.println(
//                     updated ? "Status Updated!" : "Update Failed!"
//             );

//         }
        

//         // View All Patients
//         System.out.println("\n--- ALL PATIENTS ---");

//         for (Patient p : service.getAllPatients()) {
//             System.out.println(p);
//             System.out.println("------------------");
//         }
//         // Delete Patient
// System.out.println("\n--- DELETE PATIENT ---");

// boolean deleted = service.deletePatient(p1.getPatientId());
// System.out.println("\n--- VERIFY DELETION ---");

// Patient check = service.getPatientById(p1.getPatientId());

// if (check == null) {
//     System.out.println("Patient successfully removed from database!");
// } else {
//     System.out.println("Patient still exists!");
// }

// System.out.println(
//     deleted ? "Patient Deleted Successfully!" : "Patient Not Found!"
// );
// System.out.println("\n--- STATUS VALIDATION TEST ---");

// try {
//     boolean result = service.updatePatientStatus(
//         1, "HELLO"
//     );

//     System.out.println(result ? "Updated!" : "Update Failed!");

// } catch (IllegalArgumentException e) {
//     System.out.println(e.getMessage());
// }
//     }
// }

import patient.PatientService;
import model.Patient;
import model.Severity;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PatientService service = new PatientService();

        int choice=0;

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
            System.out.println("6. Exit");

            System.out.print("\nEnter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter Severity (CRITICAL/HIGH/MODERATE/LOW): ");
                        Severity severity = Severity.valueOf(
                            sc.nextLine().trim().toUpperCase()
                        );

                        Patient p = service.registerPatient(
                            name, age, gender, severity
                        );

                        if (p != null) {
                            System.out.println("Patient ID: " + p.getPatientId());
                        }
                        break;

                    case 2:
                        System.out.println("\n--- ALL PATIENTS ---");

                        for (Patient patient : service.getAllPatients()) {
                            System.out.println(patient);
                            System.out.println("--------------------");
                        }
                        break;

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

                    case 5:
                        System.out.print("Enter Patient ID: ");
                        int deleteId = Integer.parseInt(sc.nextLine());

                        boolean deleted = service.deletePatient(deleteId);

                        System.out.println(
                            deleted ? "Patient Deleted!" : "Patient Not Found!"
                        );
                        break;

                    case 6:
                        System.out.println("Thank you for using URGENTIS!");
                        break;

                    default:
                        System.out.println("Invalid Choice! Try Again.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Input: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }

        } while (choice != 6);

        sc.close();
    }
}
