package blog.gamedevelopmentbox2dtutorial;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.badlogic.ashley.core.Entity;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.UUID;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.HighScore.HighScoreData;
import blog.gamedevelopmentbox2dtutorial.entity.components.OpponentComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.StateComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;

public class AndroidDatabase implements DatabaseHandler.DataBase {

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private IntMap<HighScoreData> hsd;
    String uniqueID;

    public AndroidDatabase() {
        db = FirebaseDatabase.getInstance();
        hsd = new IntMap<>();
        uniqueID = UUID.randomUUID().toString();
    }


    public void addPlayerEventListener(final HashMap<String,Entity> opponents, final LevelFactory levelFactory, final PooledEngine engine, final int level) {
        dbRef = db.getReference("map"+ level + "/players/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> players = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : players) {
                    String key = snapshot.getKey();

                    //Show all opponents and yourself if in debugmode
                    if (!key.equals(uniqueID) || Box2dTutorial.DEBUG) {
                        if (snapshot.child("pos").child("x").exists()) {
                            try {
                                Vector3 pos = new Vector3(snapshot.child("pos").child("x").getValue(Float.class), snapshot.child("pos").child("y").getValue(Float.class), 0);
                                if (!opponents.containsKey(key)) {
                                    opponents.put(key, levelFactory.createOpponent(pos, 1));
                                } else {
                                    opponents.get(key).getComponent(TransformComponent.class).position.set(pos);
                                    opponents.get(key).getComponent(OpponentComponent.class).setPos(pos);
                                    opponents.get(key).getComponent(StateComponent.class).set(snapshot.child("state").getValue(Integer.class));
                                }
                            } catch (Exception e) {

                            }
                        }
                    }

                }
                for (String opponent : opponents.keySet()) {
                    if (!dataSnapshot.child(opponent).exists()){
                        engine.removeEntity(opponents.get(opponent));
                        opponents.remove(opponent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbRef.child(uniqueID).onDisconnect().removeValue();
    }

    @Override
    public IntMap<HighScoreData> getHighscores() {
            dbRef = db.getReference("highscores/");
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (int i = 1; i < 5; i++) {

                        HighScoreData levelHighscores = new HighScoreData();

                        if (dataSnapshot.child("map" + i).exists()){
                            try {
                                Iterable<DataSnapshot> highscores = dataSnapshot.child("map" + i).getChildren();
                                for(DataSnapshot snapshot : highscores) {
                                    String name = snapshot.getKey();
                                    Integer score = snapshot.getValue(Integer.class);
                                    levelHighscores.addHighScore(score, name, true);

                                }
                            } catch (Exception e){

                            }
                        }

                        hsd.put(i, levelHighscores);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        return hsd;
    }

    @Override
    public void publishHighscores() {
        for (int i = 1; i < hsd.size; i++){
            dbRef = db.getReference("highscores/");
            String[] names = hsd.get(i).getNames();
            long[] scores = hsd.get(i).getHighScores();
            for (int j = 0; j < hsd.get(i).MAX_SCORES; j++) {
                dbRef.child("map" + i).child(names[j]).setValue(scores[j]);
            }
        }
    }

    @Override
    public void publishPlayer(Entity player, int level) {
        dbRef = db.getReference("map"+ level + "/players/" + uniqueID);
        dbRef.child("pos").child("x").setValue(player.getComponent(TransformComponent.class).position.x);
        dbRef.child("pos").child("y").setValue(player.getComponent(TransformComponent.class).position.y);
        dbRef.child("state").setValue(player.getComponent(StateComponent.class).state);
    }

}
