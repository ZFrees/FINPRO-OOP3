package com.Vanguard.frontend;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
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
    private List<Special> specials;

    private Texture playerTexture;
    private Texture bulletTexture;
    private Texture specialTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        bullets = new ArrayList<>();
        specials = new ArrayList<>();

        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("bullet_spray.png");
        specialTexture = new Texture("dih.png");

        player = new Player(10, 100, 200f, batch, playerTexture, bulletTexture, specialTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        player.render();

        Bullet newBullet = player.shootBullet();
        if (newBullet != null) {
            bullets.add(newBullet);
        }

        Special newSpecial = player.shootSpecial();
        if (newSpecial != null) {
            specials.add(newSpecial);
        }

        for (Bullet bullet : bullets) {
            bullet.render();
        }

        for (Special special : specials) {
            special.render();
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
