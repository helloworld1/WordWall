package org.liberty.android.wordwall.ui;

import org.liberty.android.wordwall.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class DatabasesListActivity extends ListActivity {
    private static final String TAG = "DatabaseListActivity";

    public static final String EXTRA_DB_NAME = "dbname";

    private Cursor mCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String proUri = "content://org.liberty.android.fantastischmemopro.databasesprovider";
            String freeUri = "content://org.liberty.android.fantastischmemo.databasesprovider";

            mCursor = getContentResolver().query(Uri.parse(proUri), null, null,
                    null, null);
            if (mCursor == null) {
                Log.v(TAG, "Content provider not found: " + proUri);
                Log.v(TAG, "Trying: " + freeUri);
                mCursor = getContentResolver().query(Uri.parse(freeUri), null, null,
                        null, null);
            }
            if (mCursor == null) {
                Log.v(TAG, "Content provider not found: " + freeUri);
                showAlertDialog();
                return;
            }
            startManagingCursor(mCursor);

            ListAdapter adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, mCursor,
                    new String[] { "dbname" },
                    new int[] { android.R.id.text1 }, 0);
            setListAdapter(adapter);
        } catch (Exception e) {
            Log.e(TAG, "AnyMemo not available");
            showAlertDialog();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Cursor c = (Cursor) l.getItemAtPosition(position);
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_DB_NAME,
                c.getString(c.getColumnIndex("dbname")));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void showErrorMessage() {
        Context context = getApplicationContext();
        CharSequence text = "Please install AnyMemo with version higher than 10.2.992.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void showAlertDialog() {
        View alertView = View.inflate(DatabasesListActivity.this,
                R.layout.link_alert, null);
        TextView textView = (TextView) alertView
                .findViewById(R.id.link_alert_message);
        textView.setText(Html.fromHtml(getString(R.string.msg_install_anymemo)));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        new AlertDialog.Builder(DatabasesListActivity.this)
                .setView(alertView)
                .setTitle(R.string.title_install_anymemo)
                .setPositiveButton(getString(R.string.button_install_anymemo),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent myIntent = new Intent();
                                myIntent.setAction(Intent.ACTION_VIEW);
                                myIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                                myIntent.setData(Uri
                                        .parse(getString(R.string.link_anymemo_free)));
                                startActivity(myIntent);
                                finish();
                            }
                        })
                .setNegativeButton(getString(R.string.cancel_text),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        }).create().show();
    }
}
