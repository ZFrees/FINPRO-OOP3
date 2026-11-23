package com.Vanguard.frontend.systems;

import com.Vanguard.frontend.objects.Player;
import com.Vanguard.frontend.objects.items.Item;
import com.Vanguard.frontend.objects.items.ItemType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ObjectFactory {
    public static Player spawnPlayer(float x, float y, int hp, int power, int items, float speed, SpriteBatch batch){
        return new Player(x, y, hp, power, items, speed, batch);
    }

    public static Item spawnItem(float x, float y, float speed, SpriteBatch batch, ItemType itemType){
        return new Item(x, y, speed, batch, itemType.SHOTPATT);
    }
}
