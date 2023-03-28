package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.BossOrb;
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.GameObject;
import com.example.swordfight.gameObject.Projectile;

public class OrbAnimator extends Animator {
    private int index = 0;
    private long lastUpdateTime;
    private static final long ANIMATION_DELAY = 200; // 100 milliseconds delay between frames

    public OrbAnimator(Sprite[] orbSpriteArray, float scalingFactor) {
        super(orbSpriteArray, scalingFactor);
        lastUpdateTime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject) {
        Projectile projectile = (Projectile) gameObject;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= ANIMATION_DELAY) {
            lastUpdateTime = currentTime;
            index = (++index % SpriteSheet.ORB_SIZE);
        }
        drawScaledFrame(canvas, gameDisplay, projectile, spriteArray[index]);
    }
}
