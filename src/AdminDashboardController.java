import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public void initialize(Admin admin) {
        this.currentAdmin = admin;
        welcomeLabel.setText("Welcome, " + admin.name + "!");
        loadPatients();
        loadDoctors();
    }

    private void loadPatients() {
        List<String> patients = new ArrayList<>();
        for (Patient patient : HealthClinicSystem.patients) {
            patients.add("Name: " + patient.name + " | Email: " + patient.email);
        }
        patientsListView.getItems().setAll(patients);
    }

    private void loadDoctors() {
        List<String> doctors = new ArrayList<>();
        for (Doctor doctor : HealthClinicSystem.doctors) {
            doctors.add("Dr. " + doctor.name + " | Specialization: " + doctor.specialization + " | Location: " + doctor.location);
        }
        doctorsListView.getItems().setAll(doctors);
    }

    @FXML
    private void deleteSelectedPatient() {
        int index = patientsListView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && index < HealthClinicSystem.patients.size()) {
            Patient removed = HealthClinicSystem.patients.remove(index);
            patientsListView.getItems().remove(index);
            showAlert("Patient Deleted", "Patient " + removed.name + " has been removed.");
        } else {
            showAlert("No Selection", "Please select a patient to delete.");
        }
    }

    @FXML
    private void deleteSelectedDoctor() {
        int index = doctorsListView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && index < HealthClinicSystem.doctors.size()) {
            Doctor removed = HealthClinicSystem.doctors.remove(index);
            doctorsListView.getItems().remove(index);
            showAlert("Doctor Deleted", "Doctor " + removed.name + " has been removed.");
        } else {
            showAlert("No Selection", "Please select a doctor to delete.");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            HealthClinicSystem.deloader();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login.fxml: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
