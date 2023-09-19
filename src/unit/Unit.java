package unit;

import constant.Constants;

import java.awt.*;

import static constant.Constants.*;

public abstract class Unit {

    protected int x;
    protected int y;

    protected Color color;

    protected int[][] shape;

    public Unit() {
        this.x = GAME_AREA_WIDTH / 2;
        this.y = 0;
        this.color = Color.WHITE;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
        return y + getHeight() * Constants.BLOCK_CELL_SIZE;
    }

    public int getLeftEdge() {
        return x;
    }

    public int getRightEdge() {
        return x + (getWidth() * BLOCK_CELL_SIZE);
    }

    public void moveDown() {
        this.y = y + Constants.BLOCK_CELL_SIZE;
    }

    public void moveLeft() {
        this.x = x - BLOCK_CELL_SIZE;
    }

    public void moveRight() {
        this.x = x + BLOCK_CELL_SIZE;
    }
}