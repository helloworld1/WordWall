package org.liberty.android.wordwall.ui;

import org.liberty.android.wordwall.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsUI extends PreferenceActivity {
    public static final String DB_NAME_KEY = "dbName";

    public static final String ACTION_DB_CHANGED = "actionDbChanged";

    public static final String EXTRA_DB_NAME = "dbName";

    private static final String DEFAULT_FILEBROWSER_ROOT = "/sdcard/anymemo/";
    private static final int RESULT_CODE = 500;


    private Preference mDataSourcePreference;
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
            startActivityForResult(intent, RESULT_CODE);
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

        if (requestCode == RESULT_CODE) {
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
