package com.example.swordfight.gameObject;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;
import com.example.swordfight.Vector2;

public class Bullet extends GameObject {
    private final float MAX_SPEED = 30;
    private final float MAX_DISTANCE = 1500;
    private float distanceTraveled = 0;

    private int damage = 5;
    public int getDamage(){return damage;}

    public Bullet(Context context, Player shooter) {
        super(context, shooter.getPositionX(), shooter.getPositionY(), ContextCompat.getColor(context, R.color.bullet));
        setVelocity(new Vector2(shooter.getDirectionX() * MAX_SPEED, shooter.getDirectionY() * MAX_SPEED));
    }

    public float getMaxSpeed(){return MAX_SPEED;}
    public boolean timeToGoBackPool(){ return distanceTraveled > MAX_DISTANCE; }

    public void resetAllSettings(){
        setVelocity(new Vector2(0,0));
        distanceTraveled = 0;
    }

    @Override
    public void update() {
        setPosition(getPosition().add(getVelocity()));
        distanceTraveled += getVelocity().magnitude();
    }
}
