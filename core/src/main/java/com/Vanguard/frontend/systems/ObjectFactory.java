package com.Vanguard.frontend.systems;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.bullets.BulletType;
import com.Vanguard.frontend.objects.items.Item;
import com.Vanguard.frontend.objects.items.ItemType;
import com.Vanguard.frontend.objects.Collidable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObjectFactory {
    public static Player spawnPlayer(float x, float y, int hp, int power, int items, float speed, SpriteBatch batch){
        Player player = new Player(x, y, hp, power, items, speed, batch);
        return player;

    }

    //Speed only
    public static Bullet spawnEnemyBullet(float x, float y, int power, float speed, BulletType bulletType){
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
        Bullet bullet = new Bullet(x, y, power, speed, bulletType.BRATHA);
        bullet.setSprite(sprite);
        return bullet;
    }

    public static Bullet spawnPlayerBullet(float x, float y, int power, float speed, BulletType bulletType){
        Bullet bullet = new Bullet(x, y, power, speed, bulletType.VULCAN);
        return bullet;
    }

    public static Item spawnItem(float x, float y, float speed, SpriteBatch batch, ItemType itemType){
        Item item = new Item(x, y, speed, batch, itemType.SHOTPATT);
        return item;
    }
}
