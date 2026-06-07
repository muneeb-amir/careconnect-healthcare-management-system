import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ViewAppointmentsController {

    @FXML
    private ListView<HBox> appointmentsListView;

    private String username;
    private Patient patient;

    public void setUsername(String username) {
        this.username = username;
        List<Patient> patients = HealthClinicSystem.getPatients();
        for (Patient p : patients) {
            if (p.getName().equals(username)) {
                this.patient = p;
                break;
            }
        }

        if (this.patient == null) {
            showAlert("Error", "Patient not found!");
        } else {
            loadAppointments();
        }
    }

    private void loadAppointments() {
        appointmentsListView.getItems().clear();
        if (patient == null) {
            HBox hBox = new HBox(new Label("No patient data available."));
            hBox.setStyle("-fx-padding: 10; -fx-alignment: center-left;");
            appointmentsListView.getItems().add(hBox);
            return;
        }

        List<Appointment> appointments = patient.appointments;
        if (appointments.isEmpty()) {
            HBox hBox = new HBox(new Label("No appointments found."));
            hBox.setStyle("-fx-padding: 10; -fx-alignment: center-left;");
            appointmentsListView.getItems().add(hBox);
        } else {
            for (Appointment appointment : appointments) {
                String details = String.format("Dr. %s - %s %s - Status: %s",
                        appointment.doctor.getName(),
                        appointment.date,
                        appointment.time,
                        appointment.getStatus());

                Label label = new Label(details);
                label.setStyle("-fx-font-size: 15px; -fx-text-fill: #333333;");

                HBox hBox = new HBox(label);
                hBox.setSpacing(15);
                hBox.setStyle("-fx-padding: 10; -fx-alignment: center-left; -fx-background-color: #FFFFFF; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0;");

                // Add mouse hover effect for the HBox
                hBox.setOnMouseEntered(e -> hBox.setStyle("-fx-padding: 10; -fx-alignment: center-left; -fx-background-color: #F0F4FF; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0;"));
                hBox.setOnMouseExited(e -> hBox.setStyle("-fx-padding: 10; -fx-alignment: center-left; -fx-background-color: #FFFFFF; -fx-border-color: #E0E0E0; -fx-border-width: 0 0 1 0;"));

                // Add Cancel Appointment button for non-completed appointments
                if (!appointment.getStatus().equalsIgnoreCase("Completed")) {
                    Button cancelButton = new Button("Cancel Appointment");
                    cancelButton.setStyle("-fx-background-color: #FF4D4F; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
                    // Hover effect
                    cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #E63946; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                    cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #FF4D4F; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                    cancelButton.setOnAction(e -> cancelAppointment(appointment));
                    hBox.getChildren().add(cancelButton);
                }

                // Add Review Doctor button for completed appointments
                if (appointment.getStatus().equalsIgnoreCase("Completed")) {
                    Button reviewButton = new Button("Review Doctor!");
                    reviewButton.setStyle("-fx-background-color: #28A745; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
                    // Hover effect
                    reviewButton.setOnMouseEntered(e -> reviewButton.setStyle("-fx-background-color: #218838; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                    reviewButton.setOnMouseExited(e -> reviewButton.setStyle("-fx-background-color: #28A745; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                    reviewButton.setOnAction(e -> openReviewPage(appointment.doctor));
                    hBox.getChildren().add(reviewButton);
                }

                appointmentsListView.getItems().add(hBox);
            }
        }
    }

    private void cancelAppointment(Appointment appointment) {
        // Create and style confirmation alert
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Cancellation");
        confirmAlert.setHeaderText(null); // Remove header for cleaner look
        confirmAlert.setContentText("Are you sure you want to cancel ?");

        // Style the dialog pane
        DialogPane dialogPane = confirmAlert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #F5F7FA; -fx-border-color: #D3DCE6; -fx-border-width: 1; -fx-border-radius: 8; -fx-padding: 20;");

        // Style the content text
        Label contentLabel = (Label) dialogPane.lookup(".content");
        if (contentLabel != null) {
            contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333; -fx-alignment: center;");
        }

        // Style the buttons
        ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
        if (buttonBar != null) {
            buttonBar.getButtons().forEach(button -> {
                if (button instanceof Button) {
                    Button btn = (Button) button;
                    btn.setStyle("-fx-background-color: #1A73E8; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
                    btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #1557B0; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                    btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #1A73E8; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                }
            });
        }

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            // Remove appointment from patient, doctor, and global lists
            patient.removeAppointment(appointment);
            appointment.doctor.appointments.remove(appointment);
            HealthClinicSystem.appointments.remove(appointment);

            // Update database
            try (Connection conn = databaseconnection.getConnection()) {
                String deleteQuery = "DELETE FROM Appointments WHERE patient_id = ? AND doctor_id = ? AND date = ? AND time = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                    pstmt.setInt(1, appointment.patient.getEmail().hashCode());
                    pstmt.setInt(2, appointment.doctor.getEmail().hashCode());
                    pstmt.setString(3, appointment.date);
                    pstmt.setString(4, appointment.time);
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                showAlert("Database Error", "Failed to update database: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Refresh the appointments list
            loadAppointments();
            showAlert("Success", "Appointment cancelled successfully!");
        }
    }

    private void openReviewPage(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorReview.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Review Doctor");
    
            Scene scene = new Scene(loader.load());
            DoctorReviewController controller = loader.getController();
            controller.setDoctor(doctor);
            controller.setPatientName(username);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open review page: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the information alert for consistency
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #F5F7FA; -fx-border-color: #D3DCE6; -fx-border-width: 1; -fx-border-radius: 8; -fx-padding: 20;");

        Label contentLabel = (Label) dialogPane.lookup(".content");
        if (contentLabel != null) {
            contentLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #333333; -fx-alignment: center;");
        }

        ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
        if (buttonBar != null) {
            buttonBar.getButtons().forEach(button -> {
                if (button instanceof Button) {
                    Button btn = (Button) button;
                    btn.setStyle("-fx-background-color: #1A73E8; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;");
                    btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #1557B0; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                    btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #1A73E8; -fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-padding: 8 15; -fx-background-radius: 5;"));
                }
            });
        }

        alert.showAndWait();
    }
}