import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardController {
    @FXML private Label welcomeLabel;
    @FXML private ListView<String> patientsListView;
    @FXML private ListView<String> doctorsListView;

    private Admin currentAdmin;

    // Initialize the dashboard with admin data
    public void initialize(Admin admin) {
        this.currentAdmin = admin;
        welcomeLabel.setText("Welcome, " + admin.name + "!");

        // Load patients and doctors
        loadPatients();
        loadDoctors();
    }

    // Load patients into the ListView
    private void loadPatients() {
        List<String> patients = new ArrayList<>();
        for (Patient patient : HealthClinicSystem.patients) {
            patients.add("Name: " + patient.name + " | Email: " + patient.email);
        }
        patientsListView.getItems().clear(); // Clear existing items
        patientsListView.getItems().addAll(patients); // Add new items
    }

    // Load doctors into the ListView
    private void loadDoctors() {
        List<String> doctors = new ArrayList<>();
        for (Doctor doctor : HealthClinicSystem.doctors) {
            doctors.add("Dr. " + doctor.name + " | Specialization: " + doctor.specialization + " | Location: " + doctor.location);
        }
        doctorsListView.getItems().clear(); // Clear existing items
        doctorsListView.getItems().addAll(doctors); // Add new items
    }

    // Handle logout
    @FXML
    private void handleLogout() {
        try {
            // Load the login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();

            // Set the new scene (login page)
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login.fxml: " + e.getMessage());
        }
    }
}