package com.example.swordfight.graphics;

import android.graphics.Canvas;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Piece;
import com.example.swordfight.gameObject.Player;
import com.example.swordfight.gamepanel.Joystick;

public class PlayerAnimator extends Animator {

    Joystick joystick;

    public PlayerAnimator(Sprite[] playerSpriteArray) {
        super(playerSpriteArray);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player, Joystick joystick) {
        this.joystick = joystick;
        draw(canvas, gameDisplay, player);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Piece piece) {
        Player player = (Player) piece;
        switch (player.getPlayerState().getState()) {
            case NOT_MOVING:
                drawRotatedFrame(canvas, gameDisplay, player, spriteArray[0], angle);
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
                drawRotatedFrame(canvas, gameDisplay, player, spriteArray[idxMovingFrame], angle);
                break;
            default:
                break;
        }
    }
}
