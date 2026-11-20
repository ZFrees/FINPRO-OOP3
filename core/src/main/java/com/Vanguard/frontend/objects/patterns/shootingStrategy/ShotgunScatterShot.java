package com.Vanguard.frontend.objects.patterns.shootingStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Vanguard.frontend.systems.BulletManager;
import java.util.Random;

public class ShotgunScatterShot implements ShootingPattern {
    private float baseSpeed;
    private int pelletCount;
    private float spreadConeDegrees;
    private int damage;
    private BulletMovementPattern bulletMovementPattern;
    private String spriteKey;
    private Random random = new Random();

    public ShotgunScatterShot(float baseSpeed, int pelletCount, float spreadConeDegrees, int damage,
                              BulletMovementPattern bulletMovementPattern, String spriteKey) {
        this.baseSpeed = baseSpeed;
        this.pelletCount = Math.max(1, pelletCount);
        this.spreadConeDegrees = spreadConeDegrees;
        this.damage = damage;
        this.bulletMovementPattern = bulletMovementPattern;
        this.spriteKey = spriteKey;
    }

    public ShotgunScatterShot(float baseSpeed, int pelletCount, float spreadConeDegrees, int damage) {
        this(baseSpeed, pelletCount, spreadConeDegrees, damage, null, null);
    }

    @Override
    public void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer) {
        if (bulletManager == null) return;

        float centerAngle = isPlayer ? 90.0f : 270.0f;
        float startAngle = centerAngle - (spreadConeDegrees / 2f);

        for (int i = 0; i < pelletCount; i++) {
            float randomAngleOffset = random.nextFloat() * spreadConeDegrees;
            float angleDeg = startAngle + randomAngleOffset;
            float rad = (float) Math.toRadians(angleDeg);

            // Slight speed variance for shotgun pellets
            float speed = baseSpeed * (0.85f + random.nextFloat() * 0.3f);
            float vx = (float) (speed * Math.cos(rad));
            float vy = (float) (speed * Math.sin(rad));

            Bullet bullet;
            if (isPlayer) {
                bullet = bulletManager.spawnPlayerBullet(originX, originY, vx, vy, damage, spriteKey);
            } else {
                bullet = (spriteKey != null) ? bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage, spriteKey)
                                             : bulletManager.spawnEnemyBullet(originX, originY, vx, vy, damage);
            }

            if (bullet != null) {
                bullet.setAngle(angleDeg);
                bullet.setSpeed(speed);
                if (bulletMovementPattern != null) {
                    bullet.setMovementPattern(bulletMovementPattern);
                }
            }
        }
    }

    public float getBaseSpeed() { return baseSpeed; }
    public void setBaseSpeed(float baseSpeed) { this.baseSpeed = baseSpeed; }
    public int getPelletCount() { return pelletCount; }
    public void setPelletCount(int pelletCount) { this.pelletCount = Math.max(1, pelletCount); }
    public float getSpreadConeDegrees() { return spreadConeDegrees; }
    public void setSpreadConeDegrees(float spreadConeDegrees) { this.spreadConeDegrees = spreadConeDegrees; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }
}
