package com.example.swordfight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Timer {

    private File timingDataFile;
    private ArrayList<Long> startTimeList;
    private ArrayList<Long> endTimeList;

    private Long currentStartTime;
    private Long currentEndTime;
    private boolean isRunning;

    public Timer(Context context) {
        timingDataFile = new File(context.getFilesDir(), "timing_data.txt");
        startTimeList = new ArrayList<>();
        endTimeList = new ArrayList<>();
        isRunning = false;
        loadTimingData();
    }

    public void start() {
        currentStartTime = System.currentTimeMillis();
        startTimeList.add(System.currentTimeMillis());
        isRunning = true;
    }

    public void stop() {
        currentEndTime = System.currentTimeMillis();
        endTimeList.add(System.currentTimeMillis());
        isRunning = false;
    }

    public void writeTimingData(boolean isBossAlive) {

        if (isBossAlive) {
            // Don't write timing data if the boss is still alive
            return;
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(timingDataFile);
            for (int i = 0; i < startTimeList.size(); i++) {
                long startTime = startTimeList.get(i);
                long endTime = endTimeList.get(i);
                String line = startTime + "," + endTime + "\n";
                outputStream.write(line.getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            // File could not be written
        }
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        for (int i = 0; i < startTimeList.size(); i++) {
            long startTime = startTimeList.get(i);
            long endTime = (i < endTimeList.size()) ? endTimeList.get(i) : System.currentTimeMillis();
            int seconds = (int) ((endTime - startTime) / 1000);
            canvas.drawText("Try " + (i + 1) + ": " + seconds + "s", 10, 50 + i * 50, paint);
        }
    }

    public void drawScoreBoard(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);

        // Get the width and height of the canvas
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // Calculate the x and y positions to draw the text
        int textWidth = (int) paint.measureText("Scores");
        int x = (canvasWidth / 2) - (textWidth / 2) - 100;
        int y = (int) (canvasHeight * 0.1); // Start at 10% of canvas height

        // Sort the timing data list by time in ascending order
        ArrayList<Float> timeList = new ArrayList<>();
        for (int i = 0; i < startTimeList.size(); i++) {
            long startTime = startTimeList.get(i);
            long endTime = (i < endTimeList.size()) ? endTimeList.get(i) : System.currentTimeMillis();
            float seconds = ((endTime - startTime) / 1000.0f);
            timeList.add((float) seconds);
        }
        Collections.sort(timeList);

        // Draw the top 10 scores onto the canvas
        int count = 0;
        for (int i = 0; i < timeList.size() && count < 10; i++) {
            Float time = timeList.get(i);

            String scoreText = "Score " + (count + 1) + ": " + time + "s";
            canvas.drawText(scoreText, x, y, paint);
            count++;
            y += (int) (canvasHeight * 0.07); // Increase y position by 7% of canvas height
        }
    }


    public void clear() {
        startTimeList.clear();
        endTimeList.clear();
        isRunning = false;
        timingDataFile.delete();
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void loadTimingData() {

        //check if file does not exist create it
        if (!timingDataFile.exists()) {
            try {
                timingDataFile.createNewFile();
            } catch (IOException e) {
                // File could not be created
            }
        }

        try {
            FileInputStream inputStream = new FileInputStream(timingDataFile);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                startTimeList.add(Long.parseLong(parts[0]));
                endTimeList.add(Long.parseLong(parts[1]));
            }
            inputStream.close();
        } catch (IOException e) {
            // File does not exist or could not be read
        }
    }

    public int getTimeSeconds() {
        int time = 0;
        for (int i = 0; i < startTimeList.size(); i++) {
            long startTime = startTimeList.get(i);
            long endTime = (i < endTimeList.size()) ? endTimeList.get(i) : System.currentTimeMillis();
            int seconds = (int) ((endTime - startTime) / 1000);
            time += seconds;
        }
        return time;
    }

    public float getCurrentTimeSeconds() {
        int time = 0;
        float seconds = ((currentEndTime - currentStartTime) / 1000.0f);
        return seconds;
    }
}
