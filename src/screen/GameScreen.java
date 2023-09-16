package screen;

import score.Score;
import unit.block.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static constant.Constants.*;

public class GameScreen extends Screen {
    private final GameArea gameArea;
    private final GameInfoArea gameInfoArea;
    private Block currBlock;
    private Block nextBlock;
    private Timer timer;
    private Score score;
    private int scorePerSecond = SCORE_PER_SECOND;


    public GameScreen() {
        setLayout(new BorderLayout());

        init();

        gameArea = new GameArea();
        gameInfoArea = new GameInfoArea();

        add(gameArea, BorderLayout.WEST);
        add(gameInfoArea, BorderLayout.CENTER);

        score = Score.getInstance(0);

        setKeyListener();

        startTimer();
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveDown();
                checkCollision();
                checkStatic();
                gameArea.repaint();
                gameInfoArea.repaint();
            }

        });
        timer.start();
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public Block getCurrBlock() {
        return currBlock;
    }

    private void checkStatic() {
    }

    private void checkCollision() {

    }

    private void moveDown() {
        int blockY = currBlock.getY();
        int targetPoint = blockY + currBlock.getHeight() * BLOCK_CELL_HEIGHT;
        if (targetPoint < GAME_AREA_HEIGHT - BLOCK_CELL_HEIGHT) {
            currBlock.moveDown();
            score.setScore(score.getScore() + scorePerSecond);
        }
    }

    private void setKeyListener() {
    }

    private void init() {
        createRandomBlock();
        setCurrBlock(nextBlock);
        createRandomBlock();
    }

    private void createRandomBlock() {
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

}
