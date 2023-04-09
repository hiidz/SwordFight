package com.example.swordfight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Timer timer;
    private SurfaceView scoreboardView;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);

        // Instantiate a Timer object
        timer = new Timer(this);

        // Get the SurfaceView and SurfaceHolder from the layout
        scoreboardView = findViewById(R.id.scoreboard_view);
        holder = scoreboardView.getHolder();
        holder.addCallback(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        redrawScoreboard();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        redrawScoreboard();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Do nothing
    }

    // Method to redraw the scoreboard with the current timing data
    private void redrawScoreboard() {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK); // Clear the canvas with black color

            // Draw the timing data onto the scoreboard using the Timer object
            timer.drawScoreBoard(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

}
