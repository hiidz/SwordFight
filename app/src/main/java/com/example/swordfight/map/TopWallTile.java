package com.example.swordfight.map;

import static com.example.swordfight.graphics.SpriteSheet.*;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.swordfight.graphics.Sprite;
import com.example.swordfight.graphics.SpriteSheet;

class TopWallTile extends Tile {
    private final Sprite sprite;

    public TopWallTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getTopWallSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}

