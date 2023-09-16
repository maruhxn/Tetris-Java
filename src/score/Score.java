package score;

public class Score {
    private int score;
    private static Score instance; // 싱글톤 인스턴스

    private Score(int score) {
        this.score = score;
    }

    public static Score getInstance(int score) {
        if (instance == null) {
            instance = new Score(score);
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
