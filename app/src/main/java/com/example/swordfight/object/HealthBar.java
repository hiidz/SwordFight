package com.example.swordfight.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;

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

    public void draw (Canvas canvas) {
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
        canvas.drawRect(borderLeft, borderTop, borderRight, borderBottom, borderPaint);

        // Draw Health
        float healthLeft, healthTop, healthRight, healthBottom, healthWidth, healthHeight;
        healthWidth = width - 2*margin;
        healthHeight = height - 2*margin;
        healthLeft = borderLeft + margin;
        healthRight = healthLeft + healthWidth*healthPointPercentage;
        healthBottom = borderBottom - margin;
        healthTop = healthBottom - healthHeight;

        canvas.drawRect(healthLeft, healthTop, healthRight, healthBottom, healthPaint);
    }
}
