package com.Vanguard.frontend.objects.patterns.itemStrategy;

import com.Vanguard.frontend.objects.GameObject;

public interface ItemMovementPattern {
    void move(GameObject item, float delta);
}
