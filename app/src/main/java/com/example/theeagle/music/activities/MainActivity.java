package com.example.theeagle.music.activities;

import android.Manifest;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.theeagle.music.R;
import com.example.theeagle.music.adapter.Adapter;
import com.example.theeagle.music.model.Info;
import com.example.theeagle.music.util.C;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements C, LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<Info> audioFilesList;
    private String[] projection = {MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.activity_label);
        checkStoragePermission();

    }


    private void initCursorLoader() {
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else initCursorLoader();
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION);
        }
    }

    private void initViews() {
        RecyclerView audioFilesRecyclerView = findViewById(R.id.audio_recycler_view);
        audioFilesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this, audioFilesList);
        audioFilesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
                    initCursorLoader();
                }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        audioFilesList = new ArrayList<>();
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
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
        initViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
