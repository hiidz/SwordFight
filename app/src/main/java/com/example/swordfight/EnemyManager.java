package com.example.swordfight;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.Piece;
import com.example.swordfight.gameObject.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// this class will handle enemy creation
// and its life cycle
public class EnemyManager {
    private Player player;
    private Context context;
    public EnemyManager(Context context, Player player){
        this.context = context;
        this.player = player;
        // set up all the enemy but without using them ...
        // setUpEnemyPool();
    }

    private void setUpEnemyPool(){
        for(int i = 0; i < enemyPool; i++){
            poolOfSleepingEnemy.add(new Enemy());
        }
    }

    private final static int maxEnemy = 5;
    private final static int enemyPool = 10;

    private final static int minEnemyCount = 3;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Enemy> poolOfSleepingEnemy = new ArrayList<>(); // create default amount of base enemy

    public void removeEnemy(Enemy enemy){
//        enemy.resetAllSettings();
//        poolOfSleepingEnemy.add(enemy);
        enemyList.remove(enemy);
    }
    public void addEnemy(Enemy enemy){
//        if(poolOfSleepingEnemy.size() <= Math.floor(enemyPool/3)){
//            setUpEnemyPool();
//        }
        enemyList.add(enemy); // poolOfSleepingEnemy.get(0)
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
            this.addEnemy(new Enemy(context, player));
        }

        for (Enemy enemy: getEnemyList()) {
            enemy.update();
        }

        for (Enemy enemy1: enemyList) {
            for (Enemy enemy2: enemyList) {
                if (enemy1 != enemy2) {
                    if(Piece.isColliding(enemy1, enemy2)) {
//                        enemy1.knockback(enemy2);
//                        enemy2.knockback(enemy1);
                    }
                }
            }
        }
    }

}