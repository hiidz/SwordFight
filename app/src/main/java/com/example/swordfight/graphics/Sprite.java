package com.example.swordfight.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Player;

public class Sprite {
    private final SpriteSheet spriteSheet;
    private final Rect rect;

    protected static final int SCALE_BY_ONE = 1;
    protected static final int SCALE_BY_TWO = 2;
    protected static final int SCALE_BY_THREE = 3;
    protected static final int SCALE_BY_FOUR = 4;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int gameToDisplayCoordinatesX, int gameToDisplayCoordinatesY) {
        int scaledWidth = (int) (getWidth());
        int scaledHeight = (int) (getHeight());
        int scaledX = (int) (gameToDisplayCoordinatesX - (scaledWidth - getWidth()) / 2f);
        int scaledY = (int) (gameToDisplayCoordinatesY - (scaledHeight - getHeight()) / 2f);

        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(scaledX, scaledY, scaledX + scaledWidth, scaledY + scaledHeight),
                null
        );
    }

    public void draw(Canvas canvas, int gameToDisplayCoordinatesX, int gameToDisplayCoordinatesY, float scalingFactor) {
        int scaledWidth = (int) (getWidth() * scalingFactor);
        int scaledHeight = (int) (getHeight() * scalingFactor);
        int scaledX = (int) (gameToDisplayCoordinatesX - (scaledWidth - getWidth()) / 2f);
        int scaledY = (int) (gameToDisplayCoordinatesY - (scaledHeight - getHeight()) / 2f);

        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(scaledX, scaledY, scaledX + scaledWidth, scaledY + scaledHeight),
                null
        );
    }

    public void drawRotatedAngle(Canvas canvas, int gameToDisplayCoordinatesX, int gameToDisplayCoordinatesY, float angle, float scalingFactor, Paint paint) {
        int scaledWidth = (int) (getWidth() * scalingFactor);
        int scaledHeight = (int) (getHeight() * scalingFactor);
        int scaledX = (int) (gameToDisplayCoordinatesX - (scaledWidth - getWidth()) / 2f);
        int scaledY = (int) (gameToDisplayCoordinatesY - (scaledHeight - getHeight()) / 2f);

        // Save the canvas state before applying the rotation
        canvas.save();

        // Create a rotation matrix and apply it to the canvas
        Matrix matrix = new Matrix();
        matrix.setRotate(angle, scaledX + scaledWidth / 2f, scaledY + scaledHeight / 2f);
        canvas.setMatrix(matrix);


        // Draw the character with the rotation applied
        canvas.drawBitmap(
                spriteSheet.getBitmap(),
                rect,
                new Rect(scaledX, scaledY, scaledX + scaledWidth, scaledY + scaledHeight),
                paint
        );

        // Restore the canvas state to its original state before the rotation
        canvas.restore();
    }


    public int getHeight() {
        return rect.height();
    }

    public int getWidth() {
        return rect.width();
    }
}
