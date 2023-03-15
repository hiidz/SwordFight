package com.example.swordfight;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning = false;
//    private double averageUPS;
//    private double averageFPS;

    private SurfaceHolder surfaceHolder;
    private Game game;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

//    public double getAverageUPS() {
//        return averageUPS;
//    }
//
//    public double getAverageFPS() {
//        return averageFPS;
//    }

    public void startLoop() {
        Log.d("GameLoop.java", "startLoop()");
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        // Declare time and cycle count variables
//        int updateCount = 0;
//        int frameCount = 0;
//
//        long startTime;
//        long elapsedTime;
//        long sleepTime;

        Canvas canvas = null;
//        startTime = System.currentTimeMillis();
        while(isRunning) {
            // Try to update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
//                    updateCount++;

                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
//                        frameCount++;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }

    public void stopLoop() {
        Log.d("GameLoop.java", "stopLoop()");
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
