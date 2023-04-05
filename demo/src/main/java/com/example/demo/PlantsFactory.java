package com.example.demo;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class PlantsFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data){
        return entityBuilder(data)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        PhysicsComponent physicsComponent = new PhysicsComponent();
        physicsComponent.setBodyType(BodyType.DYNAMIC);

        return entityBuilder(data)
                .from(data)
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .with(new PhysicsComponent())
                .build();

    }


}
