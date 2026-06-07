import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.util.List;

public class AllAppointmentsController {

    AllAppointmentsController(){
        loadAllAppointments();
    }

    @FXML
    private ListView<HBox> allAppointmentsListView;

    private Doctor doctor;

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        loadAllAppointments();
    }

    private void loadAllAppointments() {
        allAppointmentsListView.getItems().clear();

        List<Appointment> appointments = doctor.appointments;
        for (Appointment appointment : appointments) {
            // Create label for appointment details
            Label label = new Label(
                "Patient: " + appointment.patient.getName() +
                " - Date: " + appointment.date +
                " - Time: " + appointment.time +
                " - Status: " + appointment.getStatus()
            );
            label.setStyle("-fx-font-size: 14px;");
            label.setPrefWidth(550); // Adjusted to fit within ListView and leave space for button

            // Create Completed button
            Button completeButton = new Button("Completed");
            completeButton.setPrefWidth(100);
            completeButton.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;");

            // Enable button only for Accepted status
            if ("Accepted".equalsIgnoreCase(appointment.getStatus())) {
                completeButton.setOnAction(event -> {
                    appointment.setStatus("Completed");
                    showAlert("Success", "Appointment marked as completed.");
                    loadAllAppointments(); // Refresh the list
                });
            } else {
                completeButton.setDisable(true);
                completeButton.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-opacity: 0.5;");
            }

            // Create HBox to hold label and button
            HBox hbox = new HBox(label, completeButton);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setSpacing(15); // Increased spacing for better visibility
            hbox.setStyle("-fx-padding: 5 10 5 10; -fx-background-color: #ffffff;"); // Match screenshot's white background

            allAppointmentsListView.getItems().add(hbox);
        }

        // Handle empty appointments list
        if (allAppointmentsListView.getItems().isEmpty()) {
            HBox emptyBox = new HBox(new Label("No appointments found."));
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setStyle("-fx-padding: 10;");
            allAppointmentsListView.getItems().add(emptyBox);
        }
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) allAppointmentsListView.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Error", "Could not close the window.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}