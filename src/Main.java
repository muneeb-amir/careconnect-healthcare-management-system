import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            
            // Create a scene with the loaded FXML
            Scene scene = new Scene(root);
            
            // Set the stage properties
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HealthClinicSystem.loader();
        databaseconnection db = new databaseconnection();
        db.getConnection();
        launch(args);                      
       // HealthClinicSystem.deloader();
    }
}