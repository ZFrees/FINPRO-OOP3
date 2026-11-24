package com.Vanguard.frontend.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    // 1. Instans tunggal untuk Singleton
    private static AssetManager instance;

    // 2. Cache untuk Flyweight Pattern
    private Map<String, TextureRegion> textureRegionMap;
    private Map<String, Animation<TextureRegion>> animationMap;
    private Map<String, Texture> textureMap;

    // Cari tahu: kenapa Constructor AssetManager bersifat *private*?
    private AssetManager() {
        // TODO: Inisialisasi ketiga Map di atas sebagai HashMap kosong
        textureRegionMap = new HashMap<>();
        animationMap = new HashMap<>();
        textureMap = new HashMap<>();
    }

    // Titik akses global untuk mendapatkan instans tunggal
    public static AssetManager getInstance() {
        // TODO: Jika instance masih null, buat instance baru (Lazy Initialization)
        // Kembalikan referensi instance
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public Texture loadTexture(String filename) {
        // TODO:
        // 1. Periksa apakah filename sudah ada di dalam textureMap.
        // 2. Jika belum:
        //    - Pastikan Gdx.files != null dan file tersebut ada via Gdx.files.internal(filename).exists()
        //    - Buat Texture baru: new Texture(Gdx.files.internal(filename))
        //    - Simpan ke dalam textureMap dengan key filename
        //    - Jika file tidak ada / Gdx.files belum siap, kembalikan null
        // 3. Kembalikan Texture yang tersimpan
        if (textureMap.containsKey(filename)) {
            return textureMap.get(filename);
        }

        if (Gdx.files != null && Gdx.files.internal(filename).exists()) {
            // Buat Texture baru
            Texture newTexture = new Texture(Gdx.files.internal(filename));
            // Simpan ke dalam textureMap dengan key filename
            textureMap.put(filename, newTexture);
            return newTexture;
        }

        return null;
    }

    // ========================================================================
// Register the Region Textures
// ========================================================================

    // Mendaftarkan region tunggal
    public void registerRegion(String key, TextureRegion region) {
        textureRegionMap.put(key, region);
    }

    // Memotong satu petak tertentu dari sprite sheet pada koordinat [row][col]
    public void registerRegionFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int col) {
        Texture tex = loadTexture(filename);
        if (tex != null) {
            TextureRegion[][] grid = TextureRegion.split(tex, tileWidth, tileHeight);
            // TODO: Simpan potongan grid[row][col] ke textureRegionMap dengan key ini
            textureRegionMap.put(key, grid[row][col]);
        }
    }

    // Overload praktis: mendaftarkan animasi mulai dari kolom 0 dengan PlayMode.LOOP
    public void registerAnimationFromSheet(String key, String filename, int tileWidth, int tileHeight, int row, int numFrames, float frameDuration) {
        registerAnimationFromSheet(key, filename, tileWidth, tileHeight, row, 0, numFrames, frameDuration, Animation.PlayMode.LOOP);
    }

    // Mendaftarkan serangkaian frame horizontal sebagai objek Animation
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

    // Mengambil region berdasarkan key

    public Texture getTexture(String filename) {
        return textureMap.get(filename);
    }

    public void init() {
        // List images buat bullet:
        loadTexture("bullet_spray.png");
    }

    public void dispose() {
        if (textureMap != null) {
            for (Texture texture : textureMap.values()) {
                if (texture != null) {
                    texture.dispose();
                }
            }
            textureMap.clear();
        }

        if (animationMap != null) {
            animationMap.clear();
        }
        if (textureRegionMap != null) {
            textureRegionMap.clear();
        }
    }
}
