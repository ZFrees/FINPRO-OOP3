package com.Vanguard.frontend.objects.patterns.bulletStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;

public class SineWaveMovement implements BulletMovementPattern {
    private float amplitude;
    private float frequency;

    public SineWaveMovement(float amplitude, float frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    public SineWaveMovement() {
        this(80f, 6f);
    }

    @Override
    public void move(Bullet bullet, float delta) {
        if (bullet == null) return;

        float rad = (float) Math.toRadians(bullet.getAngle());
        float forwardX = (float) Math.cos(rad) * bullet.getSpeed() * delta;
        float forwardY = (float) Math.sin(rad) * bullet.getSpeed() * delta;

        // Lateral velocity derivative of A * sin(w * t) is A * w * cos(w * t)
        float t = bullet.getStateTime();
        float lateralSpeed = amplitude * frequency * (float) Math.cos(t * frequency);
        float perpRad = rad + (float) (Math.PI / 2.0);
        float waveX = (float) Math.cos(perpRad) * lateralSpeed * delta;
        float waveY = (float) Math.sin(perpRad) * lateralSpeed * delta;

        bullet.setX(bullet.getX() + forwardX + waveX);
        bullet.setY(bullet.getY() + forwardY + waveY);
    }

    public float getAmplitude() { return amplitude; }
    public void setAmplitude(float amplitude) { this.amplitude = amplitude; }

    public float getFrequency() { return frequency; }
    public void setFrequency(float frequency) { this.frequency = frequency; }
}
