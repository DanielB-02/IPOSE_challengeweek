package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.sun.media.jfxmedia.logging.Logger.setLevel;

public class PlantsApp extends GameApplication {

    private Entity player1;
    private Entity player2;
    private boolean isPlayer1Active = true;
    boolean userMadeChoice = false;
    private Entity plant1;
    private Entity plant2;
    private Entity End;
    private ArrayList<Entity> plants = new ArrayList<>();

    //    private ArrayList<Entity> erwten = new ArrayList<>();
    HashMap<Entity, Entity> plantErwtMap = new HashMap<>();

    private Entity erwt;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(16 * 80);
        gameSettings.setHeight(8 * 80);
        /*gameSettings.setFullScreenAllowed(true);
        gameSettings.setFullScreenFromStart(true);*/
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new EntityFactory());
        setLevelFromMap("level1.tmx");

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

    protected void initGameVars(Map<String, Object> vars) {
        vars.put("level", 0);
    }

    private void initElements() {
        player1 = getGameWorld().spawn("player1", 700, 200);



        player2 = FXGL.entityBuilder()
                .at(500, 200)
                .viewWithBBox(texture("charlotte_zombie.png", 60, 80))
                .with(new CollidableComponent(true))
                .type(PlantsTypes.PLAYER)
                .buildAndAttach();


        plant1 = FXGL.entityBuilder()
                .at(155,155)
                .viewWithBBox(new Rectangle(30, 30, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(PlantsTypes.PLANT)
                .buildAndAttach();

        plant2 = FXGL.entityBuilder()
                .at(155,235)
                .viewWithBBox(new Rectangle(30, 30, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(PlantsTypes.PLANT)
                .buildAndAttach();

        plants.add(plant1);
        plants.add(plant2);


        for (Entity plant : plants) {
            FXGL.getGameTimer().runAtInterval(() -> {
                if (plant.isActive()) {
                    erwt = FXGL.entityBuilder()
                            .at(plant.getPosition())
                            .viewWithBBox(new Rectangle(20, 20, Color.BROWN))
                            .with(new CollidableComponent(true))
                            .type(PlantsTypes.ERWT)
                            .buildAndAttach();

                    plantErwtMap.put(plant, erwt);
                }
            }, Duration.seconds(3));
            if (!plant.isActive()) {
                FXGL.getGameTimer().clear();
            }
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(PlantsTypes.PLAYER, PlantsTypes.ERWT) {
            @Override
            protected void onCollision(Entity player, Entity erwt) {
                System.out.println("player removed");
                player.removeFromWorld();
                nextLevel();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(PlantsTypes.PLAYER, PlantsTypes.PLANT) {
            @Override
            protected void onCollision(Entity player, Entity plant) {
                System.out.println("plant removed");
                plant.removeFromWorld();
                plants.remove(plant);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(PlantsTypes.PLAYER, PlantsTypes.END) {
            @Override
            protected void onCollision(Entity player, Entity End) {
                System.out.println("player reached the end");
            }
        });

    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> {
            if (isPlayer1Active) {
                player1.translateX(5);
            } else {
                player2.translateX(5);
            }
        });
        FXGL.onKey(KeyCode.A, () -> {
            if (isPlayer1Active) {
                player1.translateX(-5);
            } else {
                player2.translateX(-5);
            }
        });
        FXGL.onKey(KeyCode.W, () -> {
            if (isPlayer1Active) {
                player1.translateY(-5);
            } else {
                player2.translateY(-5);
            }
        });
        FXGL.onKey(KeyCode.S, () -> {
            if (isPlayer1Active) {
                player1.translateY(5);
            } else {
                player2.translateY(5);
            }
        });
    }

    private void nextLevel() {
        if (geti("level") == 1) {
            showMessage("You finished the demo!");
            return;
        }

        inc("level", +2); //Hij begint direct al met het spel. Niet de bedoeling. Hierdoor komt hij dus pas bij lvl 1, na het uitspelen van het begin

        setLevel(geti("level"));
    }

    public void onPlayerDied(){
        setLevel(geti("level"));
    }

    private void setLevel(int levelNum) {
        if (player1 != null) {
            player1.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
            player1.setZIndex(Integer.MAX_VALUE);
        }

        set("levelTime", 0.0);

        Level level = setLevelFromMap("level" + levelNum  + ".tmx");


    }

    @Override
    protected void onUpdate(double tpf) {
        for (Map.Entry<Entity, Entity> entry : plantErwtMap.entrySet()) {
            Entity plant = entry.getKey();
            Entity erwt = entry.getValue();
            if (!plant.isActive()) {
                erwt.removeFromWorld();
            }
            erwt.translateX(5d);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
