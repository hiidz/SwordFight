package com.example.swordfight.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.swordfight.Utils;
import com.example.swordfight.Vector2;

public class Joystick {

    private Vector2 outerCircleCenterPosition;

    private Vector2 innerCircleCenterPosition;

    private float outerCircleRadius;
    private float innerCircleRadius;

    private Paint innerCirclePaint;
    private Paint outerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed = false;
    private Vector2 actuator = new Vector2(0, 0);

    public Joystick(float centerPositionX, float centerPositionY, float outerCircleRadius, float innerCircleRadius) {
        // Outer and inner circle make up the joystick
        outerCircleCenterPosition = new Vector2(centerPositionX, centerPositionY);
        innerCircleCenterPosition = new Vector2(centerPositionX, centerPositionY);

        // radii of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.BLUE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }
    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                outerCircleCenterPosition.getX(),
                outerCircleCenterPosition.getY(),
                outerCircleRadius,
                outerCirclePaint
        );

        // Draw inner circle
        canvas.drawCircle(
                innerCircleCenterPosition.getX(),
                innerCircleCenterPosition.getY(),
                innerCircleRadius,
                innerCirclePaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPosition.setX(outerCircleCenterPosition.getX() + actuator.getX()*outerCircleRadius);
        innerCircleCenterPosition.setY(outerCircleCenterPosition.getY() + actuator.getY()*outerCircleRadius);
    }

    public boolean isPressed(float touchPositionX, float touchPositionY) {
        joystickCenterToTouchDistance = Utils.getDistanceBetweenPoints(outerCircleCenterPosition.getX(), outerCircleCenterPosition.getY(), touchPositionX, touchPositionY);
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public double getActuatorX() {
        return actuator.getX();
    }

    public double getActuatorY() {
        return actuator.getY();
    }

    public void setActuator(float touchPositionX, float touchPositionY) {
        float deltaX = touchPositionX - outerCircleCenterPosition.getX();
        float deltaY = touchPositionY - outerCircleCenterPosition.getY();
        float deltaDistance = (float) Utils.getDistanceBetweenPoints(0,0, deltaX, deltaY);

        if(deltaDistance < outerCircleRadius) {
            actuator = new Vector2(deltaX/outerCircleRadius, deltaY/outerCircleRadius);
        } else {
            actuator = new Vector2(deltaX/deltaDistance, deltaY/deltaDistance);
        }
    }

    public void resetActuator() {
        actuator = new Vector2(0,0);
    }
}
