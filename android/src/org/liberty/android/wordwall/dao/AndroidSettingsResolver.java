package org.liberty.android.wordwall.dao;

import org.liberty.android.wordwall.ui.SettingsUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AndroidSettingsResolver implements SettingsResolver {

    private SharedPreferences preferences;

    public AndroidSettingsResolver(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getBackgroundImage() {
        return preferences.getString(SettingsUI.SELECTED_BACKGROUND_KEY, "images/red_with_normal.etc1");
    }
}

