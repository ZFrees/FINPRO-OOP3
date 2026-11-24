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
        specials = new ArrayList<>();
        items = new ArrayList<>();

        playerTexture = new Texture("player.png");
        missilesTexture = new Texture("item.png");
        shotTexture = new Texture("vulcan.png");
        sprayTexture = new Texture("bullet_spray.png");
        specialTexture = new Texture("dih.png");
        straightTexture = new Texture("bullet_straight.png");
        specialExplosionTexture = new Texture("dih_explosion.png");

        bulletManager = new BulletManager(batch, sprayTexture, sprayTexture);
        bulletManager.registerTexture("straight", straightTexture);

        player = new Player(10, 1, 200f, batch, playerTexture, sprayTexture, specialTexture, specialExplosionTexture);
        items.add(new Item(240f, 400f, 256f, 256f, 250f, batch, ItemType.MISSILE));
        items.add(new Item(320f, 400f, 16f, 16f, 150f, batch, ItemType.SHOTPATT));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        // TEMP debug: E = level up, Q = level down (remove once power-up items work)
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) player.setPower(player.getPower() + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) player.setPower(player.getPower() - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) player.switchWeapon();
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            items.add(new Item(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 40f,
                16f, 16f, 40f, batch, ItemType.SHOTPATT));
        }

        Rectangle playerBounds = new Rectangle(
            player.getX(), player.getY(),
            playerTexture.getWidth(), playerTexture.getHeight()
        );

        Iterator<Item> itemIterator = items.iterator();
        while (itemIterator.hasNext()) {
            Item currentItem = itemIterator.next();
            currentItem.update(delta);

            Rectangle itemBounds = new Rectangle(
                currentItem.getX(), currentItem.getY(),
                currentItem.getWidth(), currentItem.getHeight()
            );

            if (!currentItem.isDestroyed() && playerBounds.overlaps(itemBounds)) {
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
            Texture textureToDraw = (currentItem.getItemType() == ItemType.SHOTPATT)
                ? shotTexture
                : missilesTexture;
            batch.draw(textureToDraw, currentItem.getX(), currentItem.getY(),
                currentItem.getWidth(), currentItem.getHeight());
        }

        player.shootBullet(bulletManager);
        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        playerTexture.dispose();
        missilesTexture.dispose();
        shotTexture.dispose();
        sprayTexture.dispose();
        straightTexture.dispose();
        specialTexture.dispose();
        specialExplosionTexture.dispose();
    }
}
