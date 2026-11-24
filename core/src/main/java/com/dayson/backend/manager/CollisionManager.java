
package com.dayson.backend.manager;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.enemies.Enemy;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.Vanguard.frontend.Special;

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

                if (Intersector.overlapConvexPolygons(bullet.getCoreHitbox(), enemy.getShotHitbox())) {

                    enemy.takeDamage(bullet.getDamage());

                    bulletIterator.remove();

                    if (enemy.getHp() <= 0) {
                        enemyIterator.remove();
                    }

                    break;
                }
            }
        }
    }

//    public void checkSpecialCollision(
//        List<Special> specials,
//        List<Enemy> enemies
//    ) {
//
//        Iterator<Special> specialIterator = specials.iterator();
//
//        while (specialIterator.hasNext()) {
//
//            Special special = specialIterator.next();
//
//            Iterator<Enemy> enemyIterator = enemies.iterator();
//
//            while (enemyIterator.hasNext()) {
//
//                Enemy enemy = enemyIterator.next();
//
//
//                if (Intersector.overlapConvexPolygons(special.getC, enemy.getShotHitbox())) {
//
//                    enemy.takeDamage(special.getDamage());
//
//                    specialIterator.remove();
//
//                    if (enemy.getHp() <= 0) {
//                        enemyIterator.remove();
//                    }
//
//                    break;
//                }
//            }
//        }
//    }

    public void checkPlayerCollision(
        Player player,
        List<Enemy> enemies
    ) {

        for (Enemy enemy : enemies) {

            //polygon, overlap is exclusively for rectangle
            if (Intersector.overlapConvexPolygons(player.getCoreHitBox(), enemy.getShotHitbox())) {

                player.takeDamage(enemy.getDamage());

                break;
            }
        }
    }
}
