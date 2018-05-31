package com.example.latrodectus.bkplayer.controller;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.latrodectus.bkplayer.model.Song;

import java.io.File;
import java.util.ArrayList;

public class MemoryLoad extends Service {
    boolean isRunning = true;

    DBManager dbManager;

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

        if(new DBManager(MemoryLoad.this).getSongCount() == 0) { //sua sau, moi lan them bai hat moi phai xoa du lieu hoac cai dat lai
            isRunning = true;
            Toast.makeText(this, "Loading file from memory.", Toast.LENGTH_LONG);
            Log.d("onStartCommand: ", "Success");
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Intent intent = new Intent("com.example.latrodectus.bkplayer.SHOW_DATA");

                        Bundle bundle = new Bundle();

                        ArrayList<File> list = ExternalMemoryScan.getList(Environment.getExternalStorageDirectory());

                        ArrayList<String> s_name = new ArrayList<>();
                        ArrayList<String> s_artist = new ArrayList<>();
                        ArrayList<String> s_album = new ArrayList<>();
                        ArrayList<String> s_path = new ArrayList<>();

                        for (int i = 0; i < list.size(); ++i) {
                            Song song = Id3TagReader.id3Reader(list.get(i).toString());
                            song.setSong_id(i + 1);

                            if(song.getSong_name() != null) {
                                dbManager = new DBManager(MemoryLoad.this);
                                dbManager.addSong(song);


                                Log.d("Song Info", "Name: " + song.getSong_name() +
                                        "\nArtist: " + song.getSong_artist() +
                                        "\nAlbum: " + song.getSong_album() +
                                        "\nPath: " + song.getSong_path());

                                s_name.add(song.getSong_name());
                                s_artist.add(song.getSong_artist());
                                s_album.add(song.getSong_album());
                                s_path.add(song.getSong_path());
                            }
                        }

                        bundle.putStringArrayList("song_name", s_name);
                        bundle.putStringArrayList("song_artist", s_artist);
                        bundle.putStringArrayList("song_album", s_album);
                        bundle.putStringArrayList("song_path", s_path);

                        intent.putExtra("Key", bundle);
                        sendBroadcast(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Toast.makeText(this, "Loading file complete.", Toast.LENGTH_LONG);
    }
}