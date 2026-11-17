/* package com.Vanguard.frontend.systems;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.bullets.BulletType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

public class BulletManager {
    private Queue<Bullet> playerBullet;
    private Queue<Bullet> enemyBullet;

    private List<Bullet> playerActive;
    private List<Bullet> enemyaActive;
    private List <GameObject> objectActive;

    public BulletManager(){
        playerBullet = new LinkedList<>();
        enemyBullet = new LinkedList<>();
        playerActive = new ArrayList<>();
        enemyaActive = new ArrayList<>();
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, BulletType bulletType, int power, boolean isPlayerBullet){
        Bullet bullet;
        if (!playerBullet.isEmpty()) {
            bullet = playerBullet.poll();
            bullet.init(x, y, vx, vy, bulletType.VULCAN, power, true);
        } else {
            bullet = ObjectFactory.spawnPlayerBullet(x, y, power, speeds(vx, vy), batch, bulletType);
            bullet.init(x, y, vx, vy, BulletType.VULCAN, power, true);
        }
        playerActive.add(bullet);
        return bullet;
    }

    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, BulletType bulletType, int power, boolean isPlayerBullet){
        Bullet bullet;
        if (!enemyBullet.isEmpty()) {
            bullet = enemyBullet.poll();
            bullet.init(x, y, vx, vy, BulletType.VULCAN, power, true);
        } else {
            bullet = ObjectFactory.spawnPlayerBullet(x, y, power, s);
            bullet.init(x, y, vx, vy, BulletType.BRATHA, power, true);
        }
        enemyaActive.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight){
        Iterator<Bullet> player = playerActive.iterator();
        // while(enemy.hasNext()){}
    }

    public void render(SpriteBatch batch){
        for(Bullet bullet : playerActive){
            if(!bullet.isDestroyed()) bullet.render();
        }
        for(Bullet bullet : enemyaActive){
            if(!bullet.isDestroyed()) bullet.render();
        }
    }

    public List<Bullet> getPlayerActive() {
        return playerActive;
    }

    public List<Bullet> getEnemyaActive() {
        return enemyaActive;
    }

    public int PlayerPoolSize() {
        return playerBullet.size();
    }

    public int EnemyPoolSize() {
        return enemyBullet.size();
    }
} */
