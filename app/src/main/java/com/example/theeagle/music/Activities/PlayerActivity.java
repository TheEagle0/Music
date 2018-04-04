package com.example.theeagle.music.Activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.theeagle.music.R;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView trackName, artistName;
    private Button play, pause, stop;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mediaPlayer = MediaPlayer.create(this, Uri.parse(getIntent().getStringExtra("file uri")));
        mediaPlayer.start();
        initViews();
        listeners();
        fillingViewsWithData();
    }

    private void initViews() {
        trackName = findViewById(R.id.track_name);
        artistName = findViewById(R.id.artist_name);
        play = findViewById(R.id.play_btn);
        pause = findViewById(R.id.pause_btn);
        stop=findViewById(R.id.stop_btn);
    }

    private void listeners() {
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    private void fillingViewsWithData() {
        trackName.setText(getIntent().getStringExtra("track name"));
        trackName.setSelected(true);
        artistName.setText(getIntent().getStringExtra("artist name"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_btn:
                mediaPlayer.start();
                break;
            case R.id.pause_btn:
                mediaPlayer.pause();
                break;
            case R.id.stop_btn:
                mediaPlayer.stop();
                break;
        }

    }
}
