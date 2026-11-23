package com.Vanguard.frontend.objects.items;

import com.Vanguard.frontend.objects.GameObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends GameObject {
    private float speed;
    private float vx;
    private float vy;
    private SpriteBatch batch;
    private ItemType itemType;

    public Item(float x, float y, float speed, SpriteBatch batch, Texture texture, ItemType itemType){
        super(x, y, 8, 8, speed, batch, texture);
        this.vx = vx;
        this.vy = speed;
        this.batch = batch;
        this.itemType = itemType;
    }

    public void update(float delta){
        this.x += vx * delta;
        this.y += vy * delta;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
