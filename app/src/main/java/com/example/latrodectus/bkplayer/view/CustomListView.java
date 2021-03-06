package com.example.latrodectus.bkplayer.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.latrodectus.bkplayer.R;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> song_name;
    ArrayList<String> song_artist;
    ArrayList<Integer> thumb;

    public CustomListView(Context context, int layoutToBeInflated,
                          ArrayList<String> song_name, ArrayList<String> song_artist, ArrayList<Integer> thumb) {
        super(context, layoutToBeInflated, song_name);
        this.context = context;
        this.song_name = song_name;
        this.song_artist = song_artist;
        this.thumb = thumb;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.list, null);
        TextView s_name = (TextView) row.findViewById(R.id.song_name);
        TextView s_artist = (TextView) row.findViewById(R.id.song_artist);
        ImageView s_cover = (ImageView) row.findViewById(R.id.imageView);

        s_name.setText(song_name.get(position));
        s_artist.setText(song_artist.get(position));
        s_cover.setImageResource(thumb.get(position));

        return (row);
    }
}
