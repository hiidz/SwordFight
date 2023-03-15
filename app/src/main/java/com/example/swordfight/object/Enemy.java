package com.example.swordfight.object;

/*
* Enemy is a character that chases the Player when within range
* The Enemy class is an extension of a Piece, which is an extension of GameObject
*/

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;


public class Enemy extends Piece{
    private final Player player;
    private static final double MAX_SPEED = 10;
    private static final double KNOCKBACK_SPEED = 15;
    private static int totalEnemySpawn = 0;
    private static int maxEnemy = 10;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);

        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(context, ContextCompat.getColor(context, R.color.enemy),  Math.random()*1000,
                Math.random()*1000, 15);
        this.player = player;
    }

    public static boolean readyToSpawn() {
        if (totalEnemySpawn < maxEnemy) {
            totalEnemySpawn++;
            return true;
        }
        return false;
    }

//    public static void knockback(Enemy enemy1, Enemy enemy2) {
//        double distanceX = enemy1.getPositionX() - enemy2.getPositionX();
//        double distanceY = enemy1.getPositionY() - enemy2.getPositionY();
//
//        double distanceBetweenEnemies = getDistanceBetweenObjects(enemy1, enemy2);
//
//        double directionX = distanceX/distanceBetweenEnemies;
//        double directionY = distanceY/distanceBetweenEnemies;
//
//        double velocityX = 0.0;
//        double velocityY = 0.0;
//
//        if (distanceToPlayer > 0) {
//            velocityX = directionX * KNOCKBACK_SPEED;
//            velocityY = directionY * KNOCKBACK_SPEED;
//        } else {
//            velocityX = 0;
//            velocityY = 0;
//        }
//        enemy1.setPosition(enemy1.getPositionX() + velocityX, enemy2.getPositionY() + velocityY);
//    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void knockback(Enemy otherEnemy) {
        double distanceToOtherEnemyX = otherEnemy.getPositionX() - this.positionX;
        double distanceToOtherEnemyY = otherEnemy.getPositionY() - this.positionY;

        double distanceToOtherEnemy = getDistanceBetweenObjects(this, otherEnemy);

        double directionX = distanceToOtherEnemyX/distanceToOtherEnemy;
        double directionY = distanceToOtherEnemyY/distanceToOtherEnemy;

        velocityX = -(directionX) * KNOCKBACK_SPEED;
        velocityY = -(directionY) * KNOCKBACK_SPEED;
        setPosition(positionX + velocityX, positionY + velocityY);
    }

    @Override
    public void update() {
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        double distanceToPlayer = getDistanceBetweenObjects(this, player);

        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        if (distanceToPlayer > 0) {
            velocityX = directionX * MAX_SPEED;
            velocityY = directionY * MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        if (isColliding(this, player)) {
            velocityX = -(directionX) * KNOCKBACK_SPEED;
            velocityY = -(directionY) * KNOCKBACK_SPEED;
        }
        setPosition(positionX + velocityX, positionY + velocityY);
    }
}
