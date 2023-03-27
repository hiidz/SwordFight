package com.example.swordfight.graphics;

import android.graphics.Canvas;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.Piece;

public class EnemyAnimator extends Animator{

    public EnemyAnimator(Sprite[] enemySpriteArray) {
        super(enemySpriteArray);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Piece piece) {
        Enemy enemy = (Enemy) piece;
        switch (enemy.getEnemyState().getState()) {
            case IDLE:
                drawRotatedFrame(canvas, gameDisplay, enemy, spriteArray[idxNotMovingFrame], angle);
                break;
            case CHASING:
                updatesBeforeNextMoveFrame--;
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    idxMovingFrame = (++idxMovingFrame % 1) + 1;
                }
                float velocityX = enemy.getDirectionX();
                float velocityY = enemy.getDirectionY();
                if (velocityX != 0f || velocityY != 0f) {
                    angle = (float) Math.atan2(velocityY, velocityX);
                    angle = (float) Math.toDegrees(angle) - 90f;
                    previousAngle = angle;
                } else {
                    angle = previousAngle;
                }
                drawRotatedFrame(canvas, gameDisplay, enemy, spriteArray[idxMovingFrame], angle);
                break;
            default:
                break;
        }
    }
}
