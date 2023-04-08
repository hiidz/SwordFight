package com.example.swordfight.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;
import com.example.swordfight.Timer;

public class GameOver {

    private Context context;

    private Timer timer;

    public GameOver(Context context, Timer timer) {
        this.context = context;
        this.timer = timer;
    }

    public void draw(Canvas canvas, boolean isBossAlive) {
        String text = "Game Over";

        float x = 800;
        float y = 200;

        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);

        canvas.drawText(text, x, y, paint);

        //draw the timer here with red text

        final int MAX_TIME = 60;
        int scoreMultiplier = 10;

        if (isBossAlive) {
            scoreMultiplier = 0;
        }

        String time = "Score: " + (MAX_TIME - timer.getTimeSeconds()) * scoreMultiplier;
        float timeX = 800;
        float timeY = 400;
        Paint timePaint = new Paint();
        int timeColor = ContextCompat.getColor(context, R.color.gameOver);
        timePaint.setColor(timeColor);
        float timeTextSize = 100;
        timePaint.setTextSize(timeTextSize);
        canvas.drawText(time, timeX, timeY, timePaint);

    }
}
