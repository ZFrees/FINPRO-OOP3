package com.Vanguard.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class GameObject implements Collidable{
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;
    protected boolean active = true;
    protected SpriteBatch batch;
    protected Texture texture;

    protected Polygon coreHitbox;
    protected Polygon shotHitbox;   // what bullets check against

    protected float rotation = 0f;

    public GameObject(float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.batch = batch;
        this.texture = texture;

        initHitboxes();
    }

    protected void initHitboxes() {
        float cw = width / 2f, ch = height / 2f;
        this.coreHitbox = new Polygon(new float[] { 0f, 0f, cw, 0f, cw, ch, 0f, ch });

        this.shotHitbox = new Polygon(new float[] {
            width / 2f,      height * 3f / 4f,
            width / 4f,      height / 4f,
            width * 3f / 4f, height / 4f
        });
        this.shotHitbox.setOrigin(width / 2f, height / 2f);
    }

    public void update(float delta){
        this.stateTime += delta;
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        return this.x < -75f || this.x > screenWidth + 75f || this.y < -75f || this.y > screenHeight + 75f;
    }

    public void render() {
        if (!active || batch == null) return;

        if (animation != null) {
            TextureRegion frame = animation.getKeyFrame(stateTime);
            batch.draw(frame, x, y, width / 2f, height / 2f, width, height, 1f, 1f, rotation);
        } else if (texture != null) {
            batch.draw(texture, x, y, width / 2f, height / 2f, width, height,
                1f, 1f, rotation,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
        }
    }

    public boolean isDestroyed() {
        return !active;
    }

    //overrides
    @Override
    public Polygon getCoreHitbox() {
        coreHitbox.setPosition(x + width / 4f, y + height / 4f);
        return coreHitbox;
    }

    public Polygon getShotHitbox() {
        shotHitbox.setPosition(x, y);
        return shotHitbox;
    }

    @Override
    public void onCollision(Collidable other) {
        //to be overridden
    }

    public void destroy() {
        this.active = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getSpeed() {
        return speed;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public float getRotation() { return rotation; }

    public boolean isActive(){
        return active;
    }

    public void setWidth(float width) {
        if (width > 0) this.width = width;
    }

    public void setHeight(float height) {
        if (height > 0) this.height = height;
    }

    public void setSpeed(float speed) {
        if (speed >= 0) this.speed = speed;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){ this.y = y; }

    public void setRotation(float rotation) { this.rotation = rotation; }

    public void setSprite(Texture sprite) {this.texture = sprite;}

    public void setAnimation(Animation<TextureRegion> animation) {
        if(this.animation != animation){
            this.animation = animation;
            this.stateTime = 0f;
        }
    }

    public void setActive(boolean active) { this.active = active; }

    public void setStateTime(float stateTime) { this.stateTime = stateTime; }
}
