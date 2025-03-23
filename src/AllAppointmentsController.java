import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.List;

public class AllAppointmentsController {

    @FXML
    private ListView<HBox> allAppointmentsListView;

    private Doctor doctor; // The logged-in doctor

    // Set the logged-in doctor
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        loadAllAppointments(); // Load all appointments when the doctor is set
    }

    // Load all appointments into the ListView
    private void loadAllAppointments() {
        List<Appointment> appointments = doctor.appointments;
        for (Appointment appointment : appointments) {
            // Create a label for the appointment details
            Label appointmentLabel = new Label(
                "Patient: " + appointment.patient.getName() + 
                " - Date: " + appointment.date + 
                " - Time: " + appointment.time + 
                " - Status: " + appointment.getStatus()
            );

            // Add the label to an HBox
            HBox hbox = new HBox(10, appointmentLabel);
            hbox.setAlignment(Pos.CENTER_LEFT);

            // Add the HBox to the ListView
            allAppointmentsListView.getItems().add(hbox);
        }

        // If no appointments, show a message
        if (allAppointmentsListView.getItems().isEmpty()) {
            allAppointmentsListView.getItems().add(new HBox(new Label("No appointments found.")));
        }
    }

    // Handle Back button click
    @FXML
    private void handleBack() {
        try {
            // Close the current window
            Stage currentStage = (Stage) allAppointmentsListView.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to close the current window.");
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