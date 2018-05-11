package com.example.latrodectus.bkplayer.controller;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.latrodectus.bkplayer.model.Song;

import java.io.File;
import java.util.ArrayList;

public class MemoryLoad extends Service {
    boolean isRunning = true;

    public MemoryLoad() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        Toast.makeText(this, "Loading file from memory.", Toast.LENGTH_LONG);
        Log.d("onStartCommand: ", "Success");
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent("com.example.latrodectus.bkplayer.SHOW_DATA");

                    ArrayList<File> list = ExternalMemoryScan.getList(Environment.getExternalStorageDirectory());

                    ArrayList<String> s_name = new ArrayList<>();
                    ArrayList<String> s_artist = new ArrayList<>();
                    ArrayList<String> s_album = new ArrayList<>();
                    ArrayList<String> s_path = new ArrayList<>();
                    ArrayList<Number> s_track = new ArrayList<>();

                    for (int i = 0; i < list.size(); ++i) {
                        Song song = Id3TagReader.id3Reader(list.get(i).toString());

                        Log.d("Song Info", "Name: " + song.getSong_name() +
                        "\nArtist: " + song.getSong_artist() +
                        "\nAlbum: " + song.getSong_album() +
                        "\nPath: " + song.getSong_path() +
                        "\nTrack number: " + song.getSong_track_number());

                        s_name.add(song.getSong_name());
                        s_artist.add(song.getSong_artist());
                        s_album.add(song.getSong_album());
                        s_path.add(song.getSong_artist());
                        s_track.add(song.getSong_track_number());
                    }
                    intent.putExtra("song_name", s_name);
                    intent.putExtra("song_artist", s_artist);
                    intent.putExtra("song_album", s_album);
                    intent.putExtra("song_path", s_path);
                    intent.putExtra("song_track", s_track);
                    sendBroadcast(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Toast.makeText(this, "Loading file complete.", Toast.LENGTH_LONG);
    }
}
