package com.Vanguard.frontend.objects.patterns.shootingStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Vanguard.frontend.systems.BulletManager;

public class VFormationShot implements ShootingPattern {
    private float bulletSpeed;
    private int damage;
    private float vAngleSpread = 25f; // angle offset for the wings of the V
    private BulletMovementPattern bulletMovementPattern;
    private String spriteKey;

    public VFormationShot(float bulletSpeed, int damage, float vAngleSpread, BulletMovementPattern bulletMovementPattern, String spriteKey) {
        this.bulletSpeed = bulletSpeed;
        this.damage = damage;
        this.vAngleSpread = vAngleSpread;
        this.bulletMovementPattern = bulletMovementPattern;
        this.spriteKey = spriteKey;
    }

    public VFormationShot(float bulletSpeed, int damage) {
        this(bulletSpeed, damage, 25f, null, null);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        float centerAngle = isPlayer ? 90.0f : 270.0f;
        float[] angles = {
            centerAngle,
            centerAngle + vAngleSpread,
            centerAngle - vAngleSpread,
            centerAngle + vAngleSpread * 1.5f,
            centerAngle - vAngleSpread * 1.5f
        };

        for (float angleDeg : angles) {
            float rad = (float) Math.toRadians(angleDeg);
            float vx = (float) (bulletSpeed * Math.cos(rad));
            float vy = (float) (bulletSpeed * Math.sin(rad));

            Bullet bullet;
            if (isPlayer) {
                bullet = bulletManager.spawnPlayerBullet(originX, originY, vx, vy, damage, spriteKey);
            } else {
                bullet = (spriteKey != null) ? bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage, spriteKey)
                                             : bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage);
            }

            if (bullet != null) {
                bullet.setAngle(angleDeg);
                bullet.setSpeed(bulletSpeed);
                if (bulletMovementPattern != null) {
                    bullet.setMovementPattern(bulletMovementPattern);
                }
            }
        }
    }

    public float getBulletSpeed() { return bulletSpeed; }
    public void setBulletSpeed(float bulletSpeed) { this.bulletSpeed = bulletSpeed; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public float getVAngleSpread() { return vAngleSpread; }
    public void setVAngleSpread(float vAngleSpread) { this.vAngleSpread = vAngleSpread; }
}
