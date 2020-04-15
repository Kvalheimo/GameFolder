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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import blog.gamedevelopmentbox2dtutorial.Factory.LevelFactory;
import blog.gamedevelopmentbox2dtutorial.HighScore.HighScoreData;
import blog.gamedevelopmentbox2dtutorial.entity.components.PlayerComponent;
import blog.gamedevelopmentbox2dtutorial.entity.components.TransformComponent;

public class AndroidDatabase implements DatabaseHandler.DataBase {

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private IntMap<HighScoreData> hsd;

    public AndroidDatabase() {
        db = FirebaseDatabase.getInstance();
        hsd = new IntMap<>();
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
    public IntMap<HighScoreData> getHighscores() {
            dbRef = db.getReference("highscores/");
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (int i = 1; i < 7; i++) {

                        HighScoreData levelHighscores = new HighScoreData();

                        if (dataSnapshot.child("map" + i).exists()){
                            try {
                                Iterable<DataSnapshot> highscores = dataSnapshot.child("map" + i).getChildren();
                                for(DataSnapshot snapshot : highscores) {
                                    String name = snapshot.getKey();
                                    Integer score = snapshot.getValue(Integer.class);
                                    levelHighscores.addHighScore(score, name);
                                    System.out.println("FHAKDSFJLASDFKAS"+ score + name);

                                }

//                                        String name = dataSnapshot.child("map" + i).child(String.valueOf(j)).child("name").getValue(String.class);
//                                    Integer score = dataSnapshot.child("map" + i).child(String.valueOf(j)).child("value").getValue(Integer.class);
//                                    levelHighscores.addHighScore(score, name);
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
