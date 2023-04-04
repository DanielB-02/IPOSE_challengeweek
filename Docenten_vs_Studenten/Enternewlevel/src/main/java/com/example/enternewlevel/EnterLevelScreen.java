import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EnterLevelScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a label with the message "Enter Level 2"
        Label label = new Label("Enter Level 2");

        // Create a StackPane to hold the label and center it in the scene
        StackPane root = new StackPane(label);

        // Create a Scene with the StackPane as the root and set its size
        Scene scene = new Scene(root, 400, 300);

        // Set the scene of the stage and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
