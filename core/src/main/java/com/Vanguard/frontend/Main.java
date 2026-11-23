package com.Vanguard.frontend;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.systems.BulletManager;
import com.badlogic.gdx.Input;
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
    private BulletManager bulletManager;
    private List<Special> specials;

    private Texture playerTexture;
    private Texture sprayTexture;
    private Texture straightTexture;
    private Texture specialTexture;
    private Texture specialExplosionTexture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        specials = new ArrayList<>();

        playerTexture = new Texture("player.png");
        sprayTexture = new Texture("bullet_spray.png");
        specialTexture = new Texture("dih.png");
        straightTexture = new Texture("bullet_straight.png");
        specialExplosionTexture = new Texture("dih_explosion.png");

        bulletManager = new BulletManager(batch, sprayTexture, sprayTexture);
        bulletManager.registerTexture("straight", straightTexture);

        player = new Player(10, 1, 200f, batch, playerTexture, sprayTexture, specialTexture, specialExplosionTexture);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        // TEMP debug: E = level up, Q = level down (remove once power-up items work)
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) player.setPower(player.getPower() + 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) player.setPower(player.getPower() - 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) player.switchWeapon();

        batch.begin();
        player.render();

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
        sprayTexture.dispose();
        straightTexture.dispose();
        specialTexture.dispose();
        specialExplosionTexture.dispose();
    }
}
