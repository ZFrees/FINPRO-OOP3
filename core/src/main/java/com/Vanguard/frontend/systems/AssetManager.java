package com.Vanguard.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    private static AssetManager instance;

    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    private AssetManager() {
        textureRegionMap = new HashMap<>();
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        if (textureMap.containsKey(filename)) {
            return textureMap.get(filename);
        }

        if (Gdx.files != null && Gdx.files.internal(filename).exists()) {
            Texture newTexture = new Texture(Gdx.files.internal(filename));
            textureMap.put(filename, newTexture);
            return newTexture;
        }

        return null;
    }

    // ========================================================================
// Register the Region Textures
// ========================================================================

    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int startCol, int numFrames, float frameDuration, Animation.PlayMode playMode) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            TextureRegion[] frames = new TextureRegion[numFrames];
            for (int i = 0; i < numFrames; i++) {
                frames[i] = grid[row][startCol + i];
            }

            Animation<TextureRegion> anim = new Animation<>(frameDuration, frames);
            anim.setPlayMode(playMode);
            animationMap.put(key, anim);

            if (frames.length > 0) {
                textureRegionMap.put(key, frames[0]);
            }
        }
    }

// ========================================================================
// Getting Texture
// ========================================================================

    public Texture getTexture(String filename) {
        return textureMap.get(filename);
    }

    public void init() {
        // List images buat bullet:
        loadTexture("bullet_spray.png");

        // List images for player:
        loadTexture("player.png");

        // List images for bullet:
        loadTexture("bullet_spray.png");
        loadTexture("bullet_straight.png");

        // List images for item:
        loadTexture("item.png");
        loadTexture("vulcan.png");

        // List images for enemy:
        loadTexture("Fighter.png");

        // List images for special:
        loadTexture("dih.png");
        loadTexture("dih_explosion.png");
    }

    public void dispose() {
        for (Texture texture : textureMap.values()) {
            if (texture != null) {
                texture.dispose();
            }
        }
        textureMap.clear();
        animationMap.clear();
        textureRegionMap.clear();

        instance = null;   // next getInstance() builds a fresh manager
    }
}
