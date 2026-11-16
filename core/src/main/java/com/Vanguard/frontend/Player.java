package com.Vanguard.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int hp;
    private int power;
    private float speed;
    private int score;
    private float x;
    private float y;
    private float lastBullet;
    private float lastSpecial;

    private SpriteBatch batch;
    private Texture texture;
    private Texture bulletTexture;
    private Texture specialTexture;

    public Player(int hp, int power, float speed, SpriteBatch batch, Texture texture, Texture bulletTexture, Texture specialTexture){
        this.hp = hp;
        this.power = power;
        this.speed = speed;
        this.batch = batch;
        this.texture = texture;
        this.bulletTexture = bulletTexture;
        this.specialTexture = specialTexture;

        this.x = Gdx.graphics.getWidth() / 2f;
        this.y = 50;
    }

    public void input(){
        float delta = Gdx.graphics.getDeltaTime();
        if (Gdx.input != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.y += speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.y -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.x -= speed * delta;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.x += speed * delta;
            }
        }

        float minX = 20;
        float maxX = Gdx.graphics.getWidth() - 20 - texture.getWidth();
        float minY = 20;
        float maxY = Gdx.graphics.getHeight() - 20 - texture.getHeight();

        this.x = Math.max(minX, Math.min(maxX, this.x));
        this.y = Math.max(minY, Math.min(maxY, this.y));
    }

    public Bullet shootBullet(){
        lastBullet += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            if (lastBullet >= 0.3f) {
                lastBullet = 0;
                return new Bullet(this.power, 600f, this.x + (texture.getWidth()/2f), this.y + texture.getHeight(), batch, bulletTexture);
            }
        }
        return null;
    }

    public Special shootSpecial(){
        lastSpecial += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if (lastSpecial >= 3f) {
                lastSpecial = 0;
                return new Special(this.power, 60f, this.x + (texture.getWidth()/2f), this.y + texture.getHeight(), batch, specialTexture);
            }
        }
        return null;
    }

    public void render(){
        this.input();
        batch.draw(texture, this.x, this.y);
    }

    public int getHp() { return hp; }
    public int getPower() { return power; }
    public float getSpeed() { return speed; }
    public int getScore() { return score; }
    public void addHp(int hp){ this.hp += hp; }
    public void addScore(int score){ this.score += score; }
}
