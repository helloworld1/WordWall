package org.liberty.android.wordwall;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LoadingScreen implements Screen {

    private final OrthographicCamera camera;

    // Use default font for loading. Disposed after this screen.
    private BitmapFont font;

    private WordWall game;
    public LoadingScreen(WordWall game) {
        this.game = game;

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.viewportWidth, game.viewportHeight);

        font = new BitmapFont();
        font.setScale(5.0f);
    } 

    public void postInit() {
        game.skin = SkinGenerator.getSkin(game);
        game.setScreen(new WordScreen(game));
    }

    @Override
    public void render(float delta) { Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        if (game.assetManager.update()) {
            postInit();
        }

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        font.draw(game.batch, "Loading...", 100, 420);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
