package com.Vanguard.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Special {
    private int power;
    private float speed;
    private float x;
    private float y;

    private SpriteBatch batch;
    private Texture texture;

    public Special(int power, float speed, float x, float y, SpriteBatch batch, Texture texture){
        this.power = power;
        this.speed = speed;
        this.x = x - (texture.getWidth() / 2f) - 15;
        this.y = y;
        this.batch = batch;
        this.texture = texture;
    }

    public void move(){
        this.y += speed * Gdx.graphics.getDeltaTime();
    }

    public void render(){
        this.move();
        batch.draw(texture, this.x, this.y);
    }

    public float getY() {
        return y;
    }
}
