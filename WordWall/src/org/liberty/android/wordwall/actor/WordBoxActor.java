package org.liberty.android.wordwall.actor;

import org.liberty.android.wordwall.WordWall;
import org.liberty.android.wordwall.model.Card;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

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
        //this.setY(game.viewportHeight * 0.3f);
        this.setWidth(game.viewportWidth);
        this.setHeight(game.viewportHeight);
        initTable();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void initTable() {
        table = new Table();
        table.setWidth(getWidth());
        table.setFillParent(true);
        table.center();
        table.debug();

        titleLabel = new TransparentBackgroundLabel(this.game, "", skin, "default_label");
        // Make it bigger
        titleLabel.setFontScale(1.0f);
        titleLabel.setWrap(true);
        titleLabel.setAlignment(Align.center);

        definitionLabel = new TransparentBackgroundLabel(this.game, "", skin, "default_label");
        definitionLabel.setFontScale(0.6f);
        definitionLabel.setWrap(true);
        definitionLabel.setAlignment(Align.center);

        table.add(titleLabel).center().expand().width(getWidth() * 0.9f).padTop(70f).height(getHeight() * 0.10f);
        table.row();
        table.add(definitionLabel).center().top().expand().width(getWidth() * 0.9f).maxHeight(getHeight() * 0.7f).spaceBottom(150f);
        this.addActor(table);
    }

    public void setCardToDisplay(Card card) {
        titleLabel.setText(card.getQuestion());
        definitionLabel.setText(card.getAnswer());
        titleLabel.pack();
        definitionLabel.pack();
    }
}
