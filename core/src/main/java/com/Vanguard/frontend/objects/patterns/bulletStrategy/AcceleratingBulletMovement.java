package com.Vanguard.frontend.objects.patterns.bulletStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;

public class AcceleratingBulletMovement implements BulletMovementPattern {
    private float acceleration;
    private float minSpeed;
    private float maxSpeed;

    public AcceleratingBulletMovement(float acceleration, float minSpeed, float maxSpeed) {
        this.acceleration = acceleration;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    public AcceleratingBulletMovement(float acceleration, float maxSpeed) {
        this(acceleration, 0f, maxSpeed);
    }

    @Override
    public void move(Bullet bullet, float delta) {
        if (bullet == null) return;

        float updatedSpeed = bullet.getSpeed() + acceleration * delta;
        updatedSpeed = Math.max(minSpeed, Math.min(maxSpeed, updatedSpeed));
        bullet.setSpeed(updatedSpeed);

        float rad = (float) Math.toRadians(bullet.getAngle());
        bullet.setX(bullet.getX() + (float) Math.cos(rad) * updatedSpeed * delta);
        bullet.setY(bullet.getY() + (float) Math.sin(rad) * updatedSpeed * delta);
    }

    public float getAcceleration() { return acceleration; }
    public void setAcceleration(float acceleration) { this.acceleration = acceleration; }

    public float getMinSpeed() { return minSpeed; }
    public void setMinSpeed(float minSpeed) { this.minSpeed = minSpeed; }

    public float getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(float maxSpeed) { this.maxSpeed = maxSpeed; }
}
