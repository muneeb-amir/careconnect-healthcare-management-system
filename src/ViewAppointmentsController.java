import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;

public class ViewAppointmentsController {

    @FXML
    private ListView<String> appointmentsListView; // Add @FXML annotation

    private String username;
    private Patient patient; // The logged-in patient

    // Set the username and find the corresponding patient
    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);

        List<Patient> patients = HealthClinicSystem.getPatients();
        for (Patient p : patients) {
            if (p.getName().equals(username)) {
                this.patient = p;
                break;
            }
        }

        if (this.patient == null) {
            showAlert("Error", "Patient not found!");
        } else {
            loadAppointments(); // Load appointments if the patient is found
        }
    }

    // Load the patient's appointments into the ListView
    private void loadAppointments() {
        if (patient == null) {
            appointmentsListView.getItems().add("No patient data available.");
            return;
        }

        List<Appointment> appointments = patient.appointments;
        if (appointments.isEmpty()) {
            appointmentsListView.getItems().add("No appointments found.");
        } else {
            // Clear the ListView before adding new items
            appointmentsListView.getItems().clear();

            // Add each appointment to the ListView
            for (Appointment appointment : appointments) {
                String appointmentDetails = String.format(
                    "Dr. %s - %s %s - Status: %s",
                    appointment.doctor.getName(),
                    appointment.date,
                    appointment.time,
                    appointment.getStatus()
                );
                appointmentsListView.getItems().add(appointmentDetails);
            }
        }
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