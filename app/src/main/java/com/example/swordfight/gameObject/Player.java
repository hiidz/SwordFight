package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;
import com.example.swordfight.gamepanel.HealthBar;
import com.example.swordfight.gamepanel.Joystick;
import com.example.swordfight.R;
import com.example.swordfight.Utils;
import com.example.swordfight.graphics.Sprite;

/*
* Player is the main character of the game, that can be controlled via a joystick
* The player class is an extension of a Piece, which is an extension of a GameObject
*/

public class Player extends Piece{
//    private static double SPEED_PIXELS_PER_SECOND = 400;
    private static final float MAX_SPEED = 20;
    private double radius;
    private Paint paint;

    private Vector2 velocity;

    private Joystick joystick;
    private HealthBar healthBar;
    private Sprite sprite;

    public Player(Context context, Joystick joystick, float positionX, float positionY, double radius, Sprite sprite, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius, maxHealth);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.sprite = sprite;
    }

    public void update() {
        // Update velocity based on actuator of joystick
        velocity.set((float)joystick.getActuatorX()*MAX_SPEED, (float)joystick.getActuatorY()*MAX_SPEED);

        // Update position
        position.add(velocity);

        // Update direction
        if (velocity.getX() != 0 || velocity.getY() != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            float distance = (float)Utils.getDistanceBetweenPoints(0.0f, 0.0f, velocity.getX(), velocity.getY());
            direction.set(velocity.getX()/distance, velocity.getY()/distance);
        }
    }

    public void setPosition(float positionX, float positionY) {
        position.set(positionX, positionY);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        sprite.draw(canvas, (int) gameDisplay.gameToDisplayCoordinatesX(getPositionX()) - sprite.getWidth()/2, (int) gameDisplay.gameToDisplayCoordinatesY(getPositionY()) - sprite.getHeight()/2);
        healthBar.draw(canvas, gameDisplay);
    }

}
