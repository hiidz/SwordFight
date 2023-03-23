package com.example.swordfight;

import android.graphics.Canvas;
import android.os.Debug;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private static final double MAX_UPS = 60.0;
    private static final double MAX_FPS = 60.0;

    private long lastUpdateTime = System.nanoTime();
    private long lastRenderTime = System.nanoTime();
    private double updateInterval = 1e9 / MAX_UPS;
    private double renderInterval = 1e9 / MAX_FPS;
    private int updateCount = 0;
    private int frameCount = 0;
    private boolean isRunning = false;
    private double averageUPS;
    private double averageFPS;

    private SurfaceHolder surfaceHolder;
    private Game game;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        Log.d("GameLoop.java", "startLoop()");
        isRunning = true;
        start();
    }

    public void run() {

        long lastLoopTime = System.nanoTime();
        long lastLogTime = System.nanoTime();

        while (isRunning) {

            super.run();

            long now = System.nanoTime();
            long elapsedUpdateTime = now - lastUpdateTime;
            long elapsedRenderTime = now - lastRenderTime;

            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if (elapsedUpdateTime >= updateInterval) {
                    update();
                    updateCount++;
                    lastUpdateTime = (long) (now - (elapsedUpdateTime % updateInterval));
                }

                if (elapsedRenderTime >= renderInterval) {
                    render(canvas);
                    frameCount++;
                    lastRenderTime = (long) (now - (elapsedRenderTime % renderInterval));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            long currentLoopTime = System.nanoTime();
            double elapsedLoopTime = (currentLoopTime - lastLoopTime) / 1e9;

            // Sleep if necessary to maintain target UPS and FPS
            double targetUPSInterval = 1.0 / MAX_UPS;
            double targetFPSInterval = 1.0 / MAX_FPS;
            double targetInterval = Math.min(targetUPSInterval, targetFPSInterval);

            if (elapsedLoopTime < targetInterval) {
                double sleepTime = (targetInterval - elapsedLoopTime) * 1e9;

                try {
                    long sleepMillis = (long) (sleepTime / 1e6);
                    int sleepNanos = (int) (sleepTime % 1e6);
                    Thread.sleep(sleepMillis, sleepNanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastLoopTime = currentLoopTime;

            long elapsedLogTime = System.nanoTime() - lastLogTime;
            if (elapsedLogTime >= 1e9) {
                averageUPS = (double) updateCount / (elapsedLogTime / 1e9);
                averageFPS = (double) frameCount / (elapsedLogTime / 1e9);
                updateCount = 0;
                frameCount = 0;
                lastLogTime = System.nanoTime();
            }
        }
    }

    private void update() {
        // Update game logic
        game.update();
    }

    private void render(Canvas canvas) {
        // Render graphics
        game.draw(canvas);
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
