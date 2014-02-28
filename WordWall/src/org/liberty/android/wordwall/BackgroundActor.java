package org.liberty.android.wordwall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {

    private WordWall game;

    /**
     * The shader will handle the normal mapping.
     */
    private ShaderProgram shader;

    /**
     * The texture's left half is the color and the right half is the normal.
     */
    private Texture wallTexture;

    private float lightX, lightY;

    private boolean lightMovingRight = true;

    private boolean lightMoving = true;

    public BackgroundActor(WordWall game) {
        this.game = game;
        wallTexture = game.assetManager.get("images/wall_with_normal.etc1", Texture.class);
        shader = game.assetManager.get("shaders/normalmap2d");

        this.lightX = game.viewportWidth / 2;
        this.lightY = game.viewportHeight / 2;
        setWidth(game.viewportWidth);
        setHeight(game.viewportHeight);
        setBounds(0, 0, game.viewportWidth, game.viewportHeight);
    }

    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        shader.begin();
        shader.setUniformf("lightPos", lightX, lightY, 110f);
        shader.setUniformf("ambientColor", 1.0f, 1.0f, 1.0f);
        shader.setUniformf("lightColor", 1.0f, 1.0f, 1.0f);
        shader.setUniformf("ambientIntensity", 0.3f, 0.3f, 0.3f);
        shader.setUniformi("useNormals", 1);
        shader.end();

        batch.setShader(shader);
        batch.draw(wallTexture, 0, 0, 480f, 800f);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }

    public void act(float delta) {
        super.act(delta);
        if (lightMoving) {
            if (lightMovingRight) {
                lightX += delta * 60;
            } else {
                lightX -= delta * 60;
            }
            if (lightX > game.viewportWidth) {
                lightMovingRight = false;
            }
            if (lightX < 0) {
                lightMovingRight = true;
            }
        }
    }

    /**
     * @param lightX the lightX to set
     */
    public void setLightX(float lightX) {
        this.lightX = lightX;
    }

    /**
     * @param lightY the lightY to set
     */
    public void setLightY(float lightY) {
        this.lightY = lightY;
    }

    /**
     * @param lightMoving the lightMoving to set
     */
    public void setLightMoving(boolean lightMoving) {
        this.lightMoving = lightMoving;
    }
}


