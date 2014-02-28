package org.liberty.android.wordwall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BackgroundActor extends Actor {

    private WordWall game;

    private ShaderProgram shader;

    private Texture wallTexture;

    private float lightX, lightY;

    private boolean lightMovingRight = true;

    public BackgroundActor(WordWall game) {
        this.game = game;
        wallTexture = game.assetManager.get("images/wall_with_normal.etc1", Texture.class);
        shader = game.assetManager.get("shaders/normalmap2d");

        this.lightX = game.viewportWidth / 2;
        this.lightY = game.viewportHeight / 2;

        //backgroundSprite.setSize(game.viewportWidth, game.viewportHeight);
        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down x " + x + " y " + y);
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("up");
            }
        });
    }

    @Override
    public void draw (SpriteBatch batch, float parentAlpha) {
        shader.begin();

        shader.setUniformf("lightPos", lightX, lightY, 90f);
        shader.setUniformf("ambientColor", 1.0f, 1.0f, 1.0f);
        shader.setUniformf("lightColor", 0.9f, 0.9f, 0.9f);
        shader.setUniformf("ambientIntensity", 0.3f, 0.3f, 0.3f);
        shader.setUniformi("useNormals", 1);
        //shader.setUniformf("strength", 0.2f);
        shader.end();
        batch.setShader(shader);
        batch.draw(wallTexture, 0, 0, 480f, 800f);
        super.draw(batch, parentAlpha);
        batch.setShader(null);
    }

    public void act(float delta) {
        super.act(delta);
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


