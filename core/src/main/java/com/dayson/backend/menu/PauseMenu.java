
package com.Vanguard.frontend.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PauseMenu {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private BitmapFont font;

    private final String[] buttons = {
        "RESUME",
        "SAVE GAME",
        "MAIN MENU",
        "EXIT GAME"
    };

    private float centerX;
    private float centerY;

    private final float buttonWidth = 300;
    private final float buttonHeight = 55;
    private final float gap = 18;

    public interface PauseActions {
        void resume();
        void save();
        void mainMenu();
        void exit();
    }

    private PauseActions actions;

    public PauseMenu(PauseActions actions) {
        this.actions = actions;

        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
    }

    public void render() {

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        centerX = width / 2f;
        centerY = height / 2f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(
            GL20.GL_SRC_ALPHA,
            GL20.GL_ONE_MINUS_SRC_ALPHA
        );

        shape.begin(ShapeRenderer.ShapeType.Filled);

        // Background overlay
        shape.setColor(0, 0, 0, 0.75f);
        shape.rect(0, 0, width, height);

        // Pause panel
        shape.setColor(0.02f, 0.06f, 0.15f, 1);
        shape.rect(
            centerX - 190,
            centerY - 180,
            380,
            360
        );

        // Buttons
        for (int i = 0; i < buttons.length; i++) {

            float x = centerX - buttonWidth / 2;
            float y = centerY + 70 - i * (buttonHeight + gap);

            float mouseX = Gdx.input.getX();
            float mouseY = height - Gdx.input.getY();

            boolean hover =
                mouseX >= x &&
                    mouseX <= x + buttonWidth &&
                    mouseY >= y &&
                    mouseY <= y + buttonHeight;

            if (hover) {
                shape.setColor(0.05f, 0.4f, 0.8f, 1);
            } else {
                shape.setColor(0.04f, 0.15f, 0.3f, 1);
            }

            shape.rect(x, y, buttonWidth, buttonHeight);
        }

        shape.end();

        batch.begin();

        // Title
        font.setColor(Color.CYAN);
        font.getData().setScale(3);

        font.draw(
            batch,
            "PAUSED",
            centerX - 75,
            centerY + 160
        );

        // Button text
        font.setColor(Color.WHITE);
        font.getData().setScale(1.7f);

        for (int i = 0; i < buttons.length; i++) {

            float y = centerY + 70 - i * (buttonHeight + gap);

            font.draw(
                batch,
                buttons[i],
                centerX - 85,
                y + 36
            );
        }

        batch.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        handleInput();
    }

    private void handleInput() {

        if (!Gdx.input.justTouched()) return;

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        for (int i = 0; i < buttons.length; i++) {

            float x = centerX - buttonWidth / 2;
            float y = centerY + 70 - i * (buttonHeight + gap);

            if (mouseX >= x &&
                mouseX <= x + buttonWidth &&
                mouseY >= y &&
                mouseY <= y + buttonHeight) {

                switch (i) {

                    case 0:
                        actions.resume();
                        break;

                    case 1:
                        actions.save();
                        break;

                    case 2:
                        actions.mainMenu();
                        break;

                    case 3:
                        actions.exit();
                        break;
                }

                break;
            }
        }
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
    }
}
