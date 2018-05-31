package com.example.latrodectus.bkplayer.controller;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.example.latrodectus.bkplayer.model.Song;

public class Play_Controller {
    public static MediaPlayer playBackMusic(Song song) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(song.getSong_path());
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //endOfTheSong();
                }
            });

            //isPlaying = true;
            mediaPlayer.start();
            return mediaPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
