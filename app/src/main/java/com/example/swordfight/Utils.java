package com.example.swordfight;

public class Utils {
    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return (double) Math.sqrt(Math.pow(p1x - p2x, 2) + Math.pow(p1y - p2y, 2));
    }
    public static float getDistanceBetweenPoints(Vector2 v1, Vector2 v2) {
        return (float) Math.sqrt(Math.pow(v1.getX() - v2.getX(), 2) + Math.pow(v1.getY() - v2.getY(), 2));
    }
}
