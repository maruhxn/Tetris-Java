package screen;

import constant.Constants;
import unit.block.Block;

import javax.swing.*;
import java.awt.*;

import static constant.Constants.*;

public class GameArea extends JPanel {
    private Block currBlock;
    public Color[][] background;

    public GameArea() {
        setPreferredSize(new Dimension(GAME_AREA_WIDTH, GAME_AREA_HEIGHT));
        setBackground(Color.BLACK);

        background = new Color[GAME_AREA_HEIGHT][GAME_AREA_WIDTH];
        SwingUtilities.invokeLater(() -> {
            this.currBlock = ((GameScreen) getParent()).getCurrBlock();
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBackground(g); // background 그리기
        drawBlock(g); // 현재 블럭 그리기
    }

    private void drawBlock(Graphics g) {
        currBlock = ((GameScreen) getParent()).getCurrBlock();
        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();
        Color color = currBlock.getColor();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * BLOCK_CELL_SIZE;
                    int y = currBlock.getY() + i * BLOCK_CELL_SIZE;

                    drawBlockByGraphics(g, color, x, y);
                }
            }
        }

        // STATUS TEXT
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.BOLD, 24));
        g.drawString(((GameScreen) getParent()).setStatus() ? "PAUSE!!" : "PLAYING!", GAME_AREA_WIDTH / 2 - 50, 20);
    }

    private void drawBackground(Graphics g) {
        Color color;

        for (int i = 0; i < GAME_AREA_HEIGHT; i = i + BLOCK_CELL_SIZE) {
            for (int j = 0; j < GAME_AREA_WIDTH; j = j + BLOCK_CELL_SIZE) {
                color = background[i][j];
                if (color != null) {
                    drawBlockByGraphics(g, color, j, i);
                }
            }
        }
    }

    private static void drawBlockByGraphics(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, BLOCK_CELL_SIZE, BLOCK_CELL_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, BLOCK_CELL_SIZE, BLOCK_CELL_SIZE);
    }

}
