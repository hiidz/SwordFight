package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.Animator;
import com.example.swordfight.graphics.SpriteSheet;

/*
 * Piece is an abstract class, extended by the GameObject,
 * which implements the draw method for drawing the Piece
 * onto the tileset/screen.
 * */

public abstract class Piece extends GameObject{
    protected float radius;
    protected Paint paint;
    protected int maxHealth;
    protected int currentHealth;
    protected SpriteSheet spriteSheet;
    protected Animator animator;

    protected Vector2 velocity = new Vector2(0, 0);

    public Piece(Context context, int color, float positionX, float positionY, float radius, int maxHealth) {
        super(positionX, positionY);

        this.radius = radius;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        // Set color of Piece
        paint = new Paint();
        paint.setColor(color);
    }

    public Piece(){}

    public static boolean isColliding(Piece obj1, Piece obj2) {
        double distance = obj1.getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();

        if (distance < distanceToCollision) {
            return true;
        }
        return false;
    }

    public void setDamageDealt(int damageDealt) {
        if (currentHealth > 0) {
            this.currentHealth -= damageDealt;
        }
    }

    public float getRadius() {
        return radius;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(position.getX()),
                (float) gameDisplay.gameToDisplayCoordinatesY(position.getY()),
                (float) radius,
                paint);
    }

    public int getHealthPoints() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    //get velocity
    public Vector2 getVelocity() {
        return velocity;
    }
}
