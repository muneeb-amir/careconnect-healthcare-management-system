import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;

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
        allAppointmentsListView.getItems().clear(); // Clear previous entries

        List<Appointment> appointments = doctor.appointments;
        for (Appointment appointment : appointments) {
            Label appointmentLabel = new Label(
                "Patient: " + appointment.patient.getName() +
                " - Date: " + appointment.date +
                " - Time: " + appointment.time +
                " - Status: " + appointment.getStatus()
            );

            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().add(appointmentLabel);

            // Add "Completed" button only if status is "Accepted"
            if ("Accepted".equalsIgnoreCase(appointment.getStatus())) {
                Button completeButton = new Button("Completed");
                completeButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
                completeButton.setOnAction(e -> {
                     appointment.status="Completed";
                    showAlert("Success", "Appointment marked as completed.");
                    doctor.earnings+=30;
                    loadAllAppointments(); // Refresh the list
                });
                hbox.getChildren().add(completeButton);
            }

            allAppointmentsListView.getItems().add(hbox);
        }

        if (allAppointmentsListView.getItems().isEmpty()) {
            allAppointmentsListView.getItems().add(new HBox(new Label("No appointments found.")));
        }
    }

    // Handle Back button click
    @FXML
    private void handleBack() {
        try {
            Stage currentStage = (Stage) allAppointmentsListView.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to close the current window.");
        }
    }

    // Helper method to show alert dialogs
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
