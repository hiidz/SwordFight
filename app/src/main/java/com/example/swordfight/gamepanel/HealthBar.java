package com.example.swordfight.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.R;
import com.example.swordfight.gameObject.Piece;

public class HealthBar {

    private Piece piece;
    private int width, height, margin; // pixel value
    private Paint borderPaint;
    private Paint healthPaint;

    public HealthBar(Context context, Piece piece) {
        this.piece = piece;
        this.width = 100;
        this.height = 20;
        this.margin = 2;

        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context,  R.color.healthBarBorder);
        borderPaint.setColor(borderColor);

        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context,  R.color.healthBarColor);
        healthPaint.setColor(healthColor);
    }

    public void draw (Canvas canvas, GameDisplay gameDisplay) {
        float x = (float) piece.getPositionX();
        float y = (float) piece.getPositionY();
        float distanceToPiece = 30;
        float healthPointPercentage = (float) piece.getHealthPoints()/piece.getMaxHealth();

        // Draw border
        float borderLeft, borderTop, borderRight, borderBottom;
        borderLeft = x - width/2;
        borderRight = x + width/2;
        borderBottom = y - distanceToPiece;
        borderTop = borderBottom - height;
        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinatesX(borderLeft),
                (float) gameDisplay.gameToDisplayCoordinatesY(borderTop),
                (float) gameDisplay.gameToDisplayCoordinatesX(borderRight),
                (float) gameDisplay.gameToDisplayCoordinatesY(borderBottom),
                borderPaint);

        // Draw Health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;

        canvas.drawRect(
                (float) gameDisplay.gameToDisplayCoordinatesX(healthLeft),
                (float) gameDisplay.gameToDisplayCoordinatesY(healthTop),
                (float) gameDisplay.gameToDisplayCoordinatesX(healthRight),
                (float) gameDisplay.gameToDisplayCoordinatesY(healthBottom),
                healthPaint);

        // Draw Health Number
        String healthText = piece.getHealthPoints() + "/" + piece.getMaxHealth();
        Paint textPaint = new Paint();
//        textPaint.setColor(ContextCompat.getColor(canvas.getContext(), R.color.healthBarText));
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(height); // set the text size to half the height of the health bar
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);

        // calculate the coordinates to draw the text in the center of the health bar
        float textX = (borderLeft + borderRight - textPaint.measureText(healthText)) / 2;
        float textY = borderBottom - height / 2 + textPaint.getTextSize() / 2;

        canvas.drawText(
                healthText,
                (float) gameDisplay.gameToDisplayCoordinatesX(textX),
                (float) gameDisplay.gameToDisplayCoordinatesY(textY),
                textPaint);
    }
}