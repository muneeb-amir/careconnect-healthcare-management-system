import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UpdateDoctorProfileController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField specializationField;

    @FXML
    private TextField locationField;

    private Doctor doctor; // The logged-in doctor
    private Stage dashboardStage; // Reference to the dashboard stage

    // Set the logged-in doctor
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        loadDoctorProfile(); // Load the doctor's profile information
    }

    // Set the dashboard stage
    public void setDashboardStage(Stage dashboardStage) {
        this.dashboardStage = dashboardStage;
    }

    // Load the doctor's profile information into the input fields
    private void loadDoctorProfile() {
        nameField.setText(doctor.getName());
        specializationField.setText(doctor.getSpecialization());
        locationField.setText(doctor.getLocation());
    }

    // Handle Update Profile button click
    // Handle Update Profile button click
@FXML
private void handleUpdateProfile() {
    // Get the updated values from the input fields
    String name2 = nameField.getText().trim();
    String specialization2 = specializationField.getText().trim();
    String location2 = locationField.getText().trim();

    // Validate input fields
    if (name2.isEmpty() || specialization2.isEmpty() || location2.isEmpty()) {
        showAlert("Error", "Please fill in all fields.");
        return;
    }

    // Update the doctor's profile
    doctor.name = name2;
    doctor.specialization = specialization2;
    doctor.location = location2;

    // Show success message
    showAlert("Success", "Profile updated successfully!");

    // Refresh the dashboard and return to it
    refreshDashboard();
}

// Refresh the dashboard
private void refreshDashboard() {
    try {
        // Close the current Update Profile window
        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.close();

        // Show the hidden dashboard
        if (dashboardStage != null) {
            // Reload the dashboard to reflect the updated profile
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorDashboard.fxml"));
            AnchorPane root = loader.load();

            // Pass the updated doctor object to the dashboard controller
            DoctorDashboardController controller = loader.getController();
            controller.setUsername(doctor.getName()); // Set the updated username
            controller.setStage(dashboardStage); // Set the stage

            // Set the new scene for the Doctor Dashboard
            Scene doctorScene = new Scene(root);
            dashboardStage.setScene(doctorScene);
            dashboardStage.setTitle("Doctor Dashboard");
            dashboardStage.show();
        }
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error", "Failed to refresh the dashboard.");
    }
}

    // Handle Back button click
    @FXML
    private void handleBack() {
        try {
            // Close the current window
            Stage currentStage = (Stage) nameField.getScene().getWindow();
            currentStage.close();

            // Show the hidden dashboard
            if (dashboardStage != null) {
                dashboardStage.show();
            }
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