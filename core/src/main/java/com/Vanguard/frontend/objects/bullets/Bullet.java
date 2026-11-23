package com.Vanguard.frontend.objects.bullets;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.LinearBulletMovement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A pooled projectile. x/y is the CENTER of the bullet.
 * Movement is delegated to a BulletMovementPattern (default: straight line along angle/speed).
 */
public class Bullet extends GameObject {
    private static final BulletMovementPattern DEFAULT_MOVEMENT = new LinearBulletMovement();

    private final SpriteBatch batch;
    private Texture texture;
    private BulletMovementPattern movementPattern = DEFAULT_MOVEMENT;

    private float angle = 90f;      // degrees, 90 = up
    private int damage;
    private boolean playerBullet;

    public Bullet(SpriteBatch batch) {
        super(0, 0, 8, 8, 0, Color.WHITE);
        this.batch = batch;
        this.active = false;
    }

    /** (Re)initialise a new or pooled bullet. Always resets the movement pattern. */
    public void init(float x, float y, float vx, float vy, int damage, boolean playerBullet, Texture texture) {
        this.x = x;
        this.y = y;
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        this.speed = (float) Math.hypot(vx, vy);
        this.damage = damage;
        this.playerBullet = playerBullet;
        this.texture = texture;
        this.movementPattern = DEFAULT_MOVEMENT;
        if (texture != null) {
            this.width = texture.getWidth();
            this.height = texture.getHeight();
        }
        this.stateTime = 0f;
        this.active = true;
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        movementPattern.move(this, delta);
    }

    public void render() {
        if (!active || texture == null) return;
        // Texture is drawn pointing "up" at angle 90, so rotate by (angle - 90).
        batch.draw(texture,
            x - width / 2f, y - height / 2f,
            width / 2f, height / 2f,
            width, height,
            1f, 1f,
            angle - 90f,
            0, 0, texture.getWidth(), texture.getHeight(),
            false, false);
    }

    public float getAngle() { return angle; }
    public void setAngle(float angleDegrees) { this.angle = angleDegrees; }

    public float getVx() { return (float) Math.cos(Math.toRadians(angle)) * speed; }
    public float getVy() { return (float) Math.sin(Math.toRadians(angle)) * speed; }

    public int getDamage() { return damage; }
    public boolean isPlayerBullet() { return playerBullet; }

    public BulletMovementPattern getMovementPattern() { return movementPattern; }
    public void setMovementPattern(BulletMovementPattern pattern) {
        this.movementPattern = (pattern != null) ? pattern : DEFAULT_MOVEMENT;
    }
}
