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

import java.util.Timer;
import java.util.TimerTask;


public class Enemy extends Piece{
    enum EnemyState {
        IDLE,
        CHASING,
        STUN,
        CAST_SKILL,
        ATTACK,
        DEAD,
        SLEEPING
    }

    private float attackRange = 1f;
    private float enemyDetectionRange = 5f;
    private Vector2 startingLocation;
    private Vector2 currentPosition;
    private EnemyState currentState = EnemyState.SLEEPING;

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
                //if(getDistanceBetweenPoints(this.currentPosition, player.))
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

    public void chase(Vector2 target, EnemyState previousState){
        Vector2 direction = target.subtract(target).normalized();
        float distance = target.subtract(target).magnitude();
        currentPosition = currentPosition.add(direction.multiply(MAX_SPEED));
        if (distance > attackRange && target != startingLocation){
            // maybe check if can cast skill first
            setState(EnemyState.ATTACK);
        }else if(target == startingLocation && distance <= 0.1f) {
            // some small value
            setState(EnemyState.IDLE);
        }
    }
    public void stun(float duration) {
        setState(EnemyState.STUN);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setState(EnemyState.STUN);
            }
        }, (long) (duration * 1000));
    }

    // argument skill object
    // public void castSkill( skill object){}

    // to be override by all kind of enemy, regular just attack
    public void attack(){

    }

    public void dead(){ }

    public void resetAllSettings(){
        setState(EnemyState.SLEEPING);
        currentPosition = new Vector2(0,0);
        // hp etc
        //
        //
    }


    private Player player = null;
    private static final float MAX_SPEED = 10;
    private static final float KNOCKBACK_SPEED = 15;
    private static int totalEnemySpawn = 0;
    private static int maxEnemy = 10;

    public Enemy(){setState(EnemyState.SLEEPING);}

    public Enemy(Context context, Player player, float positionX, float positionY, double radius, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius, maxHealth);

        this.player = player;
    }

    public Enemy(Context context, Player player) {
        super(context, ContextCompat.getColor(context, R.color.enemy),  (float)Math.random()*1000,
                (float)Math.random()*1000, 15, 100);
        this.player = player;
    }


    public void setPosition(float positionX, float positionY) {
        position.set(positionX, positionY);
    }

    public void knockback(Enemy otherEnemy) {
        float distanceToOtherEnemyX = otherEnemy.getPositionX() - position.getX();
        float distanceToOtherEnemyY = otherEnemy.getPositionY() - position.getY();

        float distanceToOtherEnemy = getDistanceBetweenObjects(this, otherEnemy);

        float directionX = distanceToOtherEnemyX/distanceToOtherEnemy;
        float directionY = distanceToOtherEnemyY/distanceToOtherEnemy;

        velocity.set(-(directionX) * KNOCKBACK_SPEED, -(directionY) * KNOCKBACK_SPEED);
        setPosition(position.getX() + velocity.getX(), position.getY() + velocity.getY());
    }

    @Override
    public void update() {
        // performAction(); need player to have v2 first
//        if(currentState == EnemyState.SLEEPING) return;
        float distanceToPlayerX = player.getPositionX() - position.getX();
        float distanceToPlayerY = player.getPositionY() - position.getY();

        float distanceToPlayer = getDistanceBetweenObjects(this, player);

        float directionX = distanceToPlayerX/distanceToPlayer;
        float directionY = distanceToPlayerY/distanceToPlayer;

        if (distanceToPlayer > 0) {
            velocity.set(directionX * MAX_SPEED, directionY * MAX_SPEED);
        } else {
            velocity.set(0, 0);
        }

        if (isColliding(this, player)) {
            velocity.set(-(directionX) * KNOCKBACK_SPEED, -(directionY) * KNOCKBACK_SPEED);
            player.setDamageDealt(10);
        }
        setPosition(position.getX() + velocity.getX(), position.getY() + velocity.getY());
    }
}
