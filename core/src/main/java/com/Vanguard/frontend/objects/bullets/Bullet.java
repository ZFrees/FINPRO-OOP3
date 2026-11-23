package com.Vanguard.frontend.objects.bullets;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.patterns.bulletStrategy.BulletMovementPattern;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int power;
    private float speed;
    private SpriteBatch batch;
    private Texture texture;
    float vx, vy;
    float angle = 90f;
    boolean isPlayerBullet;
    private BulletMovementPattern movementPattern;

    public Bullet(float x, float y, int power, float speed, SpriteBatch batch, BulletType bulletType){
        super(x, y, 8, 8, speed, Color.WHITE);
        this.vx = 0;
        this.vy = speed;
        this.power = power;
        this.speed = speed;
        this.batch = batch;
        this.bulletType = bulletType;
    }

    //no batch, experimenting
    public Bullet(float x, float y, int power, float speed, BulletType bulletType){
        super(x, y, 8, 8, speed, Color.WHITE);
        this.vx = 0;
        this.vy = speed;
        this.power = power;
        this.speed = speed;
        this.batch = new SpriteBatch();
        this.bulletType = bulletType;
    }

    public void init(float x, float y, float vx, float vy, BulletType bulletType, int power, boolean isPlayerBullet) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;

        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        this.speed = (float) Math.hypot(vx, vy);
        this.bulletType = bulletType;
        this.power = power;
        this.isPlayerBullet = isPlayerBullet;

        this.active = true;
        this.stateTime = 0f;
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
    public float getVx() { return vx; }
    public float getVy() { return vy; }
    public float getAngle() { return angle; }

    //setters
    public void setAngle(float angle1) { angle = angle1; }

    public void render(){
        batch.draw(texture, this.x, this.y, this.width, this.height);
    }

    public BulletType getBulletType() {
        return bulletType;
    }

}
