import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.List;

public class ViewDoctorProfilesController {

    @FXML
    private ListView<String> doctorsListView;

    // Load the list of doctors into the ListView
    public void initialize() {
        loadDoctors();
    }

    // Load available doctors into the ListView
    private void loadDoctors() {
        List<Doctor> doctors = HealthClinicSystem.getDoctors();
        if (doctors.isEmpty()) {
            doctorsListView.getItems().add("No doctors available.");
        } else {
            for (Doctor doctor : doctors) {
                String doctorDetails = "Dr. " + doctor.getName() + 
                                      " - " + doctor.getSpecialization() + 
                                      " - Location: " + doctor.getLocation();
                doctorsListView.getItems().add(doctorDetails);
            }
        }
    }
}