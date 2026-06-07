import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DoctorDashboardController {

    @FXML
    private Button viewEarningsButton;
    
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button pendingAppointmentsButton;

    @FXML
    private Button allAppointmentsButton;

    @FXML
    private Button updateProfileButton;

    @FXML
    private Button logoutButton;

    private String username;
    private Doctor doctor; // The logged-in doctor
    private Stage currentStage; // Reference to the current stage (dashboard)

    @FXML
    public void setUsername(String username) {
        this.username = username;
        System.out.println("Logged in as: " + username);
    
        // Find the doctor in the list of doctors
        List<Doctor> doctors = HealthClinicSystem.getDoctors();
        for (Doctor doc : doctors) {
            if (doc.getName().equals(username)) {
                this.doctor = doc; // Set the doctor object
                break;
            }
        }
    
        if (this.doctor != null) {
            welcomeLabel.setText("Doctor Dashboard - Dr. " + doctor.getName()); // Update the welcome label
        } else {
            welcomeLabel.setText("Doctor not found!");
            showAlert("Error", "Doctor not found!");
        }
    }

    // Set the current stage (dashboard)
    public void setStage(Stage stage) {
        this.currentStage = stage;
    }

    // Handle "View Pending Appointment Requests" button click
    @FXML
    private void handlePendingAppointments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PendingAppointments.fxml"));
            AnchorPane root = loader.load();

            // Pass the doctor object to the PendingAppointmentsController
            PendingAppointmentsController controller = loader.getController();
            controller.setDoctor(doctor);

            Stage stage = new Stage();
            stage.setTitle("Pending Appointment Requests");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Pending Appointments view.");
        }
    }

    // Handle "View All Appointments" button click
    @FXML
    private void handleAllAppointments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AllAppointments.fxml"));
            AnchorPane root = loader.load();

            // Pass the doctor object to the AllAppointmentsController
            AllAppointmentsController controller = loader.getController();
            controller.setDoctor(doctor);

            Stage stage = new Stage();
            stage.setTitle("All Appointments");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load All Appointments view.");
        }
    }

    // Handle "Update Profile" button click
    @FXML
    private void handleUpdateProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateDoctorProfile.fxml"));
            AnchorPane root = loader.load();

            // Pass the doctor object and the dashboard stage to the UpdateDoctorProfileController
            UpdateDoctorProfileController controller = loader.getController();
            controller.setDoctor(doctor);
            controller.setDashboardStage(currentStage); // Pass the dashboard stage

            // Hide the current dashboard
            if (currentStage != null) {
                currentStage.hide();
            }

            // Open the Update Profile view
            Stage stage = new Stage();
            stage.setTitle("Update Profile");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Update Profile view.");
        }
    }



@FXML
private void handleViewEarnings() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorEarnings.fxml"));
        AnchorPane root = loader.load();

        DoctorEarningsController controller = loader.getController();
        controller.setDoctor(doctor); // Pass the doctor to earnings view

        Stage stage = new Stage();
        stage.setTitle("Doctor Earnings");
        stage.setScene(new Scene(root));
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
        showAlert("Error", "Failed to load Doctor Earnings view.");
    }
}


    // Handle "Logout" button click
    @FXML
    private void handleLogout() {
        try {
            // Close the current doctor dashboard window
            if (currentStage != null) {
                currentStage.close();
            }
            HealthClinicSystem.deloader();
            // Open the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Login view.");
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