package com.example.swordfight.gameObject;

import android.graphics.Canvas;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;

/*
GameObject is an abstract class that acts as foundation for all objects in the game
 */

public abstract class GameObject {
    public Vector2 position;

    public Vector2 velocity;

    public Vector2 direction = new Vector2(1, 0);

    public GameObject(float positionX, float positionY) {
        position = new Vector2(positionX, positionY);
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);

    public abstract void update();

    public float getPositionX() {
        return position.getX();
    }
    public float getPositionY() {
        return position.getY();
    }

    public float getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return (float) Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    protected float getDirectionX() {
        return direction.getX();
    }
    protected float getDirectionY() {
        return direction.getY();
    }
}
