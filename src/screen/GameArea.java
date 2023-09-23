package screen;

import component.AbstractArea;
import unit.block.Block;

import javax.swing.*;
import java.awt.*;

import static manager.GameSizeManager.GAME_SIZE;
import static util.Utility.getLargeFont;
import static util.Utility.getSmallFont;

public class GameArea extends AbstractArea {
    private Block currBlock;
    public Color[][] background;

    public GameArea() {
        setPreferredSize(new Dimension(GAME_SIZE.getGameAreaWidth(), GAME_SIZE.getHeight()));
        background = new Color[GAME_SIZE.getHeight()][GAME_SIZE.getGameAreaWidth()];

        SwingUtilities.invokeLater(() -> this.currBlock = ((GameScreen) getParent()).getCurrBlock());
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
                    int x = currBlock.getX() + j * GAME_SIZE.getBlockCellSize();
                    int y = currBlock.getY() + i * GAME_SIZE.getBlockCellSize();

                    drawBlockByGraphics(g, color, x, y);
                }
            }
        }

        // STATUS TEXT
        g.setColor(Color.WHITE);
        g.setFont(getSmallFont());
        g.drawString(((GameScreen) getParent()).setStatus() ? "PAUSE!!" : "PLAYING!", GAME_SIZE.getGameAreaWidth() / 2 - 20, 20);
    }

    private void drawBackground(Graphics g) {
        Color color;

        for (int i = 0; i < GAME_SIZE.getHeight(); i = i + GAME_SIZE.getBlockCellSize()) {
            for (int j = 0; j < GAME_SIZE.getGameAreaWidth(); j = j + GAME_SIZE.getBlockCellSize()) {
                color = background[i][j];
                if (color != null) {
                    drawBlockByGraphics(g, color, j, i);
                }
            }
        }
    }

    private static void drawBlockByGraphics(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, GAME_SIZE.getBlockCellSize(), GAME_SIZE.getBlockCellSize());
        g.setColor(Color.BLACK);
        g.drawRect(x, y, GAME_SIZE.getBlockCellSize(), GAME_SIZE.getBlockCellSize());
    }

}
