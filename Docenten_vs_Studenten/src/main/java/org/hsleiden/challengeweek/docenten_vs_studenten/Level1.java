package org.hsleiden.challengeweek.docenten_vs_studenten;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Level1 extends GameApplication {
    @Override
    protected void initSettings(GameSettings Settings) {
        Settings.setTitle("Teachers vs Students");
        Settings.setFullScreenAllowed(true);
        Settings.setFullScreenFromStart(true);
    }
    private Entity player;

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(400, 400)
                .view(new Rectangle(30, 30, Color.RED))
                .buildAndAttach();
    }
}

