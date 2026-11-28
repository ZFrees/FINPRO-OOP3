package com.Vanguard.frontend.objects;

import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.entityStrategy.EntityMovementPattern;
import com.Vanguard.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class EntityShooter extends GameObject {
    protected ShootingPattern shootingPattern;
    protected EntityMovementPattern movementPattern;

    //for rotation
    protected boolean rotateToHeading = false;
    protected float spriteFacing = 90f;

    //custom pattern
    public EntityShooter(float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture, ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
        super(x, y, width, height, speed, batch, texture);
        setPatterns(shootingPattern, movementPattern);
    }

    //preset pattern
    public EntityShooter(float x, float y, float width, float height, float speed, SpriteBatch batch, Texture texture) {
        super(x, y, width, height, speed, batch, texture);
    }

    private void setPatterns(ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
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
            shootingPattern.execute(x + width / 2f, y + (isPlayer ? height : 0f), bulletManager, isPlayer);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        float oldX = x, oldY = y;
        updateMovement(delta);

        float dx = x - oldX, dy = y - oldY;
        if (rotateToHeading && (dx != 0f || dy != 0f)) {
            float heading = (float) Math.toDegrees(Math.atan2(dy, dx));
            rotation = heading - spriteFacing;
        }
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

    public void setRotateToHeading(boolean rotateToHeading, float spriteFacing) {
        this.rotateToHeading = rotateToHeading;
        this.spriteFacing = spriteFacing;
    }
}
