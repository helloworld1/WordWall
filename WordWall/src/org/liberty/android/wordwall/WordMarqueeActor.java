package org.liberty.android.wordwall;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WordMarqueeActor extends Group {

    public static final float INITIAL_ALPHA = 0.9f;

    private WordWall game;

    private Label textLabel;

    private float y;

    public WordMarqueeActor(WordWall game, float y) {
        this.y = y;
        this.game = game;
        initNewLabel();
    }

    public void act(float delta) {
        super.act(delta);
        if (isLabelOutOfBound()) {
            initNewLabel();
        } else {
            textLabel.act(delta);
            textLabel.setX(textLabel.getX() - delta * 100);
        }
    }

    private boolean isLabelOutOfBound() {
        return (textLabel.getX() + textLabel.getWidth() < 0);
    }

    private void initNewLabel() {
        textLabel = new Label("Hello 12312634812647812647812", game.skin, "big_white_label");
        textLabel.setX(game.viewportWidth);
        textLabel.setY(y);
        addActor(textLabel);
        addAction(Actions.alpha(INITIAL_ALPHA));
    }

}

