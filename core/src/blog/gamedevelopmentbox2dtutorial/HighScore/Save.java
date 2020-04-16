package blog.gamedevelopmentbox2dtutorial.HighScore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;

import blog.gamedevelopmentbox2dtutorial.DatabaseHandler;

public class Save {

    private static DatabaseHandler dbHandler;

    public static IntMap<HighScoreData> hsd;

    public Save() {
        dbHandler = new DatabaseHandler();
        hsd = new IntMap<>();
        for (int i = 1; i < 7; i++) {
            HighScoreData data = new HighScoreData();
            hsd.put(i, data);
        }
    }

    public static void load() {
        try {
            hsd= dbHandler.getDb().getHighscores();
        }
        catch(Exception e) {
            System.out.println("Could not retrieve highscores from database");
        }
    }

    public static void publish(){
        dbHandler.getDb().publishHighscores();
    }
}
