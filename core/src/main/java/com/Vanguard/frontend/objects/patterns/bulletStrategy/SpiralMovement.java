package com.Vanguard.frontend.objects.patterns.bulletStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;

public class SpiralMovement implements BulletMovementPattern {
    private float angularVelocity;
    private float radialAcceleration;

    public SpiralMovement(float angularVelocity, float radialAcceleration) {
        this.angularVelocity = angularVelocity;
        this.radialAcceleration = radialAcceleration;
    }

    public SpiralMovement(float angularVelocity) {
        this(angularVelocity, 0f);
    }

    @Override
    public void move(Bullet bullet, float delta) {
        if (bullet == null) return;

        bullet.setAngle(bullet.getAngle() + angularVelocity * delta);
        if (radialAcceleration != 0f) {
            bullet.setSpeed(Math.max(0f, bullet.getSpeed() + radialAcceleration * delta));
        }

        float rad = (float) Math.toRadians(bullet.getAngle());
        bullet.setX(bullet.getX() + (float) Math.cos(rad) * bullet.getSpeed() * delta);
        bullet.setY(bullet.getY() + (float) Math.sin(rad) * bullet.getSpeed() * delta);
    }

    public float getAngularVelocity() { return angularVelocity; }
    public void setAngularVelocity(float angularVelocity) { this.angularVelocity = angularVelocity; }

    public float getRadialAcceleration() { return radialAcceleration; }
    public void setRadialAcceleration(float radialAcceleration) { this.radialAcceleration = radialAcceleration; }
}
