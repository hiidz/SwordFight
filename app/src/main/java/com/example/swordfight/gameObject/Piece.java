package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gamepanel.HealthBar;

/*
 * Piece is a class, extended by the GameObject
 * It will be extended/implemented by Player and Enemy classes
 * All piece class will have an additional health and healthBar fields that gameObjects don't have
 * */

public class Piece extends GameObject{
    private int maxHealth;
    private Integer currentHealth;
    protected HealthBar healthBar;

    public Piece(){};

    public Piece(Context context, float positionX, float positionY, int color, int maxHealth, float scalingFactor) {
        super(context, positionX, positionY, color, scalingFactor);

        this.healthBar = new HealthBar(context, this);
        this.maxHealth = maxHealth;
        setCurrentHealth(maxHealth);
        this.currentHealth = maxHealth;
    }

    public Piece(Context context, float positionX, float positionY, int color, int maxHealth) {
        this(context, positionX, positionY, color, maxHealth, 1);
    }

    public void setDamageDealt(int damageDealt) {
        if (currentHealth > 0) {
            this.currentHealth -= damageDealt;
        }
    }

//    public float getRadius() {
//        return radius;
//    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
//        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(position.getX()),
//                (float) gameDisplay.gameToDisplayCoordinatesY(position.getY()),
//                (float) radius,
//                paint);
    }

    public float getScalingFactor() { return scalingFactor; }

    public int getMaxHealth() { return maxHealth; }

    public int getCurrentHealth() { return currentHealth; }
    protected void setCurrentHealth( int healthPoints) {
        this.currentHealth = healthPoints;
    }

    protected void addCurrentHealth(int healthPoints){
        if(this.currentHealth + healthPoints > maxHealth){
            this.currentHealth = maxHealth;
        }else {
            this.currentHealth = healthPoints;
        }
    }

    //multiply health
    public void multiplyHealth(int multiplier) {
        synchronized (this.currentHealth) {
            this.currentHealth *= multiplier;
        }
    }

    public void multiplyMaxHealth(int i) {
        this.maxHealth *= i;
    }
}
