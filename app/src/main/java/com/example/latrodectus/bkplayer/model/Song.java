package com.example.latrodectus.bkplayer.model;

public class Song {
    private String song_name = "default_song_name";
    private String song_path = "default_song_path";
    private String song_artist = "default_song_artist";
    private String song_album = "default_song_album";
    private Number song_track_number = 0;

    public Song() {
    }

    public Song(String name, String path, String artist, String album, int track_number) {
        this.song_name = name;
        this.song_album = album;
        this.song_artist = artist;
        this.song_path = path;
        this.song_track_number = track_number;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSong_path() {
        return song_path;
    }

    public void setSong_path(String song_path) {
        this.song_path = song_path;
    }

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public String getSong_album() {
        return song_album;
    }

    public void setSong_album(String song_album) {
        this.song_album = song_album;
    }

    public Number getSong_track_number() {
        return song_track_number;
    }

    public void setSong_track_number(Number song_track_number) {
        this.song_track_number = song_track_number;
    }
}
