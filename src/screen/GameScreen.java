package screen;

import score.ScoreManager;
import unit.block.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static constant.Constants.*;

public class GameScreen extends Screen {
    private final GameArea gameArea;
    private final GameInfoArea gameInfoArea;
    private Block currBlock;
    private Block nextBlock;
    private Timer timer;
    private boolean isPaused = false;


    public GameScreen() {
        setLayout(new BorderLayout());

        init();

        gameArea = new GameArea();
        gameInfoArea = new GameInfoArea();

        add(gameArea, BorderLayout.WEST);
        add(gameInfoArea, BorderLayout.CENTER);

        startTimer();

        SwingUtilities.invokeLater(() -> {
            setKeyListener();
            setFocusable(true);
            requestFocusInWindow();
        });
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    blockDownCycle();
                }
            }

        });
        timer.start();
    }

    private void blockDownCycle() {
        moveDown();
        ScoreManager.checkLevelUp();
        checkGameOver();
        gameArea.repaint();
        gameInfoArea.repaint();
    }

    private void checkGameOver() {
        // (0, 0~400)에 블럭이 있다면 게임 오버.
        for (int i = 0; i < GAME_AREA_WIDTH; i = i + BLOCK_CELL_WIDTH) {
            if (gameArea.background[0][i] != null) {
                timer.stop();
                resetKeyListeners();
                // 게임 종료 다이얼 띄우기

            }
        }
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public Block getCurrBlock() {
        return currBlock;
    }

    private boolean checkCollision() {
        return currBlock.getBottomEdge() == GAME_AREA_HEIGHT - BLOCK_CELL_HEIGHT || gameArea.background[currBlock.getBottomEdge()][currBlock.getX()] != null;
    }

    private boolean checkLeft() {
        return currBlock.getLeftEdge() == 0;
    }

    private boolean checkRight() {
        return currBlock.getRightEdge() == GAME_AREA_WIDTH;
    }

    private void rotateBlock() {
        currBlock.rotate();
        gameArea.repaint();
    }

    private void moveDown() {
        if (checkCollision()) {
            moveBlockToBackground();
            getNextBlockToCurr();
            return;
        }
        currBlock.moveDown();
        ScoreManager.addScorePerSecond();
    }

    private void moveLeft() {
        if (!checkLeft()) {
            currBlock.moveLeft();
            gameArea.repaint();
        }
    }

    private void moveRight() {
        if (!checkRight()) {
            currBlock.moveRight();
            gameArea.repaint();
        }
    }

    private void moveBlockToBackground() {
        int[][] shape = currBlock.getShape();
        int h = currBlock.getHeight();
        int w = currBlock.getWidth();
        Color color = currBlock.getColor();

        int xPos = currBlock.getX();
        int yPos = currBlock.getY();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (shape[i][j] == 1) {
                    gameArea.background[i * BLOCK_CELL_HEIGHT + yPos][j * BLOCK_CELL_WIDTH + xPos] = color;
                }
            }
        }
    }

    private void superDrop() {
        int startY = currBlock.getY();
        int endY;
        while (!checkCollision()) {
            currBlock.setY(currBlock.getY() + BLOCK_CELL_HEIGHT);
        }
        endY = currBlock.getY();

        int addedScore = (endY - startY) / BLOCK_CELL_HEIGHT * ScoreManager.getScorePerSecond();
        ScoreManager.addScore(addedScore);
        moveBlockToBackground();
        gameArea.repaint();
    }

    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        blockDownCycle();
                        break;
                    case KeyEvent.VK_SPACE:
                        superDrop();
                        break;
                    case KeyEvent.VK_SHIFT:
                        rotateBlock();
                        break;
                    case KeyEvent.VK_P:
                        pause();
                        break;
                    case KeyEvent.VK_ESCAPE:
                        forceExit();
                        break;
                }
            }
        });
    }

    private void forceExit() {
        System.exit(0);
    }

    private void pause() {
        // 키 이벤트 멈춤
        resetKeyListeners();

        if (isPaused) {
            setKeyListener();
        } else {
            // p에만 반응하도록
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_P) {
                        pause();
                    }
                }
            });
        }
        isPaused = !isPaused;
        gameArea.repaint();
    }


    private void init() {
        spawnNextBlock();
        getNextBlockToCurr();
    }

    private void getNextBlockToCurr() {
        setCurrBlock(getNextBlock());
        spawnNextBlock();
    }

    private void spawnNextBlock() {
        Random random = new Random();
        switch (random.nextInt(7)) {
            case 0:
                setNextBlock(new OBlock());
                break;
            case 1:
                setNextBlock(new IBlock());
                break;
            case 2:
                setNextBlock(new JBlock());
                break;
            case 3:
                setNextBlock(new LBlock());
                break;
            case 4:
                setNextBlock(new SBlock());
                break;
            case 5:
                setNextBlock(new TBlock());
                break;
            case 6:
                setNextBlock(new ZBlock());
                break;
        }
    }

    public void setCurrBlock(Block currBlock) {
        this.currBlock = currBlock;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public boolean setStatus() {
        return this.isPaused;
    }

    private void resetKeyListeners() {
        for (KeyListener kl : getKeyListeners()) {
            removeKeyListener(kl);
        }
    }
}
