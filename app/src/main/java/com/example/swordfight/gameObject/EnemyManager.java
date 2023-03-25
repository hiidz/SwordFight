package com.example.swordfight.gameObject;

import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;

import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.Piece;
import com.example.swordfight.gameObject.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// this class will handle enemy creation
// and its life cycle
public class EnemyManager {
    private Player player;
    private Context context;

    private final static int maxEnemy = 5;
    private final static int enemyPool = 10;

    private final static int minEnemyCount = 3;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Enemy> poolOfSleepingEnemy = new ArrayList<>();

    public EnemyManager(Context context, Player player){
        this.context = context;
        this.player = player;
        // set up all the enemy but without using them ...
        setUpEnemyPool();
    }

    private void setUpEnemyPool(){
        for(int i = 0; i < enemyPool; i++){
            poolOfSleepingEnemy.add( new Enemy(this.context, player));
        }
    }

    public void removeEnemy(Enemy enemy){
        enemy.resetAllSettings();
        poolOfSleepingEnemy.add(enemy);
        enemyList.remove(enemy);
    }

    public void addEnemy(){
        Log.d("Enemy ", "size " + poolOfSleepingEnemy.size() );

        if(poolOfSleepingEnemy.size() < minEnemyCount) {
            Enemy enemy = poolOfSleepingEnemy.remove(0);
            enemyList.add(enemy);
            enemy.activeEnemy();
        }else {
            Enemy enemy = new Enemy(context, player);
            enemyList.add(enemy);
            enemy.activeEnemy();
        }
    }

    public List<Enemy> getEnemyList(){return enemyList;}

    public boolean readyToSpawn() {
        if (enemyList.size() < maxEnemy) {
            return true;
        }
        return false;
    }
    public void update(){
        if (readyToSpawn()) {
            this.addEnemy();
        }

        Iterator<Enemy> iterator = getEnemyList().iterator();
        while (iterator.hasNext()) {
            Enemy e = iterator.next();
            if(e.getEnemyState().getState() == EnemyState.State.DEAD){

            }else {
                e.update();
            }
        }

//        for (Enemy enemy1: enemyList) {
//            for (Enemy enemy2: enemyList) {
//                if (enemy1 != enemy2) {
//                    if(Piece.isColliding(enemy1, enemy2)) {
//                        enemy1.setState(Enemy.EnemyState.CHASING);
//                        enemy2.setState(Enemy.EnemyState.IDLE);
//
//                    }
//                }
//            }
//        }
    }

    public synchronized void updateEnemyHealth(){
        for (Enemy enemy: enemyList) {
            enemy.multiplyHealth(2);
            enemy.multiplyMaxHealth(2);
        }
    }

}
