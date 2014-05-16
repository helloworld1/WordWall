package org.liberty.android.wordwall.ui;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.MainActivity;
import org.liberty.android.wordwall.R;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * This is used in Android to launch from launcher icon.
 */
public class MainMenuActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);

        setupCards();
    }

    private void setupCards() {
        Intent previewIntent = new Intent(this, MainActivity.class);
        Card previewCard = new MenuCard(this, R.string.preview_card_header, R.string.preview_card_text, R.drawable.cat, previewIntent);

        Intent settingsIntent = new Intent(this, SettingsUI.class);
        Card settingsCard = new MenuCard(this, R.string.settings_card_header, R.string.settings_card_text, R.drawable.monkey, settingsIntent);

        Intent launchDaydreamIntent = new Intent(android.provider.Settings.ACTION_DREAM_SETTINGS);
        Card daydreamCard = new MenuCard(this, R.string.daydream_card_header, R.string.daydream_card_text, R.drawable.dragon, launchDaydreamIntent);

        Intent launchLiveWallpaperIntent = new Intent();
        launchLiveWallpaperIntent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        Card livewallpaperCard = new MenuCard(this, R.string.livewallpaper_card_header, R.string.livewallpaper_card_text, R.drawable.lizard, launchLiveWallpaperIntent);

        CardListView cardListView = (CardListView) findViewById(R.id.menu_card_list);
        List<Card> cards = new ArrayList<Card>();
        cards.add(previewCard);
        cards.add(settingsCard);
        cards.add(daydreamCard);
        cards.add(livewallpaperCard);
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this, cards);
        cardListView.setAdapter(cardArrayAdapter);
    }
}

