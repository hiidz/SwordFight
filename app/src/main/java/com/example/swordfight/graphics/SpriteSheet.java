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
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_sprite_sheet, bitmapOptions);
    }

    public Sprite[] getPlayerSpriteArray() {
        Sprite[] spriteArray = new Sprite[9];
        spriteArray[0] = new Sprite(this, new Rect(0, 11*64, 64, 12*64));
        spriteArray[1] = new Sprite(this, new Rect(64, 11*64, 2*64, 12*64));
        spriteArray[2] = new Sprite(this, new Rect(2*64, 11*64, 3*64, 12*64));
        spriteArray[3] = new Sprite(this, new Rect(3*64, 11*64, 4*64, 12*64));
        spriteArray[4] = new Sprite(this, new Rect(4*64, 11*64, 5*64, 12*64));
        spriteArray[5] = new Sprite(this, new Rect(5*64, 11*64, 6*64, 12*64));
        spriteArray[6] = new Sprite(this, new Rect(6*64, 11*64, 7*64, 12*64));
        spriteArray[7] = new Sprite(this, new Rect(7*64, 11*64, 8*64, 12*64));
        spriteArray[8] = new Sprite(this, new Rect(8*64, 11*64, 9*64, 12*64));
        return spriteArray;
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


    public Sprite getPlayerSprite() {
//        Sprite[] spriteArray = new Sprite[3];
//        spriteArray[0] = new Sprite(this, new Rect(0*64, 0, 1*64, 64));
//        spriteArray[1] = new Sprite(this, new Rect(1*64, 0, 2*64, 64));
//        spriteArray[2] = new Sprite(this, new Rect(2*64, 0, 3*64, 64));
//        return spriteArray;
        return new Sprite(this, new Rect(0, 0, 64, 64));
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
        return getSpriteByIndex(1, 2);
    }
}
