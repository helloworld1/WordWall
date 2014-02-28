package org.liberty.android.wordwall;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.dao.CardResolver;
import org.liberty.android.wordwall.dao.OnCardResolverChangedListener;
import org.liberty.android.wordwall.screen.LoadingScreen;
import org.liberty.android.wordwall.util.ShaderAssetLoader;

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

    /**
     * Used to draw.
     */
    public SpriteBatch batch;

    /**
     * Every screen will used this viewport coordinate.
     */
    public float viewportWidth = 480;

    /**
     * Every screen will used this viewport coordinate.
     */
    public float viewportHeight= 800;

    /** Used to retrieve assets like textures, fonts, etc. */
    public AssetManager assetManager;

    /** Used to retrieve cards. */
    public CardResolver cardResolver;

    /** Will be loaded after the assets are loaded in loading screen */
    public Skin skin = null;

    private List<OnCardResolverChangedListener> onCardResolverChangedListeners = new ArrayList<OnCardResolverChangedListener>(3);

    public WordWall(CardResolver cardResolver) {
        this.cardResolver = cardResolver;
    }

    @Override
    public void create() {      
        batch = new SpriteBatch();
        batch.enableBlending();

        // Do not ignore shader error
        ShaderProgram.pedantic = true;

        assetManager = new AssetManager();
        assetManager.setLoader(ShaderProgram.class, new ShaderAssetLoader(new InternalFileHandleResolver()));

        // The droid sans fallback font.
        // It will also load fonts/dsf.etc1 texture as dependency
        assetManager.load("fonts/dsf.fnt", BitmapFont.class);

        // The compress texture with left half color info and right half normal value
        // Used for normal mapping
        assetManager.load("images/wall_with_normal.etc1", Texture.class);

        assetManager.load("shaders/font_alpha", ShaderProgram.class);
        assetManager.load("shaders/normalmap2d", ShaderProgram.class);

        this.setScreen(new LoadingScreen(this));
    }

    /**
     * Every time the card resolver changed, all the listener will be notified
     * for card resolver change.
     *
     * @param resolver the new resolver.
     */
    public void setCardResolver(CardResolver resolver) {
        this.cardResolver = resolver;
        for (OnCardResolverChangedListener listener : onCardResolverChangedListeners) {
            listener.onResolverChanged(resolver);
        }
    }

    /**
     * Register a resolver changed listener so when the card resolver changed, the listener
     * will be notified.
     *
     * @param listener the listener to register.
     */
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
