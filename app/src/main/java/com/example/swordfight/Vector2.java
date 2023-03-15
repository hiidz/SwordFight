package com.example.swordfight;

public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }
    public Vector2 multiply(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }
    public Vector2 divide(float divisor) {
        return new Vector2(x / divisor, y / divisor);
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 normalized() {
        float mag = magnitude();
        if (mag == 0) {
            return new Vector2(0, 0);
        }
        return new Vector2(x / mag, y / mag);
    }
}
