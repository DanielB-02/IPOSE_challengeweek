package org.hsleiden.challengeweek.docenten_vs_studenten.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.hsleiden.challengeweek.docenten_vs_studenten.EntityTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;

public class PlantsApp extends GameApplication {

    public static final int WIDTH_BOARD = 16 * 80;
    public static final int HEIGHT_BOARD = 8 * 80;
    private Entity player1;
    private Entity player2;
    private boolean isPlayer1Active = true;
    boolean userMadeChoice = false;
    private Entity plant1;
    private Entity plant2;
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
        getGameWorld().addEntityFactory(new EntityFactory());
        setLevelFromMap("grid.tmx");

        Button player1Button = new Button("Select Player 1");
        Button player2Button = new Button("Select Player 2");

        // set button actions to update current player variable
        player1Button.setOnAction(e ->{
            isPlayer1Active = true;
            initElements();
            player2.removeFromWorld();
            FXGL.getGameScene().removeUINode(player1Button);
            FXGL.getGameScene().removeUINode(player2Button);
        });
        player2Button.setOnAction(e ->{
            isPlayer1Active = false;
            initElements();
            player1.removeFromWorld();
            FXGL.getGameScene().removeUINode(player1Button);
            FXGL.getGameScene().removeUINode(player2Button);
        });

        // add buttons to game scene
        FXGL.getGameScene().addUINode(player1Button);
        FXGL.getGameScene().addUINode(player2Button);

        player1Button.setTranslateX(200);
        player1Button.setTranslateY(200);

        player2Button.setTranslateX(500);
        player2Button.setTranslateY(200);
    }

    private void initElements() {
        player1 = getGameWorld().spawn("player1", 700, 200);
        player2 = getGameWorld().spawn("player2", 700, 200);

        plant1 = getGameWorld().spawn("plant", 155, 155);
        plant2 = getGameWorld().spawn("plant", 155, 235);

        plants.add(plant1);
        plants.add(plant2);

        for (Entity plant : plants) {
            int randomInterval = ThreadLocalRandom.current().nextInt(0,4) + 2;
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
                player.removeFromWorld();
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
                removeErwten.add(erwt);
            }
        }
        removeErwten.forEach(erwt -> erwten.remove(erwt));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
