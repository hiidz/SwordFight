package com.example.swordfight.gameObject;

/*
* Enemy is a character that chases the Player when within range
* The Enemy class is an extension of a Piece, which is an extension of GameObject
*/

import android.content.Context;
import android.graphics.Canvas;
import android.os.Debug;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.R;
import com.example.swordfight.Utils;
import com.example.swordfight.Vector2;
import com.example.swordfight.gamepanel.HealthBar;
import com.example.swordfight.graphics.Animator;
import com.example.swordfight.graphics.SpriteSheet;

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

    private float enemyDetectionRange = 200f; // 2 * for chasing range
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
                if(this.getPosition().subtract(player.getPosition()).magnitude()<= enemyDetectionRange){
                    this.setState(EnemyState.CHASING);
                }
                break;
            case CHASING:
                if(this.getPosition().subtract(player.getPosition()).magnitude() <= enemyDetectionRange * 2){
                    // continue chasing
                    chase(player.getPosition(), EnemyState.ATTACK);

                }else {
                    // move back to original spot
                    chase(getStartingPosition(), EnemyState.IDLE);
                }
                break;
            case STUN:
                // if stun ... stop moving and ... un stun after sometime // after done go to chasing state
                break;
            case CAST_SKILL:
                // when player is within certain range && skill cool down complete ... perform action
                break;
            case ATTACK:
                if(this.getPosition().subtract(player.getPosition()).magnitude() <= this.getRadius()){
                    // do nothing attack
                    //player.setHealthPoint(player.getHealthPoint() - 1);
                }else {
                    this.setState(EnemyState.CHASING);
                }

                    // when player is within certain range ... attack
                break;
            case DEAD:
                // do nothing and de-active thread and ... wait to be revive // reset all settings .. and prepare to be resued
                break;

        }
    }

    public void chase(Vector2 target, EnemyState targetState){
        Vector2 direction = target.subtract(getPosition()).normalized();

        float distance = target.subtract(getPosition()).magnitude();
        setPosition(getPosition().add(direction.multiply(currentSpeed)));
        Log.d("direction", "" + direction.multiply(currentSpeed));
        if (targetState == EnemyState.ATTACK){
            if(distance < this.getRadius() * 1.5)
            {
                setState(EnemyState.ATTACK);
            }
            // maybe check if can cast skill first

        }else if(targetState == EnemyState.IDLE ) {
            // some small value
            if(distance <= this.getRadius())
            {
                setState(EnemyState.IDLE);
            }
        }else {
            Log.d("moving", "" );
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
        setPosition(new Vector2(0,0));
    }

    public void activeEnemy(){
        setState(EnemyState.IDLE);
    }


    private Player player = null;
    private static final float MAX_SPEED = 5;
    private static final float KNOCKBACK_SPEED = 15;
    private static int totalEnemySpawn = 0;
    private static int maxEnemy = 10;
    private HealthBar healthBar;
    private Animator animator;

    private static SpriteSheet spriteSheet;
    private static Animator playerAnimator;

    private float currentSpeed = MAX_SPEED;
    public float getCurrentSpeed () { return currentSpeed;}
    public void setCurrentSpeed(float speed){this.currentSpeed = speed;}

    public Enemy(){setState(EnemyState.SLEEPING);}

    public Enemy(Context context, Player player, float positionX, float positionY, float radius, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius, maxHealth);
        this.player = player;
        this.healthBar = new HealthBar(context,this);
        this.spriteSheet = new SpriteSheet(context);
    }

    public Enemy(Context context, Player player) {
        this(context, player, (float)Math.random()*1000, (float)Math.random()*1000, 15, 100);
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(position.getX()),
                (float) gameDisplay.gameToDisplayCoordinatesY(position.getY()),
                (float) radius,
                paint);
        //        animator.draw(canvas, gameDisplay, this, joystick);
        healthBar.draw(canvas, gameDisplay);
    }

    public void knockback(Enemy otherEnemy) {

    }

    @Override
    public void update() {
//        Log.d("test", "" + this.getPosition().subtract(player.getPosition()).magnitude() + currentState);

        performAction(); // need player to have v2 first

//
    }
}
