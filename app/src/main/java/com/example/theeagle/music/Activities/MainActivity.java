package com.example.theeagle.music.Activities;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.theeagle.music.Adapter.Adapter;
import com.example.theeagle.music.Model.Info;
import com.example.theeagle.music.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Info> audioFilesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initArrays();
        initViews();
    }

    private void initViews() {
        RecyclerView audioFilesRecyclerView = findViewById(R.id.audio_recycler_view);
        audioFilesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this, audioFilesList);
        audioFilesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initArrays() {
        audioFilesList = new ArrayList<>();
        String[] projection = {MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Info info = new Info();
                int displayNameIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                int displayArtistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                int dataIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                info.setTrackName(cursor.getString(displayNameIndex));
                info.setArtistName(cursor.getString(displayArtistIndex));
                info.setUri(cursor.getString(dataIndex));
                audioFilesList.add(info);
            }
            cursor.close();
        }

    }
}
