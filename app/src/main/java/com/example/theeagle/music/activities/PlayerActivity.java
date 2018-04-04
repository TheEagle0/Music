package com.example.theeagle.music.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.theeagle.music.R;
import com.example.theeagle.music.services.MediaPlaybackService;
import com.example.theeagle.music.util.C;

public class PlayerActivity extends AppCompatActivity implements
        View.OnClickListener, C {

    private static final String TAG = "PlayerActivity";
    private TextView trackName, artistName;
    private ImageButton play, pause;
    private static final IntentFilter intentFilter = new IntentFilter();

    static {
        intentFilter.addAction(ACTION_PLAYER_PLAY);
        intentFilter.addAction(ACTION_PLAYER_PAUSE);
        intentFilter.addAction(ACTION_PLAYER_RELEASE);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction()!=null){
              switch (intent.getAction()){
                  case ACTION_PLAYER_PAUSE:
                      changeButtonImageToPlay();
                      break;
                  case ACTION_PLAYER_PLAY:
                      changeButtonImageToPause();

                      break;
              }

            }
        }
    };

    private void changeButtonImageToPause() {
        play.setImageResource(R.drawable.ic_rounded_pause_button);
    }

    private void changeButtonImageToPlay() {
        play.setImageResource(R.drawable.ic_play_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        setListeners();
        fillingViewsWithData();
        playMedia();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private void playMedia() {
        final Uri uri = getUri();
        if (uri == null) {
            Log.e(TAG, "playMedia: uri is null ");
        }
        final Intent intent = new Intent(this, MediaPlaybackService.class);
        intent.setData(uri);
        intent.setAction(ACTION_PLAYER_PLAY);
        startService(intent);
    }

    private void pauseMedia() {
        final Intent intent = new Intent(this, MediaPlaybackService.class);
        intent.setAction(ACTION_PLAYER_PAUSE);
        startService(intent);
    }

    @Nullable
    private Uri getUri() {
        return Uri.parse(getIntent().getStringExtra(KEY_FILE_URI));
    }

    private void initViews() {
        trackName = findViewById(R.id.track_name);
        artistName = findViewById(R.id.artist_name);
        play = findViewById(R.id.play_btn);
        pause = findViewById(R.id.pause_btn);
    }

    private void setListeners() {
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
    }

    private void fillingViewsWithData() {
        trackName.setText(getIntent().getStringExtra(KEY_TRACK_NAME));
        trackName.setSelected(true);
        artistName.setText(getIntent().getStringExtra(KEY_ARTIST_NAME));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_btn:
                playMedia();
                break;
            case R.id.pause_btn:
                pauseMedia();
                break;
        }

    }
}
