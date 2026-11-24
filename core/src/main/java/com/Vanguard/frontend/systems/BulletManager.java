package com.Vanguard.frontend.systems;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.badlogic.gdx.graphics.Texture;
import com.Vanguard.frontend.objects.bullets.BulletType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class BulletManager {
    private final SpriteBatch batch;
    private final Texture defaultPlayerTexture;
    private final Texture defaultEnemyTexture;
    private final Map<String, Texture> textures = new HashMap<>();

    private final Queue<Bullet> playerPool = new ArrayDeque<>();
    private final Queue<Bullet> enemyPool = new ArrayDeque<>();
    private final List<Bullet> playerActive = new ArrayList<>();
    private final List<Bullet> enemyActive = new ArrayList<>();

    private List <GameObject> objectActive;

    public BulletManager(SpriteBatch batch, Texture defaultPlayerTexture, Texture defaultEnemyTexture) {
        this.batch = batch;
        this.defaultPlayerTexture = defaultPlayerTexture;
        this.defaultEnemyTexture = defaultEnemyTexture;
    }

    public void registerTexture(String key, Texture texture) {
        textures.put(key, texture);
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage, BulletType type) {
        return spawnPlayerBullet(x, y, vx, vy, damage, type, null);
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage, BulletType type, String spriteKey) {
        Texture tex = textures.getOrDefault(spriteKey, defaultPlayerTexture);
        return spawn(playerPool, playerActive, x, y, vx, vy, damage, true, tex, type);
    }

    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage, BulletType type) {
        return spawnEnemyBullet(x, y, vx, vy, damage, type, null);
    }

    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage, BulletType type, String spriteKey) {
        Texture tex = textures.getOrDefault(spriteKey , defaultEnemyTexture);
        return spawn(enemyPool, enemyActive, x, y, vx, vy, damage, false, tex, type);
    }

    private Bullet spawn(Queue<Bullet> pool, List<Bullet> active,
                         float x, float y, float vx, float vy,
                         int damage, boolean isPlayer, Texture tex, BulletType type) {
        Bullet bullet = pool.poll();
        if (bullet == null) bullet = new Bullet(x, y, damage, 100, batch, tex, type);
        bullet.init(x, y, vx, vy, damage, isPlayer, tex, type);
        active.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        updateList(playerActive, playerPool, delta, screenWidth, screenHeight);
        updateList(enemyActive, enemyPool, delta, screenWidth, screenHeight);
    }

    private void updateList(List<Bullet> active, Queue<Bullet> pool,
                            float delta, float w, float h) {
        for (int i = active.size() - 1; i >= 0; i--) {
            Bullet b = active.get(i);
            if (b.isActive()) b.update(delta);
            if (!b.isActive() || b.isOffScreen(w, h)) {
                b.setActive(false);
                active.remove(i);
                pool.add(b);
            }
        }
    }

    public void render() {
        for (Bullet b : playerActive) b.render();
        for (Bullet b : enemyActive) b.render();
    }


    public List<Bullet> getPlayerActive() { return playerActive; }
    public List<Bullet> getEnemyActive() { return enemyActive; }
    public int playerPoolSize() { return playerPool.size(); }
    public int enemyPoolSize() { return enemyPool.size(); }
}
