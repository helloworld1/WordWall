package org.liberty.android.wordwall.screen;

import org.liberty.android.wordwall.WordWall;
import org.liberty.android.wordwall.actor.BackgroundActor;
import org.liberty.android.wordwall.actor.WordBoxActor;
import org.liberty.android.wordwall.actor.WordMarqueeActor;
import org.liberty.android.wordwall.dao.OnSettingsChangedListener;
import org.liberty.android.wordwall.model.Card;
import org.liberty.android.wordwall.util.GameTimer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * The screen show scrolling words and a nice background.
 */
public class WordScreen implements Screen {
    private WordWall game;

    private Stage stage;

    private BackgroundActor backgroundActor;

    private WordBoxActor wordBoxActor;

    private Array<WordMarqueeActor> wordMarqueeActors;

    private boolean wordboxShown = false;

    private GameTimer timer;

    /**
     * This is registered to the game so when the settings are changed the whole screen is refreshed.
     */
    private OnSettingsChangedListener onSettingsChangedListener = new OnSettingsChangedListener() {
        @Override
        public void onSettingsChanged() {
            Gdx.app.postRunnable(new Runnable() {
                public void run() {
                    game.setScreen(new WordScreen(game));
                    dispose();
                }
            });
        }
    };

    public WordScreen(final WordWall game) {
        this.game = game;

        timer = new GameTimer();

        stage = new Stage();

        stage.setViewport(new StretchViewport(game.viewportWidth, game.viewportHeight));

        initBackground();

        wordMarqueeActors = new Array<WordMarqueeActor>();
        initWordMarqueeActors(150, 550, 3);

        wordBoxActor = new WordBoxActor(this.game);

        stage.addActor(backgroundActor);

        initActorTimers();

        Gdx.input.setInputProcessor(stage);

        game.registerOnSettingsChangedListener(onSettingsChangedListener);
    }

    @Override
    public void render(float delta) {

        // Make sure the delta is no larger than 1/30s
        // A lag will not mess around the timer and actors.
        float calibratedDelta = Math.min(Gdx.graphics.getDeltaTime(), 1f / 30);

        timer.update(calibratedDelta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(calibratedDelta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        timer.start();
    }

    @Override
    public void hide() {
        timer.stop();
    }

    @Override
    public void resume() {
        timer.start();
    }

    @Override
    public void pause() {
        timer.stop();
    }

    @Override
    public void dispose() {
        stage.dispose();
        timer.stop();
        game.unregisterOnSettingsChangedListener(onSettingsChangedListener);
    }

    /**
     * Initialize the marquee actors.
     *
     * @param bottomY the y axis of the bottom line of the marquee.
     * @param topY the y axis of the top line of the marquee.
     * @param count how many lines of marquee to display.
     */
    private void initWordMarqueeActors(float bottomY, float topY, int count) {
        float incremental = (topY - bottomY) / Math.max(1, (count - 1));

        for (int i = count - 1; i >= 0; i--) {
            float y = bottomY + i * incremental; 
            WordMarqueeActor marquee = new WordMarqueeActor(this.game, y);
            wordMarqueeActors.add(marquee);

            // Add touch listener so touching on the word will show the word box
            // Note the index is backwards so the index needs to be reversed.
            final int index = count - 1 - i;
            marquee.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("Clicked on index: " , "Index: " + index);
                    showWordBox(wordMarqueeActors.get(index).getCurrentCard());
                }
            });
        }
    }

    /**
     * Set up the background and light
     */
    private void initBackground() {
        backgroundActor = new BackgroundActor(game);
        backgroundActor.setLightMoving(true);
        backgroundActor.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                 backgroundActor.setLightX(x);
                 backgroundActor.setLightY(y);
            }
        });
    }

    /**
     * Set up the timers for the actors to initialize.
     */
    private void initActorTimers() {
        for (int i = 0; i < wordMarqueeActors.size; i++) {
            final int n = i;
            timer.schedule(new Runnable() {
                @Override
                public void run() {
                    stage.addActor(wordMarqueeActors.get(n));
                }
            }, i * MathUtils.random(1.5f, 2.5f));
        }

        // The word box will show in fixed intervals
        timer.schedule(new Runnable() {
            @Override
            public void run() {
                // Randomly choose the card to display from a marquee
                WordMarqueeActor chosenMarquee = wordMarqueeActors.get(MathUtils.random(0, wordMarqueeActors.size - 1));
                showWordBox(chosenMarquee.getCurrentCard());
            }
        }, 10f, 20f);          
    }

    /**
     * Show the wordbox if it is not shown.
     */
    private void showWordBox(Card card) {
        if (wordboxShown) {
            return;
        }
        wordboxShown = true;

        wordBoxActor.setCardToDisplay(card);

        // Here is what happened:
        // 1. The marquee text will be more transparent
        // 2. The word box fade in
        // 3. the word box disappears after a few seconds
        // 4. The word box fade out
        // 5. The marquees will return to its initial alpha
        wordBoxActor.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
        stage.addActor(wordBoxActor);

        for (WordMarqueeActor marquee : wordMarqueeActors) {
            marquee.addAction(Actions.alpha(0.2f, 1f));
        }

        stage.addAction(Actions.delay(6f, Actions.run(new Runnable() {
            public void run() {
                for (WordMarqueeActor marquee : wordMarqueeActors) {
                    marquee.addAction(Actions.alpha(WordMarqueeActor.INITIAL_ALPHA, 1f));
                }
                wordBoxActor.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    public void run() {
                        stage.getRoot().removeActor(wordBoxActor);
                        wordboxShown = false;
                    }
                })));
            }
        })));
    }
}
