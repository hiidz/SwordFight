package com.example.swordfight.gameObject;

/*
* Enemy is a character that chases the Player when within range
* The Enemy class is an extension of a Piece, which is an extension of GameObject
* Enemy will contain additional MAX_SPEED, currentSpeed, Animator, EnemyState and Player fields that Piece don't have
* And other enemy detection fields
*/

import android.content.Context;
import android.graphics.Canvas;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.R;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.EnemyAnimator;
import com.example.swordfight.graphics.PlayerAnimator;
import com.example.swordfight.map.MapLayout;

import java.util.Timer;
import java.util.TimerTask;


public class Enemy extends Piece{

    private static final float MAX_SPEED = 5;
    private float currentSpeed;
    private EnemyAnimator enemyAnimator;
    private static final float ENEMY_SCALE = 2.0f;
    private EnemyState enemyState;
    protected Player player;

    private static int totalEnemySpawn = 0;
    private static int maxEnemy = 10;
    private float enemyDetectionRange = 200f; // 2 * for chasing range


    public Enemy() { enemyState.setState(EnemyState.State.SLEEPING); }

    // Standard enemy spawn with scaling (for bosses)
    public Enemy(Context context, Player player, float positionX, float positionY, int maxHealth, float scalingFactor) {
        super(context, positionX, positionY, ContextCompat.getColor(context, R.color.enemy), maxHealth, scalingFactor);
        this.player = player;
        setCurrentSpeed(MAX_SPEED);
        enemyState = new EnemyState(this);
        this.enemyAnimator = new EnemyAnimator(spriteSheet.getEnemySpriteArray(), scalingFactor);
    }

    // Spawn enemy with default scaling (ENEMY_SCALE)
    public Enemy(Context context, Player player, float positionX, float positionY, int maxHealth) {
        this(context, player, positionX, positionY, maxHealth, ENEMY_SCALE);
    }

    // randomised enemy spawn
    public Enemy(Context context, Player player) {
        this(context, player, (float)Math.random() * MapLayout.NUMBER_OF_ROW_TILES*MapLayout.TILE_WIDTH_PIXELS, (float)Math.random() * MapLayout.NUMBER_OF_COLUMN_TILES * MapLayout.TILE_HEIGHT_PIXELS, 100);
        enemyState = new EnemyState(this);
    }

    public EnemyState getEnemyState(){ return enemyState; }
    public float getCurrentSpeed () { return currentSpeed; }
    public void setCurrentSpeed(float speed) { this.currentSpeed = speed; }

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
        setDirection(target.subtract(getPosition()).normalized());

        float distance = target.subtract(getPosition()).magnitude();
        setPosition(getPosition().add(getDirection().multiply(currentSpeed)));
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

    public void activateEnemy(){
        enemyState.setState(EnemyState.State.IDLE);
    }

    public void deathCert() {
        if (this.getCurrentHealth() <= 0) {
            enemyState.setState(EnemyState.State.DEAD);
        }
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        // CANVAS TO BE REPLACED BY ANIMATOR
//        canvas.drawCircle((float) gameDisplay.gameToDisplayCoordinatesX(getPositionX()),
//                (float) gameDisplay.gameToDisplayCoordinatesY(getPositionY()),
//                (float) getRadius(),
//                getPaint());
        enemyAnimator.draw(canvas, gameDisplay, this);
        healthBar.draw(canvas, gameDisplay);
    }

    @Override
    public void update() {
//        Log.d("test", "" + this.getPosition().subtract(player.getPosition()).magnitude() + currentState);

        performAction(); // need player to have v2 first
        deathCert();
    }
}
