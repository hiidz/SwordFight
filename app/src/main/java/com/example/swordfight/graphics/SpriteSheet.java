package com.example.swordfight.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.swordfight.R;

public class SpriteSheet {

    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;

    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.javier_spritesheet_1, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray() {
        return getSpriteArray(4, 4);
    }

    public Sprite[] getEnemySpriteArray() {
        return getSpriteArray(2, 5);
    }

    public Sprite[] getSpriteArray(int size, int row) {
        Sprite[] spriteArray = new Sprite[size];
        for (int i = 0; i < size; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*SPRITE_WIDTH_PIXELS, row*SPRITE_HEIGHT_PIXELS, (i + 1)*SPRITE_WIDTH_PIXELS, (row + 1)*SPRITE_HEIGHT_PIXELS));
        }
        return spriteArray;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        return new Sprite(this, new Rect(
                idxCol*SPRITE_WIDTH_PIXELS,
                idxRow*SPRITE_HEIGHT_PIXELS,
                (idxCol + 1)*SPRITE_WIDTH_PIXELS,
                (idxRow + 1)*SPRITE_HEIGHT_PIXELS
        ));
    }

    public Sprite getLavaSprite() {
        return getSpriteByIndex(1, 1);
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(1, 0);
    }

    public Sprite getLeftWallSprite() {
        return getSpriteByIndex(2, 3);
    }

    public Sprite getTopWallSprite() {
        return getSpriteByIndex(2, 0);
    }

    public Sprite getBottomWallSprite() {
        return getSpriteByIndex(2, 2);
    }

    public Sprite getRightWallSprite() {
        return getSpriteByIndex(2, 1);
    }
}
