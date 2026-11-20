package com.Vanguard.frontend.objects.patterns.shootingStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Vanguard.frontend.systems.BulletManager;

public class SpiralShot implements ShootingPattern {
    private float bulletSpeed;
    private int damage;
    private float currentAngle = 0f;
    private float angleIncrement = 15f;
    private BulletMovementPattern bulletMovementPattern;
    private String spriteKey;

    public SpiralShot(float bulletSpeed, int damage, float angleIncrement, BulletMovementPattern bulletMovementPattern, String spriteKey) {
        this.bulletSpeed = bulletSpeed;
        this.damage = damage;
        this.angleIncrement = angleIncrement;
        this.bulletMovementPattern = bulletMovementPattern;
        this.spriteKey = spriteKey;
    }

    public SpiralShot(float bulletSpeed, int damage) {
        this(bulletSpeed, damage, 15f, null, null);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        float rad = (float) Math.toRadians(currentAngle);
        float vx = (float) (Math.cos(rad) * bulletSpeed);
        float vy = (float) (Math.sin(rad) * bulletSpeed);

        Bullet bullet;
        if (isPlayer) {
            bullet = bulletManager.spawnPlayerBullet(originX, originY, vx, vy, damage, spriteKey);
        } else {
            bullet = (spriteKey != null) ? bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage, spriteKey)
                                         : bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage);
        }

        if (bullet != null) {
            bullet.setAngle(currentAngle);
            bullet.setSpeed(bulletSpeed);
            if (bulletMovementPattern != null) {
                bullet.setMovementPattern(bulletMovementPattern);
            }
        }

        currentAngle = (currentAngle + angleIncrement) % 360f;
    }

    public float getBulletSpeed() { return bulletSpeed; }
    public void setBulletSpeed(float bulletSpeed) { this.bulletSpeed = bulletSpeed; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public float getCurrentAngle() { return currentAngle; }
    public void setCurrentAngle(float currentAngle) { this.currentAngle = currentAngle; }
    public float getAngleIncrement() { return angleIncrement; }
    public void setAngleIncrement(float angleIncrement) { this.angleIncrement = angleIncrement; }
}
