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

                String artist, album, song_title;
                Number track_number;

                if(nullCheck(metadata.getArtist())) {
                    artist = metadata.getArtist();
                    song.setSong_artist(artist);
                }
                if(nullCheck(metadata.getAlbum())) {
                    album = metadata.getAlbum();
                    song.setSong_album(album);
                }
                if(nullCheck(metadata.getSongTitle())) {
                    song_title = metadata.getSongTitle();
                    song.setSong_name(song_title);
                }
                track_number = metadata.getTrackNumber();

                song.setSong_track_number(track_number);
                song.setSong_path(pathdata);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return song;
    }

    private static boolean nullCheck(String state) {
        if (state == null) {
            state = "Unknown";
            return true;
        }
        return false;
    }
}

