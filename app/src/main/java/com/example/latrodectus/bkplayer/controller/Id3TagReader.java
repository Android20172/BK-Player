package com.example.latrodectus.bkplayer.controller;

import android.util.Log;

import com.example.latrodectus.bkplayer.model.Song;

import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import java.io.File;
import java.io.IOException;

public class Id3TagReader {
    public static Song id3Reader(String pathdata) {

        Song song = new Song();
        File src = new File(pathdata);

        Log.d("PATH", pathdata);

        MusicMetadataSet src_set = null;
        try {
            src_set = new MyID3().read(src);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } // read metadata

        if (src_set == null) // perhaps no metadata
        {
            Log.i("NULL", "NULL");
        } else {
            try {
                IMusicMetadata metadata = src_set.getSimplified();

                String artist, album, title;

                album = metadata.getAlbum();
                artist = metadata.getArtist();
                title = metadata.getSongTitle();

                nullCheck(album);
                nullCheck(artist);
                nullCheck(title);

                song.setSong_name(title);
                song.setSong_album(album);
                song.setSong_artist(artist);
                song.setSong_path(pathdata);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return song;
    }

    private static String nullCheck(String state) {
        if (state == null)
            state = "Unknown";
        return state;
    }
}
