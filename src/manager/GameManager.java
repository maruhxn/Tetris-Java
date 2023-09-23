package manager;

public class GameManager {
    private static int score = 0;
    private static int level = 1;
    private static final int scorePerSecond = 10;
    private static final int INITIAL_BLOCK_SPEED = 1000;
    private static int blockDownSpeed = INITIAL_BLOCK_SPEED;
    private static final int increasedSpeedPerClear = 50;
    private static final int LEVEL_UP_CONDITION_SCORE = 5000;
    private static final int SPEED_UP_CONDITION_BLOCK_SPAWN_COUNT = 20;

    public static int getSpeedUpConditionBlockSpawnCount() {
        return SPEED_UP_CONDITION_BLOCK_SPAWN_COUNT;
    }

    public static int getBlockDownSpeed() {
        return blockDownSpeed;
    }

    public static void speedUp() {
        if (blockDownSpeed > 300) {
            blockDownSpeed -= increasedSpeedPerClear;
        }
    }

    public static int getScorePerSecond() {
        return scorePerSecond;
    }

    public static int getLevel() {
        return level;
    }

    public static int getScore() {
        return score;
    }

    public static void resetScore() {
        GameManager.score = 0;
    }

    public static void addScore(int addedScore) {
        score += addedScore;
    }

    public static void addScorePerSecond() {
        score += scorePerSecond;
    }

    public static void levelUp() {
        int targetLevel = score / LEVEL_UP_CONDITION_SCORE;
        if (targetLevel <= 0) return;
        level = targetLevel;
    }
}
