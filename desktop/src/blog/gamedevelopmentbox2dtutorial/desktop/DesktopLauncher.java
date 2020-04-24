package blog.gamedevelopmentbox2dtutorial.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import blog.gamedevelopmentbox2dtutorial.Box2dTutorial;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Box2dTutorial tut = new Box2dTutorial();
		tut.setOnline(false);
		new LwjglApplication(tut, config);
	}
}
