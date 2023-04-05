package com.example.demo;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {

    @Spawns("border")
    public Entity newPlatform(SpawnData data){
        return entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("End")
    public Entity newEnd(SpawnData data){
        return entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player1")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physicsComponent = new PhysicsComponent();
        PlantsApp plantsApp = new PlantsApp();

        return entityBuilder(data)
                .viewWithBBox(texture("vincent_zombie.png", 60, 80))
                .with(new CollidableComponent(true))
                .type(PlantsTypes.PLAYER)
                .with(physicsComponent)
                .build();

    }

    @Spawns("erwt")
    public Entity newErwt(SpawnData data){
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return entityBuilder(data)
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .with(physicsComponent)
                .with()
                .build();

    }


}

