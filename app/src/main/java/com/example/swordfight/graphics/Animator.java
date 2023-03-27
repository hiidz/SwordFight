package com.example.swordfight.graphics;

import android.graphics.Canvas;

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

    public Animator(Sprite[] spriteArray) {
        this.spriteArray = spriteArray;
        updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject);

    public void drawRotatedFrame(Canvas canvas, GameDisplay gameDisplay, Piece piece, Sprite sprite, float angle) {
        sprite.drawRotatedAngle(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(piece.getPositionX()) - sprite.getWidth() / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(piece.getPositionY()) - sprite.getHeight() / 2,
                angle
        );
    }

    public void drawScaledFrame(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject, Sprite sprite, float scalingFactor) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(gameObject.getPositionX()) - (int) (sprite.getWidth() * scalingFactor) / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(gameObject.getPositionY()) - (int) (sprite.getHeight() * scalingFactor) / 2
        );
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, GameObject gameObject, Sprite sprite) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(gameObject.getPositionX()) - sprite.getWidth() / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(gameObject.getPositionY()) - sprite.getHeight() / 2
        );
    }
}
