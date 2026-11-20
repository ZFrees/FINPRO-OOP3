package com.Vanguard.frontend.objects.patterns.itemStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class SineWaveDriftMovement implements ItemMovementPattern {
    private float descentSpeed;
    private float waveWidth;
    private float waveSpeed;

    public SineWaveDriftMovement(float descentSpeed, float waveWidth, float waveSpeed) {
        this.descentSpeed = descentSpeed;
        this.waveWidth = waveWidth;
        this.waveSpeed = waveSpeed;
    }

    public SineWaveDriftMovement() {
        this(35f, 40f, 3f);
    }

    @Override
    public void move(GameObject item, float delta) {
        if (item == null) return;

        float t = item.getStateTime();
        float dy = -descentSpeed * delta;
        float dx = (float) (Math.cos(t * waveSpeed) * waveWidth * delta);

        item.setX(item.getX() + dx);
        item.setY(item.getY() + dy);
    }

    public float getDescentSpeed() { return descentSpeed; }
    public void setDescentSpeed(float descentSpeed) { this.descentSpeed = descentSpeed; }
    public float getWaveWidth() { return waveWidth; }
    public void setWaveWidth(float waveWidth) { this.waveWidth = waveWidth; }
    public float getWaveSpeed() { return waveSpeed; }
    public void setWaveSpeed(float waveSpeed) { this.waveSpeed = waveSpeed; }
}
