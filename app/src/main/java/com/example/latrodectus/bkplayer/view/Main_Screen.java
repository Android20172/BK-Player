package com.example.latrodectus.bkplayer.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.latrodectus.bkplayer.R;
import com.example.latrodectus.bkplayer.controller.MemoryLoad;

import java.util.ArrayList;

public class Main_Screen extends AppCompatActivity {
    Intent intent;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getSupportActionBar().hide(); //by Hieu

        //String root_external = Environment.getExternalStorageDirectory().toString();
        //String root_sdcard = Main_Screen.this.getExternalCacheDir().getAbsolutePath();

        list = (ListView) findViewById(R.id.list_music);

        intent = new Intent(this, MemoryLoad.class);
        startService(intent);

        IntentFilter filter = new IntentFilter("com.example.latrodectus.bkplayer.SHOW_DATA");
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();

        registerReceiver(receiver, filter);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> s_name = intent.getStringArrayListExtra("song_name");
            ArrayList<String> s_artist = intent.getStringArrayListExtra("song_artist");
            ArrayList<String> s_album = intent.getStringArrayListExtra("song_album");
            ArrayList<String> s_path = intent.getStringArrayListExtra("song_path");
            ArrayList<String> s_track = intent.getStringArrayListExtra("song_track");

            CustomListView arrayAdapter = new CustomListView(Main_Screen.this, R.layout.list, s_artist, s_name);
            list.setAdapter(arrayAdapter);
        }
    }
}

