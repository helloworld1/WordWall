package org.liberty.android.wordwall.dao;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.model.Card;

/**
 * This is the default content provider when user has not select a content
 * provider.
 * 
 * @author zxy1256@gmail.com
 * 
 */
public class TutorialCardResolver implements CardResolver {
    private final List<Card> mCards = new ArrayList<Card>(10);

    public TutorialCardResolver() {
        mCards.add(new Card("What is WordWall", "It's a wallpaper/daydream that shows cards from AnyMemo."));
        mCards.add(new Card("How to use wallpaper", "Set the live wallpaper in Android's home screen."));
        mCards.add(new Card("How to use daydream", "Go to Android's Settings and set daydream in 'display'"));
        mCards.add(new Card("Download AnyMemo", "Go to Google Play and Search 'AnyMemo'"));
        mCards.add(new Card("Select card database", "Use settings in livewallpaper or daydream"));
        mCards.add(new Card("Get more cards ", "Use AnyMemo to download/create databases"));
        mCards.add(new Card("Create new cards", "Use AnyMemo to create new cards"));
    }

    @Override
    public List<Card> getCards() {
        return new ArrayList<Card>(mCards);
    }
}
