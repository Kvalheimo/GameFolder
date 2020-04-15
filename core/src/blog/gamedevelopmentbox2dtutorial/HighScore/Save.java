package blog.gamedevelopmentbox2dtutorial.HighScore;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;

import blog.gamedevelopmentbox2dtutorial.DFUtils;
import blog.gamedevelopmentbox2dtutorial.DatabaseHandler;
import sun.font.TrueTypeFont;

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

    public static void load(int level) {
        try {
            hsd= dbHandler.getDb().getHighscores();
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void publish(){
        dbHandler.getDb().publishHighscores();
    }
}
