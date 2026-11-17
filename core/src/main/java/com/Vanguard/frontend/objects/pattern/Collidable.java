package com.Vanguard.frontend.objects.pattern;

import com.badlogic.gdx.math.Polygon;

public interface Collidable {
    Polygon getCoreHitbox();
    void onCollision(Collidable other);
}
