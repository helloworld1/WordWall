package org.liberty.android.wordwall;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WordMarqueeActor extends Actor {

    private WordWall game;

    private Label textLabel;

    private float y;

    public WordMarqueeActor(WordWall game, float y) {
        this.y = y;
        this.game = game;
        initNewLabel();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        textLabel.draw(batch, parentAlpha);
    }

    public void act(float delta) {
        if (isLabelOutOfBound()) {
            initNewLabel();
        } else {
            textLabel.act(delta);
            textLabel.setX(textLabel.getX() - delta * 50);
        }
    }

    private boolean isLabelOutOfBound() {
        return (textLabel.getX() + textLabel.getWidth() < 0);
    }

    private void initNewLabel() {
        textLabel = new Label("Hello 12312634812647812647812", game.skin, "big_white_label");
        textLabel.setX(game.viewportWidth);
        textLabel.setY(y);
    }

}

