import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.text.NumberFormat;

public class DoctorEarningsController {

    @FXML
    private Label earningsLabel;

    private Doctor doctor;

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        updateEarnings();
    }

    private void updateEarnings() {
        if (doctor != null) {
            double earnings = doctor.earnings; // Or doctor.getEarnings()
            String formatted = NumberFormat.getCurrencyInstance().format(earnings);
            earningsLabel.setText("Your Earnings: " + formatted);
        } else {
            earningsLabel.setText("Doctor not set.");
        }
    }
}
