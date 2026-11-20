package com.Vanguard.frontend.objects.patterns.itemStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class BounceAndRestMovement implements ItemMovementPattern {
    private float vx;
    private float vy;
    private float minX;
    private float maxX;
    private float gravity;

    public BounceAndRestMovement(float initialVx, float initialVy, float minX, float maxX, float gravity) {
        this.vx = initialVx;
        this.vy = initialVy;
        this.minX = minX;
        this.maxX = maxX;
        this.gravity = gravity;
    }

    public BounceAndRestMovement(float initialVx, float initialVy, float minX, float maxX) {
        this(initialVx, initialVy, minX, maxX, 20f);
    }

    @Override
    public void move(GameObject item, float delta) {
        if (item == null) return;

        item.setX(item.getX() + vx * delta);
        item.setY(item.getY() + vy * delta);

        if (item.getX() <= minX) {
            vx = Math.abs(vx) * 0.5f;
            item.setX(minX);
        } else if (item.getX() + item.getWidth() >= maxX) {
            vx = -Math.abs(vx) * 0.5f;
            item.setX(maxX - item.getWidth());
        }

        if (vy > 0f) {
            vy = Math.max(0f, vy - gravity * delta);
        } else if (vy < 0f) {
            vy = Math.min(0f, vy + gravity * delta);
        }
    }

    public float getVx() { return vx; }
    public void setVx(float vx) { this.vx = vx; }
    public float getVy() { return vy; }
    public void setVy(float vy) { this.vy = vy; }
    public float getMinX() { return minX; }
    public void setMinX(float minX) { this.minX = minX; }
    public float getMaxX() { return maxX; }
    public void setMaxX(float maxX) { this.maxX = maxX; }
}
