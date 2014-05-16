package org.liberty.android.wordwall.ui;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

import org.liberty.android.wordwall.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The card for each item in the menu menu activity.
 */
public class MenuCard extends Card {

    private Context context;

    private int imageResource;

    private int innerTextResource;

    public MenuCard(final Context context, final int headerTextResource, final int innerTextResource, final int imageResource, final Intent actionIntent) {
        super(context, R.layout.menu_card_layout);
        this.context = context;
        this.innerTextResource = innerTextResource;
        this.imageResource = imageResource;

        CardHeader header = new CardHeader(context);
        header.setTitle(context.getString(headerTextResource));
        this.addCardHeader(header);
        this.setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card arg0, View arg1) {
                context.startActivity(actionIntent);
            }
        });
    }


    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        ImageView imageView = (ImageView) parent.findViewById(R.id.card_content_icon);
        imageView.setBackground(context.getResources().getDrawable(imageResource));
        TextView textView = (TextView) parent.findViewById(R.id.card_main_inner_simple_title);
        textView.setText(innerTextResource);
    }


}

