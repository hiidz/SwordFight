package com.example.swordfight.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.swordfight.R;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite getPlayerSprite() {
//        Sprite[] spriteArray = new Sprite[3];
//        spriteArray[0] = new Sprite(this, new Rect(0*64, 0, 1*64, 64));
//        spriteArray[1] = new Sprite(this, new Rect(1*64, 0, 2*64, 64));
//        spriteArray[2] = new Sprite(this, new Rect(2*64, 0, 3*64, 64));
//        return spriteArray;
        return new Sprite(this, new Rect(0, 0, 64, 64));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
