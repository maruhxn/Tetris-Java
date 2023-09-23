package unit.block;

import unit.Unit;

import java.awt.*;

public abstract class Block extends Unit {
    public Block() {
        shape = new int[][]{
                {1, 1},
                {1, 1}
        };
        color = Color.YELLOW;
    }

    public void rotate() {
        int numRows = shape.length;
        int numCols = shape[0].length;

        int[][] rotatedShape = new int[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                rotatedShape[j][numRows - 1 - i] = shape[i][j];
            }
        }

        shape = rotatedShape.clone();
    }

    public void rotateUndo() {
        int numRows = shape.length;
        int numCols = shape[0].length;

        int[][] rotatedShape = new int[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                rotatedShape[numCols - 1 - j][i] = shape[i][j];
            }
        }

        shape = rotatedShape.clone();
    }
}
