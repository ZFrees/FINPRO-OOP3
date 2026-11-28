package com.Vanguard.frontend;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.enemies.Fighter;
import com.Vanguard.frontend.objects.items.Item;
import com.Vanguard.frontend.objects.items.ItemType;
import com.Vanguard.frontend.systems.BulletManager;
import com.Vanguard.frontend.systems.AssetManager;
import com.Vanguard.frontend.systems.ObjectFactory;
import com.badlogic.gdx.math.Intersector;
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

    //enemies
    private List<Fighter> fighters;

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

        AssetManager.getInstance().init();

        playerTexture = AssetManager.getInstance().getTexture("player.png");
        missilesTexture = AssetManager.getInstance().getTexture("item.png");
        shotTexture = AssetManager.getInstance().getTexture("vulcan.png");
        sprayTexture = AssetManager.getInstance().getTexture("bullet_spray.png");
        specialTexture = AssetManager.getInstance().getTexture("dih.png");
        straightTexture = AssetManager.getInstance().getTexture("bullet_straight.png");
        specialExplosionTexture = AssetManager.getInstance().getTexture("dih_explosion.png");

        bulletManager = new BulletManager(batch, sprayTexture, sprayTexture);
        bulletManager.registerTexture("straight", straightTexture);

        //For later use in level manager, this is how to add items, players, and enemies into the simualtion
        player = ObjectFactory.spawnPlayer(20, 1, 200f, batch, playerTexture, sprayTexture, specialTexture, specialExplosionTexture);
        items.add(new Item(240f, 400f, 16f, 16f, 250f, batch, null, ItemType.MISSILE));
        items.add(new Item(320f, 400f, 16f, 16f, 150f, batch, null, ItemType.SHOTPATT));

        //Enemies for the moment, later moved to level manager
        fighters = new ArrayList<>();
        Texture fighterTexture = AssetManager.getInstance().getTexture("Fighter.png");

        float fighterWidth = 36f;    // matches the width in Fighter's constructor
        float fighterHeight = 36f;
        float spawnX = (Gdx.graphics.getWidth() - fighterWidth) / 4f;
        float spawnY = Gdx.graphics.getHeight() - fighterHeight;

        fighters.add(ObjectFactory.createFighter(spawnX, spawnY, "Fighter", 50, batch, fighterTexture));
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
                16f, 16f, 40f, batch, null, ItemType.SHOTPATT));
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

        // player bullets vs fighters
        for (Bullet b : bulletManager.getPlayerActive()) {
            if (!b.isActive()) continue;
            for (Fighter f : fighters) {
                if (f.isDestroyed()) continue;
                if (Intersector.overlapConvexPolygons(b.getCoreHitbox(), f.getShotHitbox())) {
                    boolean killed = f.takeDamage(b.getDamage());
                    if (killed) player.addScore((int) f.getScoreValue());
                    b.destroy();
                    break;
                }
            }
        }

        // enemy bullets vs player
        for (Bullet b : bulletManager.getEnemyActive()) {
            if (!b.isActive()) continue;
            if (player.isAlive() && Intersector.overlapConvexPolygons(b.getCoreHitbox(), player.getCoreHitbox())) {
                player.takeDamage(b.getDamage());
                b.destroy();
            }
        }

        //Iterator for fighters
        for (Fighter f : fighters) {
            f.update(delta);
            if (f.canShoot(delta)) f.shootBullet(bulletManager);
        }
        fighters.removeIf(f -> f.isDestroyed() || f.isOffScreen(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        if (player.isAlive()) {
            player.update(delta);
            player.shootBullet(bulletManager);
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

        bulletManager.update(delta, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Special newSpecial = player.shootSpecial();
        if (newSpecial != null) {
            specials.add(newSpecial);
        }

        bulletManager.render();

        for (Special special : specials) {
            special.render();
        }

        //For fighters asw
        for (Fighter f : fighters) f.render();
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
