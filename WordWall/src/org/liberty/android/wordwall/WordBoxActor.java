package org.liberty.android.wordwall;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class WordBoxActor extends Group {
    private WordWall game;

    private Label titleLabel;

    private Label definitionLabel;

    private Table table;

    public WordBoxActor(WordWall game) {
        this.game = game;
        this.setX(game.viewportWidth * 0.118f);
        this.setY(game.viewportHeight * 0.118f);
        this.setWidth(game.viewportWidth * 0.618f);
        this.setHeight(game.viewportHeight * 0.618f);
        initTable();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void initTable() {
        table = new Table();
        table.setFillParent(true);

        titleLabel = new Label("Hello", game.skin, "big_white_label");
        definitionLabel = new Label("22222222222 jdsfof osdfjasd iosdjf", game.skin, "med_white_label");
        definitionLabel.setWrap(true);
        table.row().padBottom(25);
        table.add(titleLabel).width(getWidth()).center();
        table.row().maxHeight(getHeight() - 25 - titleLabel.getHeight());
        table.add(definitionLabel).width(getWidth()).center();
        this.addActor(table);
    }


}


