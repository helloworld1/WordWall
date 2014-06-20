package org.liberty.android.wordwall;

import java.util.ArrayList;
import java.util.List;

import org.liberty.android.wordwall.dao.CardResolver;
import org.liberty.android.wordwall.dao.OnCardResolverChangedListener;
import org.liberty.android.wordwall.dao.OnSettingsChangedListener;
import org.liberty.android.wordwall.dao.SettingsResolver;
import org.liberty.android.wordwall.screen.LoadingScreen;
import org.liberty.android.wordwall.util.ShaderAssetLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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

    public final SettingsResolver settingsResolver;

    /** Will be loaded after the assets are loaded in loading screen */
    public Skin skin = null;

    private List<OnCardResolverChangedListener> onCardResolverChangedListeners = new ArrayList<OnCardResolverChangedListener>(3);

    private List<OnSettingsChangedListener> onSettingsChangedListeners = new ArrayList<OnSettingsChangedListener>(3);

    public WordWall(CardResolver cardResolver, SettingsResolver settingsResolver) {
        this.cardResolver = cardResolver;
        this.settingsResolver = settingsResolver;
    }

    @Override
    public void create() {      
        batch = new SpriteBatch();
        batch.enableBlending();

        // Do not ignore shader error
        ShaderProgram.pedantic = true;

        assetManager = new AssetManager();
        assetManager.setLoader(ShaderProgram.class, new ShaderAssetLoader(new InternalFileHandleResolver()));

        // The serif font with its texture.
        // It will also load fonts/serif.etc1 texture as dependency
        // Note the font's texutre is specially crafted. The green component
        // is actually alpha. Additional shader is used to convert it back
        assetManager.load("fonts/serif.fnt", BitmapFont.class);

        // The compress texture with left half color info and right half normal value
        // Used for normal mapping
        assetManager.load("images/wall_with_normal.etc1", Texture.class);
        assetManager.load("images/red_with_normal.etc1", Texture.class);
        assetManager.load("images/wood_with_normal.etc1", Texture.class);

        assetManager.load("shaders/font_process", ShaderProgram.class);
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
     * Notify the listeners that the settings has changed.
     */
    public void notifySettingsChanged() {
        Gdx.app.log("WordWall", "Received settings change notification");
        for (OnSettingsChangedListener listener : onSettingsChangedListeners) {
            listener.onSettingsChanged();
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

    /**
     * Unregister a card resolve changed listener.
     *
     * @param listener the listener to unregister.
     */
    public void unregisterOnCardResolverChangedListener(OnCardResolverChangedListener listener) {
        onCardResolverChangedListeners.remove(listener);
    }

    /**
     * Register listener for settings change.
     */
    public void registerOnSettingsChangedListener(OnSettingsChangedListener listener) {
        onSettingsChangedListeners.add(listener);
    }

    /**
     * Unregister a settings changed listener.
     */
    public void unregisterOnSettingsChangedListener(OnSettingsChangedListener listener) {
        onSettingsChangedListeners.remove(listener);
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
