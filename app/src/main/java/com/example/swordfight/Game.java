package com.example.swordfight;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//import com.example.androidstudio2dgamedevelopment.gameobject.Circle;
//import com.example.androidstudio2dgamedevelopment.gameobject.Enemy;
//import com.example.androidstudio2dgamedevelopment.gameobject.Player;
//import com.example.androidstudio2dgamedevelopment.gameobject.Spell;
//import com.example.androidstudio2dgamedevelopment.gamepanel.GameOver;
//import com.example.androidstudio2dgamedevelopment.gamepanel.Joystick;
//import com.example.androidstudio2dgamedevelopment.gamepanel.Performance;
//import com.example.androidstudio2dgamedevelopment.graphics.Animator;
//import com.example.androidstudio2dgamedevelopment.graphics.SpriteSheet;
//import com.example.androidstudio2dgamedevelopment.map.Tilemap;

import com.example.swordfight.object.Bullet;
import com.example.swordfight.object.Enemy;
import com.example.swordfight.object.Piece;
import com.example.swordfight.object.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private final Enemy enemy;
    private GameLoop gameLoop;

    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Bullet> bulletList = new ArrayList<Bullet>();

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), joystick,500, 500, 30);
        enemy = new Enemy(getContext(), player,100, 100, 15);


        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()) {
                    // Joystick was pressed before this event -> cast spell
                    bulletList.add(new Bullet(getContext(), player));
                } else if(joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is being pressed during this event -> cast spell
                    joystick.setIsPressed(true);
                } else {
                    // Joystick was never pressed -> cast spell
                    bulletList.add(new Bullet(getContext(), player));
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        player.draw(canvas);
        joystick.draw(canvas);
        for(Enemy enemy: enemyList) {
            enemy.draw(canvas);
        }
        for(Bullet bullet: bulletList) {
            bullet.draw(canvas);
        }
    }

    public void update() {
        joystick.update();
        player.update();
        if (Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        for (Enemy enemy: enemyList) {
            enemy.update();
        }

        for (Enemy enemy1: enemyList) {
            for (Enemy enemy2: enemyList) {
                if (enemy1 != enemy2) {
                    if(Piece.isColliding(enemy1, enemy2)) {
                        enemy1.knockback(enemy2);
                        enemy2.knockback(enemy1);
                    }
                }
            }
        }

        for (Bullet bullet: bulletList) {
            bullet.update();
        }

        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Piece enemy = iteratorEnemy.next();
//            if (Piece.isColliding(enemy, player)) {
//                // Remove enemy if it collides with the player
//                iteratorEnemy.remove();
//                player.setHealthPoint(player.getHealthPoint() - 1);
//                continue;
//            }

            Iterator<Bullet> iteratorBullet = bulletList.iterator();
            while (iteratorBullet.hasNext()) {
                Piece bullet = iteratorBullet.next();
                // Remove enemy if it collides with a spell
                if (Piece.isColliding(bullet, enemy)) {
                    iteratorBullet.remove();
//                    iteratorEnemy.remove();
                    break;
                }
            }
        }


//        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
//        while (iteratorEnemy.hasNext()) {
//            // Remove enemy if it collides with a spell
//            if (Piece.isColliding(iteratorEnemy.next(), player)) {
//                iteratorEnemy.remove();
//            }
//        }
    }

//    public void drawFPS(Canvas canvas) {
//        String averageFPS = Double.toString(gameLoop.getAverageFPS());
//        Paint paint = new Paint();
//        int color = ContextCompat.getColor(context, R.color.magenta);
//        canvas.drawText("FPS: " + averageFPS, 100, 20, paint);
//    }
}
