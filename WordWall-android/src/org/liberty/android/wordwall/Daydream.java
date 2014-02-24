package org.liberty.android.wordwall;

import android.annotation.TargetApi;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidDaydream;

@TargetApi(17)
public class Daydream extends AndroidDaydream {
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        initialize(new WordWall(), cfg);
    }
}
