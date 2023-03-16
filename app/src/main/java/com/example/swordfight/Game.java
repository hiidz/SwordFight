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
import com.example.swordfight.gameObject.Enemy;
import com.example.swordfight.gameObject.Piece;
import com.example.swordfight.gameObject.Player;
import com.example.swordfight.gamepanel.GameOver;
import com.example.swordfight.gamepanel.Joystick;
import com.example.swordfight.graphics.Animator;
import com.example.swordfight.graphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private EnemyManager enemyManager;
    private List<Bullet> bulletList = new ArrayList<Bullet>();
    private int joystickPointerId = 0;
    private GameOver gameOver;
    private GameDisplay gameDisplay;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        SpriteSheet spriteSheet = new SpriteSheet(context);
        Animator animator = new Animator(spriteSheet.getPlayerSpriteArray());

        gameLoop = new GameLoop(this, surfaceHolder);

        gameOver = new GameOver(getContext());

        joystick = new Joystick(275, 700, 70, 40);
        player = new Player(getContext(), joystick,500.0f, 500.0f, 30.0, spriteSheet.getPlayerSprite(), 5000, animator);
        enemyManager = new EnemyManager(context, player);
        // Initialize display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

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
                } else if(joystick.isPressed(event.getX(), event.getY())) {
                    // Joystick is being pressed during this event -> cast spell
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    // Joystick was never pressed -> cast spell
                    bulletList.add(new Bullet(getContext(), player));
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
        player.draw(canvas, gameDisplay);
        joystick.draw(canvas);
        for(Enemy enemy: enemyManager.getEnemyList()) {
            enemy.draw(canvas, gameDisplay);
        }
        for(Bullet bullet: bulletList) {
            bullet.draw(canvas, gameDisplay);
        }

        if (player.getHealthPoints() <= 0) {
            gameOver.draw(canvas);
        }
    }

    public void update() {

        if (player.getHealthPoints() <= 0) {
            return;
        }

        joystick.update();
        player.update();
        enemyManager.update();



        for (Bullet bullet: bulletList) {
            bullet.update();
        }

        Iterator<Enemy> iteratorEnemy = enemyManager.getEnemyList().iterator();
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
            gameDisplay.update();
        }


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
