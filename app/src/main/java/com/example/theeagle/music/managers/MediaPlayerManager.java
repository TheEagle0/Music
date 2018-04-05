package com.example.theeagle.music.managers;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;

public class MediaPlayerManager {

    @NonNull
    private final MediaPlayer mediaPlayer;
    @NonNull
    private final Uri uri;
    @NonNull
    private final OnPlaybackStateChanged onPlaybackStateChanged;

    public MediaPlayerManager(@NonNull final Application app, @NonNull final Uri uri,
                              @NonNull final OnPlaybackStateChanged onPlaybackStateChanged) {
        this.uri = uri;
        this.onPlaybackStateChanged = onPlaybackStateChanged;
        mediaPlayer = MediaPlayer.create(app, uri);
    }

    public void play() {
        if (mediaPlayer.isPlaying()) return;
        mediaPlayer.start();
        onPlaybackStateChanged.onResume();
    }

    public void pause() {
        if (!mediaPlayer.isPlaying()) return;
        mediaPlayer.pause();
        onPlaybackStateChanged.onPause();
    }

    public void release() {
        mediaPlayer.release();
        onPlaybackStateChanged.onRelease();
    }

    @NonNull
    public Uri getUri() {
        return uri;
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public interface OnPlaybackStateChanged{
        void onPause();
        void onResume();
        void onRelease();
    }
}
