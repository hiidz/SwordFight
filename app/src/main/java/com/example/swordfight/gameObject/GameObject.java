package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.R;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.SpriteSheet;

/*
GameObject is an class that acts as foundation for all objects in the game (bullets, pieces, and possibly environment_objects)
Contains position, velocity, direction fields
Has isColliding() and getDistanceBetweenObjects();
 */

public class GameObject {
    private Vector2 startingLocation = new Vector2(0,0);
    private Vector2 position;
    private Vector2 velocity = new Vector2(0, 0);
    private Vector2 direction = new Vector2(1, 0);
    protected SpriteSheet spriteSheet;
    private float radius = 16;
    private Paint paint;
    protected float scalingFactor = 1;

    public GameObject(){}

    public GameObject(Context context, float positionX, float positionY, int color, float scalingFactor) {
        this(context, positionX, positionY, color);
        this.scalingFactor = scalingFactor;
        setRadius(getRadius() * scalingFactor);
    }

    public GameObject(Context context, float positionX, float positionY, int color) {
        position = new Vector2(positionX, positionY);
        startingLocation = position;

        paint = new Paint();
        paint.setColor(color);
        this.spriteSheet = new SpriteSheet(context, R.drawable.javier_spritesheet_2);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(getPositionX()),
                (float) gameDisplay.gameToDisplayCoordinatesY(getPositionY()),
                (float) radius,
                paint);
    }

    public void update() {};

    protected float getRadius() {
        return radius;
    }
    protected void setRadius(float radius) { this.radius = radius; }

    public float getPositionX() {
        return position.getX();
    }
    public float getPositionY() {
        return position.getY();
    }
    protected Vector2 getPosition() {return position;}
    protected void setPosition(Vector2 position){this.position = position;}

    public float getDirectionX() {
        return direction.getX();
    }
    public float getDirectionY() {
        return direction.getY();
    }
    protected Vector2 getDirection() { return direction; }
    protected void setDirection(Vector2 direction){this.direction = direction; }

    protected Vector2 getStartingPosition() {return startingLocation;}

    public float getVelocityX() { return velocity.getX(); };
    public float getVelocityY() { return velocity.getY(); };
    protected Vector2 getVelocity() { return velocity; }

    protected void setVelocity(Vector2 velocity) { this.velocity = velocity; }

    protected float getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return (float) Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    protected boolean isColliding(GameObject obj1, GameObject obj2) {
        double distance = obj1.getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getRadius() + obj2.getRadius();

        if (distance < distanceToCollision) {
            return true;
        }
        return false;
    }
}
