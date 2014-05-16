package org.liberty.android.wordwall.ui;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * The card for each item in the menu menu activity.
 */
public class MenuCard extends Card {
    public MenuCard(final Context context, final int headerTextResource, final int innerTextResource, final int imageResource, final Intent actionIntent) {
        //super(context, R.layout.menu_card_layout);
        super(context);

        CardHeader header = new CardHeader(context);
        header.setTitle(context.getString(headerTextResource));
        this.addCardHeader(header);

        CardThumbnail thumbnail = new CardThumbnail(context);
        thumbnail.setDrawableResource(imageResource);
        this.addCardThumbnail(thumbnail);
        this.setTitle(context.getString(innerTextResource));
        this.setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card arg0, View arg1) {
                context.startActivity(actionIntent);
            }
        });
    }
}

