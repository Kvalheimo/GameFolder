package blog.gamedevelopmentbox2dtutorial;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector3;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.badlogic.ashley.core.Entity;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;

public class AndroidDatabase implements DatabaseHandler.DataBase {

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    public AndroidDatabase() {
        db = FirebaseDatabase.getInstance();
    }


    public void addPlayerEventListener(final HashMap<String,Entity> opponents, final LevelFactory levelFactory, final PooledEngine engine) {
        dbRef = db.getReference("players/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : players) {
                    String key = snapshot.getKey();
                    if (snapshot.child("pos").child("x").exists()){
                        Vector3 pos = new Vector3(snapshot.child("pos").child("x").getValue(Float.class), snapshot.child("pos").child("y").getValue(Float.class),0);
                        if (!opponents.containsKey(key)) {
                            opponents.put(key, levelFactory.createOpponent(pos, 1));
                        }
                        else {
                            opponents.get(key).getComponent(TransformComponent.class).position.set(pos);
                        }
                    }

                }
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
