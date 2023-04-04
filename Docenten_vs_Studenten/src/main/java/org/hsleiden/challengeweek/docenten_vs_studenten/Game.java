package org.hsleiden.challengeweek.docenten_vs_studenten;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;


public class Game extends GameApplication {
    private Grid grid;

    @Override
    protected void initSettings(GameSettings Settings) {
        Settings.setTitle("Teachers vs Students");
//        Settings.setFullScreenAllowed(true);
//        Settings.setFullScreenFromStart(true);
    }

    private Entity player;
    private Entity plant;

    private Entity erwt;
    private Entity map;
    private Entity left;
    private Entity top;
    private Entity right;
    private Entity bottom;

    @Override
    protected void initGame() {
        grid = new Grid();
        grid.draw();
        player = FXGL.entityBuilder()
                .at(400, 200)
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .buildAndAttach();
        plant = FXGL.entityBuilder()
                .at(155,155)
                .viewWithBBox(new Rectangle(30, 30, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLANT)
                .buildAndAttach();

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
//            erwt = FXGL.entityBuilder()
//                    .at(100, ypos)
//                    .viewWithBBox(new Rectangle(20, 20, Color.BROWN))
//                    .with(new CollidableComponent(true))
//                    .type(EntityTypes.ERWT)
//                    .buildAndAttach();
//        }, Duration.millis(2200));

        FXGL.getGameTimer().runAtInterval(() -> {
            erwt = FXGL.entityBuilder()
                    .at(plant.getPosition())
                    .viewWithBBox(new Rectangle(20, 20, Color.BROWN))
                    .with(new CollidableComponent(true))
                    .type(EntityTypes.ERWT)
                    .buildAndAttach();

            // Set the erwt's velocity to shoot it in a certain direction
            erwt.translateX(10);
        }, Duration.seconds(2));

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
        if (erwt != null) {
            erwt.translateX(5);
        }
//        if (erwt.isColliding(player)) {
//            FXGL.entityBuilder()
//                    .at(erwt.getX(), erwt.getY())
//                    .view(new Circle(80, Color.RED))
//                    .buildAndAttach();
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
