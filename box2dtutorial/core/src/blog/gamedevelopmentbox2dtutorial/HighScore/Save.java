package blog.gamedevelopmentbox2dtutorial.HighScore;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.IntMap;

import blog.gamedevelopmentbox2dtutorial.DFUtils;

public class Save {


    public static IntMap<HighScoreData> hsd = new IntMap<>();

    public static void save(int level) {
        try {
            System.out.println(level);
            switch (level) {
                case 1:
                    ObjectOutputStream out1 = new ObjectOutputStream(
                            new FileOutputStream("highscore_l1.sav")
                    );
                    out1.writeObject(hsd.get(1));
                    out1.close();
                case 2:
                    ObjectOutputStream out2 = new ObjectOutputStream(
                            new FileOutputStream("highscore_l2.sav")
                    );
                    out2.writeObject(hsd.get(2));
                    out2.close();
                case 3:
                    ObjectOutputStream out3 = new ObjectOutputStream(
                            new FileOutputStream("highscore_l3.sav")
                    );
                    out3.writeObject(hsd.get(3));
                    out3.close();
                case 4:
                    ObjectOutputStream out4 = new ObjectOutputStream(
                            new FileOutputStream("highscore_l4.sav")
                    );
                    out4.writeObject(hsd.get(4));
                    out4.close();
                case 5:
                    ObjectOutputStream out5 = new ObjectOutputStream(
                            new FileOutputStream("highscore_l5.sav")
                    );
                    out5.writeObject(hsd.get(5));
                    out5.close();
                case 6:
                    ObjectOutputStream out6 = new ObjectOutputStream(
                            new FileOutputStream("highscore_l6.sav")
                    );
                    out6.writeObject(hsd.get(6));
                    out6.close();
            }


        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load(int level) {
        try {
            if(!saveFileExists(level)) {
                init(level);
            }

            switch (level) {
                case 1:
                    ObjectInputStream in1 = new ObjectInputStream(
                            new FileInputStream("highscore_l1.sav")
                    );
                    hsd.put(1, (HighScoreData) in1.readObject());
                    in1.close();
                case 2:
                    ObjectInputStream in2 = new ObjectInputStream(
                            new FileInputStream("highscore_l2.sav")
                    );
                    hsd.put(2, (HighScoreData)in2.readObject());
                    in2.close();
                case 3:
                    ObjectInputStream in3 = new ObjectInputStream(
                            new FileInputStream("highscore_l3.sav")
                    );
                    hsd.put(3, (HighScoreData)in3.readObject());
                    in3.close();
                case 4:
                    ObjectInputStream in4 = new ObjectInputStream(
                            new FileInputStream("highscore_l4.sav")
                    );
                    hsd.put(4, (HighScoreData) in4.readObject());
                    in4.close();
                case 5:
                    ObjectInputStream in5 = new ObjectInputStream(
                            new FileInputStream("highscore_l5.sav")
                    );
                    hsd.put(5, (HighScoreData) in5.readObject());
                    in5.close();
                case 6:
                    ObjectInputStream in6 = new ObjectInputStream(
                            new FileInputStream("highscore_l6.sav")
                    );
                    hsd.put(6, (HighScoreData) in6.readObject());
                    in6.close();

            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists(int level) {

        switch (level){
            case 1:
                File f1 = new File("highscore_l1.sav");
                return f1.exists();
            case 2:
                File f2 = new File("highscore_l2.sav");
                return f2.exists();
            case 3:
                File f3 = new File("highscore_l3.sav");
                return f3.exists();
            case 4:
                File f4 = new File("highscore_l4.sav");
                return f4.exists();
            case 5:
                File f5 = new File("highscore_l5.sav");
                return f5.exists();
            case 6:
                File f6 = new File("highscore_l6.sav");
                return f6.exists();
        }
        return true;


    }

    public static void init(int level) {
        HighScoreData data = new HighScoreData();
        data.init();
        hsd.put(level, data);
        save(level);
    }

}
