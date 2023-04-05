package org.hsleiden.challengeweek.docenten_vs_studenten;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;


public class Game extends GameApplication {
    private Grid grid;

    @Override
    protected void initSettings(GameSettings Settings) {
        Settings.setTitle("Teachers vs Students");
//        Settings.setFullScreenAllowed(true);
//        Settings.setFullScreenFromStart(true);
    }

    private Entity player;
    private Entity plant1;
    private Entity plant2;
    private ArrayList<Entity> plants = new ArrayList<>();

//    private ArrayList<Entity> erwten = new ArrayList<>();
    HashMap<Entity, Entity> plantErwtMap = new HashMap<>();

    private Entity erwt;
    private Entity map;
    private Entity left;
    private Entity top;
    private Entity right;
    private Entity bottom;

    @Override
    protected void initGame() {



        player = FXGL.entityBuilder()
                .at(400, 200)
                .viewWithBBox(texture("vincent_zombie.png", 80, 80))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        plant1 = FXGL.entityBuilder()
                .at(155,155)
                .viewWithBBox(new Rectangle(30, 30, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLANT)
                .buildAndAttach();

        plant2 = FXGL.entityBuilder()
                .at(155,235)
                .viewWithBBox(new Rectangle(30, 30, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLANT)
                .buildAndAttach();

        plants.add(plant1);
        plants.add(plant2);

//        FXGL.getGameTimer().runAtInterval(() -> {
//            int ypos = 0;
//            Random random = new Random();
//            int randomNumber = random.nextInt(5);
//            System.out.println(randomNumber);
//            switch (randomNumber){
//                case 0:
//                    ypos = 400;
//                    break;
//                case 1:
//                    ypos = 320;
//                    break;
//                case 2:
//                    ypos = 240;
//                    break;
//                case 3:
//                    ypos = 160;
//                    break;
//                case 4:
//                    ypos = 80;
//                    break;
//            }

        for (Entity plant : plants) {
            FXGL.getGameTimer().runAtInterval(() -> {
                if (plant.isActive()) {
                    erwt = FXGL.entityBuilder()
                            .at(plant.getPosition())
                            .viewWithBBox(new Rectangle(20, 20, Color.BROWN))
                            .with(new CollidableComponent(true))
                            .type(EntityTypes.ERWT)
                            .buildAndAttach();

                    plantErwtMap.put(plant, erwt);
                }
            }, Duration.seconds(2));
            if (!plant.isActive()) {
                FXGL.getGameTimer().clear();
            }
        }

        left = FXGL.entityBuilder()
                .at(690, 50)
                .viewWithBBox(new Rectangle(10, 400))
                .with(new CollidableComponent(true))
                .type(EntityTypes.LEFT)
                .buildAndAttach();
        right = FXGL.entityBuilder()
                .at(40, 50)
                .viewWithBBox(new Rectangle(10, 400))
                .with(new CollidableComponent(true))
                .type(EntityTypes.RIGHT)
                .buildAndAttach();
        top = FXGL.entityBuilder()
                .at(40, 50)
                .viewWithBBox(new Rectangle(650, 10))
                .with(new CollidableComponent(true))
                .type(EntityTypes.TOP)
                .buildAndAttach();
        bottom = FXGL.entityBuilder()
                .at(40, 450)
                .viewWithBBox(new Rectangle(650, 10))
                .with(new CollidableComponent(true))
                .type(EntityTypes.BOTTOM)
                .buildAndAttach();
    }

    @Override
    protected void initInput(){
        FXGL.onKey(KeyCode.D, () ->{
            player.translateX(5);
        });
        FXGL.onKey(KeyCode.A, () ->{
            player.translateX(-5);
        });
        FXGL.onKey(KeyCode.W, () ->{
            player.translateY(-5);
        });
        FXGL.onKey(KeyCode.S, () ->{
            player.translateY(5);
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {

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
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.ERWT, EntityTypes.LEFT) {
            @Override
            protected void onCollision(Entity erwt, Entity left) {
                System.out.println("Erwt removed");
                erwt.removeFromWorld();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.TOP) {
            @Override
            protected void onCollision(Entity player, Entity top) {
                System.out.println("player touched the top");
                player.translateY(5);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOTTOM) {
            @Override
            protected void onCollision(Entity player, Entity bottom) {
                System.out.println("player touched the bottom");
                player.translateY(-5);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.LEFT) {
            @Override
            protected void onCollision(Entity player, Entity left) {
                System.out.println("player touched the left");
                player.translateX(-5);
            }
        });
    }
    @Override
    protected void onUpdate(double tpf) {
        for (Map.Entry<Entity, Entity> entry : plantErwtMap.entrySet()) {
            Entity plant = entry.getKey();
            Entity erwt = entry.getValue();
            if (!plant.isActive()) {
                erwt.removeFromWorld();
            }
            erwt.translateX(5);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
