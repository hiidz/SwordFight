package com.example.swordfight.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Player;
import com.example.swordfight.gamepanel.Joystick;

public class Animator {
    private Sprite[] playerSpriteArray;
    private int idxNotMovingFrame = 0;
    private int idxMovingFrame = 1;
    private int updatesBeforeNextMoveFrame;
    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5;

    private static float angle = 0f;
    private float previousAngle = 0f;

    public Animator(Sprite[] playerSpriteArray) {
        this.playerSpriteArray = playerSpriteArray;
        updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player, Joystick joystick) {
        switch (player.getPlayerState().getState()) {
            case NOT_MOVING:
                drawRotatedFrame(canvas, gameDisplay, player, playerSpriteArray[0], angle);
                break;
            case IS_MOVING:
                updatesBeforeNextMoveFrame--;
                if (updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    idxMovingFrame = (++idxMovingFrame % 3) + 1;
                }
                float joystickX = (float) joystick.getActuatorX();
                float joystickY = (float) joystick.getActuatorY();
                if (joystickX != 0f || joystickY != 0f) {
                    angle = (float) Math.atan2(joystickY, joystickX);
                    angle = (float) Math.toDegrees(angle) - 90f;
                    previousAngle = angle;
                } else {
                    angle = previousAngle;
                }
                drawRotatedFrame(canvas, gameDisplay, player, playerSpriteArray[idxMovingFrame], angle);
                break;
            default:
                break;
        }
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth() / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight() / 2
        );
    }

    public void drawRotatedFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite, float angle) {
        sprite.drawRotatedAngle(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth() / 2,
                (int) gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight() / 2,
                angle
        );
    }
}
