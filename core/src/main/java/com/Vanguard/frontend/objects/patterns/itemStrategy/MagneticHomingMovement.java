package com.Vanguard.frontend.objects.patterns.itemStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class MagneticHomingMovement implements ItemMovementPattern {
    private GameObject player;
    private float triggerRadius;
    private float magnetSpeed;
    private float defaultFallSpeed;

    public MagneticHomingMovement(GameObject player, float triggerRadius, float magnetSpeed, float defaultFallSpeed) {
        this.player = player;
        this.triggerRadius = triggerRadius;
        this.magnetSpeed = magnetSpeed;
        this.defaultFallSpeed = defaultFallSpeed;
    }

    public MagneticHomingMovement(GameObject player, float triggerRadius, float magnetSpeed) {
        this(player, triggerRadius, magnetSpeed, 40f);
    }

    @Override
    public void move(GameObject item, float delta) {
        if (item == null) return;

        if (player != null && !player.isDestroyed()) {
            float itemCenterX = item.getX() + item.getWidth() / 2f;
            float itemCenterY = item.getY() + item.getHeight() / 2f;
            float playerCenterX = player.getX() + player.getWidth() / 2f;
            float playerCenterY = player.getY() + player.getHeight() / 2f;

            float dx = playerCenterX - itemCenterX;
            float dy = playerCenterY - itemCenterY;
            float distSq = dx * dx + dy * dy;

            if (distSq <= triggerRadius * triggerRadius) {
                float dist = (float) Math.sqrt(distSq);
                if (dist > 1f) {
                    item.setX(item.getX() + (dx / dist) * magnetSpeed * delta);
                    item.setY(item.getY() + (dy / dist) * magnetSpeed * delta);
                }
                return;
            }
        }

        item.setY(item.getY() - defaultFallSpeed * delta);
    }

    public GameObject getPlayer() { return player; }
    public void setPlayer(GameObject player) { this.player = player; }
    public float getTriggerRadius() { return triggerRadius; }
    public void setTriggerRadius(float triggerRadius) { this.triggerRadius = triggerRadius; }
    public float getMagnetSpeed() { return magnetSpeed; }
    public void setMagnetSpeed(float magnetSpeed) { this.magnetSpeed = magnetSpeed; }
    public float getDefaultFallSpeed() { return defaultFallSpeed; }
    public void setDefaultFallSpeed(float defaultFallSpeed) { this.defaultFallSpeed = defaultFallSpeed; }
}
