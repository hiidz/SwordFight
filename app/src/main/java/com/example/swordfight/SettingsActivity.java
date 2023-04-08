package com.example.swordfight;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ToggleButton audioToggleButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        // Initialize the toggle button and media player
        audioToggleButton = findViewById(R.id.audio_togglebutton);
        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(true);
        audioToggleButton.setOnCheckedChangeListener(this);

        Log.d("test1", mediaPlayer.toString());
        Log.d("test2", audioToggleButton.toString());


        // Set the initial state of the toggle button based on whether the media player is playing
        if (mediaPlayer.isPlaying()) {
            audioToggleButton.setChecked(true);
        } else {
            audioToggleButton.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        // Start or stop the media player based on the state of the toggle button
        if (isChecked) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the media player when the activity is destroyed
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
