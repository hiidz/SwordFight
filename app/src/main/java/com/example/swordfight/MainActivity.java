package com.example.swordfight;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

// MainActivity is the entry point to our application
public class MainActivity extends Activity {
    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Set fullscreen
//        WindowInsets window = new  WindowInsetsController ();
//        window.insetsController.hide(WindowInsets.Type.statusBars());
        game = new Game(this);
        setContentView(game);
    }
}