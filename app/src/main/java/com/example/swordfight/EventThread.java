package com.example.swordfight;

public class EventThread implements Runnable {
    private final Runnable event;
    private final long intervalMillis;
    private boolean isRunning;

    public EventThread(Runnable event, long intervalMillis) {
        this.event = event;
        this.intervalMillis = intervalMillis;
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(intervalMillis);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event.run();
        }
    }
}