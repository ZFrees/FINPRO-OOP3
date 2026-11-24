package com.Vanguard.frontend.objects;

import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.entityStrategy.EntityMovementPattern;
import com.Vanguard.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class EntityShooter extends GameObject {
    protected ShootingPattern shootingPattern;
    protected EntityMovementPattern movementPattern;

    //float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture
    public EntityShooter(float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture) {
        super(x, y, width, height, speed, batch, texture);
    }

    public EntityShooter(float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture, ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
        super(x, y, width, height, speed, batch, texture);
        this.shootingPattern = shootingPattern;
        this.movementPattern = movementPattern;
    }

    public void updateMovement(float delta) {
        if (movementPattern != null && active) {
            movementPattern.move(this, delta);
        }
    }

    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && active && !isDestroyed()) {
            boolean isPlayer = (this instanceof Player);
            shootingPattern.execute(x + width / 2f - 8f, y + (isPlayer ? height : 0f), bulletManager, isPlayer);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        updateMovement(delta);
    }

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public EntityMovementPattern getMovementPattern() {
        return movementPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    public void setEntityMovementPattern(EntityMovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }
}
