package com.Vanguard.frontend.objects.items;

import com.Vanguard.frontend.objects.Collidable;
import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.patterns.itemStrategy.FloatingBobbingMovement;
import com.Vanguard.frontend.objects.patterns.itemStrategy.ItemMovementPattern;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends GameObject {
    private float speed;
    private float vx;
    private float vy;
    private SpriteBatch batch;
    private ItemType itemType;
    private ItemMovementPattern movementPattern;
    private float duration = 5f;
    private boolean collected = false;

    public Item(float x, float y, float speed, SpriteBatch batch, Texture texture, ItemType itemType){
        super(x, y, 16, 16, speed, batch, texture);
        this.vx = vx;
        this.vy = -speed;
        this.batch = batch;
        this.itemType = itemType;
    }

    public Item(float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture, ItemType itemType){
        super(x, y, width, height, speed, batch, texture);
        this.vx = vx;
        this.vy = -speed;
        this.itemType = itemType;
        this.movementPattern = createMovementPattern(itemType, speed);
    }


    private ItemMovementPattern createMovementPattern(ItemType type, float speed) {
        if (type == ItemType.SHOTPATT) {
            return new FloatingBobbingMovement(speed, 15f, 4f);
        }
        return new FloatingBobbingMovement(speed, 15f, 4f);
    }

    public void updateMovementStrategy(float delta) {
        if (isDestroyed() || movementPattern == null) {
            return;
        }
        movementPattern.move(this, delta);
    }

    public void update(float delta) {
        if(collected || isDestroyed()){
            return;
        }
        super.update(delta);
        this.x += vx * delta;
        this.y += vy * delta;
        this.duration -= delta;

        if(duration < 0){
            destroy();
        }
    }

    public void updateMovementStrategy(Item item) {
        if(isDestroyed()) return;
        ItemType type = item.getItemType();
        if(type != null){
            switch (type){
                case SHOTPATT -> {

                }
            }
        }
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
        if (movementPattern instanceof FloatingBobbingMovement bobbing) {
            bobbing.setFallSpeed(speed);
        }
    }

    public ItemType getItemType() {
        return itemType;
    }
}
