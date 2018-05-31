package com.example.latrodectus.bkplayer.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.latrodectus.bkplayer.R;
import com.example.latrodectus.bkplayer.controller.DBManager;
import com.example.latrodectus.bkplayer.controller.MemoryLoad;
import com.example.latrodectus.bkplayer.controller.Play_Controller;
import com.example.latrodectus.bkplayer.model.Song;

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Main_Screen extends AppCompatActivity {
    Intent intent;
    ListView list;
    ActionBar actionBar;
    SearchView txtSearchValue;
    int oldPos = -1;

    MediaPlayer mediaPlayer;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main_screen);

        actionBar = getSupportActionBar();
        actionBar.setTitle("ActionBarDemo3");
        actionBar.setSubtitle("Version3.0");
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true); // allow custom views to be shown
        actionBar.setDisplayHomeAsUpEnabled(true); // show ‘UP’ affordance < button
        actionBar.setDisplayShowHomeEnabled(true); // allow app icon – logo to be shown
        actionBar.setHomeButtonEnabled(true);
        actionBar.setLogo(R.drawable.menu_icon);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        list = (ListView) findViewById(R.id.list_music);

        mediaPlayer = new MediaPlayer();

        intent = new Intent(this, MemoryLoad.class);
        startService(intent);

        IntentFilter filter = new IntentFilter("com.example.latrodectus.bkplayer.SHOW_DATA");

        DBManager dbManager = new DBManager(Main_Screen.this);
        MyBroadcastReceiver receiver;
        if (dbManager.getSongCount() == 0) {
            Log.d("CONTEXT", "LOAD FROM MEMORY");

            receiver = new MyBroadcastReceiver();

            registerReceiver(receiver, filter);
        } else {
            Log.d("CONTEXT", "LOAD FROM DATABASE");
            DBload(dbManager);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen_menu, menu);
        txtSearchValue = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        txtSearchValue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                invalidateOptionsMenu();
                txtSearchValue.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            return true;
        } else if (id == R.id.action_share) {
            return true;
        } else if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_about) {
            final Dialog dialog = new Dialog(Main_Screen.this);
            dialog.setContentView(R.layout.dialog_about_us);
            dialog.setTitle("About Us");
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            System.exit(1);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void DBload(DBManager dbManager) {
        ArrayList<String> s_name = new ArrayList<>();
        ArrayList<String> s_artist = new ArrayList<>();
        ArrayList<String> s_album = new ArrayList<>();
        ArrayList<String> s_path = new ArrayList<>();
        List<Song> songArrayList = dbManager.getAllSong();
        ArrayList<Integer> thumb = new ArrayList<>();
        for (int i = 0; i < dbManager.getSongCount(); ++i) {
            s_name.add(songArrayList.get(i).getSong_name());
            s_artist.add(songArrayList.get(i).getSong_artist());
            s_album.add(songArrayList.get(i).getSong_album());
            s_path.add(songArrayList.get(i).getSong_path());
            thumb.add(R.drawable.music);
        }
        CustomListView arrayAdapter = new CustomListView(Main_Screen.this, R.layout.list, s_name, s_artist, thumb);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                task(position);
            }
        });
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("Key");
            ArrayList<String> s_name = bundle.getStringArrayList("song_name");
            ArrayList<String> s_artist = bundle.getStringArrayList("song_artist");
            ArrayList<String> s_album = bundle.getStringArrayList("song_album");
            ArrayList<Integer> thumb = new ArrayList<>();
            ArrayList<String> s_path = bundle.getStringArrayList("song_path");
            for (int i = 0; i < s_name.size(); ++i) {
                thumb.add(R.drawable.music);
            }
            CustomListView arrayAdapter = new CustomListView(Main_Screen.this, R.layout.list, s_name, s_artist, thumb);
            list.setAdapter(arrayAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    task(position);
                }
            });
        }
    }

    public void task(int position) {

        Intent intent = new Intent(Main_Screen.this, Detail_Screen.class);
        Main_Screen.this.startActivity(intent);
        int newPos = position;
        if (oldPos != newPos) {
            final Song song = new DBManager(Main_Screen.this).getSongById(position + 1);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            oldPos = position;
            mediaPlayer = Play_Controller.playBackMusic(song);
        }
    }
}
