package org.liberty.android.wordwall.actor;

import org.liberty.android.wordwall.WordWall;
import org.liberty.android.wordwall.model.Card;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A text box with a title for question and a definition for the question.
 */
public class WordBoxActor extends Group {
    private WordWall game;

    private Label titleLabel;

    private Label definitionLabel;

    private Table table;

    private Skin skin;

    public WordBoxActor(WordWall game) {
        this.game = game;

        skin = game.skin;
        // The X is set to 0 because the label will be center aligned
        // SO the width will control the X.
        this.setX(0f);
        this.setY(game.viewportHeight * 0.3f);
        this.setWidth(game.viewportWidth * 0.7f);
        this.setHeight(game.viewportHeight * 0.7f);
        initTable();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void initTable() {
        table = new Table();
        table.setFillParent(true);
        table.center();

        titleLabel = new TransparentBackgroundLabel(this.game, "", skin, "default_label");
        // Make it bigger
        titleLabel.setFontScale(2.0f);
        titleLabel.setWrap(true);
        definitionLabel = new TransparentBackgroundLabel(this.game, "", skin, "default_label");
        definitionLabel.setFontScale(1.5f);
        definitionLabel.setWrap(true);
        table.row().width(0.7f * getWidth()).center().maxHeight(100).padBottom(25);
        table.add(titleLabel);
        table.row().width(0.7f * getWidth()).center().maxHeight(675);
        table.add(definitionLabel);
        this.addActor(table);
    }

    public void setCardToDisplay(Card card) {
        titleLabel.setText(card.getQuestion());
        definitionLabel.setText(card.getAnswer());
        titleLabel.pack();
        definitionLabel.pack();
    }
}
