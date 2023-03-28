package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.OrbAnimator;

public class Projectile extends GameObject {
    private float speed;
    private int damage;
    private float maxX;
    private float maxY;

    private Vector2 slotOffset = new Vector2(0, 0);

    public Projectile() { //Uninitialised for producer-consumer
        super();
    }

    public Projectile(Context context, int color, float positionX, float positionY, Vector2 direction, float speed, int damage) {
        super(context, positionX, positionY, color);
        setDirection(direction);
        this.speed = speed;
        this.damage = 0;
//        maxX = gameDisplay.getWidthPixels();
        maxX = 2560;
//        maxY = gameDisplay.getHeightPixels();
        maxY = 1440;
    }

    @Override
    public void update() {
        setPosition(getPosition().add(getDirection().scale(speed)));
    }

    public boolean isOutOfBounds() {
        return getPositionX() < 0 || getPositionX() > maxX || getPositionY() < 0 || getPositionY() > maxY;
    }

    public int getDamage() {
        return damage;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, OrbAnimator orbAnimator) {
        orbAnimator.draw(canvas, gameDisplay, this);
    }

    //set slot offset
    public void setSlotOffset(Vector2 slotOffset) {
        this.slotOffset = slotOffset;
    }

    public Vector2 getSlotOffset() {
        return slotOffset;
    }
}
