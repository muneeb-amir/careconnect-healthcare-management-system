import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookAppointmentController {

    @FXML
    private ListView<String> doctorListView;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    private String username; // The logged-in patient's username
    private Patient patient; // The logged-in patient

    // Set the logged-in patient's username
    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);

        // Find the patient in the list of patients
        List<Patient> patients = HealthClinicSystem.getPatients();
        for (Patient p : patients) {
            if (p.getName().equals(username)) {
                this.patient = p; // Set the patient object
                break;
            }
        }

        if (this.patient != null) {
            loadDoctors(); // Load available doctors
            loadTimes(); // Load available times
        } else {
            showAlert("Error", "Patient not found!");
        }
    }

    // Load available doctors into the ListView
    private void loadDoctors() {
        List<Doctor> doctors = HealthClinicSystem.getDoctors();
        for (Doctor doctor : doctors) {
            doctorListView.getItems().add("Dr. " + doctor.getName() + " - " + doctor.getSpecialization() + " - " + doctor.getLocation());
        }
    }

    // Load available times into the ComboBox
    private void loadTimes() {
        List<String> times = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0); // Start time (e.g., 9:00 AM)
        LocalTime endTime = LocalTime.of(17, 0); // End time (e.g., 5:00 PM)

        while (startTime.isBefore(endTime)) {
            times.add(startTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            startTime = startTime.plusMinutes(30); // Add celebration intervals
        }

        timeComboBox.getItems().addAll(times); // Add times to the ComboBox
    }

    // Handle Book Appointment button click
    @FXML
    private void handleBookAppointment() {
        String selectedDoctor = doctorListView.getSelectionModel().getSelectedItem();
        LocalDate date = datePicker.getValue();
        String time = timeComboBox.getValue();

        // Validate inputs
        if (selectedDoctor == null || date == null || time == null) {
            showAlert("Error", "Please select a doctor, date, and time.");
            return;
        }

        // Validate date (prevent past dates)
        LocalDate currentDate = LocalDate.now();
        if (date.isBefore(currentDate)) {
            showAlert("Error", "Choose a valid date.");
            return;
        }

        // Extract doctor name from the selected item
        String doctorName = selectedDoctor.split(" - ")[0].substring(4); // Remove "Dr. " prefix

        // Find the selected doctor
        Doctor doctor = null;
        for (Doctor d : HealthClinicSystem.getDoctors()) {
            if (d.getName().equals(doctorName)) {
                doctor = d;
                break;
            }
        }

        if (doctor == null) {
            showAlert("Error", "Selected doctor not found.");
            return;
        }

        // Create a new appointment
        Appointment newAppointment = new Appointment(patient, doctor, date.toString(), time);
        patient.appointments.add(newAppointment);
        doctor.appointments.add(newAppointment);
        HealthClinicSystem.appointments.add(newAppointment);

        // Show success message
        showAlert("Success", "Appointment booked successfully!");

        // Close the current window
        Stage currentStage = (Stage) doctorListView.getScene().getWindow();
        currentStage.close();
    }

    // Handle Cancel button click
    @FXML
    private void handleCancel() {
        Stage currentStage = (Stage) doctorListView.getScene().getWindow();
        currentStage.close();
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}