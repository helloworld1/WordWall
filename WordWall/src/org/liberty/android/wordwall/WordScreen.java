package org.liberty.android.wordwall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WordScreen implements Screen {

    private WordWall game;

    private Stage stage;

    private Texture backgroundTexture;

    private Actor backgroundActor;

    public WordScreen(WordWall game) {
        this.game = game;

        stage = new Stage();
        stage.setViewport(game.viewportWidth, game.viewportHeight);

        backgroundTexture= new Texture(Gdx.files.internal("images/art001.jpg"));
        backgroundActor = new BackgroundActor(backgroundTexture, game.viewportWidth, game.viewportHeight);

        Label label = new Label("Hello 你好", game.skin, "big_white_label");
        label.setX(100);
        label.setY(100);
        stage.addActor(backgroundActor);
        stage.addActor(label);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
