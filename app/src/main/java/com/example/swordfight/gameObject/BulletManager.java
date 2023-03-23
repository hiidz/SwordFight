package com.example.swordfight.gameObject;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BulletManager {
    private Player player;
    private Context context;

    private EnemyManager enemyManager;
    private final static int bulletPool = 50;
    private final static int minBulletCount = 5;
    private List<Bullet> bulletList = new ArrayList<>();
    private List<Bullet> poolOfBullet = new ArrayList<>();

    public List<Bullet> getBulletList(){return bulletList;}


    public BulletManager(Context context, Player player, EnemyManager enemyManager){
        this.context = context;
        this.player = player;
        this.enemyManager = enemyManager;
        // set up all the enemy but without using them ...
        setUpBulletPool();
    }

    public void setUpBulletPool(){
        for(int i = 0; i < bulletPool; i++){
            poolOfBullet.add( new Bullet(this.context, player));
        }
    }


    public void removeBullet(Bullet b){
        b.resetAllSettings();
        poolOfBullet.add(b);
        bulletList.remove(b);
    }

    public void addBullet(){
        Bullet b ;
        if(poolOfBullet.size() < minBulletCount) {
            b = poolOfBullet.remove(0);
            bulletList.add(b);
        }else {
            b  = new Bullet(context, player);
            bulletList.add(b);
        }
        b.velocity.set(player.getDirectionX() * b.getMaxSpeed(), player.getDirectionY() * b.getMaxSpeed());

    }

    public void update() {
        for (Bullet b : getBulletList()) {
            b.update();
            if(b.timeToGoBackPool() || currentBulletCollidingWithAnyEnemy(b)){
                removeBullet(b);
            }
        }
    }

    public boolean currentBulletCollidingWithAnyEnemy(Bullet b){
        for(Enemy e: enemyManager.getEnemyList()){
            if(e.isColliding(e,b)){
                return true;
            }
        }
        return false;
    }
}