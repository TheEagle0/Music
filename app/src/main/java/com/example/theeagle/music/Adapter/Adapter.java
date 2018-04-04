package com.example.theeagle.music.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.theeagle.music.Activities.PlayerActivity;
import com.example.theeagle.music.Model.Info;
import com.example.theeagle.music.R;

import java.util.ArrayList;


/**
 * Created by theeagle on 4/3/18
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Info> audioFilesList;
    private Context context;

    public Adapter(Context context, ArrayList<Info> audioFilesList) {
        this.audioFilesList = audioFilesList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view, context, audioFilesList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.info = audioFilesList.get(position);
        holder.trackName.setText(holder.info.getTrackName());
        holder.artistName.setText(holder.info.getArtistName());
    }

    @Override
    public int getItemCount() {
        return audioFilesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trackName, artistName;
        private ArrayList<Info> infos = new ArrayList<>();
        public Info info;
        private Context context;

        private ViewHolder(View itemView, Context context, ArrayList<Info> infos) {

            super(itemView);
            this.infos = infos;
            this.context = context;
            itemView.setOnClickListener(this);
            trackName = itemView.findViewById(R.id.track_name);
            trackName.setSelected(true);
            artistName = itemView.findViewById(R.id.artist_name);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Info info = this.infos.get(position);
            context.startActivity(new Intent(this.context, PlayerActivity.class)
                    .putExtra("track name", info.getTrackName())
                    .putExtra("artist name", info.getArtistName())
                    .putExtra("file uri", info.getUri()));

        }
    }
}
