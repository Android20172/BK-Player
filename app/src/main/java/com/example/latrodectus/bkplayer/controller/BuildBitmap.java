package com.example.latrodectus.bkplayer.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class BuildBitmap {
    public static ArrayList<Bitmap> build(ArrayList<byte[]> data) {
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
        for (int i = 0; i < data.size(); ++i) {
            if (data.get(i) != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.get(i), 0, data.get(i).length);
                bitmapArrayList.add(bitmap);
            }
        }
        return bitmapArrayList;
    }
}
