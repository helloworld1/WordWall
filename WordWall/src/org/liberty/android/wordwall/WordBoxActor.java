package org.liberty.android.wordwall;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A text box with a title for question and a definition for the question.
 */
public class WordBoxActor extends Group {
    private WordWall game;

    private Label titleLabel;

    private Label definitionLabel;

    private Table table;

    public WordBoxActor(WordWall game) {
        this.game = game;
        this.setX(game.viewportWidth * 0.1f);
        this.setY(game.viewportHeight * 0.3f);
        this.setWidth(game.viewportWidth * 0.8f);
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

        titleLabel = new TransparentBackgroundLabel(this.game, "", game.skin, "big_white_label");
        titleLabel.setWrap(true);
        definitionLabel = new TransparentBackgroundLabel(this.game, "", game.skin, "med_white_label");
        definitionLabel.setWrap(true);
        table.row().width(getWidth()).center().maxHeight(100).padBottom(25);
        table.add(titleLabel);
        table.row().width(getWidth()).center().maxHeight(675);
        table.add(definitionLabel);
        this.addActor(table);
    }

    public void setCardToDisplay(Card card) {
        titleLabel.setText(card.getQuestion());
        definitionLabel.setText(card.getAnswer());
    }
}


