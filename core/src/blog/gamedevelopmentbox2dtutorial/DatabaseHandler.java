package blog.gamedevelopmentbox2dtutorial;

import com.badlogic.ashley.core.Entity;

import java.util.List;


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
        List<PlayerComponent> getPlayers();
    }
}
