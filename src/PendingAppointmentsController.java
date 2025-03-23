import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class PendingAppointmentsController {

    @FXML
    private ListView<HBox> pendingAppointmentsListView;

    private Doctor doctor; // The logged-in doctor

    // Set the logged-in doctor
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        loadPendingAppointments(); // Load pending appointments when the doctor is set
    }

    // Load pending appointments into the ListView
    private void loadPendingAppointments() {
        List<Appointment> appointments = doctor.appointments;
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals("Pending")) {
                // Create a label for the appointment details
                Label appointmentLabel = new Label(
                    "Patient: " + appointment.patient.getName() + 
                    " - Date: " + appointment.date + 
                    " - Time: " + appointment.time
                );

                // Create Accept button
                Button acceptButton = new Button("Accept");
                acceptButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
                acceptButton.setOnAction(e -> handleAcceptAppointment(appointment));

                // Create Reject button
                Button rejectButton = new Button("Reject");
                rejectButton.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white; -fx-font-size: 14px;");
                rejectButton.setOnAction(e -> handleRejectAppointment(appointment));

                // Add the label and buttons to an HBox
                HBox hbox = new HBox(10, appointmentLabel, acceptButton, rejectButton);
                hbox.setAlignment(Pos.CENTER_LEFT);

                // Add the HBox to the ListView
                pendingAppointmentsListView.getItems().add(hbox);
            }
        }

        // If no pending appointments, show a message
        if (pendingAppointmentsListView.getItems().isEmpty()) {
            pendingAppointmentsListView.getItems().add(new HBox(new Label("No pending appointments found.")));
        }
    }

    // Handle Accept button click
    private void handleAcceptAppointment(Appointment appointment) {
        appointment.status="Accepted";
        showAlert("Success", "Appointment accepted successfully!");
        refreshAppointmentsList(); // Refresh the list after accepting
    }

    // Handle Reject button click
    private void handleRejectAppointment(Appointment appointment) {
        appointment.status="Rejected";
        showAlert("Success", "Appointment rejected successfully!");
        refreshAppointmentsList(); // Refresh the list after rejecting
    }

    // Refresh the appointments list
    private void refreshAppointmentsList() {
        pendingAppointmentsListView.getItems().clear(); // Clear the current list
        loadPendingAppointments(); // Reload the list
    }

    
// Handle Back button click
@FXML
private void handleBack() {
    try {
        // Close the current window
        Stage currentStage = (Stage) pendingAppointmentsListView.getScene().getWindow();
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