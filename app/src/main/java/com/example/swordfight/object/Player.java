package com.example.swordfight.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.swordfight.Joystick;
import com.example.swordfight.R;
import com.example.swordfight.Utils;

/*
* Player is the main character of the game, that can be controlled via a joystick
* The player class is an extension of a Piece, which is an extension of a GameObject
*/

public class Player extends Piece{
//    private static double SPEED_PIXELS_PER_SECOND = 400;
    private static final double MAX_SPEED = 20;
    private double radius;
    private Paint paint;
    private double velocityX;
    private double velocityY;
    private Joystick joystick;
    private HealthBar healthBar;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius, maxHealth);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
    }

    public void update() {
        // Update velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        // Update position
        positionX += velocityX;
        positionY += velocityY;

        // Update direction
        if (velocityX != 0 || velocityY != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX/distance;
            directionY = velocityY/distance;
        }
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        healthBar.draw(canvas);
    }
}
