package org.liberty.android.wordwall;

import org.liberty.android.wordwall.ui.SettingsUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * This is used in Android to launch from launcher icon.
 */
public class MainMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
    }

    public void openPreview(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void openSettings(View v) {
        Intent intent = new Intent(this, SettingsUI.class);
        startActivity(intent);
    }

    public void forceClose(View v) {
        new AlertDialog.Builder(this)
            .setTitle(R.string.force_close_text)
            .setMessage(R.string.force_close_dialog_message)
            .setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
            .setNegativeButton(R.string.cancel_text, null)
            .show();
    }
}

