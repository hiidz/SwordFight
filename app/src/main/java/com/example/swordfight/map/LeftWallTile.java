package com.example.swordfight.map;

import static com.example.swordfight.graphics.SpriteSheet.*;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.swordfight.graphics.Sprite;
import com.example.swordfight.graphics.SpriteSheet;

class LeftWallTile extends Tile {
    private final Sprite sprite;

    public LeftWallTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getLeftWallSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}

