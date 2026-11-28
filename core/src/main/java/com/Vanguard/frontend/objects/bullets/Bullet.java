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
    private float angle = 90f;
    private BulletMovementPattern movementPattern = DEFAULT_MOVEMENT;

    public Bullet(float x, float y, int damage, float speed, SpriteBatch batch, Texture texture, BulletType bulletType){
        super(x, y, 8, 8, speed, batch, texture);
        this.damage = damage;
        this.bulletType = bulletType;
    }

    public void init(float x, float y, float vx, float vy, int damage, boolean playerBullet, Texture texture, BulletType bulletType) {
        this.x = x;
        this.y = y;

        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        this.speed = (float) Math.hypot(vx, vy);
        this.damage = damage;
        this.playerBullet = playerBullet;
        this.bulletType = bulletType;
        this.movementPattern = DEFAULT_MOVEMENT;
        if (texture != null) {
            this.texture = texture;
            this.width = texture.getWidth();
            this.height = texture.getHeight();
            initHitboxes();
        }
        this.stateTime = 0f;
        this.active = true;
    }

    @Override
    public void update(float delta){
        super.update(delta);
        movementPattern.move(this, delta);
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
        if (!active || batch == null || texture == null) return;
        batch.draw(texture,
            x, y,
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
