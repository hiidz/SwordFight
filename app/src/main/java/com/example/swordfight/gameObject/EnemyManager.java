package com.example.swordfight.gameObject;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// this class will handle enemy creation
// and its life cycle
public class EnemyManager {
    private Player player;
    private Context context;

    private final static int maxEnemy = 5;
    private final static int enemyPool = 10;

    private final static int minEnemyCount = 3;
    private CopyOnWriteArrayList<Enemy> enemyList = new CopyOnWriteArrayList<Enemy>();
    private CopyOnWriteArrayList<Enemy> poolOfSleepingEnemy = new CopyOnWriteArrayList<>();
    private BossEnemy bossEnemy;

    public EnemyManager(Context context, Player player){
        this.context = context;
        this.player = player;
        this.bossEnemy = new BossEnemy(context, player);
        enemyList.add(bossEnemy);
        bossEnemy.activateEnemy();
        // set up all the enemy but without using them ...
        setUpEnemyPool();
    }

    private void setUpEnemyPool(){
        for(int i = 0; i < enemyPool - 1; i++){
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
            enemy.activateEnemy();
        }else {
            Enemy enemy = new Enemy(context, player);
            enemyList.add(enemy);
            enemy.activateEnemy();
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
            if (e.isColliding(player, e)) {
                player.setDamageDealt(e.getDamagePower());
            }
            if(e.getEnemyState().getState() == EnemyState.State.DEAD){
                removeEnemy(e);
            }else {
                e.update();
            }
        }
    }

    public void updateEnemyHealth(){
        for (Enemy enemy: enemyList) {
            Log.d("Enemy", "updateEnemyHealth: " + enemy.getCurrentHealth());
            enemy.multiplyHealth(2);
            enemy.multiplyMaxHealth(2);
        }
    }


    public boolean isBossDead(){
//        Log.d("Boss", "isBossDead: " + bossEnemy.getEnemyState().getState());
        return bossEnemy.getEnemyState().getState() == EnemyState.State.DEAD;
    }

}
