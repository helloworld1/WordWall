package org.liberty.android.wordwall;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class WordScreen implements Screen {

    private WordWall game;

    private Stage stage;

    private Actor backgroundActor;

    private WordMarqueeActor wordMarqueeActor1;

    private WordMarqueeActor wordMarqueeActor2;

    private WordMarqueeActor wordMarqueeActor3;

    private WordBoxActor wordBoxActor;

    public WordScreen(WordWall game) {
        this.game = game;

        stage = new Stage();
        stage.setViewport(game.viewportWidth, game.viewportHeight);

        backgroundActor = new BackgroundActor(game);

        wordMarqueeActor1 = new WordMarqueeActor(this.game, 650);
        wordMarqueeActor2 = new WordMarqueeActor(this.game, 400);
        wordMarqueeActor3 = new WordMarqueeActor(this.game, 150);
        wordBoxActor = new WordBoxActor(this.game);

        stage.addActor(backgroundActor);
        stage.addActor(wordMarqueeActor1);
        Timer.schedule(new Task() {
            @Override
            public void run() {
                stage.addActor(wordMarqueeActor2);
            }
        }, 2.5f);

        Timer.schedule(new Task() {
            @Override
            public void run() {
                stage.addActor(wordMarqueeActor3);
            }
        }, 4.0f);

        Timer.schedule(new Task() {
            @Override
            public void run() {
                wordBoxActor.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
                stage.addActor(wordBoxActor);

                wordMarqueeActor1.addAction(Actions.alpha(0.3f, 1f));
                wordMarqueeActor2.addAction(Actions.alpha(0.3f, 1f));
                wordMarqueeActor3.addAction(Actions.alpha(0.3f, 1f));

                Timer.schedule(new Task() {
                    @Override
                    public void run() {
                        wordMarqueeActor1.addAction(Actions.alpha(WordMarqueeActor.INITIAL_ALPHA, 1f));
                        wordMarqueeActor2.addAction(Actions.alpha(WordMarqueeActor.INITIAL_ALPHA, 1f));
                        wordMarqueeActor3.addAction(Actions.alpha(WordMarqueeActor.INITIAL_ALPHA, 1f));
                        wordBoxActor.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                            public void run() {
                                stage.getRoot().removeActor(wordBoxActor);
                            }
                        })));
                    }
                }, 3.0f);
            }
        }, 10, 20);

        //wordMarqueeActor.setColor(new Color(1, 1, 1, 0.5f));

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
