import javafx.application.Application
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Image.ImageView;

public class zombie extends ZombieGuy

        @Override
        public void start(Stage primaryStage) {
            Image zombieImage = new Image("zombie-docent.jpeg");

            ImageView zombieImageView = new ImageView(zombieImage)

            StackPane root = new StackPane();
            root.getChildren().add(zombieImageView);

            Scene scene = new Scene(root, 800, 600);





        }
