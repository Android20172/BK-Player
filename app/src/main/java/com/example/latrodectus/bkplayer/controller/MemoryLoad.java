package com.example.latrodectus.bkplayer.controller;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

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
                    ArrayList<String> items = new ArrayList<>();
                    for (int i = 0; i < list.size(); ++i) {
                        items.add(list.get(i).toString());
                    }
                    intent.putExtra("Data", items);
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
