package com.example.flight_mode;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_STICKY;
    }
    
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {
    }
}