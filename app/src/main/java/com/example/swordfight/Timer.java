package com.example.swordfight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Timer {

    private File timingDataFile;
    private ArrayList<Long> startTimeList;
    private ArrayList<Long> endTimeList;
    private boolean isRunning;

    public Timer(Context context) {
        timingDataFile = new File(context.getFilesDir(), "timing_data.txt");
        startTimeList = new ArrayList<>();
        endTimeList = new ArrayList<>();
        isRunning = false;
        loadTimingData();
    }

    public void start() {
        startTimeList.add(System.currentTimeMillis());
        isRunning = true;
    }

    public void stop() {
        endTimeList.add(System.currentTimeMillis());
        isRunning = false;
    }

    public void writeTimingData() {
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
}
