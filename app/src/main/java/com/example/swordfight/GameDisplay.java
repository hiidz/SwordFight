package com.example.swordfight;

import android.util.Log;

import com.example.swordfight.gameObject.GameObject;

import java.util.Vector;

public class GameDisplay {
    private final int widthPixels;
    private final int heightPixels;

    private Vector2 gameToDisplayCoordinates;

    private Vector2 displayCenter;

    private Vector2 gameCenter;

    private GameObject centerObject;

    public GameDisplay(int widthPixels, int heightPixels, GameObject centerObject) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
//        DISPLAY_RECT = new Rect(0, 0, widthPixels, heightPixels);

        this.centerObject = centerObject;
        displayCenter = new Vector2((float) (widthPixels/2.0), (float) (heightPixels/2.0));

        update();
    }

    public void update() {
        gameCenter = new Vector2(centerObject.getPositionX(), centerObject.getPositionY());
        gameToDisplayCoordinates = displayCenter.subtract(gameCenter);
    }

    public double gameToDisplayCoordinatesX(double positionX) {
        return positionX + gameToDisplayCoordinates.getX();
    }

    public double gameToDisplayCoordinatesY(double positionY) {
        return positionY + gameToDisplayCoordinates.getY();
    }
}
