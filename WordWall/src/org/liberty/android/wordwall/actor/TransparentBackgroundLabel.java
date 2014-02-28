package org.liberty.android.wordwall.actor;

import org.liberty.android.wordwall.WordWall;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** A label that always have transparent background.
 *
 * This is very useful for the ETC1 texture comrpession
 */
public class TransparentBackgroundLabel extends Label {

    private ShaderProgram shader;

    public TransparentBackgroundLabel(WordWall game, CharSequence text, Skin skin,
            String name) {
        super(text, skin, name);
        shader = game.assetManager.get("shaders/font_alpha", ShaderProgram.class);
    }


    @Override
    public void draw(SpriteBatch batch, float delta) {
        // Use the shader to make the black background transparent
        batch.setShader(shader);
        super.draw(batch, delta);
        batch.setShader(null);
    }




}
