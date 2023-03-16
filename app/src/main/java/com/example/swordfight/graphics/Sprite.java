package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.swordfight.GameDisplay;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    private static final int PLAYER_SCALE = 2;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }
    public void draw(Canvas canvas, int gameToDisplayCoordinatesX, int gameToDisplayCoordinatesY) {
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(gameToDisplayCoordinatesX, gameToDisplayCoordinatesY, gameToDisplayCoordinatesX+getWidth() * PLAYER_SCALE, gameToDisplayCoordinatesY+getHeight() * PLAYER_SCALE),
                null
        );
    }

    public int getHeight() {
        return rect.height();
    }

    public int getWidth() {
        return rect.width();
    }
}
