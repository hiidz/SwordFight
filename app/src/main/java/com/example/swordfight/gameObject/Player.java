package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;
import com.example.swordfight.gamepanel.HealthBar;
import com.example.swordfight.gamepanel.Joystick;
import com.example.swordfight.R;
import com.example.swordfight.Utils;
import com.example.swordfight.graphics.Animator;
import com.example.swordfight.graphics.Sprite;
import com.example.swordfight.graphics.SpriteSheet;
import com.example.swordfight.map.MapLayout;

/*
* Player is the main character of the game, that can be controlled via a joystick
* The player class is an extension of a Piece, which is an extension of a GameObject
*/

public class Player extends Piece{
//    private static double SPEED_PIXELS_PER_SECOND = 400;
    private static final float MAX_SPEED = 12;

    private Vector2 velocity = new Vector2(0, 0);

    private Joystick joystick;
    private HealthBar healthBar;
    private MapLayout maplayout;


    private PlayerState playerState;

    public Player(Context context, Joystick joystick, float positionX, float positionY, float radius, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius, maxHealth);
        this.joystick = joystick;
        this.healthBar = new HealthBar(context,this);
        this.playerState = new PlayerState(this);
        this.spriteSheet = new SpriteSheet(context);
        this.animator = new Animator(spriteSheet.getSpriteArray(9, 11), spriteSheet.getSpriteArray(9, 9));
    }

    public void update() {
        float velocityX = (float)joystick.getActuatorX()*MAX_SPEED;
        float velocityY = (float)joystick.getActuatorY()*MAX_SPEED;
        if (this.getPositionX() > maplayout.TILE_WIDTH_PIXELS * maplayout.NUMBER_OF_ROW_TILES) {
            this.getPosition().setX(maplayout.TILE_WIDTH_PIXELS * maplayout.NUMBER_OF_ROW_TILES);
        }
        if(this.getPositionX() < 0) {
            this.getPosition().setX(0);
        }
        if (this.getPositionY() > maplayout.TILE_WIDTH_PIXELS * maplayout.NUMBER_OF_ROW_TILES) {
            this.getPosition().setY(maplayout.TILE_WIDTH_PIXELS * maplayout.NUMBER_OF_ROW_TILES);
        }
        if(this.getPositionY() < 0) {
            this.getPosition().setY(0);
        }
        // Update velocity based on actuator of joystick
        velocity.set(velocityX, velocityY);

        // Update position
        position = position.add(velocity);


        // Update direction
        if (velocity.getX() != 0 || velocity.getY() != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            float distance = (float)Utils.getDistanceBetweenPoints(0.0f, 0.0f, velocity.getX(), velocity.getY());
            direction.set(velocity.getX()/distance, velocity.getY()/distance);
        }

        playerState.update();
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
//        sprite.draw(canvas, (int) gameDisplay.gameToDisplayCoordinatesX(getPositionX()) - sprite.getWidth()/2, (int) gameDisplay.gameToDisplayCoordinatesY(getPositionY()) - sprite.getHeight()/2);
        animator.draw(canvas, gameDisplay, this, joystick);
        healthBar.draw(canvas, gameDisplay);
    }

    //get velocity
    public Vector2 getVelocity() {
        return velocity;
    }

    //get playerstate
    public PlayerState getPlayerState() {
        return playerState;
    }

}
