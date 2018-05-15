package com.example.latrodectus.bkplayer.view;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
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

            Intent intent1 = getIntent();
            Bundle bundle = intent.getBundleExtra("Key");
            ArrayList<String> s_name = bundle.getStringArrayList("song_name");
            ArrayList<String> s_artist = bundle.getStringArrayList("song_artist");
            ArrayList<Integer> thumb = new ArrayList<>();
            for(int i = 0; i < s_name.size(); ++i) {
                thumb.add(R.drawable.music);
            }

            CustomListView arrayAdapter = new CustomListView(Main_Screen.this, R.layout.list, s_name, s_artist, thumb);
            list.setAdapter(arrayAdapter);
        }
    }

    public void getAudioAlbumImageContentUri(Context context, String filePath) {
        Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA + "=? ";
        String[] projection = new String[] { MediaStore.Audio.Media._ID , MediaStore.Audio.Media.ALBUM_ID};

        Cursor cursor = context.getContentResolver().query(
                audioUri,
                projection,
                selection,
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri imgUri = ContentUris.withAppendedId(sArtworkUri,
                    albumId);
            Log.d("TAG","AudioCoverImgUri = "+ imgUri.toString());

            Glide.with(this)
                    .load(imgUri)
                    .into(((ImageView)findViewById(R.id.imageView)));

            cursor.close();
        }
    }
}

