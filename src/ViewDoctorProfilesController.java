import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewDoctorProfilesController {

    @FXML
    private ListView<String> doctorsListView;

    @FXML
    private Button viewRatingButton;

    @FXML
    private ComboBox<String> specializationFilter;

    @FXML
    private ComboBox<String> locationFilter;

    @FXML
    private Button applyFilterButton;

    @FXML
    private Button removeFilterButton;

    private List<Doctor> allDoctors = new ArrayList<>();
    private List<Doctor> filteredDoctors = new ArrayList<>();

    private final List<String> pakistaniCities = Arrays.asList(
        "Karachi", "Lahore", "Islamabad", "Rawalpindi",
        "Peshawar", "Quetta", "Multan", "Faisalabad"
    );

    public void initialize() {
        allDoctors = HealthClinicSystem.getDoctors();
        filteredDoctors = new ArrayList<>(allDoctors);
        populateFilterOptions();
        displayDoctors(filteredDoctors);
        specializationFilter.setPromptText("Filter by Specialization");
        locationFilter.setPromptText("Filter by Location");
    }

    private void populateFilterOptions() {
        specializationFilter.getItems().clear();
        locationFilter.getItems().clear();

        // Specializations from existing doctors
        for (Doctor doctor : allDoctors) {
            if (!specializationFilter.getItems().contains(doctor.getSpecialization())) {
                specializationFilter.getItems().add(doctor.getSpecialization());
            }
        }

        // Use fixed list of Pakistani cities
        locationFilter.getItems().addAll(pakistaniCities);
    }

    private void displayDoctors(List<Doctor> doctorList) {
        doctorsListView.getItems().clear();

        if (doctorList.isEmpty()) {
            doctorsListView.getItems().add("No doctors available.");
            viewRatingButton.setDisable(true);
        } else {
            for (Doctor doctor : doctorList) {
                String info = String.format("Dr. %s - %s - Location: %s",
                        doctor.getName(), doctor.getSpecialization(), doctor.getLocation());
                doctorsListView.getItems().add(info);
            }
            viewRatingButton.setDisable(false);
        }
    }

    @FXML
    private void applyFilter() {
        String selectedSpec = specializationFilter.getValue();
        String selectedLoc = locationFilter.getValue();

        filteredDoctors = allDoctors.stream()
                .filter(d -> (selectedSpec == null || selectedSpec.isEmpty() || d.getSpecialization().equals(selectedSpec)) &&
                             (selectedLoc == null || selectedLoc.isEmpty() || d.getLocation().equals(selectedLoc)))
                .collect(Collectors.toList());

        displayDoctors(filteredDoctors);
    }

    @FXML
    private void removeFilters() {
        specializationFilter.getSelectionModel().clearSelection();
        locationFilter.getSelectionModel().clearSelection();

        populateFilterOptions();

        specializationFilter.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Filter by Specialization" : item);
            }
        });

        locationFilter.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "Filter by Location" : item);
            }
        });

        filteredDoctors = new ArrayList<>(allDoctors);
        displayDoctors(filteredDoctors);
    }

    @FXML
    private void viewRating() {
        int index = doctorsListView.getSelectionModel().getSelectedIndex();

        if (index == -1 || filteredDoctors.isEmpty() || doctorsListView.getItems().get(index).equals("No doctors available.")) {
            showAlert("No Selection", "Please select a doctor to view ratings.");
            return;
        }

        Doctor selectedDoctor = filteredDoctors.get(index);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorRatingsView.fxml"));
            VBox root = loader.load();

            DoctorRatingsViewController controller = loader.getController();
            controller.setDoctor(selectedDoctor);

            Stage stage = new Stage();
            stage.setTitle("Doctor Ratings");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load ratings view.");
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
