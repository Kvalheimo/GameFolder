package blog.gamedevelopmentbox2dtutorial;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.firebase.FirebaseApp;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		Box2dTutorial tut = new Box2dTutorial();
		tut.setOnline(true);
		initialize(tut, config);
		DatabaseHandler.setDb(new AndroidDatabase());
	}
}
