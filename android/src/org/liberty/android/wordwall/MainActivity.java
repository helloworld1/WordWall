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

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {

    private WordWall game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        
        String dbName = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsUI.DB_NAME_KEY, "");
        CardResolver resolver = new CardResolverMultiplexer(new AnyMemoCardResolver(this, dbName), new TutorialCardResolver());
        game = new WordWall(resolver);
        initialize(game, cfg);
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(SettingsUI.ACTION_DB_CHANGED);
        registerReceiver(serviceEventListener, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
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
                CardResolver resolver = new CardResolverMultiplexer(new AnyMemoCardResolver(MainActivity.this, dbName), new TutorialCardResolver());
                game.setCardResolver(resolver);

            }
        }
    };
}
