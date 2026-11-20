package com.Vanguard.frontend.objects.patterns.shootingStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Vanguard.frontend.systems.BulletManager;

public class LaserBeamShot implements ShootingPattern {
    private float laserSpeed;
    private int damage;
    private int beamDensity; // number of rapid sub-bullets forming the laser line
    private BulletMovementPattern bulletMovementPattern;
    private String spriteKey;

    public LaserBeamShot(float laserSpeed, int damage, int beamDensity, BulletMovementPattern bulletMovementPattern, String spriteKey) {
        this.laserSpeed = laserSpeed;
        this.damage = damage;
        this.beamDensity = Math.max(1, beamDensity);
        this.bulletMovementPattern = bulletMovementPattern;
        this.spriteKey = spriteKey;
    }

    public LaserBeamShot(float laserSpeed, int damage) {
        this(laserSpeed, damage, 3, null, null);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        float angleDeg = isPlayer ? 90.0f : 270.0f;
        float rad = (float) Math.toRadians(angleDeg);
        float vx = (float) (laserSpeed * Math.cos(rad));
        float vy = (float) (laserSpeed * Math.sin(rad));

        for (int i = 0; i < beamDensity; i++) {
            Bullet bullet;
            // Slightly offset spawn positions to create a continuous beam look if needed
            float spawnY = originY + (isPlayer ? -i * 6f : i * 6f);

            if (isPlayer) {
                bullet = bulletManager.spawnPlayerBullet(originX, spawnY, vx, vy, damage, spriteKey);
            } else {
                bullet = (spriteKey != null) ? bulletManager.spawnEnemyBullet(originX, spawnY, vx, vy, damage, spriteKey)
                                             : bulletManager.spawnEnemyBullet(originX, spawnY, vx, vy, damage);
            }

            if (bullet != null) {
                bullet.setAngle(angleDeg);
                bullet.setSpeed(laserSpeed);
                if (bulletMovementPattern != null) {
                    bullet.setMovementPattern(bulletMovementPattern);
                }
            }
        }
    }

    public float getLaserSpeed() { return laserSpeed; }
    public void setLaserSpeed(float laserSpeed) { this.laserSpeed = laserSpeed; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
    public int getBeamDensity() { return beamDensity; }
    public void setBeamDensity(int beamDensity) { this.beamDensity = Math.max(1, beamDensity); }
}
