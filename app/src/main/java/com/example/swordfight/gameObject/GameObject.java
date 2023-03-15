package com.example.swordfight.gameObject;

import android.graphics.Canvas;

/*
GameObject is an abstract class that acts as foundation for all objects in the game
 */

public abstract class GameObject {
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    protected double directionX = 1;
    protected double directionY = 0;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas);

    public abstract void update();

    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }

    protected double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    protected double getDirectionX() {
        return directionX;
    }
    protected double getDirectionY() {
        return directionY;
    }
}
