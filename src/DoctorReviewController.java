import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorReviewController implements Initializable {

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private HBox starBox;

    private Doctor doctor;
    private String patientName;
    private int selectedRating = 0;
    private final Label[] starLabels = new Label[5];

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatientName(String name) {
        this.patientName = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 5; i++) {
            Label star = new Label("☆");
            star.setFont(new Font(24));
            int index = i;
            star.setOnMouseClicked((MouseEvent e) -> updateStars(index + 1));
            starLabels[i] = star;
            starBox.getChildren().add(star);
        }
    }

    private void updateStars(int rating) {
        selectedRating = rating;
        for (int i = 0; i < 5; i++) {
            starLabels[i].setText(i < rating ? "★" : "☆");
        }
    }

    @FXML
    public void submitReview() {
        String review = reviewTextArea.getText().trim();

        if (selectedRating == 0) {
            showAlert("Error", "Please select a star rating.");
            return;
        }

        if (review.isEmpty()) {
            showAlert("Error", "Review text cannot be empty.");
            return;
        }

        doctor.addRating(selectedRating, review, patientName);
        showAlert("Success", "Review submitted successfully!");
        starBox.getScene().getWindow().hide();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
