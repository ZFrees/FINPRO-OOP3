package com.Vanguard.frontend.objects.enemies;

import com.Vanguard.frontend.objects.EntityShooter;
import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.items.ItemType;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.entityStrategy.EntityMovementPattern;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends EntityShooter {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue = 100;
    protected int collisionCooldown = 1;
    protected long lastcollision = 0;

    protected ItemType itemDrop = null;
    protected boolean damageable = true;
    protected boolean collidable = true;
    protected float shotHitboxWidth = 0f;
    protected float shotHitboxHeight = 0f;

    public Enemy(String name, int maxHp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = maxHp;
        this.maxHp = maxHp;
        this.scoreValue = scoreValue;
        this.collisionCooldown = collisionCooldown;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue, ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
        super(x, y, width, height, 0, color, shootingPattern, movementPattern);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    //setter
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScoreValue(long scoreValue) {
        this.scoreValue = scoreValue;
    }

    //getter
    public int getHp() {
        return this.hp;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public long getScoreValue() {
        return this.scoreValue;
    }

    public int getCollisionCooldown() {
        return this.collisionCooldown;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height, 0, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
        this.collisionCooldown = collisionCooldown;
    }

    public boolean takeDamage(int damage) {
        if (!damageable) {
            return false;
        }

        boolean wasAlive = isAlive();
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        if (wasAlive && this.hp == 0) { //dead
            destroy();
            return true;
        }
        return false;
    }

    public boolean isAlive() {
        return (this.hp > 0);
    }

    public boolean handleCCD() {
        if (this.collisionCooldown != 0) {
            long record = System.currentTimeMillis();
            if ((record - lastcollision) >= collisionCooldown* 1000L) {
                lastcollision = record;
                return true;
            }
        }
        return false;
    }

    public ItemType getItemDrop() { return itemDrop; }
    public void setItemDrop(ItemType itemDrop) { this.itemDrop = itemDrop; }

    public boolean isDamageable() { return damageable; }
    public void setDamageable(boolean damageable) { this.damageable = damageable; }

    public boolean isCollidable() { return collidable; }
    public void setCollidable(boolean collidable) { this.collidable = collidable; }

    public Polygon getShotHitbox() {
        if (this.shotHitbox == null) {
            return getCoreHitbox();
        }
        return this.shotHitbox;
    }

    public void setShotHitboxSize(float width, float height) {
        this.shotHitboxWidth = width;
        this.shotHitboxHeight = height;
    }
}
