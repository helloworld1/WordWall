package org.liberty.android.wordwall;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * The main entry point to WordWall.
 */
public class WordWall extends Game {

    public SpriteBatch batch;

    public float viewportWidth = 480;

    public float viewportHeight= 800;

    public AssetManager assetManager;

    public CardResolver cardResolver;

    // WIll be loaded after the assets are loaded in loading screen
    public Skin skin = null;

    private List<OnCardResolverChangedListener> onCardResolverChangedListeners = new ArrayList<OnCardResolverChangedListener>(3);

    public WordWall(CardResolver cardResolver) {
        this.cardResolver = cardResolver;
    }

    @Override
    public void create() {      
        batch = new SpriteBatch();
        batch.enableBlending();

        ShaderProgram.pedantic = true;
        assetManager = new AssetManager();
        assetManager.setLoader(ShaderProgram.class, new ShaderAssetLoader(new InternalFileHandleResolver()));

        // The droid sans fallback font.
        // It will also load fonts/dsf.etc1 texture as dependency
        assetManager.load("fonts/dsf.fnt", BitmapFont.class);

        assetManager.load("images/wall_with_normal.etc1", Texture.class);

        assetManager.load("shaders/font_alpha", ShaderProgram.class);
        assetManager.load("shaders/normalmap2d", ShaderProgram.class);

        this.setScreen(new LoadingScreen(this));
    }

    public void setCardResolver(CardResolver resolver) {
        this.cardResolver = resolver;
        for (OnCardResolverChangedListener listener : onCardResolverChangedListeners) {
            listener.onResolverchanged(resolver);
        }
    }

    public void registerOnCardResolverChangedListener(OnCardResolverChangedListener listener) {
        onCardResolverChangedListeners.add(listener);
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
    }

    @Override
    public void render() {      
        super.render();
    }
}
