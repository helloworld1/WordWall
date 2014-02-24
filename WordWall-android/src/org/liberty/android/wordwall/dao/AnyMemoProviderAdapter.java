package org.liberty.android.wordwall.dao;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.Card;
import org.liberty.android.wordwall.ContentAdapter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Content provider to work with AnyMemo.
 * 
 * @author zxy1256@gmail.com
 * 
 */
public class AnyMemoProviderAdapter implements ContentAdapter {
    private static String AUTHROITY = "org.liberty.android.fantastischmemo.cardprovider";
    private static String TAG = "AnyMemoProviderAdapter";

    private final Context mContext;
    private final String dbPath;

    /**
     * Throws exception if the DBPATH does not exist (either AnyMemo or the db
     * is not available).
     * 
     * @param context
     * @param dbPath
     * @throws Exception
     */
    public AnyMemoProviderAdapter(Context context, String dbPath) {
        mContext = context;
        this.dbPath = dbPath;

        // Check existence of AnyMemo and the DB pointed by dbPath.
        String dbProviderUri = "content://org.liberty.android.fantastischmemo.databasesprovider";
        String cardProviderUri = "content://" + AUTHROITY + "/"
                + Uri.encode(dbPath) + "/random/50";
        if (null == dbPath || dbPath.isEmpty()
                || !contentProviderExist(dbProviderUri)
                || !contentProviderExist(cardProviderUri)) {
            Log.e(TAG, "AnyMemo not available");
            throw new IllegalArgumentException("AnyMemo not available");
        }
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<Card>(50);
        ContentResolver cr = mContext.getContentResolver();
        String pathUri = Uri.encode(dbPath);
        Log.d(TAG, "PathUri = " + pathUri);
        try {
            Cursor cursor = cr.query(
                    Uri.parse("content://" + AUTHROITY + "/" + pathUri
                            + "/random/50"), null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String question = cursor.getString(cursor
                            .getColumnIndex("question"));
                    String answer = cursor.getString(cursor
                            .getColumnIndex("answer"));
                    Card c = new Card(question, answer);
                    cards.add(c);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error when loading cards.");
        }
        return cards;
    }

    @Override
    public String getSource() {
        return dbPath;
    }

    private boolean contentProviderExist(String uriString) {
        Uri providerUri = Uri.parse(uriString);
        try {
            Cursor cursor = mContext.getContentResolver().query(providerUri,
                    null, null, null, null);
            if (cursor != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
