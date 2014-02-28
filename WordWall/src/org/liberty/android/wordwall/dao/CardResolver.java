package org.liberty.android.wordwall.dao;

import java.util.List;

import org.liberty.android.wordwall.model.Card;

public interface CardResolver {
    List<Card> getCards();
}
