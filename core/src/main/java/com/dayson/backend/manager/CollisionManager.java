package com.Vanguard.backend.managers;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.backend.entities.Enemy;
import com.Vanguard.backend.entities.Special;

import java.util.List;
import java.util.Iterator;

public class CollisionManager {

    public void checkBulletCollision(
        List<Bullet> bullets,
        List<Enemy> enemies
    ) {

        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {

            Bullet bullet = bulletIterator.next();

            Iterator<Enemy> enemyIterator = enemies.iterator();

            while (enemyIterator.hasNext()) {

                Enemy enemy = enemyIterator.next();

                if (bullet.getBounds().overlaps(enemy.getBounds())) {

                    enemy.takeDamage(bullet.getDamage());

                    bulletIterator.remove();

                    if (enemy.getHealth() <= 0) {
                        enemyIterator.remove();
                    }

                    break;
                }
            }
        }
    }

    public void checkSpecialCollision(
        List<Special> specials,
        List<Enemy> enemies
    ) {

        Iterator<Special> specialIterator = specials.iterator();

        while (specialIterator.hasNext()) {

            Special special = specialIterator.next();

            Iterator<Enemy> enemyIterator = enemies.iterator();

            while (enemyIterator.hasNext()) {

                Enemy enemy = enemyIterator.next();

                if (special.getBounds().overlaps(enemy.getBounds())) {

                    enemy.takeDamage(special.getDamage());

                    specialIterator.remove();

                    if (enemy.getHealth() <= 0) {
                        enemyIterator.remove();
                    }

                    break;
                }
            }
        }
    }

    public void checkPlayerCollision(
        Player player,
        List<Enemy> enemies
    ) {

        for (Enemy enemy : enemies) {

            if (player.getBounds().overlaps(enemy.getBounds())) {

                player.takeDamage(enemy.getDamage());

                break;
            }
        }
    }
}
