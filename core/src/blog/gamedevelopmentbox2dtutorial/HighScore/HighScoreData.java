package blog.gamedevelopmentbox2dtutorial.HighScore;

import com.badlogic.gdx.utils.IntMap;

import java.io.Serializable;
import java.util.Random;

public class HighScoreData implements Serializable{


    private static final long serialVersionUID = 1;


    public final int MAX_SCORES = 10;
    private long[] highScores;
    private String[] names;
    Random rand;

    private long tentativeScore;

    public HighScoreData() {
        highScores = new long[MAX_SCORES];
        names = new String[MAX_SCORES];

        for(int i = 0; i < MAX_SCORES; i++) {
            highScores[i] = 9999;
            names[i] = "NAN";
        }
    }

    public long[] getHighScores() { return highScores; }
    public String[] getNames() { return names; }

    public long getTentativeScore() { return tentativeScore; }
    public void setTentativeScore(long i) { tentativeScore = i; }

    public boolean isHighScore(long score) {
        return score < highScores[MAX_SCORES - 1];
    }

    public void addHighScore(long newScore, String name, boolean fromDatabase) {
        if (!fromDatabase){
            int randomId = rand.nextInt((9999) + 1);
            name = name + "-" + randomId;
        }

        if(isHighScore(newScore)) {
            highScores[MAX_SCORES - 1] = newScore;
            names[MAX_SCORES - 1] = name;
            sortHighScores();
        }
    }

    public void sortHighScores() {
        for(int i = 0; i < MAX_SCORES; i++) {
            long score = highScores[i];
            String name = names[i];
            int j;
            for(j = i - 1;
                j >= 0 && highScores[j] > score;
                j--) {
                highScores[j + 1] = highScores[j];
                names[j + 1] = names[j];
            }
            highScores[j + 1] = score;
            names[j + 1] = name;
        }
    }

}

















