package com.Vanguard.frontend.systems;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.bullets.BulletType;
import com.Vanguard.frontend.objects.enemies.Fighter;
import com.Vanguard.frontend.objects.items.Item;
import com.Vanguard.frontend.objects.items.ItemType;
import com.Vanguard.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ObjectFactory {
    private static Player currentPlayer;
    public static Player spawnPlayer(int hp, int power, float speed, SpriteBatch batch, Texture texture,
                                     Texture bulletTexture, Texture specialTexture, Texture specialExplosionTexture) {
        Player player = new Player(hp, power, speed, batch, texture, bulletTexture, specialTexture, specialExplosionTexture);
        currentPlayer = player;
        return player;
    }

    //Speed only
    public static Bullet spawnEnemyBullet(float x, float y, int power, float speed, SpriteBatch batch, Texture texture, BulletType bulletType){
        String key = switch (bulletType) {
            case VULCAN -> "bullet_spray.png";

            //TBA (To be added)
            case LASER -> null;
            case PLASMA -> null;
            case MISSILES -> null;
            case BOMBS -> null;
            case BRATHA -> null;
            case TOPHER -> null;
        };
        Texture sprite = AssetManager.getInstance().loadTexture(key);
        Bullet bullet = new Bullet(x, y, power, speed, batch, texture, bulletType.BRATHA);
        bullet.setSprite(sprite);
        return bullet;
    }

    public static Fighter createFighter(float x, float y, String name, int hp, SpriteBatch batch, Texture texture) {
        Fighter fighter = new Fighter(x, y, name, hp, batch, texture, currentPlayer);
        return fighter;
    }

    public static Bullet spawnPlayerBullet(float x, float y, int power, float speed, SpriteBatch batch, Texture texture, BulletType bulletType){
        Bullet bullet = new Bullet(x, y, power, speed, batch, texture, bulletType.VULCAN);
        return bullet;
    }

    public static Item spawnItem(float x, float y, float speed, SpriteBatch batch, Texture texture, ItemType itemType){
        Item item = new Item(x, y, speed, batch, texture, itemType.SHOTPATT);
        return item;
    }
}
