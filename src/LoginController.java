import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Hyperlink patientRegisterLink;

    @FXML
    private Hyperlink doctorRegisterLink;

    @FXML
    public void initialize() {
        // Populate the ComboBox with roles
        roleComboBox.getItems().addAll("Patient", "Doctor", "Admin");
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String selectedRole = roleComboBox.getValue();

        // Validate input fields
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Login Failed", "Please enter both email and password.");
            return;
        }

        if (selectedRole == null) {
            showAlert("Login Failed", "Please select a role to login.");
            return;
        }

        // Authenticate user based on selected role
        boolean authenticated = authenticateUser(email, password, selectedRole);

        if (!authenticated) {
            showAlert("Login Failed", "Invalid email or password for the selected role.");
        }
    }

    @FXML
    private void goToPatientRegister() {
        navigateToPage("PatientRegister.fxml", "Patient Registration");
    }

    @FXML
    private void goToDoctorRegister() {
        navigateToPage("DoctorRegister.fxml", "Doctor Registration");
    }

    private void navigateToPage(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane registerRoot = loader.load();

            Scene registerScene = new Scene(registerRoot);
            Stage stage = (Stage) roleComboBox.getScene().getWindow();
            stage.setScene(registerScene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open " + title + " page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Authenticate user based on role
     */
    private boolean authenticateUser(String email, String password, String role) {
        
        try {

            if (role.equals("Patient")) {
                // Authenticate Patient
                Patient patient = HealthClinicSystem.authenticatePatient(email, password);
                if (patient != null) {
                    // Open the Patient Dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientDashboard.fxml"));
                    AnchorPane patientRoot = loader.load();

                    // Pass the patient's name to the dashboard controller
                    PatientDashboardController controller = loader.getController();
                    controller.setUsername1(patient.getName());

                    // Set the new scene for the Patient Dashboard
                    Scene patientScene = new Scene(patientRoot);
                    Stage stage = (Stage) roleComboBox.getScene().getWindow();
                    stage.setScene(patientScene);
                    stage.setTitle("Patient Dashboard");
                    stage.show();

                    return true; // Authentication successful
                }
            } // In the LoginController's authenticateUser method (for doctor role)
            else if (role.equals("Doctor")) {
                // Authenticate Doctor
                Doctor doctor = HealthClinicSystem.authenticateDoctor(email, password);
                if (doctor != null) {
                    // Open the Doctor Dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorDashboard.fxml"));
                    AnchorPane doctorRoot = loader.load();
            
                    // Pass the doctor's name to the dashboard controller
                    DoctorDashboardController controller = loader.getController();
                    controller.setUsername(doctor.getName());
            
                    // Set the current stage (dashboard)
                    Stage stage = (Stage) roleComboBox.getScene().getWindow();
                    controller.setStage(stage); // Set the current stage
            
                    // Set the new scene for the Doctor Dashboard
                    Scene doctorScene = new Scene(doctorRoot);
                    stage.setScene(doctorScene);
                    stage.setTitle("Doctor Dashboard");
                    stage.show();
            
                    return true; // Authentication successful
                }
            } else if (role.equals("Admin")) {
                // Authenticate Admin
                Admin admin = HealthClinicSystem.authenticateAdmin(email, password);
                if (admin != null) {
                    // Open the Admin Dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminDashboard.fxml"));
                    AnchorPane adminRoot = loader.load();

                    // Pass the admin's name to the dashboard controller
                    AdminDashboardController controller = loader.getController();
                    controller.initialize(admin); // Pass the logged-in admin object

                    // Set the new scene for the Admin Dashboard
                    Scene adminScene = new Scene(adminRoot);
                    Stage stage = (Stage) roleComboBox.getScene().getWindow();
                    stage.setScene(adminScene);
                    stage.setTitle("Admin Dashboard");
                    stage.show();

                    return true; // Authentication successful
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation Error", "Could not open the dashboard.");
        }

        return false; // Authentication failed
    }
}