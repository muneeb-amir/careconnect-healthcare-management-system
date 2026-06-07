import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DoctorRatingsViewController {

    @FXML
    private ListView<String> ratingsListView;

    @FXML
    private Label doctorInfoLabel;

    public void setDoctor(Doctor doctor) {
        ratingsListView.getItems().clear();

        if (doctor.getRatings().isEmpty()) {
            doctorInfoLabel.setText("Dr. " + doctor.getName() + " (No ratings yet)");
            ratingsListView.getItems().add("No ratings available for this doctor.");
        } else {
            double avgRating = doctor.getRatings().stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

            String avgStars = getStars((int) Math.round(avgRating));
            doctorInfoLabel.setText(String.format("Dr. %s (%s %.1f/5)", doctor.getName(), avgStars, avgRating));

            for (Rating rating : doctor.getRatings()) {
                String stars = getStars(rating.getScore());
                String entry = String.format(
                    "Patient: %s\nRating: %s (%d/5)\nReview: %s",
                    rating.getPatientName(), stars, rating.getScore(), rating.getReview()
                );
                ratingsListView.getItems().add(entry);
            }
        }
    }

    private String getStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            stars.append(i < rating ? "★" : "☆");
        }
        return stars.toString();
    }
}
