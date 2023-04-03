package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.GameObject;
import com.example.swordfight.gameObject.Piece;

public class BossAnimator extends Animator {

    private long lastUpdateTime;
    private static final long ANIMATION_DELAY = 200; // 100 milliseconds delay between frames

    public BossAnimator(Sprite[] bossSpriteArray, float scalingFactor) {
        super(bossSpriteArray, scalingFactor);
        lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject) {
        Enemy enemy = (Enemy) gameObject;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= ANIMATION_DELAY) {
            lastUpdateTime = currentTime;
            idxMovingFrame = (++idxMovingFrame % (SpriteSheet.BOSS_SIZE - 1)) + 1;
        }

        switch (enemy.getEnemyState().getState()) {
            case IDLE:
                drawScaledFrame(canvas, gameDisplay, enemy, spriteArray[idxNotMovingFrame]);
                break;
            case CHASING:
                drawScaledFrame(canvas, gameDisplay, enemy, spriteArray[idxMovingFrame]);
                break;
            default:
                break;
        }
    }
}
