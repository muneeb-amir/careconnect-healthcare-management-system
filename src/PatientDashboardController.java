import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PatientDashboardController {

    public String username; // To store the logged-in patient's username

    @FXML
    private Button bookAppointmentButton;

    @FXML
    private Button viewAppointmentsButton;

    @FXML
    private Button viewDoctorProfilesButton;

    @FXML
    private Button logoutButton;

    // Set the username of the logged-in patient
    @FXML
    public void setUsername1(String username) {
        this.username = username;
        System.out.println(username);
    }

    // Handle the "Book Appointment" action
    @FXML
    private void handleBookAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookAppointment.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Book Appointment");
            stage.setScene(new Scene(root));
            stage.show();

            // Pass the username to the BookAppointmentController if needed
             BookAppointmentController controller = loader.getController();
             controller.setUsername(username);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Book Appointment view.");
        }
    }

    // Handle the "View Appointments" action
    @FXML
    private void handleViewAppointments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAppointments.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Appointments");
            stage.setScene(new Scene(root));
            stage.show();

            // Pass the username to the ViewAppointmentsController if needed
             ViewAppointmentsController controller = loader.getController();
             controller.setUsername(username);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the View Appointments view.");
        }
    }

    // Handle the "View Doctor Profiles" action
    @FXML
    private void handleViewDoctorProfiles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewDoctorProfiles.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Doctor Profiles");
            stage.setScene(new Scene(root));
            stage.show();

            // Pass the username to the ViewDoctorProfilesController if needed
            // ViewDoctorProfilesController controller = loader.getController();
            // controller.setUsername(username);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the View Doctor Profiles view.");
        }
    }

    // Handle the "Logout" action
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current patient dashboard window
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the Login view.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Ensure it's INFORMATION type
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
}