package blog.boomerangbeast.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import blog.boomerangbeast.BoomerangBeast;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		BoomerangBeast tut = new BoomerangBeast();
		tut.setOnline(false);
		new LwjglApplication(tut, config);
	}
}
