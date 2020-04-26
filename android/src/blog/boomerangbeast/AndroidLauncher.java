package blog.boomerangbeast;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		BoomerangBeast tut = new BoomerangBeast();
		tut.setOnline(true);
		initialize(tut, config);
		DatabaseHandler.setDb(new AndroidDatabase());
	}
}
