import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public class DoctorRegisterController {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField specializationField;
    
    @FXML
    private TextField locationField;
    
    @FXML
    private void handleDoctorRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String specialization = specializationField.getText().trim();
        String location = locationField.getText().trim();
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || specialization.isEmpty() || location.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }
        
        HealthClinicSystem.doctors.add(new Doctor(name, email, password, specialization, location));
        showAlert("Registration successful.");
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Change to INFORMATION type
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    

        @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the Login.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            
            // Get the current stage (window)
            Stage stage = (Stage) nameField.getScene().getWindow();
            
            // Set the new scene to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load the login page.");
        }
    }
}
