package org.liberty.android.wordwall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {

    private Sprite backgroundSprite;

    public BackgroundActor(WordWall game) {
        // Bigger stars and scrolls faster
        backgroundSprite = new Sprite(game.assetManager.get("images/background.etc1", Texture.class), 512, 910);

        backgroundSprite.setSize(game.viewportWidth, game.viewportHeight);
    }

    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        backgroundSprite.draw(batch);
    }

    public void act(float delta) {
    }
}


