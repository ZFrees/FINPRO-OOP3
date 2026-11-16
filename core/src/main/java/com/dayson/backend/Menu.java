
package com.dayson.backend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Menu extends ScreenAdapter {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private BitmapFont font;

    private final String[] buttons = {
        "NEW GAME",
        "RESUME",
        "LOAD GAME",
        "SAVE GAME",
        "EXIT"
    };

    private float centerX;
    private float startY;

    private final float buttonWidth = 300;
    private final float buttonHeight = 55;
    private final float gap = 18;

    public Menu() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        centerX = width / 2f;
        startY = height * 0.58f;

        Gdx.gl.glClearColor(0.01f, 0.025f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBackground(width, height);
        drawTitle();
        drawButtons();
        handleInput();
    }

    private void drawBackground(float width, float height) {

        shape.begin(ShapeRenderer.ShapeType.Filled);

        shape.setColor(0.01f, 0.025f, 0.08f, 1);
        shape.rect(0, 0, width, height);

        for (int i = 0; i < 80; i++) {
            float x = (i * 137f) % width;
            float y = (i * 83f) % height;

            shape.setColor(Color.CYAN);
            shape.circle(x, y, 2);
        }

        shape.end();
    }

    private void drawTitle() {

        batch.begin();

        font.getData().setScale(4);
        font.setColor(Color.CYAN);

        font.draw(
            batch,
            "GALACTIC VANGUARD",
            centerX - 160,
            Gdx.graphics.getHeight() * 0.85f
        );

        font.getData().setScale(1.2f);
        font.setColor(Color.WHITE);

        font.draw(
            batch,
            "GALACTIC DEFENSE FORCE",
            centerX - 115,
            Gdx.graphics.getHeight() * 0.78f
        );

        batch.end();
    }

    private void drawButtons() {

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        shape.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < buttons.length; i++) {

            float x = centerX - buttonWidth / 2;
            float y = startY - i * (buttonHeight + gap);

            boolean hover =
                mouseX >= x &&
                    mouseX <= x + buttonWidth &&
                    mouseY >= y &&
                    mouseY <= y + buttonHeight;

            if (hover) {
                shape.setColor(0.05f, 0.4f, 0.8f, 1);
            } else {
                shape.setColor(0.04f, 0.13f, 0.27f, 1);
            }

            shape.rect(x, y, buttonWidth, buttonHeight);
        }

        shape.end();

        batch.begin();

        font.getData().setScale(1.7f);

        for (int i = 0; i < buttons.length; i++) {

            float y = startY - i * (buttonHeight + gap);

            font.setColor(Color.CYAN);

            font.draw(
                batch,
                buttons[i],
                centerX - 75,
                y + 36
            );
        }

        batch.end();
    }

    private void handleInput() {

        if (!Gdx.input.justTouched()) return;

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        for (int i = 0; i < buttons.length; i++) {

            float x = centerX - buttonWidth / 2;
            float y = startY - i * (buttonHeight + gap);

            if (mouseX >= x &&
                mouseX <= x + buttonWidth &&
                mouseY >= y &&
                mouseY <= y + buttonHeight) {

                switch (i) {
                    case 0:
                        Systems.out.println("NEW GAME");
                        break;
                    case 1:
                        Systems.out.println("RESUME");
                        break;
                    case 2:
                        Systems.out.println("LOAD GAME");
                        break;
                    case 3:
                        Systems.out.println("SAVE GAME");
                        break;
                    case 4:
                        Gdx.app.exit();
                        break;
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
        font.dispose();
    }
}
