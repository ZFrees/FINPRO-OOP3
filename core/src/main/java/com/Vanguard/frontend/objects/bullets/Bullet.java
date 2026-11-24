package com.Vanguard.frontend.objects.bullets;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.badlogic.gdx.Gdx;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.LinearBulletMovement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject {
    private static final BulletMovementPattern DEFAULT_MOVEMENT = new LinearBulletMovement();
    private BulletType bulletType;
    private int damage;
    private boolean playerBullet;
    private float speed;
    private SpriteBatch batch;
    private Texture texture;
    float vx, vy;
    private float angle = 90f;
    private BulletMovementPattern movementPattern = DEFAULT_MOVEMENT;

    public Bullet(float x, float y, int damage, float speed, SpriteBatch batch, Texture texture, BulletType bulletType){
        super(x, y, 8, 8, speed, batch, texture);
        this.vx = 0;
        this.vy = speed;
        this.damage = damage;
        this.speed = speed;
        this.batch = batch;
        this.texture = texture;
        this.bulletType = bulletType;
    }

//    public Bullet(SpriteBatch batch) {
//        super(0, 0, 8, 8, 0,  batch, texture);
//        this.batch = batch;
//        this.active = false;
//    }

    public void init(float x, float y, float vx, float vy, int damage, boolean playerBullet, Texture texture, BulletType bulletType) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

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
    public void update(float delta){
        this.stateTime += delta;
        if (movementPattern != null) {
            movementPattern.move(this, delta);
        } else {
            this.x += vx * delta;
            this.y += vy * delta;
        }
    }

    //getters
    public float getVx() { return (float) Math.cos(Math.toRadians(angle)) * speed; }
    public float getVy() { return (float) Math.sin(Math.toRadians(angle)) * speed; }
    public float getAngle() { return angle; }
    public int getDamage() { return damage; }
    public boolean isPlayerBullet() { return playerBullet; }

    //setters
    public void setAngle(float angleDegrees) { this.angle = angleDegrees; }

    public void render(){
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

    public BulletType getBulletType() {
        return bulletType;
    }

    public BulletMovementPattern getMovementPattern() { return movementPattern; }
    public void setMovementPattern(BulletMovementPattern pattern) {
        this.movementPattern = (pattern != null) ? pattern : DEFAULT_MOVEMENT;
    }
}
