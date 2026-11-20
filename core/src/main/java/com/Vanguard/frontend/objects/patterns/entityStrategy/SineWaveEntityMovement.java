package com.Vanguard.frontend.objects.patterns.entityStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class SineWaveEntityMovement implements EntityMovementPattern {
    private float speed;
    private float angle; // in radians
    private float amplitude;
    private float frequency;
    private float timer = 0f;

    public SineWaveEntityMovement(float speed, float angleRadians, float amplitude, float frequency) {
        this.speed = speed;
        this.angle = angleRadians;
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    @Override
    public void move(GameObject entity, float delta) {
        if (entity == null) return;

        timer += delta;
        float forwardX = (float) Math.cos(angle) * speed * delta;
        float forwardY = (float) Math.sin(angle) * speed * delta;

        float lateralSpeed = amplitude * frequency * (float) Math.cos(timer * frequency);
        float perpAngle = angle + (float) (Math.PI / 2.0);
        float waveX = (float) Math.cos(perpAngle) * lateralSpeed * delta;
        float waveY = (float) Math.sin(perpAngle) * lateralSpeed * delta;

        entity.setX(entity.getX() + forwardX + waveX);
        entity.setY(entity.getY() + forwardY + waveY);
    }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }
    public float getAngle() { return angle; }
    public void setAngle(float angle) { this.angle = angle; }
    public float getAmplitude() { return amplitude; }
    public void setAmplitude(float amplitude) { this.amplitude = amplitude; }
    public float getFrequency() { return frequency; }
    public void setFrequency(float frequency) { this.frequency = frequency; }
}
