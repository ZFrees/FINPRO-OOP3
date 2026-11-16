package com.Vanguard.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private List<Bullet> bullets;

    private Texture playerTexture;
    private Texture bulletTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bullets = new ArrayList<>();

        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet_spray.png");

        player = new Player(10, 100, 200f, batch, playerTexture, bulletTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        player.render();

        Bullet newBullet = player.shoot();
        if (newBullet != null) {
            bullets.add(newBullet);
        }

        for (Bullet bullet : bullets) {
            bullet.render();
        }
        batch.end();

        bullets.removeIf(b -> b.getY() > Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerTexture.dispose();
        bulletTexture.dispose();
    }
}
