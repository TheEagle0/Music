package com.example.theeagle.music.services;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.theeagle.music.managers.MediaPlayerManager;
import com.example.theeagle.music.util.C;

public class MediaPlaybackService extends Service implements C,
        MediaPlayerManager.OnPlaybackStateChanged {

    private static final String TAG = "MediaPlaybackService";
    private MediaPlayerManager mediaPlayerManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleComingIntent(intent);
        return START_NOT_STICKY;
    }

    private void handleComingIntent(final Intent intent) {
        if (intent == null) {
            Log.e(TAG, "handleComingIntent: intent is null");
            return;
        }
        final String action = intent.getAction();
        if (action == null) {
            Log.e(TAG, "handleComingIntent: action is null");
            return;
        }
        switch (action) {
            case ACTION_PLAYER_PAUSE: {
                pause();
            }
            break;
            case ACTION_PLAYER_PLAY: {
                initPlayer(intent);
                play();
            }
            break;
            default: {
                Log.e(TAG, "handleComingIntent: unknown action -> " + action + "");
            }
        }
    }

    private void pause() {
        if (mediaPlayerManager == null) return;
        mediaPlayerManager.pause();
    }

    private void initPlayer(Intent intent) {
        initMediaPlayer(intent.getData());
    }

    private void play() {
        mediaPlayerManager.play();
    }

    private void initMediaPlayer(Uri uri) {
        if (mediaPlayerManager != null && uri.equals(mediaPlayerManager.getUri())) {
            Log.e(TAG, "initMediaPlayer: not init media player is already init");
            return;
        }
        if (uri == null) {
            Log.e(TAG, "initMediaPlayer: uri is null not init media player");
            return;
        }
        if (mediaPlayerManager != null) {
            mediaPlayerManager.stop();
            mediaPlayerManager.release();
            mediaPlayerManager = null;
        }
        mediaPlayerManager = new MediaPlayerManager(this.getApplication(), uri,this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onPause() {
        sendEvent(ACTION_PLAYER_PAUSE);
    }

    @Override
    public void onResume() {
        sendEvent(ACTION_PLAYER_PLAY);
    }

    @Override
    public void onRelease() {
        sendEvent(ACTION_PLAYER_RELEASE);
    }

    private void sendEvent(@NonNull final String action){
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
    }
}
