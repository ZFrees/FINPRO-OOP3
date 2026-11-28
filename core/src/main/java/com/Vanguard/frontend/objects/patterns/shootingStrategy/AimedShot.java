package com.Vanguard.frontend.objects.patterns.shootingStrategy;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.systems.BulletManager;

public class AimedShot implements ShootingPattern {
    private float speed;
    private int damage;
    private GameObject target;
    private String spriteKey;

    public AimedShot(float speed, int damage, GameObject target, String spriteKey) {
        this.speed = speed;
        this.damage = damage;
        this.target = target;
        this.spriteKey = spriteKey;
    }

    public AimedShot(float speed, int damage, GameObject target) {
        this(speed, damage, target, null);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        float angleDeg;
        if (target != null) {
            float targetCenterX = target.getX() + target.getWidth() / 2f;
            float targetCenterY = target.getY() + target.getHeight() / 2f;
            angleDeg = (float) Math.toDegrees(Math.atan2(targetCenterY - originY, targetCenterX - originX));
        } else {
            angleDeg = 270f; // no target: fire straight down
        }

        float angleRad = (float) Math.toRadians(angleDeg);
        float vx = (float) (speed * Math.cos(angleRad));
        float vy = (float) (speed * Math.sin(angleRad));

        Bullet bullet;
        if (isPlayer) {
            bullet = bulletManager.spawnPlayerBullet(originX, originY, vx, vy, damage, null, spriteKey);
        } else {
            bullet = (spriteKey != null)
                ? bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage, null, spriteKey)
                : bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage, null);
        }

        if (bullet != null) {
            bullet.setAngle(angleDeg);
            bullet.setSpeed(speed);
        }
    }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public GameObject getTarget() { return target; }
    public void setTarget(GameObject target) { this.target = target; }
    public String getSpriteKey() { return spriteKey; }
    public void setSpriteKey(String spriteKey) { this.spriteKey = spriteKey; }
}
