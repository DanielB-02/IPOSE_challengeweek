package org.hsleiden.challengeweek.docenten_vs_studenten;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class Game extends GameApplication {
    @Override
    protected void initSettings(GameSettings Settings) {
        Settings.setTitle("Teachers vs Students");
        Settings.setFullScreenAllowed(true);
        Settings.setFullScreenFromStart(true);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
