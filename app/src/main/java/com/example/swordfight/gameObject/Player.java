package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.Vector2;
import com.example.swordfight.gamepanel.Joystick;
import com.example.swordfight.R;
import com.example.swordfight.Utils;
import com.example.swordfight.graphics.Animator;
import com.example.swordfight.graphics.PlayerAnimator;
import com.example.swordfight.graphics.SpriteSheet;
import com.example.swordfight.map.MapLayout;

/*
* Player is the main character of the game, that can be controlled via a joystick
* The player class is an extension of a Piece, which is an extension of a GameObject
* Player will contain additional MAX_SPEED, Joystick, Animator and PlayerState fields that Piece don't have
*/

public class Player extends Piece{
//    private static double SPEED_PIXELS_PER_SECOND = 400;
    private static final float MAX_SPEED = 12;
    private Joystick joystick;
    private PlayerState playerState;
    private PlayerAnimator playerAnimator;
    private static final float PLAYER_SCALE = 4.0f;

    public Player(Context context, Joystick joystick, float positionX, float positionY, int maxHealth) {
        super(context, positionX, positionY, ContextCompat.getColor(context, R.color.player), maxHealth, PLAYER_SCALE);
        this.joystick = joystick;
        this.playerState = new PlayerState(this);
        this.playerAnimator = new PlayerAnimator(spriteSheet.getPlayerSpriteArray(), PLAYER_SCALE);
        this.spriteSheet = new SpriteSheet(context, R.drawable.javier_spritesheet_1);
    }

    @Override
    public void update() {
        float velocityX = (float)joystick.getActuatorX()*MAX_SPEED;
        float velocityY = (float)joystick.getActuatorY()*MAX_SPEED;

        // Player cant exit right wall
        if (this.getPositionX() > (MapLayout.TILE_WIDTH_PIXELS * MapLayout.NUMBER_OF_ROW_TILES) - MapLayout.TILE_WIDTH_PIXELS) {
            this.getPosition().setX((MapLayout.TILE_WIDTH_PIXELS * MapLayout.NUMBER_OF_ROW_TILES) - MapLayout.TILE_WIDTH_PIXELS);
        }

        // Player cant exit left wall
        if(this.getPositionX() < MapLayout.TILE_WIDTH_PIXELS) {
            this.getPosition().setX(MapLayout.TILE_WIDTH_PIXELS);
        }

        // Player cant exit top wall
        if (this.getPositionY() > (MapLayout.TILE_WIDTH_PIXELS * MapLayout.NUMBER_OF_ROW_TILES) - MapLayout.TILE_HEIGHT_PIXELS) {
            this.getPosition().setY((MapLayout.TILE_WIDTH_PIXELS * MapLayout.NUMBER_OF_ROW_TILES) - MapLayout.TILE_HEIGHT_PIXELS);
        }

        // Player cant exit bottom wall
        if(this.getPositionY() < MapLayout.TILE_HEIGHT_PIXELS) {
            this.getPosition().setY(MapLayout   .TILE_HEIGHT_PIXELS);
        }
        // Update velocity based on actuator of joystick
        setVelocity(new Vector2(velocityX, velocityY));

        // Update position
        setPosition(getPosition().add(getVelocity()));

        // Update direction
        if (getVelocity().getX() != 0 || getVelocity().getY() != 0) {
            // Normalize velocity to get direction (unit vector of velocity)
            float distance = (float)Utils.getDistanceBetweenPoints(0.0f, 0.0f, getVelocityX(), getVelocityY());
            setDirection(new Vector2(getVelocityX()/distance, getVelocityY()/distance));
        }
        playerState.update();
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
//        sprite.draw(canvas, (int) gameDisplay.gameToDisplayCoordinatesX(getPositionX()) - sprite.getWidth()/2, (int) gameDisplay.gameToDisplayCoordinatesY(getPositionY()) - sprite.getHeight()/2);
        playerAnimator.draw(canvas, gameDisplay, this, joystick);
        healthBar.draw(canvas, gameDisplay);
    }

    //get playerState
    public PlayerState getPlayerState() {
        return playerState;
    }

}
