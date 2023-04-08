package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.GameObject;
import com.example.swordfight.gameObject.Piece;

public abstract class Animator {

    protected Sprite[] spriteArray;

    protected int idxNotMovingFrame = 0;
    protected int idxMovingFrame = 1;
    protected int updatesBeforeNextMoveFrame;
    protected static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5;

    protected float angle = 0f;
    protected float previousAngle = 0f;
    private float scalingFactor;

    public Animator(Sprite[] spriteArray, float scalingFactor) {
        this.spriteArray = spriteArray;
        this.scalingFactor = scalingFactor;
        updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject, Paint paint);

    public void drawRotatedFrame(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject, Sprite sprite, float angle, Paint paint) {
        sprite.drawRotatedAngle(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(gameObject.getPositionX()) - (int) (sprite.getWidth()),
                (int) gameDisplay.gameToDisplayCoordinatesY(gameObject.getPositionY()) - (int) (sprite.getHeight()),
                angle,
                scalingFactor,
                paint
        );
    }


    public void drawScaledFrame(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject, Sprite sprite) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(gameObject.getPositionX()),
                (int) gameDisplay.gameToDisplayCoordinatesY(gameObject.getPositionY()),
                scalingFactor
        );
    }
}
