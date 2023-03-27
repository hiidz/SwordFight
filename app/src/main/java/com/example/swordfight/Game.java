package com.example.swordfight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.swordfight.gameObject.Bullet;
import com.example.swordfight.gameObject.BulletManager;
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.EnemyManager;
import com.example.swordfight.gameObject.Player;
import com.example.swordfight.gamepanel.GameOver;
import com.example.swordfight.gamepanel.Joystick;
import com.example.swordfight.gamepanel.Performance;
import com.example.swordfight.graphics.SpriteSheet;
import com.example.swordfight.map.Tilemap;

class Game extends SurfaceView implements SurfaceHolder.Callback {

    // Game Objects
    private final Player player;
    private final Joystick joystick;

    private Performance performance;
    private int joystickPointerId = 0;

    private BulletManager bulletManager;

    // Game Logic/Services/HUD
    private GameLoop gameLoop;
    private EnemyManager enemyManager;
    private GameOver gameOver;
    private GameDisplay gameDisplay;
    private final Tilemap tilemap;
    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        gameOver = new GameOver(getContext());
        performance = new Performance(context, gameLoop);

        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), joystick,500.0f, 500.0f, 30.0f, 5000);
        enemyManager = new EnemyManager(context, player);
        bulletManager = new BulletManager(context, player, enemyManager);

        // Initialize display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);
        SpriteSheet spriteSheet = new SpriteSheet(context, R.drawable.javier_spritesheet_1);
        tilemap = new Tilemap(spriteSheet);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()) {
                    // Joystick was pressed before this event -> cast spell
                    bulletManager.addBullet();
                } else if(joystick.isPressed(event.getX(), event.getY())) {
                    // Joystick is being pressed during this event -> cast spell
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    // Joystick was never pressed -> cast spell
                    bulletManager.addBullet();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    joystick.setActuator(event.getX(), event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
        gameLoop.startLoop();

        Runnable updateEnemyHealthEvent = new Runnable() {
            @Override
            public void run() {
                enemyManager.updateEnemyHealth();
            }
        };
        EventThread enemyHealthUpdater = new EventThread(updateEnemyHealthEvent, 30000);
        enemyHealthUpdater.start(); // start the thread
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        tilemap.draw(canvas, gameDisplay);
        performance.draw(canvas);
        player.draw(canvas, gameDisplay);
        joystick.draw(canvas);
        for(Enemy enemy: enemyManager.getEnemyList()) {
            enemy.draw(canvas, gameDisplay);
        }
        for(Bullet bullet: bulletManager.getBulletList()) {
            bullet.draw(canvas, gameDisplay);
        }

        if (player.getCurrentHealth() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {

        if (player.getCurrentHealth() <= 0) {
            return;
        }

        joystick.update();
        player.update();
        enemyManager.update();
        bulletManager.update();
        gameDisplay.update();


//        for (Bullet bullet: bulletList) {
//            bullet.update();
//        }

//
//        Iterator<Enemy> iteratorEnemy = enemyManager.getEnemyList().iterator();
//        while (iteratorEnemy.hasNext()) {
//            Piece enemy = iteratorEnemy.next();
//            if (Piece.isColliding(enemy, player)) {
//                // Remove enemy if it collides with the player
//                iteratorEnemy.remove();
//                player.setHealthPoint(player.getHealthPoint() - 1);
//                continue;
//            }

//            Iterator<Bullet> iteratorBullet = bulletList.iterator();
//            while (iteratorBullet != null && iteratorBullet.hasNext()) {
//                Piece bullet = iteratorBullet.next();
//                // Remove enemy if it collides with a spell
//                if (Piece.isColliding(bullet, enemy)) {
//                    iteratorBullet.remove();
//                    enemy.setDamageDealt(10);
////                    iteratorEnemy.remove();
//                    break;
//                }
//            }
//            gameDisplay.update();
//        }


//        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
//        while (iteratorEnemy.hasNext()) {
//            // Remove enemy if it collides with a spell
//            if (Piece.isColliding(iteratorEnemy.next(), player)) {
//                iteratorEnemy.remove();
//            }
//        }
    }

    public void pause() {
        gameLoop.stopLoop();
    }

//    public void drawFPS(Canvas canvas) {
//        String averageFPS = Double.toString(gameLoop.getAverageFPS());
//        Paint paint = new Paint();
//        int color = ContextCompat.getColor(context, R.color.magenta);
//        canvas.drawText("FPS: " + averageFPS, 100, 20, paint);
//    }
}
