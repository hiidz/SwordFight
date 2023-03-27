package com.example.swordfight.gameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.swordfight.GameDisplay;
import com.example.swordfight.R;
import com.example.swordfight.Vector2;
import com.example.swordfight.graphics.BossAnimator;
import com.example.swordfight.graphics.SpriteSheet;

import java.util.Random;

public class BossEnemy extends Enemy {

    private BossAnimator bossAnimator;

    private SpriteSheet spriteSheet;

    private BossOrb bossOrb;

    private float oscillationAngle = 0.0f;

    private int zigzagDuration;
    private int zigzagCounter;

    public BossEnemy(Context context, Player player) {
        super(context, player, ContextCompat.getColor(context, R.color.bossEnemy));
        this.spriteSheet = new SpriteSheet(context, R.drawable.boss_spritesheet);
        this.bossAnimator = new BossAnimator(spriteSheet.getBossSpriteArray(5, 1));
        this.setCurrentSpeed(2f);

        // Initialize the BossOrb instance
        this.bossOrb = new BossOrb(context, ContextCompat.getColor(context, R.color.orbColor), position.getX(), position.getY(), 15, 100, player, this);

        // Initialize the zigzagDuration and zigzagCounter
        this.zigzagDuration = new Random().nextInt(60) + 30; // Random duration between 30 and 90 frames
        this.zigzagCounter = 0;
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        bossAnimator.draw(canvas, gameDisplay, this);
        bossOrb.draw(canvas, gameDisplay);
    }

    @Override
    public void update() {
        super.update();
        bossOrb.update();
    }

    @Override
    public void performAction() {
//        Log.d("test", "performAction: " + getEnemyState().getState());
        switch (getEnemyState().getState()) {
            case IDLE:
                // PLAY animation
                getEnemyState().setState(EnemyState.State.CHASING);
                break;
            case CHASING:
//                int maxValue = 500;
//                double skew = 0.8;
//                int randomValue = generateSkewedRandomValue(maxValue, skew);
                chase(getPlayer().getPosition(), EnemyState.State.ATTACK, 50);
                break;
            case STUN:
                // if stun ... stop moving and ... un stun after sometime // after done go to chasing state
                break;
            case CAST_SKILL:
                // when player is within certain range && skill cool down complete ... perform action
                break;
            case ATTACK:
                if (this.getPosition().subtract(getPlayer().getPosition()).magnitude() <= this.getRadius()) {
                    // do nothing attack
                    getPlayer().setHealthPoints(getPlayer().getHealthPoints() - 10);
                } else {
                    getEnemyState().setState(EnemyState.State.CHASING);
                }
                // when player is within certain range ... attack
                break;
            case DEAD:
                // do nothing and de-active thread and ... wait to be revive // reset all settings .. and prepare to be resued
                break;

        }
    }

    public static int generateSkewedRandomValue(int maxValue, double skew) {
        Random random = new Random();
        double probability = random.nextDouble();

        if (probability < 0.5) { // 80% chance of returning 0
            return 0;
        } else { // 20% chance of generating a skewed random value
            double uniformRandom = random.nextDouble();
            int skewedValue = (int) Math.round(Math.pow(uniformRandom, skew) * maxValue);
            return skewedValue;
        }
    }

    public void chase(Vector2 target, EnemyState.State targetState, float desiredDistance) {
        Vector2 direction = target.subtract(getPosition()).normalized();
        float distance = target.subtract(getPosition()).magnitude();
        Vector2 desiredPosition = target.subtract(direction.multiply(desiredDistance));

        if (targetState == EnemyState.State.ATTACK) {
            if (distance < this.getRadius() * 1.5) {
                getEnemyState().setState(EnemyState.State.ATTACK);
            }
        } else if (targetState == EnemyState.State.IDLE) {
            if (distance <= this.getRadius()) {
                getEnemyState().setState(EnemyState.State.IDLE);
            }
        }

        // Update the zigzagCounter
        zigzagCounter++;
        if (zigzagCounter >= zigzagDuration) {
            zigzagCounter = 0;
            zigzagDuration = new Random().nextInt(60) + 30; // Random duration between 30 and 90 frames
        }

        // Add sinusoidal oscillation only during the zigzag period
        float oscillationMagnitude = 720.0f; // You can adjust this value to change the amplitude of the zigzag pattern
        Vector2 oscillationDirection = new Vector2(0, 0);
        if (zigzagCounter < zigzagDuration / 2) {
            oscillationDirection = new Vector2(-direction.getY(), direction.getX()).multiply((float) Math.sin(oscillationAngle) * oscillationMagnitude);
            oscillationAngle += 0.05f;
        }

        Vector2 seek = seek(desiredPosition.add(oscillationDirection));
        Vector2 arrive = arrive(desiredPosition.add(oscillationDirection));
        float blendFactor = Math.min(distance / desiredDistance, 1.0f);

        Vector2 blendedSteering = seek.multiply(blendFactor).add(arrive.multiply(1.0f - blendFactor));
        setPosition(getPosition().add(blendedSteering.multiply(getCurrentSpeed())));
    }

    private Vector2 seek(Vector2 target) {
        Vector2 desiredVelocity = target.subtract(getPosition()).normalized().multiply(getCurrentSpeed());
        return desiredVelocity.subtract(getCurrentVelocity());
    }

    private Vector2 arrive(Vector2 target) {
        float slowingDistance = this.getRadius() * 2.0f;
        Vector2 toTarget = target.subtract(getPosition());
        float distance = toTarget.magnitude();

        if (distance < slowingDistance) {
            float rampedSpeed = getCurrentSpeed() * (distance / slowingDistance);
            float clampedSpeed = Math.min(rampedSpeed, getCurrentSpeed());
            Vector2 desiredVelocity = toTarget.multiply(clampedSpeed / distance);
            return desiredVelocity.subtract(getCurrentVelocity());
        } else {
            return new Vector2(0, 0);
        }
    }

}