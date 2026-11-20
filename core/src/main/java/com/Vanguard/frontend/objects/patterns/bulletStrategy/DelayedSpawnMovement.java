package com.Vanguard.frontend.objects.patterns.bulletStrategy;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.bullets.Bullet;

public class DelayedSpawnMovement implements BulletMovementPattern {
    private final float delayDuration;
    private final float launchSpeed;
    private final GameObject aimTarget;
    private boolean launched = false;

    public DelayedSpawnMovement(float delayDuration, float launchSpeed, GameObject aimTarget) {
        this.delayDuration = delayDuration;
        this.launchSpeed = launchSpeed;
        this.aimTarget = aimTarget;
    }

    public DelayedSpawnMovement(float delayDuration, float launchSpeed) {
        this(delayDuration, launchSpeed, null);
    }

    @Override
    public void move(Bullet bullet, float delta) {
        if (bullet == null) return;

        if (bullet.getStateTime() < delayDuration) {
            bullet.setSpeed(0f);
            return;
        }

        if (!launched) {
            launched = true;
            if (aimTarget != null && !aimTarget.isDestroyed()) {
                float targetCenterX = aimTarget.getX() + aimTarget.getWidth() / 2f;
                float targetCenterY = aimTarget.getY() + aimTarget.getHeight() / 2f;
                float targetAngle = (float) Math.toDegrees(Math.atan2(targetCenterY - bullet.getY(), targetCenterX - bullet.getX()));
                bullet.setAngle(targetAngle);
            }
            bullet.setSpeed(launchSpeed);
        }

        float rad = (float) Math.toRadians(bullet.getAngle());
        bullet.setX(bullet.getX() + (float) Math.cos(rad) * bullet.getSpeed() * delta);
        bullet.setY(bullet.getY() + (float) Math.sin(rad) * bullet.getSpeed() * delta);
    }

    public float getDelayDuration() { return delayDuration; }
    public float getLaunchSpeed() { return launchSpeed; }
    public GameObject getAimTarget() { return aimTarget; }
}
