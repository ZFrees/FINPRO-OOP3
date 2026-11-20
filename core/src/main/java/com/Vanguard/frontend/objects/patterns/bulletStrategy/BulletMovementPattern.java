package com.Vanguard.frontend.objects.patterns.bulletStrategy;

import com.Vanguard.frontend.objects.bullets.Bullet;

public interface BulletMovementPattern {
    void move(Bullet bullet, float delta);
}
