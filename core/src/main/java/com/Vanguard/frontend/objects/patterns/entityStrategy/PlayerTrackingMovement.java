package com.Vanguard.frontend.objects.patterns.entityStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class PlayerTrackingMovement implements EntityMovementPattern {
    private GameObject target;
    private float speed;
    private float turnSpeed; // radians per second
    private float currentAngle; // in radians

    public PlayerTrackingMovement(GameObject target, float speed, float turnSpeedRadians, float initialAngleRadians) {
        this.target = target;
        this.speed = speed;
        this.turnSpeed = turnSpeedRadians;
        this.currentAngle = initialAngleRadians;
    }

    @Override
    public void move(GameObject entity, float delta) {
        if (entity == null) return;

        if (target != null && !target.isDestroyed()) {
            float entityCenterX = entity.getX() + entity.getWidth() / 2f;
            float entityCenterY = entity.getY() + entity.getHeight() / 2f;
            float targetCenterX = target.getX() + target.getWidth() / 2f;
            float targetCenterY = target.getY() + target.getHeight() / 2f;

            float desiredAngle = (float) Math.atan2(targetCenterY - entityCenterY, targetCenterX - entityCenterX);
            float diff = (desiredAngle - currentAngle) % (float) (2.0 * Math.PI);
            if (diff > Math.PI) diff -= (float) (2.0 * Math.PI);
            if (diff < -Math.PI) diff += (float) (2.0 * Math.PI);

            currentAngle += Math.signum(diff) * Math.min(Math.abs(diff), turnSpeed * delta);
        }

        float vx = (float) Math.cos(currentAngle) * speed;
        float vy = (float) Math.sin(currentAngle) * speed;
        entity.setX(entity.getX() + vx * delta);
        entity.setY(entity.getY() + vy * delta);
    }

    public GameObject getTarget() { return target; }
    public void setTarget(GameObject target) { this.target = target; }
    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public float getTurnSpeed() { return turnSpeed; }
    public void setTurnSpeed(float turnSpeed) { this.turnSpeed = turnSpeed; }
    public float getCurrentAngle() { return currentAngle; }
    public void setCurrentAngle(float currentAngle) { this.currentAngle = currentAngle; }
}
