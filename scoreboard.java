import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameScoreboard extends Application {

    public void start(Stage primaryStage) {

        TableColumn<Player, String> nameCol = new TableColumn<>("Player Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Player, Integer> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));


        TableView<Player> table = new TableView<>();
        table.getColumns().add(nameCol);
        table.getColumns().add(scoreCol);


        ObservableList<Player> players = FXCollections.observableArrayList(
                new Player("Player 1", 10),
                new Player("Player 2", 15),
                new Player("Player 3", 20),
                new Player("Player 4", 5)
        );


        table.setItems(players);


        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(table);


        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);


        Button resetButton = new Button("Reset Scores");
        resetButton.setOnAction(e -> {

            for (Player p : players) {
                p.setScore(0);
            }
        });


        hbox.getChildren().add(resetButton);


        BorderPane root = new BorderPane();
        root.setCenter(vbox);
        root.setBottom(hbox);


        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static class Player {
        private final SimpleStringProperty name;
        private final SimpleIntegerProperty score;

        public Player(String name, int score) {
            this.name = new SimpleStringProperty(name);
            this.score = new SimpleIntegerProperty(score);
        }

        public String getName() {
            return name.get();
        }

        public int getScore() {
            return score.get();
        }

        public void setScore(int score) {
            this.score.set(score);
        }
    }
}
