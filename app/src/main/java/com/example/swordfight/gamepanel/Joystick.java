package com.example.swordfight.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.swordfight.Utils;

public class Joystick {

    private float outerCircleCenterPositionX;
    private float outerCircleCenterPositionY;
    private float innerCircleCenterPositionX;
    private float innerCircleCenterPositionY;

    private float outerCircleRadius;
    private float innerCircleRadius;

    private Paint innerCirclePaint;
    private Paint outerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed = false;
    private float actuatorX;
    private float actuatorY;

    public Joystick(float centerPositionX, float centerPositionY, float outerCircleRadius, float innerCircleRadius) {
        // Outer and inner circle make up the joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

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
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

        // Draw inner circle
        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX*outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY*outerCircleRadius);
    }

    public boolean isPressed(float touchPositionX, float touchPositionY) {
        joystickCenterToTouchDistance = Utils.getDistanceBetweenPoints(outerCircleCenterPositionX, outerCircleCenterPositionY, touchPositionX, touchPositionY);
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void setActuator(float touchPositionX, float touchPositionY) {
        float deltaX = touchPositionX - outerCircleCenterPositionX;
        float deltaY = touchPositionY - outerCircleCenterPositionY;
        float deltaDistance = (float) Utils.getDistanceBetweenPoints(0,0, deltaX, deltaY);

        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0f;
        actuatorY = 0.0f;
    }
}
