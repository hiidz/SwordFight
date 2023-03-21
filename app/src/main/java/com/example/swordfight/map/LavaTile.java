package com.example.swordfight.map;

import static com.example.swordfight.graphics.SpriteSheet.*;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.swordfight.graphics.Sprite;
import com.example.swordfight.graphics.SpriteSheet;

class LavaTile extends Tile {
    private final Sprite sprite;

    public LavaTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getLavaSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}

