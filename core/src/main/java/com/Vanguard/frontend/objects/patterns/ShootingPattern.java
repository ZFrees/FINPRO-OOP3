package com.Vanguard.frontend.objects.patterns;

import com.Vanguard.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
