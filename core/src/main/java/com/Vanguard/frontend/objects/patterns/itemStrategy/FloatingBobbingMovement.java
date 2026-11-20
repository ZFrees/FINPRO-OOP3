package com.Vanguard.frontend.objects.patterns.itemStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class FloatingBobbingMovement implements ItemMovementPattern {
    private float fallSpeed;
    private float floatAmplitude;
    private float floatFrequency;

    public FloatingBobbingMovement(float fallSpeed, float floatAmplitude, float floatFrequency) {
        this.fallSpeed = fallSpeed;
        this.floatAmplitude = floatAmplitude;
        this.floatFrequency = floatFrequency;
    }

    public FloatingBobbingMovement() {
        this(30f, 15f, 4f);
    }

    @Override
    public void move(GameObject item, float delta) {
        if (item == null) return;

        float t = item.getStateTime();
        float dy = -fallSpeed * delta;
        float dx = (float) (Math.cos(t * floatFrequency) * floatAmplitude * delta);

        item.setX(item.getX() + dx);
        item.setY(item.getY() + dy);
    }

    public float getFallSpeed() { return fallSpeed; }
    public void setFallSpeed(float fallSpeed) { this.fallSpeed = fallSpeed; }
    public float getFloatAmplitude() { return floatAmplitude; }
    public void setFloatAmplitude(float floatAmplitude) { this.floatAmplitude = floatAmplitude; }
    public float getFloatFrequency() { return floatFrequency; }
    public void setFloatFrequency(float floatFrequency) { this.floatFrequency = floatFrequency; }
}
