package org.liberty.android.wordwall.dao;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.model.Card;

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
public class AnyMemoCardResolver implements CardResolver {
    private static String FREE_AUTHROITY = "org.liberty.android.fantastischmemo.cardprovider";
    private static String PRO_AUTHROITY = "org.liberty.android.fantastischmemopro.cardprovider";
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
    public AnyMemoCardResolver(Context context, String dbPath) {
        mContext = context;
        this.dbPath = dbPath;
    }

    @Override
    public List<Card> getCards() {
        List<Card> cards = new ArrayList<Card>(50);
        ContentResolver cr = mContext.getContentResolver();
        String pathUri = Uri.encode(dbPath);
        Log.d(TAG, "PathUri = " + pathUri);
        try {
            Uri proUri = Uri.parse("content://" + PRO_AUTHROITY + "/" + pathUri + "/random/50");
            Uri freeUri = Uri.parse("content://" + FREE_AUTHROITY + "/" + pathUri + "/random/50");

            // First trying pro uri
            Cursor cursor = cr.query(proUri, null, null, null, null);
            if (cursor == null) {
                Log.i(TAG, "Content provider not found: " + proUri);
                Log.i(TAG, "trying: " + freeUri);
                cursor = cr.query(freeUri, null, null, null, null);
            }
            if (cursor == null) {
                Log.i(TAG, "Content provider not found: " + freeUri);

            } else {
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
}
