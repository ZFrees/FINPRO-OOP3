package com.Vanguard.frontend.objects;

import com.Vanguard.frontend.Special;
import com.Vanguard.frontend.objects.patterns.ShootingPattern;
import com.Vanguard.frontend.objects.patterns.shootingStrategy.SpreadShot;
import com.Vanguard.frontend.systems.BulletManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public class Player extends EntityShooter{
    private int hp;
    private int power;
    private int score;
    private float lastBullet;
    private float lastSpecial;
    private int items;
    private Texture bulletTexture;
    private Texture specialTexture;
    private Texture specialExplosionTexture;

    public static final int MAX_LEVEL = 10;
    private static final float MAX_SPREAD_DEG = 120f;
    private static final float BULLET_SPEED = 800f;
    private static final int BULLET_DAMAGE = 10;
    private static final float FIRE_INTERVAL = 0.15f;

    private static final float STRAIGHT_SPEED = 800f;
    private static final float STRAIGHT_SPACING = 3f;
    private static final String STRAIGHT_SPRITE = "straight";

    public static final int WEAPON_SPRAY = 0;
    public static final int WEAPON_STRAIGHT = 1;
    private int weapon = WEAPON_SPRAY;

    private final ShootingPattern[][] patterns = buildPatterns();

    private static ShootingPattern[][] buildPatterns() {
        ShootingPattern[][] result = new ShootingPattern[2][MAX_LEVEL];
        for (int level = 1; level <= MAX_LEVEL; level++) {
            // Spray: 1, 3, 5 ... 19 bullets fanned up to 120 degrees
            float spread = MAX_SPREAD_DEG * (level - 1) / (MAX_LEVEL - 1);
            result[WEAPON_SPRAY][level - 1] =
                new SpreadShot(BULLET_SPEED, 2 * level - 1, spread, BULLET_DAMAGE);

            // Straight: 1 ... 10 parallel bullets, all going up, using bullet_straight.png
            result[WEAPON_STRAIGHT][level - 1] =
                new SpreadShot(STRAIGHT_SPEED, level, 0f, BULLET_DAMAGE * 2, //stronger damage for straight bullet
                    STRAIGHT_SPACING, null, null, STRAIGHT_SPRITE);
        }
        return result;
    }

    public Player(int hp, int power, float speed, SpriteBatch batch, Texture texture,
                  Texture bulletTexture, Texture specialTexture, Texture specialExplosionTexture){
        super(280, 50, texture.getWidth(), texture.getHeight(), speed, batch, texture, null, null);
        this.hp = hp;
        this.power = clampLevel(power);
        this.bulletTexture = bulletTexture;
        this.specialTexture = specialTexture;
        this.specialExplosionTexture = specialExplosionTexture;
    }

    public void input(float delta){
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))    y += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))  y -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))  x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += speed * delta;

        x = Math.max(20, Math.min(Gdx.graphics.getWidth()  - 20 - width,  x));
        y = Math.max(20, Math.min(Gdx.graphics.getHeight() - 20 - height, y));
    }

    public void shootBullet(BulletManager bulletManager){
        lastBullet += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.Z) && lastBullet >= FIRE_INTERVAL) {
            lastBullet = 0;
            float originX = this.x + width / 2f;
            float originY = this.y + height;
            patterns[weapon][power - 1].execute(originX, originY, bulletManager, true);
        }
    }

    public Special shootSpecial(){
        lastSpecial += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if (lastSpecial >= 3f) {
                lastSpecial = 0;
                return new Special(this.power, 60f, this.x + (texture.getWidth()/2f), this.y + texture.getHeight(), batch, specialTexture, specialExplosionTexture);
            }
        }
        return null;
    }

    public void switchWeapon() {
        weapon = (weapon == WEAPON_SPRAY) ? WEAPON_STRAIGHT : WEAPON_SPRAY; }
    public int getWeapon() {
        return weapon;
    }

    public boolean takeDamage(int damage) {
        if (!active) return false;
        hp = Math.max(0, hp - damage);
        if (hp == 0) {
            destroy();
            return true;   //if player is dead
        }
        return false;
    }

    public boolean isAlive() { return hp > 0; }

    @Override
    public void update(float delta) {
        super.update(delta);
        input(delta);
    }

    public int getHp() { return hp; }
    public int getPower() { return power; }
    public void setPower(int power) { this.power = clampLevel(power); }
    private static int clampLevel(int p) { return Math.max(1, Math.min(MAX_LEVEL, p)); }
    public float getSpeed() { return speed; }
    public int getScore() { return score; }
    public void addHp(int hp){ this.hp += hp; }
    public void addScore(int score){ this.score += score; }
}
