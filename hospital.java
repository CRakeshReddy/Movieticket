package appointment;

import java.util.*;
import java.time.*;

public class hospital {

	public hospital() {
		// TODO Auto-generated constructor stub
	}

    static Scanner sc = new Scanner(System.in);

    static Map<Integer, Patient> patients = new HashMap<>();
    static Map<Integer, Doctor> doctors = new HashMap<>();
    static Map<String, Appointment> appointments = new HashMap<>();

    static ArrayList<LocalTime> slots = new ArrayList<>(
        Arrays.asList(
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0)
        )
    );

    // Patient class
    static class Patient {
        int id;
        String name;
        int age;
        String phone;

        Patient(int id, String name, int age, String phone) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.phone = phone;
        }
    }

    // Doctor class
    static class Doctor {
        int id;
        String name;
        String specialization;

        Doctor(int id, String name, String specialization) {
            this.id = id;
            this.name = name;
            this.specialization = specialization;
        }
    }

    // Appointment class
    static class Appointment {
        int patientId;
        int doctorId;
        LocalDate date;
        LocalTime time;
        String status;

        Appointment(int patientId, int doctorId,
                    LocalDate date, LocalTime time) {
            this.patientId = patientId;
            this.doctorId = doctorId;
            this.date = date;
            this.time = time;
            this.status = "Booked";
        }
    }

    // Generate unique key for each doctor time slot
    static String getKey(int doctorId, LocalDate date,
                         LocalTime time) {
        return doctorId + "_" + date + "_" + time;
    }

    // Register patient
    static void registerPatient() {
        try {
            System.out.print("Enter patient ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (patients.containsKey(id)) {
                System.out.println("Patient ID already exists.");
                return;
            }

            System.out.print("Enter patient name: ");
            String name = sc.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Enter phone: ");
            String phone = sc.nextLine();

            if (name.trim().isEmpty() || age <= 0 ||
                phone.trim().isEmpty()) {
                throw new IllegalArgumentException(
                    "Invalid patient details."
                );
            }

            patients.put(id, new Patient(id, name, age, phone));
            System.out.println("Patient registered successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Enter valid numeric values.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Add doctor
    static void addDoctor() {
        try {
            System.out.print("Enter doctor ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (doctors.containsKey(id)) {
                System.out.println("Doctor ID already exists.");
                return;
            }

            System.out.print("Enter doctor name: ");
            String name = sc.nextLine();

            System.out.print("Enter specialization: ");
            String specialization = sc.nextLine();

            if (name.trim().isEmpty() ||
                specialization.trim().isEmpty()) {
                throw new IllegalArgumentException(
                    "Doctor details cannot be empty."
                );
            }

            doctors.put(id, new Doctor(id, name, specialization));
            System.out.println("Doctor added successfully!");

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid doctor ID.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // View available slots
    static void viewAvailableSlots() {
        try {
            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(sc.nextLine());

            if (!doctors.containsKey(doctorId)) {
                System.out.println("Doctor not found.");
                return;
            }

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            if (date.isBefore(LocalDate.now())) {
                System.out.println("Cannot view past dates.");
                return;
            }

            System.out.println("\nAvailable slots:");

            boolean found = false;

            for (LocalTime time : slots) {
                String key = getKey(doctorId, date, time);

                if (!appointments.containsKey(key) ||
                    appointments.get(key).status.equals("Cancelled")) {

                    if (date.equals(LocalDate.now()) &&
                        !time.isAfter(LocalTime.now())) {
                        continue;
                    }

                    System.out.println(time);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No available slots.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid doctor ID.");
        } catch (DateTimeException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
        }
    }

    // Book appointment
    static void bookAppointment() {
        try {
            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(sc.nextLine());

            if (!patients.containsKey(patientId)) {
                System.out.println("Patient not found.");
                return;
            }

            if (!doctors.containsKey(doctorId)) {
                System.out.println("Doctor not found.");
                return;
            }

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.print("Enter time (HH:MM): ");
            LocalTime time = LocalTime.parse(sc.nextLine());

            if (date.isBefore(LocalDate.now()) ||
                (date.equals(LocalDate.now()) &&
                 !time.isAfter(LocalTime.now()))) {
                System.out.println("Select a future date and time.");
                return;
            }

            if (!slots.contains(time)) {
                System.out.println("Invalid time slot.");
                return;
            }

            String key = getKey(doctorId, date, time);

            if (appointments.containsKey(key) &&
                appointments.get(key).status.equals("Booked")) {
                System.out.println("This slot is already booked.");
                return;
            }

            Appointment appointment = new Appointment(
                patientId, doctorId, date, time
            );

            appointments.put(key, appointment);

            System.out.println("Appointment booked successfully!");
            System.out.println("Doctor: " +
                doctors.get(doctorId).name);
            System.out.println("Patient: " +
                patients.get(patientId).name);
            System.out.println("Date: " + date);
            System.out.println("Time: " + time);

        } catch (NumberFormatException e) {
            System.out.println("Enter valid numeric IDs.");
        } catch (DateTimeException e) {
            System.out.println("Invalid date or time format.");
        }
    }

    // Cancel appointment
    static void cancelAppointment() {
        try {
            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            System.out.print("Enter time (HH:MM): ");
            LocalTime time = LocalTime.parse(sc.nextLine());

            String key = getKey(doctorId, date, time);

            Appointment appointment = appointments.get(key);

            if (appointment != null &&
                appointment.patientId == patientId &&
                appointment.status.equals("Booked")) {

                appointment.status = "Cancelled";
                System.out.println("Appointment cancelled successfully!");
            } else {
                System.out.println("Active appointment not found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Enter valid numeric IDs.");
        } catch (DateTimeException e) {
            System.out.println("Invalid date or time format.");
        }
    }

    // View doctor schedule
    static void viewDoctorSchedule() {
        try {
            System.out.print("Enter doctor ID: ");
            int doctorId = Integer.parseInt(sc.nextLine());

            if (!doctors.containsKey(doctorId)) {
                System.out.println("Doctor not found.");
                return;
            }

            System.out.println("\nDoctor Schedule:");
            boolean found = false;

            for (Appointment a : appointments.values()) {
                if (a.doctorId == doctorId &&
                    a.status.equals("Booked")) {

                    System.out.println(
                        "Patient: " + patients.get(a.patientId).name +
                        " | Date: " + a.date +
                        " | Time: " + a.time
                    );
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No appointments found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid doctor ID.");
        }
    }

    // View patient history
    static void viewPatientHistory() {
        try {
            System.out.print("Enter patient ID: ");
            int patientId = Integer.parseInt(sc.nextLine());

            if (!patients.containsKey(patientId)) {
                System.out.println("Patient not found.");
                return;
            }

            System.out.println("\nPatient Appointment History:");
            boolean found = false;

            for (Appointment a : appointments.values()) {
                if (a.patientId == patientId) {
                    System.out.println(
                        "Doctor: " + doctors.get(a.doctorId).name +
                        " | Date: " + a.date +
                        " | Time: " + a.time +
                        " | Status: " + a.status
                    );
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No history found.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Enter a valid patient ID.");
        }
    }

    // Main method
    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== HOSPITAL MANAGEMENT =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Book Appointment");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. View Available Slots");
            System.out.println("6. View Doctor Schedule");
            System.out.println("7. View Patient History");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        registerPatient();
                        break;
                    case 2:
                        addDoctor();
                        break;
                    case 3:
                        bookAppointment();
                        break;
                    case 4:
                        cancelAppointment();
                        break;
                    case 5:
                        viewAvailableSlots();
                        break;
                    case 6:
                        viewDoctorSchedule();
                        break;
                    case 7:
                        viewPatientHistory();
                        break;
                    case 8:
                        System.out.println("Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid menu number.");
                choice = 0;
            }

        } while (choice != 8);

        sc.close();
    }
}


