package org.liberty.android.wordwall.ui;

import org.liberty.android.wordwall.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

public class SettingsUI extends PreferenceActivity {
    private static final String DEFAULT_FILEBROWSER_ROOT = "/sdcard/anymemo/";
    private static final int RESULT_CODE = 500;
    public static final String PREFERENCE_KEY_DATA_SOURCE = "data_source";
    private String mPreferenceValueDataSource = null;
    private Preference mDataSourcePreference;
    public static final String DEFAULT_DATA_SOURCE = "french-body-parts.db";
    private static final String DEFAULT_FILE_EXTENSION = ".db";

    private static final String TAG = "SettingsUI";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        addPreferencesFromResource(R.layout.settings_ui);

        mDataSourcePreference = findPreference(PREFERENCE_KEY_DATA_SOURCE);
        mDataSourcePreference
                .setOnPreferenceClickListener(dataSourcePreferenceOnClickListener);

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        /* set if the orientation change is allowed */
        if (!settings.getBoolean("allow_orientation", false)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mPreferenceValueDataSource = settings.getString(
                mDataSourcePreference.getKey(), DEFAULT_DATA_SOURCE);
        Log.v(TAG, "onCreate -- Prefereence loaded: "
                + mPreferenceValueDataSource);
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
            mPreferenceValueDataSource = data
                    .getStringExtra(DatabasesListActivity.EXTRA_DB_NAME);
            Log.i(TAG, "Preference updated" + mPreferenceValueDataSource);
            return;
        }
        Log.v(TAG, "Preference not updated" + mPreferenceValueDataSource);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "Start onPause");
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .edit()
                .putString(mDataSourcePreference.getKey(),
                        mPreferenceValueDataSource).commit();
        Log.v(TAG, "End onPause -- Preference saved"
                + mPreferenceValueDataSource);
    }
}
