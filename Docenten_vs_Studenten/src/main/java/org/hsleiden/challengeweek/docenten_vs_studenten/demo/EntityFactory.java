package org.hsleiden.challengeweek.docenten_vs_studenten.demo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.hsleiden.challengeweek.docenten_vs_studenten.EntityTypes;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {

    @Spawns("border")
    public Entity newPlatform(SpawnData data){
        return entityBuilder(data)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player1")
    public Entity newPlayer1(SpawnData data){
        return newPlayer(data, "vincent_zombie.png");
    }

    @Spawns("player2")
    public Entity newPlayer2(SpawnData data){
        return newPlayer(data, "charlotte_zombie.png");
    }

    private Entity newPlayer(SpawnData data, String teacherImage) {
        return entityBuilder(data)
                .viewWithBBox(texture(teacherImage, 60, 80))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER)
                .build();
    }

    @Spawns("erwt")
    public Entity newErwt(SpawnData data){
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(20, 20, Color.BROWN))
                .with(new CollidableComponent(true))
                .type(EntityTypes.ERWT)
                .buildAndAttach();
    }

    @Spawns("plant")
    public Entity newPlant(SpawnData data){
        return FXGL.entityBuilder(data)
                .viewWithBBox(new Rectangle(30, 30, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLANT)
                .buildAndAttach();
    }

}

