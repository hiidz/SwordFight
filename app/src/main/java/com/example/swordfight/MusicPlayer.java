package com.example.swordfight;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer extends Thread {
    private MediaPlayer mediaPlayer;
    private Context context;
    private int musicResId;

    public MusicPlayer(Context context, int musicResId) {
        this.context = context;
        this.musicResId = musicResId;
    }

    @Override
    public void run() {
        mediaPlayer = MediaPlayer.create(context, musicResId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

// Create a MusicPlayer object
// MusicPlayer musicPlayer = new MusicPlayer(this, R.raw.my_music);

// Start playing the music on a separate thread
// musicPlayer.start();

// Pause the music from the main thread
// musicPlayer.pauseMusic();

// Stop the music and release resources from the main thread
// musicPlayer.stopMusic();