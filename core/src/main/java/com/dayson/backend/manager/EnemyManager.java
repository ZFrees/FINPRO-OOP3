
package com.dayson.backend.manager;

import com.Vanguard.frontend.objects.enemies.Enemy;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EnemyManager {

    public interface EnemyFactory {
        Enemy create(float x, float y);
    }

    private List<Enemy> enemies;
    private EnemyFactory factory;
    private Random random;

    public EnemyManager(EnemyFactory factory) {
        this.factory = factory;
        this.enemies = new ArrayList<>();
        this.random = new Random();
    }

    public void spawnEnemy() {

        float screenWidth = Gdx.graphics.getWidth();

        float x = 40 + random.nextFloat() *
            (screenWidth - 80);

        float y = Gdx.graphics.getHeight();

        Enemy enemy = factory.create(x, y);

        enemies.add(enemy);
    }
    public void update(float delta) {

        Iterator<Enemy> iterator = enemies.iterator();

        while (iterator.hasNext()) {

            Enemy enemy = iterator.next();

            enemy.update(delta);

            if (enemy.getY() + enemy.getHeight() < 0) {
                iterator.remove();
            }
        }
    }

    public void render() {

        for (Enemy enemy : enemies) {
            enemy.render();
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getEnemyCount() {
        return enemies.size();
    }

    public boolean isEmpty() {
        return enemies.isEmpty();
    }

    public void clearEnemies() {
        enemies.clear();
    }
}
