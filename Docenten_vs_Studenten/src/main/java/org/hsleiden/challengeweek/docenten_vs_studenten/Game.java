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
        erwt = FXGL.entityBuilder()
                .at(100, 200)
                .viewWithBBox(new Rectangle(20, 20, Color.BROWN))
                .with(new CollidableComponent(true))
                .type(EntityTypes.ERWT)
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
                System.out.println("hallo");
                player.removeFromWorld();
            }
        });
    }
    @Override
    protected void onUpdate(double tpf) {
        erwt.translateX(5);
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
