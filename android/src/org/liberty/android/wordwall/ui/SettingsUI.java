package org.liberty.android.wordwall.ui;

import org.liberty.android.wordwall.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsUI extends PreferenceActivity {

    public static final String DB_NAME_KEY = "dbName";

    public static final String SELECTED_BACKGROUND_KEY = "selected_background";

    public static final String ACTION_DB_CHANGED = "actionDbChanged";

    public static final String ACTION_SETTINGS_CHANGED = "actionSettingsChanged";

    public static final String EXTRA_DB_NAME = "dbName";

    private static final int ACTIVITY_DB = 1;

    private Preference mDataSourcePreference;

    private ListPreference backgroundPreference;

    public static final String DEFAULT_DATA_SOURCE = "french-body-parts.db";

    private static final String TAG = "SettingsUI";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        addPreferencesFromResource(R.layout.settings_ui);

        mDataSourcePreference = findPreference(DB_NAME_KEY);

        mDataSourcePreference
                .setOnPreferenceClickListener(dataSourcePreferenceOnClickListener);

        backgroundPreference = (ListPreference) findPreference(SELECTED_BACKGROUND_KEY);
        backgroundPreference
            .setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference,
                        Object newValue) {
                    Log.v(TAG, "Preference changed!");
                    if (newValue != null) {
                        Intent intent = new Intent();
                        intent.setAction(ACTION_SETTINGS_CHANGED);
                        sendBroadcast(intent);
                        return true;
                    } else {
                        return false;
                    }
                }
        });

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigurationChanged");
    }

    private final Preference.OnPreferenceClickListener dataSourcePreferenceOnClickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference p) {
            Intent intent = new Intent(SettingsUI.this,
                    DatabasesListActivity.class);
            Log.v(TAG, "Before start FileBrowser");
            startActivityForResult(intent, ACTIVITY_DB);
            Log.v(TAG, "After start FileBrowser");
            return true;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "onActivityResult" + resultCode);
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.i(TAG, "Inside onActivityResult: Canceled");
            return;
        }

        if (requestCode == ACTIVITY_DB) {
            String mPreferenceValueDataSource = data
                    .getStringExtra(DatabasesListActivity.EXTRA_DB_NAME);
            PreferenceManager
                .getDefaultSharedPreferences(this)
                .edit()
                .putString(mDataSourcePreference.getKey(), mPreferenceValueDataSource)
                .commit();

            // Broadcast the db name change so the receivers can change the card provider
            Intent intent = new Intent();
            intent.setAction(ACTION_DB_CHANGED);
            intent.putExtra(EXTRA_DB_NAME, mPreferenceValueDataSource);
            sendBroadcast(intent);
            Log.i(TAG, "Preference updated" + mPreferenceValueDataSource);
        }
    }
}
