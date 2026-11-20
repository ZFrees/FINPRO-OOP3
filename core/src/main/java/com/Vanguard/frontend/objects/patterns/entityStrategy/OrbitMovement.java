package com.Vanguard.frontend.objects.patterns.entityStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class OrbitMovement implements EntityMovementPattern {
    private GameObject centerEntity;
    private float centerX;
    private float centerY;
    private float radius;
    private float angularVelocity; // radians per second
    private float currentAngle;

    public OrbitMovement(GameObject centerEntity, float radius, float angularVelocity, float initialAngleRadians) {
        this.centerEntity = centerEntity;
        this.radius = radius;
        this.angularVelocity = angularVelocity;
        this.currentAngle = initialAngleRadians;
    }

    public OrbitMovement(float centerX, float centerY, float radius, float angularVelocity, float initialAngleRadians) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.angularVelocity = angularVelocity;
        this.currentAngle = initialAngleRadians;
    }

    @Override
    public void move(GameObject entity, float delta) {
        if (entity == null) return;

        if (centerEntity != null && !centerEntity.isDestroyed()) {
            centerX = centerEntity.getX() + centerEntity.getWidth() / 2f;
            centerY = centerEntity.getY() + centerEntity.getHeight() / 2f;
        }

        currentAngle += angularVelocity * delta;
        float targetX = centerX + (float) Math.cos(currentAngle) * radius - entity.getWidth() / 2f;
        float targetY = centerY + (float) Math.sin(currentAngle) * radius - entity.getHeight() / 2f;

        entity.setX(targetX);
        entity.setY(targetY);
    }

    public GameObject getCenterEntity() { return centerEntity; }
    public void setCenterEntity(GameObject centerEntity) { this.centerEntity = centerEntity; }
    public float getRadius() { return radius; }
    public void setRadius(float radius) { this.radius = radius; }
    public float getAngularVelocity() { return angularVelocity; }
    public void setAngularVelocity(float angularVelocity) { this.angularVelocity = angularVelocity; }
    public float getCurrentAngle() { return currentAngle; }
    public void setCurrentAngle(float currentAngle) { this.currentAngle = currentAngle; }
}
