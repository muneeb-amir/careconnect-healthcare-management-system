import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class User {
    String name, email, password;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

class Patient extends User {
    List<Appointment> appointments = new ArrayList<>();
    public Patient(String name, String email, String password) {
        super(name, email, password);
    }
    public String getName() {
       return name;
    }
        public String getEmail() {
           return email;
        }
                public String getPassword() {
                    return password;
                }
                
            }
            
            class Doctor extends User {
                String specialization, location;
                List<Appointment> appointments = new ArrayList<>();
                double earnings = 0;
            
                public Doctor(String name, String email, String password, String specialization, String location) {
                    super(name, email, password);
                    this.specialization = specialization;
                    this.location = location;
                }
            
                public String getName() {
                    return name;
                }
            
                public String getSpecialization() {
                    return specialization;
                }
            
                public String getLocation() {
                    return location;
                }
                
                                public Object getEmail() {
                                    return email;
                                }
                                
                                                                public String getPassword() {
                                                                    return password;
                                                                }
                                                            }
                                                            
                                                            class Admin extends User {
                                                                public Admin(String name, String email, String password) {
                                                                    super(name, email, password);
                                                                }
                                                            }
                                                            
                                                            class Appointment {
                                                                Patient patient;
                                                                Doctor doctor;
                                                                String date, time;
                                                                boolean completed = false;
                                                                String status = "Pending"; // Possible values: "Pending", "Accepted", "Rejected", "Completed"
                                                            
                                                                public Appointment(Patient patient, Doctor doctor, String date, String time) {
                                                                    this.patient = patient;
                                                                    this.doctor = doctor;
                                                                    this.date = date;
                                                                    this.time = time;
                                                                }
                                                            
                                                                public String getStatus() {
                                                                   return status;
                                                                }
                                                            }
                                                            
                                                            public  class HealthClinicSystem {
                                                
                                                                private static final String URL = "jdbc:mysql://localhost:3306/HealthClinicSystem";
                                                                private static final String USER = "root";
                                                                private static final String PASSWORD="muneeb1234";
                                                
                                                                static Scanner scanner = new Scanner(System.in);
                                                                static List<Patient> patients = new ArrayList<>();
                                                                static List<Doctor> doctors = new ArrayList<>();
                                                                static List<Admin> admins = new ArrayList<>();
                                                                static List<Appointment> appointments = new ArrayList<>();
                                                            
                                                                public static void main(String[] args) {
                                                                    initializeData();
                                                                    mainMenu();
                                                                }
                                                            
                                                    
                                                                static void initializeData() {
                                                                    admins.add(new Admin("Admin", "admin@clinic.com", "admin123"));
                                                                    doctors.add(new Doctor("Dr. Smith", "d", "d", "Cardiology", "New York"));
                                                                    doctors.add(new Doctor("Dr. Jane", "drjane@clinic.com", "password", "Dermatology", "Los Angeles"));
                                                                    patients.add(new Patient("John Doe", "p", "p"));
                                                                    patients.add(new Patient("Jane Smith", "patient@clinic.com", "password"));
                                                                }
                                
       
                                public static void loader() {
                                    // First, try to load the MySQL driver
                                    try {
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                    } catch (ClassNotFoundException e) {
                                        System.err.println("MySQL JDBC Driver not found. Include it in your library path");
                                        e.printStackTrace();
                                        return;
                                    }
                                
                                    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                                        patients.clear();
                                        doctors.clear();
                                        admins.clear();
                                        appointments.clear();
                                
                                        // Load Patients
                                        String patientQuery = "SELECT * FROM Patients";
                                        try (Statement stmt = conn.createStatement();
                                             ResultSet rs = stmt.executeQuery(patientQuery)) {
                                            while (rs.next()) {
                                                patients.add(new Patient(
                                                    rs.getString("name"),
                                                    rs.getString("email"),
                                                    rs.getString("password")
                                                ));
                                            }
                                        }
                                
                                        // Load Doctors
                                        String doctorQuery = "SELECT * FROM Doctors";
                                        try (Statement stmt = conn.createStatement();
                                             ResultSet rs = stmt.executeQuery(doctorQuery)) {
                                            while (rs.next()) {
                                                doctors.add(new Doctor(
                                                    rs.getString("name"),
                                                    rs.getString("email"),
                                                    rs.getString("password"),
                                                    rs.getString("specialization"),
                                                    rs.getString("location")
                                                ));
                                            }
                                        }
                                
                                        // Load Admins
                                        String adminQuery = "SELECT * FROM Admins";
                                        try (Statement stmt = conn.createStatement();
                                             ResultSet rs = stmt.executeQuery(adminQuery)) {
                                            while (rs.next()) {
                                                admins.add(new Admin(
                                                    rs.getString("name"),
                                                    rs.getString("email"),
                                                    rs.getString("password")
                                                ));
                                            }
                                        }
                                
                                        // Load Appointments
                                        String appointmentQuery = "SELECT * FROM Appointments";
                                        try (Statement stmt = conn.createStatement();
                                             ResultSet rs = stmt.executeQuery(appointmentQuery)) {
                                            while (rs.next()) {
                                                Patient patient = null;
                                                Doctor doctor = null;
                                                
                                                int patientId = rs.getInt("patient_id");
                                                int doctorId = rs.getInt("doctor_id");
                                                
                                                for (Patient p : patients) {
                                                    if (p.getEmail().hashCode() == patientId) {
                                                        patient = p;
                                                        break;
                                                    }
                                                }
                                                
                                                for (Doctor d : doctors) {
                                                    if (d.getEmail().hashCode() == doctorId) {
                                        doctor = d;
                                        break;
                                    }
                                }
                                
                                if (patient != null && doctor != null) {
                                    Appointment appointment = new Appointment(
                                        patient,
                                        doctor,
                                        rs.getString("date"),
                                        rs.getString("time")
                                    );
                                    appointment.status = rs.getString("status");
                                    appointment.completed = rs.getBoolean("completed");
                                    appointments.add(appointment);
                                    patient.appointments.add(appointment);
                                    doctor.appointments.add(appointment);
                                }
                            }
                        }
                
                        System.out.println("Data successfully loaded from database to arrays");
                
                    } catch (SQLException e) {
                        System.err.println("Database error: " + e.getMessage());
                        System.err.println("SQL State: " + e.getSQLState());
                        System.err.println("Error Code: " + e.getErrorCode());
                        e.printStackTrace();
                    }
                }
            

    static void bookAppointment(Patient patient) {
        System.out.println("Available Doctors:");
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". Dr. " + doctors.get(i).name + " - " 
                               + doctors.get(i).specialization + " - Location: " + doctors.get(i).location);
        }
        System.out.println("0. Exit"); // Option to exit
    
        System.out.print("Select a doctor by number (or 0 to exit): ");
        int doctorIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline
    
        if (doctorIndex == -1) {
            System.out.println("Booking canceled.");
            return;
        }
    
        if (doctorIndex < 0 || doctorIndex >= doctors.size()) {
            System.out.println("Invalid selection!");
            return;
        }
    
        System.out.print("Enter appointment date (YYYY-MM-DD) or type 'exit' to cancel: ");
        String date = scanner.nextLine();
        if (date.equalsIgnoreCase("exit")) {
            System.out.println("Booking canceled.");
            return;
        }
    
        System.out.print("Enter appointment time (HH:MM) or type 'exit' to cancel: ");
        String time = scanner.nextLine();
        if (time.equalsIgnoreCase("exit")) {
            System.out.println("Booking canceled.");
            return;
        }
    
        Doctor selectedDoctor = doctors.get(doctorIndex);
        Appointment newAppointment = new Appointment(patient, selectedDoctor, date, time);
        
        patient.appointments.add(newAppointment);
        selectedDoctor.appointments.add(newAppointment);
        appointments.add(newAppointment);
    
        System.out.println("Appointment booking request sent to Dr. " + selectedDoctor.name + 
                           " on " + date + " at " + time + " at " + selectedDoctor.location);
        System.out.println("Status: " + newAppointment.status + " (waiting for doctor's confirmation)");
    }
    
    static void mainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Health Clinic System");
            System.out.println("1. Register as Patient");
            System.out.println("2. Register as Doctor");
            System.out.println("3. Login as Patient");
            System.out.println("4. Login as Doctor");
            System.out.println("5. Login as Admin");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: registerPatient(); break;
                case 2: registerDoctor(); break;
                case 3: loginPatient(); break;
                case 4: loginDoctor(); break;
                case 5: loginAdmin(); break;
                case 6: System.exit(0);
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void registerPatient() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        patients.add(new Patient(name, email, password));
        System.out.println("Patient registered successfully!");
    }

    static void registerDoctor() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        
        doctors.add(new Doctor(name, email, password, specialization, location));
        System.out.println("Doctor registered successfully!");
    }



    static Patient authenticatePatient(String emailPrompt, String passwordPrompt) {
     
        for (Patient p : patients) {
            if (p.email.equals(emailPrompt) && p.password.equals(passwordPrompt)) {
                return p;
            }
        }
        return null;
    }

        // Authenticate Doctor
        static Doctor authenticateDoctor(String email, String password) {
            for (Doctor d : doctors) {
                if (d.email.equals(email) && d.password.equals(password)) {
                    return d;
                }
            }
            return null;
        }
    
        // Authenticate Admin
        static Admin authenticateAdmin(String email, String password) {
            for (Admin a : admins) {
                if (a.email.equals(email) && a.password.equals(password)) {
                    return a;
                }
            }
            return null;
        }

        static void loginPatient() {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            for (Patient p : patients) {
                if (p.email.equals(email) && p.password.equals(password)) {
                    patientDashboard(p);
                    return;
                }
            }
            System.out.println("Invalid credentials!");
        }

        static void loginDoctor() {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            for (Doctor d : doctors) {
                if (d.email.equals(email) && d.password.equals(password)) {
                    doctorDashboard(d);
                    return;
                }
            }
            System.out.println("Invalid credentials!");
        }

        static void loginAdmin() {
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            for (Admin a : admins) {
                if (a.email.equals(email) && a.password.equals(password)) {
                    adminDashboard(a);
                    return;
                }
            }
            System.out.println("Invalid credentials!");
        }

    static void patientDashboard(Patient patient) {
        while (true) {
            System.out.println("\nPatient Dashboard");
            System.out.println("1. Book Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. View Doctor Profiles");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: bookAppointment(patient); break;
                case 2: viewAppointments(patient); break;
                case 3: viewDoctorProfiles(); break;
                case 4: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void doctorDashboard(Doctor doctor) {
        while (true) {
            System.out.println("\nDoctor Dashboard - Dr. " + doctor.name);
            System.out.println("1. View Pending Appointment Requests");
            System.out.println("2. View All Appointments");
            System.out.println("3. Update Profile");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: managePendingAppointments(doctor); break;
                case 2: viewDoctorAppointments(doctor); break;
                case 3: updateDoctorProfile(doctor); break;
                case 4: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void adminDashboard(Admin admin) {
        while (true) {
            System.out.println("\nAdmin Dashboard - " + admin.name);
            System.out.println("1. View All Patients");
            System.out.println("2. View All Doctors");
            System.out.println("3. View All Appointments");
            System.out.println("4. Manage Patient Accounts");
            System.out.println("5. Manage Doctor Accounts");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: viewAllPatients(); break;
                case 2: viewAllDoctors(); break;
                case 3: viewAllAppointments(); break;
                case 4: managePatientAccounts(); break;
                case 5: manageDoctorAccounts(); break;
                case 6: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void viewAllPatients() {
        System.out.println("\n--- All Registered Patients ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered in the system.");
            return;
        }
        
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.println((i + 1) + ". Name: " + p.name + 
                              " | Email: " + p.email + 
                              " | Appointments: " + p.appointments.size());
        }
        
        System.out.print("\nEnter patient number to view details (or 0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 0 || choice > patients.size()) {
            return;
        }
        
        viewPatientDetails(patients.get(choice - 1));
    }
    
    static void viewPatientDetails(Patient patient) {
        System.out.println("\n--- Patient Details ---");
        System.out.println("Name: " + patient.name);
        System.out.println("Email: " + patient.email);
        System.out.println("Total Appointments: " + patient.appointments.size());
        
        if (!patient.appointments.isEmpty()) {
            System.out.println("\nAppointments:");
            for (Appointment a : patient.appointments) {
                System.out.println("Doctor: Dr. " + a.doctor.name + 
                                  " | Date: " + a.date + 
                                  " | Time: " + a.time + 
                                  " | Status: " + a.status);
            }
        } else {
            System.out.println("No appointments scheduled.");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    static void viewAllDoctors() {
        System.out.println("\n--- All Registered Doctors ---");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered in the system.");
            return;
        }
        
        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            System.out.println((i + 1) + ". Dr. " + d.name + 
                              " | Specialization: " + d.specialization + 
                              " | Location: " + d.location +
                              " | Appointments: " + d.appointments.size() +
                              " | Earnings: $" + d.earnings);
        }
        
        System.out.print("\nEnter doctor number to view details (or 0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 0 || choice > doctors.size()) {
            return;
        }
        
        viewDoctorDetails(doctors.get(choice - 1));
    }
    
    static void viewDoctorDetails(Doctor doctor) {
        System.out.println("\n--- Doctor Details ---");
        System.out.println("Name: Dr. " + doctor.name);
        System.out.println("Email: " + doctor.email);
        System.out.println("Specialization: " + doctor.specialization);
        System.out.println("Location: " + doctor.location);
        System.out.println("Total Appointments: " + doctor.appointments.size());
        System.out.println("Earnings: $" + doctor.earnings);
        
        if (!doctor.appointments.isEmpty()) {
            System.out.println("\nAppointments:");
            for (Appointment a : doctor.appointments) {
                System.out.println("Patient: " + a.patient.name + 
                                  " | Date: " + a.date + 
                                  " | Time: " + a.time + 
                                  " | Status: " + a.status);
            }
        } else {
            System.out.println("No appointments scheduled.");
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    static void viewAllAppointments() {
        System.out.println("\n--- All System Appointments ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments in the system.");
            return;
        }
        
        for (int i = 0; i < appointments.size(); i++) {
            Appointment a = appointments.get(i);
            System.out.println((i + 1) + ". Patient: " + a.patient.name + 
                              " | Doctor: Dr. " + a.doctor.name + 
                              " | Date: " + a.date + 
                              " | Time: " + a.time + 
                              " | Status: " + a.status);
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    static void managePatientAccounts() {
        System.out.println("\n--- Manage Patient Accounts ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered in the system.");
            return;
        }
        
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            System.out.println((i + 1) + ". " + p.name + " | Email: " + p.email);
        }
        
        System.out.print("\nEnter patient number to manage (or 0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 0 || choice > patients.size()) {
            return;
        }
        
        Patient selectedPatient = patients.get(choice - 1);
        
        System.out.println("\n1. Edit Patient Information");
        System.out.println("2. Delete Patient Account");
        System.out.println("3. Go Back");
        System.out.print("Choose an option: ");
        int action = scanner.nextInt();
        scanner.nextLine();
        
        switch (action) {
            case 1:
                editPatientInformation(selectedPatient);
                break;
            case 2:
                deletePatientAccount(selectedPatient);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    static void editPatientInformation(Patient patient) {
        System.out.println("\n--- Edit Patient Information ---");
        System.out.println("Current Information:");
        System.out.println("Name: " + patient.name);
        System.out.println("Email: " + patient.email);
        
        System.out.println("\n1. Edit Name");
        System.out.println("2. Edit Email");
        System.out.println("3. Reset Password");
        System.out.println("4. Go Back");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                patient.name = scanner.nextLine();
                System.out.println("Name updated successfully!");
                break;
            case 2:
                System.out.print("Enter new email: ");
                patient.email = scanner.nextLine();
                System.out.println("Email updated successfully!");
                break;
            case 3:
                System.out.print("Enter new password: ");
                patient.password = scanner.nextLine();
                System.out.println("Password reset successfully!");
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    static void deletePatientAccount(Patient patient) {
        System.out.println("\nWARNING: Are you sure you want to delete " + patient.name + "'s account?");
        System.out.println("This action cannot be undone and will cancel all pending appointments.");
        System.out.print("Type 'CONFIRM' to proceed or anything else to cancel: ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equals("CONFIRM")) {
            // Remove all appointments related to this patient
            List<Appointment> appointmentsToRemove = new ArrayList<>();
            
            for (Appointment a : appointments) {
                if (a.patient == patient) {
                    // Remove from doctor's appointment list
                    a.doctor.appointments.remove(a);
                    appointmentsToRemove.add(a);
                }
            }
            
            appointments.removeAll(appointmentsToRemove);
            patients.remove(patient);
            
            System.out.println("Patient account deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    static void manageDoctorAccounts() {
        System.out.println("\n--- Manage Doctor Accounts ---");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered in the system.");
            return;
        }
        
        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            System.out.println((i + 1) + ". Dr. " + d.name + 
                              " | Specialization: " + d.specialization + 
                              " | Location: " + d.location);
        }
        
        System.out.print("\nEnter doctor number to manage (or 0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 0 || choice > doctors.size()) {
            return;
        }
        
        Doctor selectedDoctor = doctors.get(choice - 1);
        
        System.out.println("\n1. Edit Doctor Information");
        System.out.println("2. Delete Doctor Account");
        System.out.println("3. Go Back");
        System.out.print("Choose an option: ");
        int action = scanner.nextInt();
        scanner.nextLine();
        
        switch (action) {
            case 1:
                editDoctorInformation(selectedDoctor);
                break;
            case 2:
                deleteDoctorAccount(selectedDoctor);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    static void editDoctorInformation(Doctor doctor) {
        System.out.println("\n--- Edit Doctor Information ---");
        System.out.println("Current Information:");
        System.out.println("Name: Dr. " + doctor.name);
        System.out.println("Email: " + doctor.email);
        System.out.println("Specialization: " + doctor.specialization);
        System.out.println("Location: " + doctor.location);
        
        System.out.println("\n1. Edit Name");
        System.out.println("2. Edit Email");
        System.out.println("3. Edit Specialization");
        System.out.println("4. Edit Location");
        System.out.println("5. Reset Password");
        System.out.println("6. Go Back");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter new name: ");
                doctor.name = scanner.nextLine();
                System.out.println("Name updated successfully!");
                break;
            case 2:
                System.out.print("Enter new email: ");
                doctor.email = scanner.nextLine();
                System.out.println("Email updated successfully!");
                break;
            case 3:
                System.out.print("Enter new specialization: ");
                doctor.specialization = scanner.nextLine();
                System.out.println("Specialization updated successfully!");
                break;
            case 4:
                System.out.print("Enter new location: ");
                doctor.location = scanner.nextLine();
                System.out.println("Location updated successfully!");
                break;
            case 5:
                System.out.print("Enter new password: ");
                doctor.password = scanner.nextLine();
                System.out.println("Password reset successfully!");
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    static void deleteDoctorAccount(Doctor doctor) {
        System.out.println("\nWARNING: Are you sure you want to delete Dr. " + doctor.name + "'s account?");
        System.out.println("This action cannot be undone and will cancel all pending appointments.");
        System.out.print("Type 'CONFIRM' to proceed or anything else to cancel: ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equals("CONFIRM")) {
            // Remove all appointments related to this doctor
            List<Appointment> appointmentsToRemove = new ArrayList<>();
            
            for (Appointment a : appointments) {
                if (a.doctor == doctor) {
                    // Remove from patient's appointment list
                    a.patient.appointments.remove(a);
                    appointmentsToRemove.add(a);
                }
            }
            
            appointments.removeAll(appointmentsToRemove);
            doctors.remove(doctor);
            
            System.out.println("Doctor account deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    static void managePendingAppointments(Doctor doctor) {
        List<Appointment> pendingAppointments = new ArrayList<>();
        
        // Collect all pending appointments for this doctor
        for (Appointment a : doctor.appointments) {
            if (a.status.equals("Pending")) {
                pendingAppointments.add(a);
            }
        }
        
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointment requests.");
            return;
        }
        
        System.out.println("\nPending Appointment Requests:");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            Appointment a = pendingAppointments.get(i);
            System.out.println((i + 1) + ". Patient: " + a.patient.name + 
                              " - Date: " + a.date + " - Time: " + a.time);
        }
        
        System.out.print("\nSelect an appointment to manage (or 0 to go back): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        if (choice == 0 || choice > pendingAppointments.size()) {
            return;
        }
        
        Appointment selected = pendingAppointments.get(choice - 1);
        
        System.out.println("\nSelected Appointment Details:");
        System.out.println("Patient: " + selected.patient.name);
        System.out.println("Date: " + selected.date);
        System.out.println("Time: " + selected.time);
        System.out.println("Status: " + selected.status);
        
        System.out.println("\n1. Accept Appointment");
        System.out.println("2. Reject Appointment");
        System.out.println("3. Go Back");
        System.out.print("Choose an option: ");
        int action = scanner.nextInt();
        scanner.nextLine();
        
        switch (action) {
            case 1:
                selected.status = "Accepted";
                System.out.println("Appointment accepted successfully!");
                break;
            case 2:
                selected.status = "Rejected";
                System.out.println("Appointment rejected.");
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    static void viewDoctorAppointments(Doctor doctor) {
        System.out.println("\nAll Your Appointments:");
        boolean hasAppointments = false;
        
        for (Appointment a : doctor.appointments) {
            hasAppointments = true;
            System.out.println("Patient: " + a.patient.name + 
                              " - Date: " + a.date + 
                              " - Time: " + a.time + 
                              " - Status: " + a.status);
        }
        
        if (!hasAppointments) {
            System.out.println("You have no appointments scheduled.");
        }
    }

    static void updateDoctorProfile(Doctor doctor) {
        System.out.println("\nUpdate Profile");
        System.out.println("Current Information:");
        System.out.println("Name: " + doctor.name);
        System.out.println("Email: " + doctor.email);
        System.out.println("Specialization: " + doctor.specialization);
        System.out.println("Location: " + doctor.location);
        
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Specialization");
        System.out.println("2. Location");
        System.out.println("3. Password");
        System.out.println("4. Go Back");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                System.out.print("Enter new specialization: ");
                doctor.specialization = scanner.nextLine();
                System.out.println("Specialization updated successfully!");
                break;
            case 2:
                System.out.print("Enter new location: ");
                doctor.location = scanner.nextLine();
                System.out.println("Location updated successfully!");
                break;
            case 3:
                System.out.print("Enter new password: ");
                doctor.password = scanner.nextLine();
                System.out.println("Password updated successfully!");
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    static void viewAppointments(Patient patient) {
        System.out.println("Your Appointments:");
        boolean hasAppointments = false;
        
        for (Appointment a : patient.appointments) {
            hasAppointments = true;
            System.out.println("Doctor: " + a.doctor.name + 
                              " - Date: " + a.date + 
                              " - Time: " + a.time + 
                              " - Status: " + a.status);
        }
        
        if (!hasAppointments) {
            System.out.println("You have no appointments scheduled.");
        }
    }

    static void viewDoctorProfiles() {
        System.out.println("Doctor Profiles:");
        for (Doctor d : doctors) {
            System.out.println("Dr. " + d.name + " - Specialization: " + d.specialization + " - Location: " + d.location);
        }
    }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static List<Patient> getPatients() {
       return patients;
    }
}
class databaseconnection 
{
    private static final String URL = "jdbc:mysql://localhost:3306/HealthClinicSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "muneeb1234";

    private static Connection connection;

    public static Connection getConnection() {
        try {
            // Check if the connection is null or closed and reinitialize if needed
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Database connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }
}

