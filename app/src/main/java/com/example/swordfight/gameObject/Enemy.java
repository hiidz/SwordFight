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

import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;


public class Enemy extends Piece{

    private EnemyState enemyState;

    public EnemyState getEnemyState(){return enemyState;}

    private float enemyDetectionRange = 200f; // 2 * for chasing range

    public void performAction(){
        switch (enemyState.getState()) {
            case IDLE:
                // PLAY animation
                // check if player within range ... if so change to chasing
                if(this.getPosition().subtract(player.getPosition()).magnitude()<= enemyDetectionRange){
                    enemyState.setState (EnemyState.State.CHASING);
                }
                break;
            case CHASING:
                if(this.getPosition().subtract(player.getPosition()).magnitude() <= enemyDetectionRange * 2){
                    // continue chasing
                    chase(player.getPosition(), EnemyState.State.ATTACK);

                }else {
                    // move back to original spot
                    chase(getStartingPosition(), EnemyState.State.IDLE);
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
                    enemyState.setState (EnemyState.State.CHASING);
                }

                    // when player is within certain range ... attack
                break;
            case DEAD:
                // do nothing and de-active thread and ... wait to be revive // reset all settings .. and prepare to be resued
                break;

        }
    }

    public void chase(Vector2 target, EnemyState.State targetState){
        Vector2 direction = target.subtract(getPosition()).normalized();

        float distance = target.subtract(getPosition()).magnitude();
        setPosition(getPosition().add(direction.multiply(currentSpeed)));
        if (targetState == EnemyState.State.ATTACK){
            if(distance < this.getRadius() * 1.5)
            {
                enemyState.setState(EnemyState.State.ATTACK);
            }
            // maybe check if can cast skill first

        }else if(targetState == EnemyState.State.IDLE ) {
            // some small value
            if(distance <= this.getRadius())
            {
                enemyState.setState(EnemyState.State.IDLE);
            }
        }else {
        }
    }
    public void stun(float duration) {
        enemyState.setState(EnemyState.State.STUN);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                enemyState.setState(EnemyState.State.STUN);
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
        enemyState.setState(EnemyState.State.SLEEPING);
        setPosition(new Vector2(0,0));
    }

    public void activeEnemy(){
        enemyState.setState(EnemyState.State.IDLE);

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

    public Enemy(){enemyState.setState(EnemyState.State.SLEEPING);}

    public Enemy(Context context, Player player, float positionX, float positionY, float radius, int maxHealth) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius, maxHealth);
        this.player = player;
        this.healthBar = new HealthBar(context,this);
        this.spriteSheet = new SpriteSheet(context);
        enemyState = new EnemyState(this);
    }

    public Enemy(Context context, Player player) {
        this(context, player, (float)Math.random()*1000, (float)Math.random()*1000, 15, 100);
        enemyState = new EnemyState(this);
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

    public void deathCert(){
        if(this.getHealthPoints() <= 0){
            enemyState.setState(EnemyState.State.DEAD);
        }
    }

    @Override
    public void update() {
//        Log.d("test", "" + this.getPosition().subtract(player.getPosition()).magnitude() + currentState);

        performAction(); // need player to have v2 first
        deathCert();
//
    }
}
