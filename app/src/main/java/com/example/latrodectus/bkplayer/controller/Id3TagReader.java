package com.example.latrodectus.bkplayer.controller;

import android.util.Log;

import com.example.latrodectus.bkplayer.model.Song;

import org.cmc.music.common.ID3WriteException;
import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Id3TagReader {
    public Song id3Reader(String pathdata) {
        File src = new File(pathdata);
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
        }
        else
        {
            try{
                IMusicMetadata metadata = src_set.getSimplified();
                String artist = metadata.getArtist();
                String album = metadata.getAlbum();
                String song_title = metadata.getSongTitle();
                Number track_number = metadata.getTrackNumber();
                Log.i("artist", artist);
                Log.i("album", album);
            }catch (Exception e) {
                e.printStackTrace();
            }
            File dst = new File(pathdata);
            MusicMetadata meta = new MusicMetadata("name");
            meta.setAlbum("Chirag");
            meta.setArtist("CS");
            try {
                new MyID3().write(src, dst, src_set, meta);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ID3WriteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }  // write updated metadata
        }
        return null;
    }
}
