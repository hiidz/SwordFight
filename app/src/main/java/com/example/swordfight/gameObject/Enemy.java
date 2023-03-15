package com.example.swordfight.gameObject;

/*
* Enemy is a character that chases the Player when within range
* The Enemy class is an extension of a Piece, which is an extension of GameObject
*/

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.swordfight.R;
import com.example.swordfight.Utils;
import com.example.swordfight.Vector2;


public class Enemy extends Piece{
    enum EnemyState {
        IDLE,
        CHASING,
        STUN,
        CAST_SKILL,
        ATTACK,
        DEAD
    }

    private float enemyDetectionRange;
    private Vector2 startingLocation;
    private Vector2 currentPosition;
    private EnemyState currentState = EnemyState.IDLE;

    public void setState(EnemyState state) {
        currentState = state;
    }

    public EnemyState getState() {
        return currentState;
    }

    public void performAction(){
        switch (currentState) {
            case IDLE:
                // PLAY animation
                // check if player within range ... if so change to chasing
                break;
            case CHASING:
                // if player within certain range - pathing finding algo ....
                // if(Utils.getDistanceBetweenPoints(enemy and user) <= enemy detection){
                //chase(player.pos);
                //} else {
                //chase(startingLocation); // once reach location go back to idle
                //}
                break;
            case STUN:
                // if stun ... stop moving and ... un stun after sometime // after done go to chasing state
                break;
            case CAST_SKILL:
                // when player is within certain range && skill cool down complete ... perform action
                break;
            case ATTACK:
                // when player is within certain range ... attack
                break;
            case DEAD:
                // do nothing and de-active thread and ... wait to be revive // reset all settings .. and prepare to be resued 
                break;

        }
    }

    public void chase(Vector2 target){
        // move towards target .... after all place change to vector 2
    }
    public void stun(float duration){
        // free the enemy after this duration
    }

    // argument skill object
    // public void castSkill( skill object){}

    // to be override by all kind of enemy, regular just attack
    public void attack(){

    }

    public  void dead(){ }



    private final Player player;
    private static final double MAX_SPEED = 10;
    private static final double KNOCKBACK_SPEED = 15;
    private static int totalEnemySpawn = 0;
    private static int maxEnemy = 10;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius, maxHealth);

        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(context, ContextCompat.getColor(context, R.color.enemy),  Math.random()*1000,
                Math.random()*1000, 15, 100);
        this.player = player;
    }

    public static boolean readyToSpawn() {
        if (totalEnemySpawn < maxEnemy) {
            totalEnemySpawn++;
            return true;
        }
        return false;
    }


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
            player.setDamageDealt(10);
        }
        setPosition(positionX + velocityX, positionY + velocityY);
    }
}
