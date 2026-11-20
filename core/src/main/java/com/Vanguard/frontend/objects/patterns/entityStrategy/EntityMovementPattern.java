package com.Vanguard.frontend.objects.patterns.entityStrategy;

import com.Vanguard.frontend.objects.GameObject;

public interface EntityMovementPattern {
    void move(GameObject entity, float delta);
}
