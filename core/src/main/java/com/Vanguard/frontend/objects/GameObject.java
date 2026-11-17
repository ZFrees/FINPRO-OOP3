package com.Vanguard.frontend.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameObject {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float speed;
    protected Color color;
    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;
    protected boolean active = true;

    public GameObject(float x, float y, float width, float height, float speed, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    public void update(float delta){
        this.stateTime += delta;
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        return this.x < -75f || this.x > screenWidth + 75f || this.y < -75f || this.y > screenHeight + 75f;
    }

    public void render(ShapeRenderer shapeRenderer) {
        if(shapeRenderer != null && color != null && active) {
            shapeRenderer.setColor(this.color);
            shapeRenderer.rect(this.x, this.y, this.width, this.height);
        }
    }

    public boolean isDestroyed() {
        if(!this.active){
            return true;
        }
        return false;
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

    public Color getColor() {
        return color;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public float getStateTime() {
        return stateTime;
    }

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

    public void setColor(Color color){
        this.color = color;
    }

    public void setAnimation(Animation<TextureRegion> animation) {
        if(this.animation != animation){
            this.animation = animation;
            this.stateTime = 0f;
        }
    }

    public void setActive(boolean active) { this.active = active; }

    public void setStateTime(float stateTime) { this.stateTime = stateTime; }
}
