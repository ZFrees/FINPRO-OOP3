package com.Vanguard.frontend.objects.patterns.entityStrategy;

import com.Vanguard.frontend.objects.GameObject;

public class BezierMovement implements EntityMovementPattern {
    private final float p0x, p0y;
    private final float p1x, p1y;
    private final float p2x, p2y;
    private final float p3x, p3y;
    private final float duration;
    private float timer = 0f;

    public BezierMovement(float p0x, float p0y, float p1x, float p1y,
                          float p2x, float p2y, float p3x, float p3y, float duration) {
        this.p0x = p0x;
        this.p0y = p0y;
        this.p1x = p1x;
        this.p1y = p1y;
        this.p2x = p2x;
        this.p2y = p2y;
        this.p3x = p3x;
        this.p3y = p3y;
        this.duration = Math.max(0.001f, duration);
    }

    @Override
    public void move(GameObject entity, float delta) {
        if (entity == null) return;

        timer = Math.min(duration, timer + delta);
        float t = timer / duration;
        float u = 1f - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        float x = uuu * p0x + 3f * uu * t * p1x + 3f * u * tt * p2x + ttt * p3x;
        float y = uuu * p0y + 3f * uu * t * p1y + 3f * u * tt * p2y + ttt * p3y;

        entity.setX(x);
        entity.setY(y);
    }

    public boolean isFinished() {
        return timer >= duration;
    }

    public float getDuration() { return duration; }
    public float getTimer() { return timer; }
}
