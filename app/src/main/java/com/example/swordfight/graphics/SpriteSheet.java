package com.example.swordfight.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.swordfight.R;
import com.example.swordfight.Utils;

public class SpriteSheet {

    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;
    protected static final int ENEMY_ONE_ROW = 5;
    protected static final int ENEMY_TWO_ROW = 6;
    protected static final int ENEMY_SIZE = 2;
    protected static final int PLAYER_ROW = 4;
    protected static final int PLAYER_SIZE = 4;

    private Bitmap bitmap;

    public SpriteSheet(Context context, int id) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), id, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray() {
        return getSpriteArray(PLAYER_SIZE, PLAYER_ROW);
    }

    public Sprite[] getEnemySpriteArray() {
        return getSpriteArray(ENEMY_SIZE, Utils.randomBetween(ENEMY_ONE_ROW,ENEMY_TWO_ROW + 1));
    }

    public Sprite[] getSpriteArray(int size, int row) {
        Sprite[] spriteArray = new Sprite[size];
        for (int i = 0; i < size; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*SPRITE_WIDTH_PIXELS, row*SPRITE_HEIGHT_PIXELS, (i + 1)*SPRITE_WIDTH_PIXELS, (row + 1)*SPRITE_HEIGHT_PIXELS));
        }
        return spriteArray;
    }

    public Sprite[] getBossSpriteArray(int size, int row) {
        Sprite[] spriteArray = new Sprite[size];
        for (int i = 0; i < size; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*120, row*100, (i + 1)*120, (row + 1)*100));
        }
        return spriteArray;
    }

    public Sprite[] getOrbSpriteArray(int size, int row) {
        Sprite[] spriteArray = new Sprite[size];
        for (int i = 0; i < size; i++) {
            spriteArray[i] = new Sprite(this, new Rect(i*192, row*192, (i + 1)*192, (row + 1)*192));
        }
        return spriteArray;
    }

//    public Sprite[] getSpriteArray(int size, int row) {
//        Sprite[] spriteArray = new Sprite[size];
//        for (int i = 0; i < size; i++) {
//            spriteArray[i] = new Sprite(this, new Rect(i*SPRITE_WIDTH_PIXELS, row*SPRITE_HEIGHT_PIXELS, (i + 1)*SPRITE_WIDTH_PIXELS, (row + 1)*SPRITE_HEIGHT_PIXELS));
//        }
//        return spriteArray;
//    }

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
