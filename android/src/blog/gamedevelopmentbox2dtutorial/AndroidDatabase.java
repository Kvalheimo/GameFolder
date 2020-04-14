package blog.gamedevelopmentbox2dtutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.math.Vector3;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.badlogic.ashley.core.Entity;


import java.util.ArrayList;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;

public class AndroidDatabase implements DatabaseHandler.DataBase {

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public AndroidDatabase() {
        db = FirebaseDatabase.getInstance();
    }


    public void addPlayerEventListener(final ArrayList<Entity> opponents, final LevelFactory levelFactory) {
        dbRef = db.getReference("players/");
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : players) {
                    if (snapshot.child("x").exists()){
                        Float posX = snapshot.child("x").getValue(Float.class);
                        Vector3 pos = new Vector3(snapshot.child("x").getValue(Float.class), snapshot.child("y").getValue(Float.class),0);
                        opponents.add(levelFactory.createOpponent(pos, 1));
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void publishPlayer(String uniqueID, Entity player) {
        dbRef = db.getReference("players/" + uniqueID + "/pos");
        dbRef.child("x").setValue(player.getComponent(TransformComponent.class).position.x);
        dbRef.child("y").setValue(player.getComponent(TransformComponent.class).position.y);
    }

    @Override
    public ArrayList<Entity> getPlayers() {
        dbRef = db.getReference("players/");
        return null;
    }
}
