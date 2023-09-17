package score;

public class ScoreManager {
    private static int score = 0;
    private static int level = 1;
    private static int scorePerSecond = 10;

    public static int getScorePerSecond() {
        return scorePerSecond;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        if (level <= 0) return;
        ScoreManager.level = level;
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
        setLevel(score / 1000);
    }
}
