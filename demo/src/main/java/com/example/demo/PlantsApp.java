package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PlantsApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(10 * 80);
        gameSettings.setHeight(6 * 80);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlantsFactory());
        Level level = getAssetLoader().loadLevel("grid.tmx", new TextLevelLoader(40, 40, ' '));
        getGameWorld().setLevel(level);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
