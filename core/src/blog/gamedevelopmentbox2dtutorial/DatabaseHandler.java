package blog.gamedevelopmentbox2dtutorial;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;


import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
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
        void publishPlayer(String uniqueID,Entity player);
        ArrayList<Entity> getPlayers();
        void addPlayerEventListener(final ArrayList<Entity> opponents, final LevelFactory levelFactory);
    }
}
