package org.liberty.android.wordwall.client;

import org.liberty.android.wordwall.TutorialCardResolver;
import org.liberty.android.wordwall.WordWall;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new WordWall(new TutorialCardResolver());
	}
}
