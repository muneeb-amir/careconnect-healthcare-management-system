import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class HealthClinicSystemTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Reset lists before each test
        HealthClinicSystem.getPatients().clear();
        HealthClinicSystem.getDoctors().clear();
        HealthClinicSystem.getAppointments().clear();

        // Set up System.out redirection
        System.setOut(new PrintStream(outContent));

        // Reset Scanner
        HealthClinicSystem.scanner = new Scanner(System.in);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

   

    @Test
    public void testLoginPatient_Failure() {
        HealthClinicSystem.getPatients().add(new Patient("John", "john@example.com", "pass123"));
        String input = "wrong@example.com\nwrongpass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.loginPatient();

        assertTrue(outContent.toString().contains("Invalid credentials!"));
        
        
    }

    // ID 2, 3: View Doctor Profiles
    @Test
    public void testViewDoctorProfiles_WithDoctors() {
        HealthClinicSystem.getDoctors().add(new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York"));
        HealthClinicSystem.viewDoctorProfiles();

        assertTrue(outContent.toString().contains("Dr. Smith - Specialization: Cardiology - Location: New York"));
    }

    // ID 4: Book Appointment
    @Test
    public void testBookAppointment_Success() {
        Patient patient = new Patient("John", "john@example.com", "pass123");
        HealthClinicSystem.getDoctors().add(new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York"));
        String input = "1\n2025-04-27\n14:30\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.bookAppointment(patient);

        assertEquals(1, HealthClinicSystem.getAppointments().size());
        assertTrue(outContent.toString().contains("Appointment booking request sent to Dr. Smith"));
    }

    @Test
    public void testBookAppointment_ExitAtDoctorSelection() {
        Patient patient = new Patient("John", "john@example.com", "pass123");
        HealthClinicSystem.getDoctors().add(new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York"));
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.bookAppointment(patient);

        assertEquals(0, HealthClinicSystem.getAppointments().size());
        assertTrue(outContent.toString().contains("Booking canceled."));
    }

    @Test
    public void testBookAppointment_InvalidDoctor() {
        Patient patient = new Patient("John", "john@example.com", "pass123");
        HealthClinicSystem.getDoctors().add(new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York"));
        String input = "2\n2025-04-27\n14:30\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.bookAppointment(patient);

        assertEquals(0, HealthClinicSystem.getAppointments().size());
        assertTrue(outContent.toString().contains("Invalid selection!"));
    }

    // ID 5: Update Doctor Profile
    @Test
    public void testUpdateDoctorProfile_Specialization() {
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");
        String input = "1\nNeurology\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.updateDoctorProfile(doctor);

        assertEquals("Neurology", doctor.getSpecialization());
        assertTrue(outContent.toString().contains("Specialization updated successfully!"));
    }

    // ID 6: View Patient Appointments
    @Test
    public void testViewAppointments_WithAppointments() {
        Patient patient = new Patient("John", "john@example.com", "pass123");
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");
        Appointment appointment = new Appointment(patient, doctor, "2025-04-27", "14:30");
        patient.appointments.add(appointment);

        HealthClinicSystem.viewAppointments(patient);

        assertTrue(outContent.toString().contains("Doctor: Smith - Date: 2025-04-27 - Time: 14:30"));
    }

    @Test
    public void testViewAppointments_NoAppointments() {
        Patient patient = new Patient("John", "john@example.com", "pass123");

        HealthClinicSystem.viewAppointments(patient);

        assertTrue(outContent.toString().contains("You have no appointments scheduled."));
    }

    // ID 7: Provide Ratings/Reviews (Assumed in FXML, testing Doctor.addRating())
    @Test
    public void testAddRating() {
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");
        doctor.addRating(4, "Great doctor", "John");

        assertEquals(1, doctor.getRatings().size());
        Rating rating = doctor.getRatings().get(0);
        assertEquals(4, rating.getScore());
        assertEquals("Great doctor", rating.getReview());
        assertEquals("John", rating.getPatientName());
    }

    // ID 8: Manage Schedule (Pending Appointments)
    @Test
    public void testManagePendingAppointments_Accept() {
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");
        Patient patient = new Patient("John", "john@example.com", "pass123");
        Appointment appointment = new Appointment(patient, doctor, "2025-04-27", "14:30");
        doctor.appointments.add(appointment);
        String input = "1\n1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.managePendingAppointments(doctor);

        assertEquals("Accepted", appointment.getStatus());
        assertTrue(outContent.toString().contains("Appointment accepted successfully!"));
    }

    @Test
    public void testManagePendingAppointments_NoPending() {
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");

        HealthClinicSystem.managePendingAppointments(doctor);

        assertTrue(outContent.toString().contains("No pending appointment requests."));
    }

    // ID 9: View Doctor Appointments
    @Test
    public void testViewDoctorAppointments_WithAppointments() {
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");
        Patient patient = new Patient("John", "john@example.com", "pass123");
        Appointment appointment = new Appointment(patient, doctor, "2025-04-27", "14:30");
        doctor.appointments.add(appointment);

        HealthClinicSystem.viewDoctorAppointments(doctor);

        assertTrue(outContent.toString().contains("Patient: John - Date: 2025-04-27 - Time: 14:30"));
    }

    // ID 10: Manage Patient Accounts
    @Test
    public void testManagePatientAccounts_Edit() {
        HealthClinicSystem.getPatients().add(new Patient("John", "john@example.com", "pass123"));
        String input = "1\n1\n1\nJane\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.managePatientAccounts();

        assertEquals("Jane", HealthClinicSystem.getPatients().get(0).getName());
        assertTrue(outContent.toString().contains("Name updated successfully!"));
    }

    @Test
    public void testManagePatientAccounts_Delete() {
        HealthClinicSystem.getPatients().add(new Patient("John", "john@example.com", "pass123"));
        String input = "1\n2\nCONFIRM\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.managePatientAccounts();

        assertEquals(0, HealthClinicSystem.getPatients().size());
        assertTrue(outContent.toString().contains("Patient account deleted successfully!"));
    }

    // ID 13: Suspend Accounts (Assumed in FXML, using deletePatientAccount as proxy)
    @Test
    public void testDeletePatientAccount_SuspendProxy() {
        Patient patient = new Patient("John", "john@example.com", "pass123");
        HealthClinicSystem.getPatients().add(patient);
        String input = "CONFIRM\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        HealthClinicSystem.scanner = new Scanner(System.in);

        HealthClinicSystem.deletePatientAccount(patient);

        assertEquals(0, HealthClinicSystem.getPatients().size());
        assertTrue(outContent.toString().contains("Patient account deleted successfully!"));
    }

    // ID 15: Cancel Appointment (Assumed in FXML, using Patient.removeAppointment)
    @Test
    public void testCancelAppointment() {
        Patient patient = new Patient("John", "john@example.com", "pass123");
        Doctor doctor = new Doctor("Smith", "smith@example.com", "pass", "Cardiology", "New York");
        Appointment appointment = new Appointment(patient, doctor, "2025-04-27", "14:30");
        patient.appointments.add(appointment);

        patient.removeAppointment(appointment);

        assertEquals(0, patient.appointments.size());
    }
}