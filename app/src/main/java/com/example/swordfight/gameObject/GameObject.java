package com.example.swordfight.gameObject;

import android.graphics.Canvas;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.Animator;
import com.example.swordfight.graphics.SpriteSheet;

/*
GameObject is an abstract class that acts as foundation for all objects in the game
 */

public abstract class GameObject {
    private Vector2 startingLocation = new Vector2(0,0);
    public Vector2 position;
    public Vector2 velocity = new Vector2(0, 0);
    public Vector2 direction = new Vector2(1, 0);

    public GameObject(){}
    public GameObject(float positionX, float positionY) {
        position = new Vector2(positionX, positionY);
        startingLocation = position;
    }

    public abstract void draw(Canvas canvas, GameDisplay gameDisplay);

    public abstract void update();

    public float getPositionX() {
        return position.getX();
    }
    public float getPositionY() {
        return position.getY();
    }

    public Vector2 getPosition() {return position;}
    public void setPosition(Vector2 position){this.position = position;}
    public Vector2 getStartingPosition() {return startingLocation;}

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
