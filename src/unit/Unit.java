package unit;

import java.awt.*;

import static constant.Constants.BLOCK_CELL_HEIGHT;
import static constant.Constants.GAME_AREA_WIDTH;

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

    public void setX(int x) {
        this.x = x;
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

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        if (shape.length > 0) return shape[0].length;
        return 0;
    }

    public void moveDown() {
        this.y = y + BLOCK_CELL_HEIGHT;
    }
}
