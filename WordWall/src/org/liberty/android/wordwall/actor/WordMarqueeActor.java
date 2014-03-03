package org.liberty.android.wordwall.actor;

import java.util.List;

import org.liberty.android.wordwall.WordWall;
import org.liberty.android.wordwall.dao.CardResolver;
import org.liberty.android.wordwall.dao.OnCardResolverChangedListener;
import org.liberty.android.wordwall.model.Card;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class WordMarqueeActor extends Group {

    public static final float INITIAL_ALPHA = 0.9f;

    private OrthographicCamera camera = new OrthographicCamera();

    private WordWall game;

    private Label textLabel;

    private Card currentCard;

    private float y;

    private Array<Card> cardQueue = new Array<Card>();

    private Skin skin;

    // When the resolver changed clear the queue so next time,
    // it will use the new resolver to chagne card
    private OnCardResolverChangedListener onCardResolverChangedListener = new OnCardResolverChangedListener() {
        @Override
        public void onResolverChanged(CardResolver resolver) {
            cardQueue.clear();
        }
    };


    public WordMarqueeActor(WordWall game, float y) {
        this.y = y;

        this.game = game;

        skin = game.skin;

        textLabel = new TransparentBackgroundLabel(this.game, "Nothing here", skin, "default_label");
        textLabel.setFontScale(1.0f);
        addActor(textLabel);
        initNewLabel();
        this.game.registerOnCardResolverChangedListener(onCardResolverChangedListener);
        this.camera.setToOrtho(false, game.viewportWidth, game.viewportHeight);

        addAction(Actions.alpha(INITIAL_ALPHA));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isLabelOutOfBound()) {
            initNewLabel();
        } else {
            textLabel.act(delta);
            textLabel.setX(textLabel.getX() - delta * 100);
        }
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Card getNextCard() {
        if (cardQueue.size == 0) {
            List<Card> cards = game.cardResolver.getCards();
            for (Card card : cards) {
                cardQueue.add(card);
            }
            cardQueue.shuffle();
        }

        currentCard = cardQueue.pop();
        return currentCard;
    }

    private boolean isLabelOutOfBound() {
        return (textLabel.getX() + textLabel.getWidth() < 0);
    }

    private void initNewLabel() {
        Card card = getNextCard();
        if (card == null) {
            return;
        }

        textLabel.setText(card.getQuestion());
        textLabel.setX(game.viewportWidth);
        textLabel.setY(y);
        textLabel.pack();
    }
}

