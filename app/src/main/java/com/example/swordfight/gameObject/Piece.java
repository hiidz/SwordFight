package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gamepanel.HealthBar;

/*
 * Piece is a class, extended by the GameObject
 * It will be extended/implemented by Player and Enemy classes
 * All piece class will have an additional health and healthBar fields that gameObjects don't have
 * */

public class Piece extends GameObject{
    private int maxHealth;
    private int currentHealth;
    protected HealthBar healthBar;

    public Piece(){};

    public Piece(Context context, float positionX, float positionY, int color,  float radius, int maxHealth) {
        super(context, positionX, positionY, color, radius);

        this.healthBar = new HealthBar(context, this);
        this.maxHealth = maxHealth;
        setCurrentHealth(maxHealth);
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
//        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(position.getX()),
//                (float) gameDisplay.gameToDisplayCoordinatesY(position.getY()),
//                (float) radius,
//                paint);
    }

    public int getMaxHealth() { return maxHealth; }

    public int getCurrentHealth() { return currentHealth; }
    protected void setCurrentHealth( int healthPoints) { this.currentHealth = healthPoints; }

    //multiply health
    public void multiplyHealth(int multiplier) {
        this.currentHealth *= multiplier;
    }

    public void multiplyMaxHealth(int i) {
        this.maxHealth *= i;
    }
}
