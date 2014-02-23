package org.liberty.android.wordwall;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class WordWall extends Game {

    public SpriteBatch batch;

    public Skin skin;

    private SkinGenerator skinGenerator;

    private Texture fontImageTexture;

    public float viewportWidth = 480;

    public float viewportHeight= 800;

    public WordWall() {
    }

    @Override
    public void create() {      
        batch = new SpriteBatch();
        batch.enableBlending();
        fontImageTexture = new Texture(Gdx.files.internal("fonts/dsf.png"));
        skinGenerator = new SkinGenerator(fontImageTexture);
        skin = skinGenerator.getSkin();


        this.setScreen(new WordScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        fontImageTexture.dispose();
    }

    @Override
    public void render() {      
        super.render();
    }
}
