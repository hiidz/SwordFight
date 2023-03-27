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

    private float scalingFactor = 0.5f; // Adjust this value to scale the sprite (0.5 = 50% size)

    public OrbAnimator(Sprite[] orbSpriteArray) {
        super(orbSpriteArray);
        lastUpdateTime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject) {
        Projectile projectile = (Projectile) gameObject;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= ANIMATION_DELAY) {
            lastUpdateTime = currentTime;
            index = (++index % 5);
        }
        drawScaledFrame(canvas, gameDisplay, projectile, spriteArray[index], scalingFactor);
    }
}
