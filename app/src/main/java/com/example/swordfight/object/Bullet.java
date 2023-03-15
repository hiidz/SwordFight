package com.example.swordfight.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;
import com.example.swordfight.object.Piece;
import com.example.swordfight.object.Player;

public class Bullet extends Piece {
    private static final double MAX_SPEED = 30;
    private final Player shooter;

    public Bullet(Context context, Player shooter) {
        super(context, ContextCompat.getColor(context, R.color.bullet),  shooter.getPositionX(),
                shooter.getPositionY(), 10,  0);
        this.shooter = shooter;
        velocityX = shooter.getDirectionX() * MAX_SPEED;
        velocityY = shooter.getDirectionY() * MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
