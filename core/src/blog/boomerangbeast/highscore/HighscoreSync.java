package blog.boomerangbeast.highscore;

import com.badlogic.gdx.utils.IntMap;

import blog.boomerangbeast.DatabaseHandler;

public class HighscoreSync {

    private static DatabaseHandler dbHandler;

    public static IntMap<HighscoreData> hsd;

    static {
        dbHandler = new DatabaseHandler();
        hsd = new IntMap<>();
        for (int i = 1; i < 5; i++) {
            HighscoreData data = new HighscoreData();
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
        try {
            dbHandler.getDb().publishHighscores();
        }
        catch(Exception e) {
            System.out.println("Could not publish highscore to database");
        }
    }
}
