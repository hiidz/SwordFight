package com.example.swordfight.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * Piece is an abstract class, extended by the GameObject,
 * which implements the draw method for drawing the Piece
 * onto the tileset/screen.
 * */

public abstract class Piece extends GameObject{
    protected double radius;
    protected Paint paint;

    public Piece(Context context, int color, double positionX, double positionY, double radius) {
        super(positionX, positionY);

        this.radius = radius;

        // Set color of Piece
        paint = new Paint();
        paint.setColor(color);
    }

    public static boolean isColliding(Piece obj1, Piece obj2) {
        double distance = obj1.getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();

        if (distance < distanceToCollision) {
            return true;
        }
        return false;
    }

    public double getRadius() {
        return radius;
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }
}
