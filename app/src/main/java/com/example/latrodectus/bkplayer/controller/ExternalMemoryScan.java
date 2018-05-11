package com.example.latrodectus.bkplayer.controller;

import java.io.File;
import java.util.ArrayList;

public class ExternalMemoryScan { //include sdcard and external memory
    public static ArrayList<File> getList(File root_path) {
        ArrayList<File> list = new ArrayList<File>();
        File[] files = root_path.listFiles(); //load add folder and file from path
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (!file.isHidden()) {
                        list.addAll(getList(file)); //de quy
                    }
                } else {
                    if (!file.getName().startsWith(".") && !file.getName().startsWith("/storage/emulated/0/Android/")) {
                        if (file.getName().endsWith(".mp3")
                                || file.getName().endsWith(".flag")
                                || file.getName().endsWith(".wma")
                                || file.getName().endsWith(".wav")) {
                            list.add(file);
                        }
                    }
                }
            }
        }
        return list;
    }
}
