package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.BossOrb;
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.Projectile;

public class OrbAnimator {

    private Sprite[] orbSpriteArray;

    private int index = 0;
    private long lastUpdateTime;
    private static final long ANIMATION_DELAY = 200; // 100 milliseconds delay between frames

    private float scalingFactor = 0.5f; // Adjust this value to scale the sprite (0.5 = 50% size)

    public OrbAnimator(Sprite[] orbSpriteArray) {
        this.orbSpriteArray = orbSpriteArray;
        lastUpdateTime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Projectile projectile) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= ANIMATION_DELAY) {
            lastUpdateTime = currentTime;
            index = (++index % 5);
        }
        drawFrame(canvas, gameDisplay, projectile, orbSpriteArray[index]);
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Projectile projectile, Sprite sprite) {
        int scaledWidth = (int) (sprite.getWidth() * scalingFactor);
        int scaledHeight = (int) (sprite.getHeight() * scalingFactor);

        sprite.drawScaled(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(projectile.getPositionX()) - scaledWidth / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(projectile.getPositionY()) - scaledHeight / 2,
                scaledWidth,
                scaledHeight
        );

    }
}
