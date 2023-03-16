package com.example.swordfight.gameObject;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;
import com.example.swordfight.Vector2;

public class Bullet extends Piece {
    private static final float MAX_SPEED = 30;
    private final Player shooter;

    public Bullet(Context context, Player shooter) {
        super(context, ContextCompat.getColor(context, R.color.bullet),  shooter.getPositionX(),
                shooter.getPositionY(), 10,  0);
        this.shooter = shooter;
        velocity.set(shooter.getDirectionX() * MAX_SPEED, shooter.getDirectionY() * MAX_SPEED);
    }

    @Override
    public void update() {
        position.add(velocity);
    }
}
