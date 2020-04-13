package blog.gamedevelopmentbox2dtutorial;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.badlogic.ashley.core.Entity;


import java.util.List;

import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;

public class AndroidDatabase implements DatabaseHandler.DataBase {

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public AndroidDatabase() {
        db = FirebaseDatabase.getInstance();
    }

    @Override
    public void publishPlayer(Entity player) {
        dbRef = db.getReference("message");
        dbRef.setValue("Hello, World!");
    }

    @Override
    public List<PlayerComponent> getPlayers() {
        return null;
    }
}
