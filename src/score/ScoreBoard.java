package score;

import java.util.List;

public class ScoreBoard {
    public static void main(String[] args) {
        ScoreDao dao = new ScoreDao();
//
//        Score score = new Score();
//        score.setName("지완");
//        score.setScore(1000);
//
//        dao.addResult(score);
//
//        System.out.println(score.getName() + "등록 성공");
//
//        List<Score> scores = dao.getTop5();
//        System.out.println("전체 조회 성공");
//
//        System.out.println(scores);
        dao.deleteAllScores();
    }
}
