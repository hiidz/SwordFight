package com.example.swordfight.gameObject;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;
import com.example.swordfight.Vector2;

public class Bullet extends Piece {
    private final float MAX_SPEED = 30;
    private final float MAX_DISTANCE = 1500;
    private float distanceTraveled = 0;


    public float getMaxSpeed(){return MAX_SPEED;}
    public boolean timeToGoBackPool(){
        return distanceTraveled > MAX_DISTANCE;
    }

    public Bullet(Context context, Player shooter) {
        super(context, ContextCompat.getColor(context, R.color.bullet),  shooter.getPositionX(),
                shooter.getPositionY(), 10,  0);
        velocity.set(shooter.getDirectionX() * MAX_SPEED, shooter.getDirectionY() * MAX_SPEED);
    }

    public void resetAllSettings(){
        velocity = new Vector2(0,0);
        distanceTraveled = 0;
    }

    @Override
    public void update() {
        position = position.add(velocity);
        distanceTraveled+= velocity.magnitude();
    }
}
