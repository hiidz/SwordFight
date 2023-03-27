package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.Player;
import com.example.swordfight.gamepanel.Joystick;

import java.util.Timer;
import java.util.TimerTask;

public class BossAnimator {

    private Sprite[] bossSpriteArray;

    private int idxNotMovingFrame = 0;
    private int idxMovingFrame = 1;
    private long lastUpdateTime;
    private static final long ANIMATION_DELAY = 200; // 100 milliseconds delay between frames

    public BossAnimator(Sprite[] bossSpriteArray) {
        this.bossSpriteArray = bossSpriteArray;
        lastUpdateTime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Enemy enemy) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdateTime >= ANIMATION_DELAY) {
            lastUpdateTime = currentTime;
            idxMovingFrame = (++idxMovingFrame % 4) + 1;
        }

        switch (enemy.getEnemyState().getState()) {
            case IDLE:
                drawFrame(canvas, gameDisplay, enemy, bossSpriteArray[idxMovingFrame]);
                break;
            case CHASING:
                drawFrame(canvas, gameDisplay, enemy, bossSpriteArray[idxMovingFrame]);
                break;
            default:
                break;
        }
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Enemy enemy, Sprite sprite) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(enemy.getPositionX()) - sprite.getWidth() / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(enemy.getPositionY()) - sprite.getHeight() / 2
        );

    }

}
