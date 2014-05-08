package org.liberty.android.wordwall.desktop;

import org.liberty.android.wordwall.WordWall;
import org.liberty.android.wordwall.dao.TutorialCardResolver;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "WordWall";
        cfg.width = 480;
        cfg.height = 800;

        new LwjglApplication(new WordWall(new TutorialCardResolver()), cfg);

	}
}
