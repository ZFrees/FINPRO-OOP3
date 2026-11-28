
package com.Vanguard.frontend;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.items.Item;
import com.Vanguard.frontend.objects.items.ItemType;
import com.Vanguard.frontend.systems.BulletManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;

    private Texture menuTexture;

    private Rectangle startButton;
    private Rectangle loadButton;
    private Rectangle exitButton;

    private boolean inMenu = true;

    private Texture pauseTexture;

    private Rectangle resumeButton;
    private Rectangle saveButton;
    private Rectangle mainMenuButton;
    private Rectangle pauseExitButton;

    private boolean isPaused = false;

    private Player player;
    private List<Item> items;
    private BulletManager bulletManager;
    private List<Special> specials;

    private Texture playerTexture;
    private Texture missilesTexture;
    private Texture shotTexture;
    private Texture sprayTexture;
    private Texture straightTexture;
    private Texture specialTexture;
    private Texture specialExplosionTexture;

    @Override
    public void create() {

        batch = new SpriteBatch();

        menuTexture = new Texture("menu_game.png");
        pauseTexture = new Texture("pause.png");

        specials = new ArrayList<>();
        items = new ArrayList<>();

        playerTexture = new Texture("player.png");
        missilesTexture = new Texture("item.png");
        shotTexture = new Texture("vulcan.png");
        sprayTexture = new Texture("bullet_spray.png");
        specialTexture = new Texture("dih.png");
        straightTexture = new Texture("bullet_straight.png");
        specialExplosionTexture = new Texture("dih_explosion.png");

        bulletManager = new BulletManager(
            batch,
            sprayTexture,
            sprayTexture
        );

        bulletManager.registerTexture("straight", straightTexture);

        player = new Player(
            10,
            1,
            200f,
            batch,
            playerTexture,
            sprayTexture,
            specialTexture,
            specialExplosionTexture
        );

        items.add(new Item(
            240f, 400f, 16f, 16f,
            250f, batch, null, ItemType.MISSILE
        ));

        items.add(new Item(
            320f, 400f, 16f, 16f,
            150f, batch, null, ItemType.SHOTPATT
        ));

        createMenuButtons();
        createPauseButtons();
    }

    private void createMenuButtons() {

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float buttonWidth = width * 0.65f;
        float buttonHeight = height * 0.10f;
        float buttonX = (width - buttonWidth) / 2f;

        startButton = new Rectangle(
            buttonX, height * 0.548f,
            buttonWidth, buttonHeight
        );

        loadButton = new Rectangle(
            buttonX, height * 0.433f,
            buttonWidth, buttonHeight
        );

        exitButton = new Rectangle(
            buttonX, height * 0.318f,
            buttonWidth, buttonHeight
        );
    }

    private void createPauseButtons() {

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float buttonWidth = width * 0.70f;
        float buttonHeight = height * 0.12f;
        float buttonX = (width - buttonWidth) / 2f;

        resumeButton = new Rectangle(
            buttonX, height * 0.58f,
            buttonWidth, buttonHeight
        );

        saveButton = new Rectangle(
            buttonX, height * 0.42f,
            buttonWidth, buttonHeight
        );

        mainMenuButton = new Rectangle(
            buttonX, height * 0.26f,
            buttonWidth, buttonHeight
        );

        pauseExitButton = new Rectangle(
            buttonX, height * 0.10f,
            buttonWidth, buttonHeight
        );
    }

    @Override
    public void resize(int width, int height) {

        createMenuButtons();
        createPauseButtons();
    }

    @Override
    public void render() {

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (inMenu) {

            renderMenu();
            handleMenuInput();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            isPaused = !isPaused;
        }

        if (isPaused) {

            renderPauseMenu();
            handlePauseInput();
            return;
        }

        renderGameplay();
    }

    private void renderMenu() {

        batch.begin();

        batch.draw(
            menuTexture,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        batch.end();
    }

    private void handleMenuInput() {

        if (Gdx.input.justTouched()) {

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (startButton.contains(mouseX, mouseY)) {

                inMenu = false;
                isPaused = false;

                Gdx.app.log("MENU", "START GAME");

            } else if (loadButton.contains(mouseX, mouseY)) {

                Gdx.app.log("MENU", "LOAD GAME");

            } else if (exitButton.contains(mouseX, mouseY)) {

                Gdx.app.exit();
            }
        }
    }

    private void renderPauseMenu() {

        batch.begin();

        batch.draw(
            pauseTexture,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        batch.end();
    }

    private void handlePauseInput() {

        if (Gdx.input.justTouched()) {

            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            Gdx.app.log(
                "PAUSE CLICK",
                "X: " + mouseX + " Y: " + mouseY
            );

            if (resumeButton.contains(mouseX, mouseY)) {

                isPaused = false;

                Gdx.app.log("PAUSE", "RESUME GAME");

            } else if (saveButton.contains(mouseX, mouseY)) {

                Gdx.app.log("PAUSE", "SAVE GAME");

            } else if (mainMenuButton.contains(mouseX, mouseY)) {

                isPaused = false;
                inMenu = true;

                Gdx.app.log("PAUSE", "MAIN MENU");

            } else if (pauseExitButton.contains(mouseX, mouseY)) {

                Gdx.app.exit();

            } else {

                Gdx.app.log("PAUSE", "CLICK OUTSIDE BUTTON");
            }
        }
    }

    private void renderGameplay() {

        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {

            player.setPower(player.getPower() + 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {

            player.setPower(player.getPower() - 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {

            player.switchWeapon();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {

            items.add(new Item(
                Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() - 40f,
                16f,
                16f,
                40f,
                batch,
                null,
                ItemType.SHOTPATT
            ));
        }

        Rectangle playerBounds = new Rectangle(
            player.getX(),
            player.getY(),
            playerTexture.getWidth(),
            playerTexture.getHeight()
        );

        Iterator<Item> itemIterator = items.iterator();

        while (itemIterator.hasNext()) {

            Item currentItem = itemIterator.next();

            currentItem.update(delta);

            Rectangle itemBounds = new Rectangle(
                currentItem.getX(),
                currentItem.getY(),
                currentItem.getWidth(),
                currentItem.getHeight()
            );

            if (!currentItem.isDestroyed()
                && playerBounds.overlaps(itemBounds)) {

                if (currentItem.getItemType() == ItemType.SHOTPATT) {

                    player.switchWeapon();

                } else if (currentItem.getItemType() == ItemType.MISSILE) {

                    player.setPower(player.getPower() + 1);
                }

                currentItem.destroy();
            }

            if (currentItem.isDestroyed()) {

                itemIterator.remove();
            }
        }

        batch.begin();

        player.render();

        for (Item currentItem : items) {

            Texture textureToDraw =
                (currentItem.getItemType() == ItemType.SHOTPATT)
                    ? shotTexture
                    : missilesTexture;

            batch.draw(
                textureToDraw,
                currentItem.getX(),
                currentItem.getY(),
                currentItem.getWidth(),
                currentItem.getHeight()
            );
        }

        player.shootBullet(bulletManager);

        bulletManager.update(
            delta,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        Special newSpecial = player.shootSpecial();

        if (newSpecial != null) {

            specials.add(newSpecial);
        }

        bulletManager.render();

        for (Special special : specials) {

            special.render();
        }

        batch.end();

        specials.removeIf(Special::isFinished);
    }

    @Override
    public void dispose() {

        batch.dispose();

        menuTexture.dispose();
        pauseTexture.dispose();

        playerTexture.dispose();
        missilesTexture.dispose();
        shotTexture.dispose();
        sprayTexture.dispose();
        straightTexture.dispose();
        specialTexture.dispose();
        specialExplosionTexture.dispose();
    }
}
