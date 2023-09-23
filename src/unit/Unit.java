package unit;

import java.awt.*;

import static manager.GameSizeManager.GAME_SIZE;

public abstract class Unit {
    protected int x;
    protected int y;

    protected Color color;

    protected int[][] shape;

    public Unit() {
        this.x = GAME_SIZE.getGameAreaWidth() / 2;
        this.y = 0;
        this.color = Color.WHITE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int[][] getShape() {
        return shape;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        if (shape.length > 0) return shape[0].length;
        return 0;
    }

    public int getBottomEdge() {
        return y + getHeight() * GAME_SIZE.getBlockCellSize();
    }

    public int getLeftEdge() {
        return x;
    }

    public int getRightEdge() {
        return x + (getWidth() * GAME_SIZE.getBlockCellSize());
    }

    public void moveDown() {
        this.y = y + GAME_SIZE.getBlockCellSize();
    }

    public void moveLeft() {
        this.x = x - GAME_SIZE.getBlockCellSize();
    }

    public void moveRight() {
        this.x = x + GAME_SIZE.getBlockCellSize();
    }
}
