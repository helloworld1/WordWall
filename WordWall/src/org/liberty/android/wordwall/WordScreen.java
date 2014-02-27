package org.liberty.android.wordwall;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class WordScreen implements Screen {

    private WordWall game;

    private Stage stage;

    private Actor backgroundActor;

    private WordBoxActor wordBoxActor;

    private Array<WordMarqueeActor> wordMarqueeActors;

    private final OrthographicCamera camera;

    public WordScreen(WordWall game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.viewportWidth, game.viewportHeight);

        stage = new Stage();
        stage.setViewport(game.viewportWidth, game.viewportHeight);

        backgroundActor = new BackgroundActor(game);

        wordMarqueeActors = new Array<WordMarqueeActor>();
        initWordMarqueeActors(150, 550, 3);

        wordBoxActor = new WordBoxActor(this.game);

        stage.addActor(backgroundActor);

        initActorTimers();

        //wordMarqueeActor.setColor(new Color(1, 1, 1, 0.5f));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1f / 30));
        stage.draw();

    }

    private void initWordMarqueeActors(float bottomY, float topY, int count) {
        float incremental = (topY - bottomY) / Math.max(1, (count - 1));

        for (int i = count - 1; i >= 0; i--) {
            float y = bottomY + i * incremental; 
            WordMarqueeActor marquee = new WordMarqueeActor(this.game, y);
            wordMarqueeActors.add(marquee);
        }
    }

    private void initActorTimers() {

        for (int i = 0; i < wordMarqueeActors.size; i++) {
            final int n = i;
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    stage.addActor(wordMarqueeActors.get(n));
                }
            }, i * MathUtils.random(1.5f, 2.5f));
        }

        // The word box will show in fixed intervals
        Timer.schedule(new Task() {
            @Override
            public void run() {
                // Randomly choose the card to display from a marquee
                WordMarqueeActor chosenMarquee = wordMarqueeActors.get(MathUtils.random(0, wordMarqueeActors.size - 1));
                wordBoxActor.setCardToDisplay(chosenMarquee.getCurrentCard());
                
                // Here is what happened:
                // 1. The marquee text will be more transparent
                // 2. The word box fade in
                // 3. the word box disappears after a few seconds
                // 4. The word box fade out
                // 5. The marquees will return to its initial alpha
                wordBoxActor.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
                stage.addActor(wordBoxActor);

                for (WordMarqueeActor marquee : wordMarqueeActors) {
                    marquee.addAction(Actions.alpha(0.3f, 1f));
                }

                stage.addAction(Actions.delay(6f, Actions.run(new Runnable() {
                    public void run() {
                        for (WordMarqueeActor marquee : wordMarqueeActors) {
                            marquee.addAction(Actions.alpha(WordMarqueeActor.INITIAL_ALPHA, 1f));
                        }
                        wordBoxActor.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                            public void run() {
                                stage.getRoot().removeActor(wordBoxActor);
                            }
                        })));
                    }
                })));
            }
        }, 10, 20);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        Timer.instance().stop();
    }

    @Override
    public void resume() {
        Timer.instance().start();
    }

    @Override
    public void dispose() {
    }

}
