package blog.gamedevelopmentbox2dtutorial;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.IntMap;

import java.util.ArrayList;
import java.util.HashMap;


import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.HighScore.HighScoreData;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;

public class DatabaseHandler {

    private static DataBase DB;

    public static void setDb(DataBase db) {
        DB = db;
    }

    public static DataBase getDb() {
        return DB;
    }

    public interface DataBase {
        void publishPlayer(Entity player);
        ArrayList<Entity> getPlayers();
        void addPlayerEventListener(final HashMap<String,Entity> opponents, final LevelFactory levelFactory, final PooledEngine engine);
        IntMap<HighScoreData> getHighscores();
        void publishHighscores();
    }
}
