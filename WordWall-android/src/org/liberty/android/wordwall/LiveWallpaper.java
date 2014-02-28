package org.liberty.android.wordwall;

import org.liberty.android.wordwall.dao.AnyMemoCardResolver;
import org.liberty.android.wordwall.dao.CardResolver;
import org.liberty.android.wordwall.dao.CardResolverMultiplexer;
import org.liberty.android.wordwall.dao.TutorialCardResolver;
import org.liberty.android.wordwall.ui.SettingsUI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;

public class LiveWallpaper extends AndroidLiveWallpaperService {

    private WordWall game;

    @Override
    public void onCreateApplication () {
        super.onCreateApplication();

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.getTouchEventsForLiveWallpaper = true;
        
        String dbName = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsUI.DB_NAME_KEY, "");
        CardResolver resolver = new CardResolverMultiplexer(new AnyMemoCardResolver(LiveWallpaper.this, dbName), new TutorialCardResolver());
        game = new WordWall(resolver);
        initialize(game, cfg);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter filter = new IntentFilter();
        filter.addAction(SettingsUI.ACTION_DB_CHANGED);
        registerReceiver(serviceEventListener, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(serviceEventListener);
    }

    private BroadcastReceiver serviceEventListener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (game == null) {
                return;
            }
            String action = intent.getAction();
            if (action.equals(SettingsUI.ACTION_DB_CHANGED)) {
                Bundle extras = intent.getExtras();
                String dbName = extras.getString(SettingsUI.EXTRA_DB_NAME);
                CardResolver resolver = new CardResolverMultiplexer(new AnyMemoCardResolver(LiveWallpaper.this, dbName), new TutorialCardResolver());
                game.setCardResolver(resolver);

            }
        }
    };
}
