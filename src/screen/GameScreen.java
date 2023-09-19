package screen;

import manager.GameManager;
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

    private int blockSpawnCnt;


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
        GameManager.checkLevelUp();
        checkGameOver();
        gameArea.repaint();
        gameInfoArea.repaint();
    }

    private void checkClearLine() {
        for (int y = GAME_AREA_HEIGHT - BLOCK_CELL_SIZE; y > 0; y -= BLOCK_CELL_SIZE) {
            int clearedColumnCnt = 0;
            for (int x = 0; x < GAME_AREA_WIDTH; x += BLOCK_CELL_SIZE) {
                if (gameArea.background[y][x] == null) break;
                clearedColumnCnt++;
            }

            if (clearedColumnCnt == GAME_AREA_WIDTH / BLOCK_CELL_SIZE) {
                y = clearLine(y);
            }
        }
    }

    private int clearLine(int y) {
        gameArea.background[y] = new Color[GAME_AREA_WIDTH];
        for (int i = y; i > 0; i -= BLOCK_CELL_SIZE) {
            gameArea.background[i] = gameArea.background[i - BLOCK_CELL_SIZE];
        }
        y += BLOCK_CELL_SIZE;
        speedUp();
        gameArea.repaint();
        gameInfoArea.repaint();
        return y;
    }

    private void speedUp() {
        GameManager.speedUp();
        timer.setDelay(GameManager.getBlockDownSpeed());
        System.out.println("SPEED UP!!");
        System.out.println(GameManager.getBlockDownSpeed());
    }

    private void checkGameOver() {
        // (0, 0~400)에 블럭이 있다면 게임 오버.
        for (int i = 0; i < GAME_AREA_WIDTH; i = i + BLOCK_CELL_SIZE) {
            if (gameArea.background[0][i] != null) {
                timer.stop();
                resetKeyListeners();
                // TODO: 게임 종료 다이얼 띄우기

            }
        }
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public Block getCurrBlock() {
        return currBlock;
    }

    private boolean checkBottomCollision() {

        if (currBlock.getBottomEdge() == GAME_AREA_HEIGHT - BLOCK_CELL_SIZE) return true;

        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * BLOCK_CELL_SIZE;
                    int y = currBlock.getY() + i * BLOCK_CELL_SIZE;
                    // 현재 y 바로 다음 칸의 background가 채워져 있다면 충돌.
                    if (gameArea.background[y + BLOCK_CELL_SIZE][x] != null) return true;
                }
            }
        }
        return false;
    }

    private boolean checkLeftCollision() {
        if (currBlock.getLeftEdge() <= 0) return true;

        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * BLOCK_CELL_SIZE;
                    int y = currBlock.getY() + i * BLOCK_CELL_SIZE;
                    if (gameArea.background[y][x - BLOCK_CELL_SIZE] != null) return true;
                }
            }
        }

        return false;
    }

    private boolean checkRightCollision() {
        if (currBlock.getRightEdge() >= GAME_AREA_WIDTH) return true;

        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * BLOCK_CELL_SIZE;
                    int y = currBlock.getY() + i * BLOCK_CELL_SIZE;
                    if (gameArea.background[y][x + BLOCK_CELL_SIZE] != null) return true;
                }
            }
        }

        return false;
    }

    private void rotateBlock() {
        currBlock.rotate();

        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();


        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * BLOCK_CELL_SIZE;
                    int y = currBlock.getY() + i * BLOCK_CELL_SIZE;

                    if (x < 0 || x >= GAME_AREA_WIDTH || y >= GAME_AREA_HEIGHT) {
                        currBlock.rotateUndo();
                        return;
                    }

                    if (gameArea.background[y][x] != null) {
                        currBlock.rotateUndo();
                        return;
                    }
                }
            }
        }

        gameArea.repaint();

    }

    private void moveDown() {
        if (checkBottomCollision()) {
            moveBlockToBackground();
            checkClearLine();
            getNextBlockToCurr();
            return;
        }
        currBlock.moveDown();
        GameManager.addScorePerSecond();
    }

    private void moveLeft() {
        if (!checkLeftCollision()) {
            currBlock.moveLeft();
            gameArea.repaint();
        }
    }

    private void moveRight() {
        if (!checkRightCollision()) {
            currBlock.moveRight();
            gameArea.repaint();
        }
    }

    private void moveBlockToBackground() {
        int[][] shape = currBlock.getShape();
        int h = currBlock.getHeight();
        int w = currBlock.getWidth();
        Color color = currBlock.getColor();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (shape[i][j] == 1) {
                    int x = currBlock.getX() + j * BLOCK_CELL_SIZE;
                    int y = currBlock.getY() + i * BLOCK_CELL_SIZE;
                    gameArea.background[y][x] = color;
                }
            }
        }
    }

    private void superDrop() {
        int startY = currBlock.getY();
        int endY;
        while (!checkBottomCollision()) {
            currBlock.moveDown();
        }
        endY = currBlock.getY();

        int addedScore = (endY - startY) / BLOCK_CELL_SIZE * GameManager.getScorePerSecond();
        GameManager.addScore(addedScore);
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
        blockSpawnCnt = 0;
    }

    private void getNextBlockToCurr() {
        setCurrBlock(getNextBlock());
        spawnNextBlock();
        blockSpawnCnt++;
        if (blockSpawnCnt == GameManager.getSpeedUpConditionBlockSpawnCount()) {
            blockSpawnCnt = 0;
            speedUp();
        }
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
