package com.Vanguard.frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Special {
    private enum State { FLYING, EXPLODING, DONE }

    private static final float FUSE_TIME = 1.5f;             // seconds flying before it explodes
    private static final float EXPLOSION_TIME = 0.6f;        // seconds the explosion lasts
    private static final float EXPLOSION_START_SCALE = 0.3f; // 135 px * 0.3 = ~40 px
    private static final float EXPLOSION_END_SCALE = 1.5f;   // 135 px * 1.5 = ~200 px

    private int damage;
    private float speed;
    private float x;
    private float y;

    private SpriteBatch batch;
    private Texture texture;
    private Texture explosionTexture;

    private State state = State.FLYING;
    private float timer = 0f;
    private float explosionX;   // center of the explosion
    private float explosionY;

    public Special(int damage, float speed, float x, float y, SpriteBatch batch,
                   Texture texture, Texture explosionTexture){
        this.damage = damage;
        this.speed = speed;
        this.x = x - (texture.getWidth() / 2f) - 15;
        this.y = y;
        this.batch = batch;
        this.texture = texture;
        this.explosionTexture = explosionTexture;
    }

    public void update(float delta){
        timer += delta;

        switch (state) {
            case FLYING:
                y += speed * delta;
                if (timer >= FUSE_TIME) {
                    // explode from the middle of the special, then stop drawing dih.png
                    explosionX = x + texture.getWidth() / 2f;
                    explosionY = y + texture.getHeight() / 2f;
                    state = State.EXPLODING;
                    timer = 0f;
                }
                break;
            case EXPLODING:
                if (timer >= EXPLOSION_TIME) state = State.DONE;
                break;
            case DONE:
                break;
        }
    }

    public void render(){
        update(Gdx.graphics.getDeltaTime());

        if (state == State.FLYING) {
            batch.draw(texture, x, y);
        } else if (state == State.EXPLODING) {
            float t = Math.min(1f, timer / EXPLOSION_TIME);   // 0 -> 1
            float scale = EXPLOSION_START_SCALE + (EXPLOSION_END_SCALE - EXPLOSION_START_SCALE) * t;
            float w = explosionTexture.getWidth() * scale;
            float h = explosionTexture.getHeight() * scale;

            batch.setColor(1f, 1f, 1f, 1f - t);               // fade out
            batch.draw(explosionTexture, explosionX - w / 2f, explosionY - h / 2f, w, h);
            batch.setColor(1f, 1f, 1f, 1f);                   // reset so other sprites aren't faded
        }
    }

    public boolean isFinished() { return state == State.DONE; }
    public boolean isExploding() { return state == State.EXPLODING; }

    /** Current explosion radius in px (for collision later). 0 when not exploding. */
    public float getExplosionRadius() {
        if (state != State.EXPLODING) return 0f;
        float t = Math.min(1f, timer / EXPLOSION_TIME);
        float scale = EXPLOSION_START_SCALE + (EXPLOSION_END_SCALE - EXPLOSION_START_SCALE) * t;
        return explosionTexture.getWidth() * scale / 2f;
    }

    public float getY() {
        return y;
    }
    public int getDamage() { return damage; }
}
