package com.example.swordfight.graphics;

import android.graphics.Canvas;
import android.util.Log;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.gameObject.Player;
import com.example.swordfight.gamepanel.Joystick;

public class Animator {
    private Sprite[] playerSpriteArrayLeft;
    private Sprite[] playerSpriteArrayRight;
    private int idxNotMovingFrame = 0;
    private int idxMovingFrame = 1;
    private int updatesBeforeNextMoveFrame;
    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 5;

    public Animator(Sprite[] playerSpriteArrayLeft, Sprite[] playerSpriteArrayRight) {
        this.playerSpriteArrayLeft = playerSpriteArrayLeft;
        this.playerSpriteArrayRight = playerSpriteArrayRight;
        updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay, Player player, Joystick joystick) {
        switch (player.getPlayerState().getState()) {
            case NOT_MOVING:
                if(joystick.getActuatorX() < 0) {
                    drawFrame(canvas, gameDisplay, player, playerSpriteArrayRight[0]);
                } else {
                    drawFrame(canvas, gameDisplay, player, playerSpriteArrayLeft[0]);
                }
                break;
            case IS_MOVING:
                updatesBeforeNextMoveFrame--;
                if(updatesBeforeNextMoveFrame == 0) {
                    updatesBeforeNextMoveFrame = MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME;
                    idxMovingFrame = (++idxMovingFrame % 8) + 1;
                }
                if(joystick.getActuatorX() < 0) {
                    drawFrame(canvas, gameDisplay, player, playerSpriteArrayRight[idxMovingFrame]);
                } else {
                    drawFrame(canvas, gameDisplay, player, playerSpriteArrayLeft[idxMovingFrame]);
                }
                break;
            default:
                break;
        }
    }

    public void drawFrame(Canvas canvas, GameDisplay gameDisplay, Player player, Sprite sprite) {
        sprite.draw(
                canvas,
                (int) gameDisplay.gameToDisplayCoordinatesX(player.getPositionX()) - sprite.getWidth()/2,
                (int) gameDisplay.gameToDisplayCoordinatesY(player.getPositionY()) - sprite.getHeight()/2
        );
    }
}
