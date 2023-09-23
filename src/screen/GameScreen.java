package screen;

import client.GameClient;
import component.AbstractScreen;
import manager.GameKeyManager;
import manager.GameManager;
import unit.block.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static manager.GameSizeManager.GAME_SIZE;

public class GameScreen extends AbstractScreen {
    public final GameArea gameArea;
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

        add(gameInfoArea, BorderLayout.EAST);

        startTimer();

        SwingUtilities.invokeLater(() -> {
            setKeyListener();
            setFocusable(true);
            requestFocusInWindow();
        });
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            if (!isPaused) {
                blockDownCycle();
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
        for (int y = GAME_SIZE.getHeight() - GAME_SIZE.getBlockCellSize(); y > 0; y -= GAME_SIZE.getBlockCellSize()) {
            int clearedColumnCnt = 0;
            for (int x = 0; x < GAME_SIZE.getGameAreaWidth(); x += GAME_SIZE.getBlockCellSize()) {
                if (gameArea.background[y][x] == null) break;
                clearedColumnCnt++;
            }

            if (clearedColumnCnt == GAME_SIZE.getGameAreaWidth() / GAME_SIZE.getBlockCellSize()) {
                y = clearLine(y);
            }
        }
    }

    private int clearLine(int y) {
        gameArea.background[y] = new Color[GAME_SIZE.getGameAreaWidth()];
        for (int i = y; i > 0; i -= GAME_SIZE.getBlockCellSize()) {
            gameArea.background[i] = gameArea.background[i - GAME_SIZE.getBlockCellSize()];
        }
        y += GAME_SIZE.getBlockCellSize();
        speedUp();
        gameArea.repaint();
        gameInfoArea.repaint();
        return y;
    }

    private void speedUp() {
        GameManager.speedUp();
        timer.setDelay(GameManager.getBlockDownSpeed());
    }

    private void checkGameOver() {
        // (0, 0~400)에 블럭이 있다면 게임 오버.
        for (int i = 0; i < GAME_SIZE.getGameAreaWidth(); i = i + GAME_SIZE.getBlockCellSize()) {
            if (gameArea.background[0][i] != null) {
                timer.stop();
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                resetKeyListeners();

                Runnable task = () -> {
                    GameClient client = (GameClient) getTopLevelAncestor();
                    client.switchPanel(new GameOverScreen());
                };
                scheduler.schedule(task, 3, TimeUnit.SECONDS);
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
        if (currBlock.getBottomEdge() == GAME_SIZE.getHeight()) return true;

        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * GAME_SIZE.getBlockCellSize();
                    int y = currBlock.getY() + i * GAME_SIZE.getBlockCellSize();
                    // 현재 y 바로 다음 칸의 background가 채워져 있다면 충돌.
                    if (gameArea.background[y + GAME_SIZE.getBlockCellSize()][x] != null) return true;
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
                    int x = currBlock.getX() + j * GAME_SIZE.getBlockCellSize();
                    int y = currBlock.getY() + i * GAME_SIZE.getBlockCellSize();
                    if (gameArea.background[y][x - GAME_SIZE.getBlockCellSize()] != null) return true;
                }
            }
        }

        return false;
    }

    private boolean checkRightCollision() {
        if (currBlock.getRightEdge() >= GAME_SIZE.getGameAreaWidth()) return true;

        int[][] blockShape = currBlock.getShape();
        int w = currBlock.getWidth();
        int h = currBlock.getHeight();

        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (blockShape[i][j] == 1) {
                    int x = currBlock.getX() + j * GAME_SIZE.getBlockCellSize();
                    int y = currBlock.getY() + i * GAME_SIZE.getBlockCellSize();
                    if (gameArea.background[y][x + GAME_SIZE.getBlockCellSize()] != null) return true;
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
                    int x = currBlock.getX() + j * GAME_SIZE.getBlockCellSize();
                    int y = currBlock.getY() + i * GAME_SIZE.getBlockCellSize();

                    if (x < 0 || x >= GAME_SIZE.getGameAreaWidth() || y >= GAME_SIZE.getHeight()) {
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
                    int x = currBlock.getX() + j * GAME_SIZE.getBlockCellSize();
                    int y = currBlock.getY() + i * GAME_SIZE.getBlockCellSize();
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

        int addedScore = (endY - startY) / GAME_SIZE.getBlockCellSize() * GameManager.getScorePerSecond();
        GameManager.addScore(addedScore);
        moveBlockToBackground();
        gameArea.repaint();
    }

    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int gameOverKey = GameKeyManager.getGameOverKey();
                int moveLeftKey = GameKeyManager.getMoveLeftKey();
                int moveDownKey = GameKeyManager.getMoveDownKey();
                int moveRightKey = GameKeyManager.getMoveRightKey();
                int pauseKey = GameKeyManager.getPauseKey();
                int rotateKey = GameKeyManager.getRotateKey();
                int superDropKey = GameKeyManager.getSuperDropKey();

                if (e.getKeyCode() == moveLeftKey) moveLeft();
                else if (e.getKeyCode() == moveRightKey) moveRight();
                else if (e.getKeyCode() == moveDownKey) blockDownCycle();
                else if (e.getKeyCode() == superDropKey) superDrop();
                else if (e.getKeyCode() == rotateKey) rotateBlock();
                else if (e.getKeyCode() == pauseKey) pause();
                else if (e.getKeyCode() == gameOverKey) forceExit();

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
                    if (e.getKeyCode() == GameKeyManager.getPauseKey()) {
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
