package com.Vanguard.frontend.systems;

import com.Vanguard.frontend.objects.GameObject;
import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.bullets.Bullet;
import com.Vanguard.frontend.objects.bullets.BulletType;
import com.Vanguard.frontend.objects.items.Item;
import com.Vanguard.frontend.objects.items.ItemType;
import com.Vanguard.frontend.objects.pattern.Collidable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ObjectFactory {
    public static Player spawnPlayer(float x, float y, int hp, int power, int items, float speed, SpriteBatch batch){
        Player player = new Player(x, y, hp, power, items, speed, batch);
        return player;

    }

    public static Bullet spawnEnemyBullet(float x, float y, int power, float speed, SpriteBatch batch, BulletType bulletType){
        Bullet bullet = new Bullet(x, y, power, speed, batch, bulletType.BRATHA);
        return bullet;
    }

    public static Bullet spawnPlayerBullet(float x, float y, int power, float speed, SpriteBatch batch, BulletType bulletType){
        Bullet bullet = new Bullet(x, y, power, speed, batch, bulletType.VULCAN);
        return bullet;
    }

    public static Item spawnItem(float x, float y, float speed, SpriteBatch batch, ItemType itemType){
        Item item = new Item(x, y, speed, batch, itemType.SHOTPATT);
        return item;
    }
}
