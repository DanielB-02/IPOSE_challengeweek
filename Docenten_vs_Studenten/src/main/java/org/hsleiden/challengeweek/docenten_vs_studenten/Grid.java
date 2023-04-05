package org.hsleiden.challengeweek.docenten_vs_studenten;

import javafx.scene.paint.Color;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.shape.Rectangle;

public class Grid {
    private static final int CUBE_WIDTH = 80;
    private static final int ROWS = 5;
    private static final int COLS = 8;

    private Cell[][] cells;

    private static final Color[] colors = new Color[] {
             Color.DARKGREEN,
             Color.DARKOLIVEGREEN,
             Color.FORESTGREEN
    };

    public Grid() {
        initGrid();
    }

    private void initGrid() {
        this.cells = new Cell[ROWS][];
        for (int y = 0; y < ROWS; y++) {
            this.cells[y] = new Cell[COLS];
            for (int x = 0; x < COLS; x++) {
                isVeelVoudVanGridVanDrie(y, x);
            }
        }
    }

    private void isVeelVoudVanGridVanDrie(int y, int x) {
        this.cells[y][x] = new Cell(colors[(x + y) % 3]);
    }

    public void draw() {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                tekenenVanGrid(y, x);
            }
        }
    }

    private void tekenenVanGrid(int y, int x) {
        FXGL.entityBuilder()
                .at(50 + x * CUBE_WIDTH, 50 + y * CUBE_WIDTH)
                .view(new Rectangle(CUBE_WIDTH, CUBE_WIDTH, this.cells[y][x].getColor()))
                .buildAndAttach();
    }

}
