package com.Vanguard.frontend.objects.patterns.bulletStrategy;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.bullets.Bullet;

public class BoomerangMovement implements BulletMovementPattern {
    private float deceleration;
    private float returnMaxSpeed;
    private GameObject returnTarget;
    private boolean hasTurned = false;

    public BoomerangMovement(float deceleration, float returnMaxSpeed, GameObject returnTarget) {
        this.deceleration = deceleration;
        this.returnMaxSpeed = returnMaxSpeed;
        this.returnTarget = returnTarget;
    }

    public BoomerangMovement(float deceleration, float returnMaxSpeed) {
        this(deceleration, returnMaxSpeed, null);
    }

    @Override
    public void move(Bullet bullet, float delta) {
        if (bullet == null) return;

        float currentSpeed = bullet.getSpeed();

        if (!hasTurned) {
            currentSpeed -= deceleration * delta;
            if (currentSpeed <= 0f) {
                hasTurned = true;
                currentSpeed = 0f;
                if (returnTarget != null && !returnTarget.isDestroyed()) {
                    float targetCenterX = returnTarget.getX() + returnTarget.getWidth() / 2f;
                    float targetCenterY = returnTarget.getY() + returnTarget.getHeight() / 2f;
                    float targetAngle = (float) Math.toDegrees(Math.atan2(targetCenterY - bullet.getY(), targetCenterX - bullet.getX()));
                    bullet.setAngle(targetAngle);
                } else {
                    bullet.setAngle((bullet.getAngle() + 180f) % 360f);
                }
            }
        } else {
            currentSpeed = Math.min(returnMaxSpeed, currentSpeed + deceleration * delta);
        }

        bullet.setSpeed(currentSpeed);
        float rad = (float) Math.toRadians(bullet.getAngle());
        bullet.setX(bullet.getX() + (float) Math.cos(rad) * currentSpeed * delta);
        bullet.setY(bullet.getY() + (float) Math.sin(rad) * currentSpeed * delta);
    }

    public float getDeceleration() { return deceleration; }
    public void setDeceleration(float deceleration) { this.deceleration = deceleration; }

    public float getReturnMaxSpeed() { return returnMaxSpeed; }
    public void setReturnMaxSpeed(float returnMaxSpeed) { this.returnMaxSpeed = returnMaxSpeed; }

    public GameObject getReturnTarget() { return returnTarget; }
    public void setReturnTarget(GameObject returnTarget) { this.returnTarget = returnTarget; }
}
