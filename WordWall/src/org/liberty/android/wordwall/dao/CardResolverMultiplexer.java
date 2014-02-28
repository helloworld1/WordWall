package org.liberty.android.wordwall.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.liberty.android.wordwall.model.Card;

/**
 * Use multiple card resolvers to retrieve cards.
 * If one failed to retrieve card next resolver will be used.
 */
public class CardResolverMultiplexer implements CardResolver {

    public List<CardResolver> cardResolverList = new ArrayList<CardResolver>();

    public CardResolverMultiplexer(CardResolver cardResolver, CardResolver... cardResolvers) {
        cardResolverList.add(cardResolver);
        Collections.addAll(cardResolverList, cardResolvers);
    }

    @Override
    public List<Card> getCards() {
        for (CardResolver cardResolver : cardResolverList) {
            List<Card> cards = cardResolver.getCards();
            if (cards != null && !cards.isEmpty()) {
                return cards;
            }
        }
        return null;
    }

}
