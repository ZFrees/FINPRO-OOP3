package com.Vanguard.frontend.objects.enemies;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.entityStrategy.DiveAndBankMovement;
import com.Vanguard.frontend.objects.patterns.entityStrategy.EntityMovementPattern;
import com.Vanguard.frontend.objects.patterns.shootingStrategy.AimedShot;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fighter extends Enemy {
    private static final float BULLET_SPEED = 500f;
    private static final int BULLET_DAMAGE = 10;

    private float shootTimer = 0f;
    private float shootDelay = 0.3f;
    private float shootInterval = 0.95f;
    private int maxShots = 2;
    private int shotsFired = 0;
    private int power = 50; //collision dmg btw not bullet

    //custom pattern
    public Fighter(String name, int hp, SpriteBatch batch, Texture texture, GameObject target, ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
        super(150, 380, 36, 36, batch, texture, name, hp, 500L);
        rotationInit();
        setPower(power);
    }

    //preset pattern
    public Fighter(String name, int hp, SpriteBatch batch, Texture texture, GameObject target) {
        super(150, 380, 36, 36, batch, texture, name, hp, 500L);
        rotationInit();
        this.setEntityMovementPattern(new DiveAndBankMovement(120f, 270f, 1.0f, 90f, 1.0f));
        this.setShootingPattern(new AimedShot(BULLET_SPEED, BULLET_DAMAGE, target));
        setPower(power);
    }

    //custom pattern AND position
    public Fighter(float x, float y, String name, int hp, SpriteBatch batch, Texture texture, GameObject target, ShootingPattern shootingPattern, EntityMovementPattern movementPattern) {
        super(x, y, 36, 36, batch, texture, name, hp, 500L);
        rotationInit();
        setPower(power);
    }

    //preset pattern AND position
    public Fighter(float x, float y, String name, int hp, SpriteBatch batch, Texture texture, GameObject target) {
        super(x, y, 36, 36, batch, texture, name, hp, 500L);
        rotationInit();
        this.setEntityMovementPattern(new DiveAndBankMovement(100f, 270f, 0.5f, 90f, 1.0f));
        this.setShootingPattern(new AimedShot(BULLET_SPEED, BULLET_DAMAGE, target));
        setPower(power);
    }

    public void rotationInit() {
        setRotateToHeading(true, 90f);
    }

    public boolean canShoot(float delta) {
        shootTimer += delta;
        if (shotsFired == 0) {
            if (shootTimer >= shootDelay) {
                shotsFired++;
                shootTimer = 0f;
                return true;
            }
        } else if (shotsFired < maxShots) {
            if (shootTimer >= shootInterval) {
                shotsFired++;
                shootTimer = 0f;
                return true;
            }
        }
        return false;
    }

    public void setShootTiming(float shootDelay, float shootInterval, int maxShots) {
        this.shootDelay = shootDelay;
        this.shootInterval = shootInterval;
        this.maxShots = maxShots;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getShotsFired() { return shotsFired; }
    public float getShootTimer() { return shootTimer; }
    public float getShootDelay() { return shootDelay; }
    public float getShootInterval() { return shootInterval; }
    public int getMaxShots() { return maxShots; }
}
