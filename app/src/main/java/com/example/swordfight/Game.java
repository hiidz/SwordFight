package com.example.swordfight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);
        joystick = new Joystick(275, 700, 70, 40);

        player = new Player(getContext(), 500, 500, 30);


        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
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
    }

    public void update() {
        joystick.update();
        player.update(joystick);
    }

//    public void drawFPS(Canvas canvas) {
//        String averageFPS = Double.toString(gameLoop.getAverageFPS());
//        Paint paint = new Paint();
//        int color = ContextCompat.getColor(context, R.color.magenta);
//        canvas.drawText("FPS: " + averageFPS, 100, 20, paint);
//    }
}
