package org.liberty.android.wordwall;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.Card;

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
        mCards.add(new Card("What is WordWall?",
                "It's a live wallpaper that shows words from AnyMemo."));
        mCards.add(new Card("Do I need AnyMemo to use WordWall", "Yes"));
    }

    @Override
    public List<Card> getCards() {
        return new ArrayList<Card>(mCards);
    }
}
