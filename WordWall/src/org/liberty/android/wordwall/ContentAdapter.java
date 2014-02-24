package org.liberty.android.wordwall;

import java.util.List;

import org.liberty.android.wordwall.Card;

public interface ContentAdapter {
    List<Card> getCards();

    String getSource();
}
