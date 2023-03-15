package com.example.swordfight;

import com.example.swordfight.gameObject.GameObject;

public class GameDisplay {
    private final int widthPixels;
    private final int heightPixels;
    private double gameToDisplayCoordinatesX;
    private double gameToDisplayCoordinatesY;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int widthPixels, int heightPixels, GameObject centerObject) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
//        DISPLAY_RECT = new Rect(0, 0, widthPixels, heightPixels);

        this.centerObject = centerObject;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;

        update();
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayCoordinatesX = displayCenterX - gameCenterX;
        gameToDisplayCoordinatesY = displayCenterY - gameCenterY;
    }

    public double gameToDisplayCoordinatesX(double positionX) {
        return positionX + gameToDisplayCoordinatesX;
    }

    public double gameToDisplayCoordinatesY(double positionY) {
        return positionY + gameToDisplayCoordinatesY;
    }
}
