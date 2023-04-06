package org.hsleiden.challengeweek.docenten_vs_studenten.demo;

import com.almasb.fxgl.app.FXGLApplication;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hsleiden.challengeweek.docenten_vs_studenten.EntityTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlantsApp extends GameApplication {

    public static final int WIDTH_BOARD = 16 * 80;
    public static final int HEIGHT_BOARD = 8 * 80;
    private Entity player1;
    private Entity player2;
    private Entity background;
    private boolean isPlayer1Active = true;
    boolean userMadeChoice = false;
    private Entity plant1;
    private Entity plant2;
    private Entity plant3;
    private Entity plant4;
    private Entity plant5;
    private Entity plant6;

    private List<Entity> plants = new ArrayList<>();

    private List<Entity> erwten = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(WIDTH_BOARD);
        gameSettings.setHeight(HEIGHT_BOARD);
        /*gameSettings.setFullScreenAllowed(true);
        gameSettings.setFullScreenFromStart(true);*/
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntities();
        getGameWorld().addEntityFactory(new EntityFactory());
        background = getGameWorld().spawn("background");

        Button startButton = new Button("START THE GAME!");

        FXGL.getGameScene().addUINode(startButton);

        startButton.setTranslateX(500);
        startButton.setTranslateY(200);

        startButton.setOnAction(e ->{
            initZombieButtons();
            FXGL.getGameScene().removeUINode(startButton);

        });
    }

    private void initZombieButtons() {
        Button player1Button = new Button("Select Player 1");
        Button player2Button = new Button("Select Player 2");

        player1Button.setOnAction(e ->{
            isPlayer1Active = true;
            initElements();
            player2.removeFromWorld();
//            background.removeFromWorld();
            FXGL.getGameScene().removeUINode(player1Button);
            FXGL.getGameScene().removeUINode(player2Button);
        });
        player2Button.setOnAction(e ->{
            isPlayer1Active = false;
            initElements();
            player1.removeFromWorld();
//            background.removeFromWorld();
            FXGL.getGameScene().removeUINode(player1Button);
            FXGL.getGameScene().removeUINode(player2Button);
        });

        FXGL.getGameScene().addUINode(player1Button);
        FXGL.getGameScene().addUINode(player2Button);

        player1Button.setTranslateX(900);
        player1Button.setTranslateY(450);

        player2Button.setTranslateX(700);
        player2Button.setTranslateY(325);
    }

    private void initElements() {
        setLevelFromMap("grid.tmx");

        player1 = getGameWorld().spawn("player1", 700, 200);
        player2 = getGameWorld().spawn("player2", 700, 200);

        plant1 = getGameWorld().spawn("plant", 265, 105);
        plant2 = getGameWorld().spawn("plant", 265, 185);
        plant3 = getGameWorld().spawn("plant", 265, 265);
        plant4 = getGameWorld().spawn("plant", 265, 345);
        plant5 = getGameWorld().spawn("plant", 265, 425);
        plant6 = getGameWorld().spawn("plant", 265, 505);

        plants.add(plant1);
        plants.add(plant2);
        plants.add(plant3);
        plants.add(plant4);
        plants.add(plant5);
        plants.add(plant6);

        for (Entity plant : plants) {
            int randomInterval = ThreadLocalRandom.current().nextInt(0,4) + 4;
            FXGL.getGameTimer().runAtInterval(() -> {
                if (plant.isActive()) {
                    erwten.add(getGameWorld().spawn("erwt", plant.getPosition()));
                }
            }, Duration.seconds(randomInterval));
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.ERWT) {
            @Override
            protected void onCollision(Entity player, Entity erwt) {
                System.out.println("player removed");
                showGameOver();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.PLANT) {
            @Override
            protected void onCollision(Entity player, Entity plant) {
                System.out.println("plant removed");
                plant.removeFromWorld();
                plants.remove(plant);
            }
        });
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> {
            (isPlayer1Active ? player1 : player2).translateX(5);
        });
        FXGL.onKey(KeyCode.A, () -> {
            (isPlayer1Active ? player1 : player2).translateX(-5);
        });
        FXGL.onKey(KeyCode.W, () -> {
            (isPlayer1Active ? player1 : player2).translateY(-5);
        });
        FXGL.onKey(KeyCode.S, () -> {
            (isPlayer1Active ? player1 : player2).translateY(5);
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        List<Entity> removeErwten = new ArrayList<>();
        for (Entity erwt : erwten) {
            erwt.translateX(5d);
            if (erwt.getPosition().getX() > WIDTH_BOARD) {
                erwt.removeFromWorld();
            }
        }
        removeErwten.forEach(erwt -> erwten.remove(erwt));
    }

    private void showGameOver() {
        showMessage("You died!", getGameController()::startNewGame);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
