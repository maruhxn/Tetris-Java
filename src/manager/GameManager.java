package manager;

public class GameManager {
    private static int score = 0;
    private static int level = 1;
    private static int scorePerSecond = 10;

    private static final int INITIAL_BLOCK_SPEED = 1000;
    private static int blockDownSpeed = INITIAL_BLOCK_SPEED;
    private static int minusSpeedPerClear = 50;
    private static int LEVEL_UP_CONDITION_SCORE = 1000;

    private static int SPEED_UP_CONDITION_BLOCK_SPAWN_COUNT = 10;

    public static int getSpeedUpConditionBlockSpawnCount() {
        return SPEED_UP_CONDITION_BLOCK_SPAWN_COUNT;
    }

    public static int getBlockDownSpeed() {
        return blockDownSpeed;
    }

    public static void speedUp() {
        if (blockDownSpeed > 300) {
            GameManager.blockDownSpeed -= minusSpeedPerClear;
        }
    }

    public static int getScorePerSecond() {
        return scorePerSecond;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        if (level <= 0) return;
        GameManager.level = level;
    }

    public static int getScore() {
        return score;
    }

    public static void addScore(int addedScore) {
        score += addedScore;
    }

    public static void addScorePerSecond() {
        score += scorePerSecond;
    }

    public static void checkLevelUp() {
        setLevel(score / LEVEL_UP_CONDITION_SCORE);
    }
}
