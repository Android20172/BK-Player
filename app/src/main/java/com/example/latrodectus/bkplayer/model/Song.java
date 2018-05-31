package com.example.latrodectus.bkplayer.model;

public class Song {
    private int song_id = -1;
    private String song_name = "default_song_name";
    private String song_path = "default_song_path";
    private String song_artist = "default_song_artist";
    private String song_album = "default_song_album";

    public Song() {
    }

    public Song(int id, String path, String name, String artist, String album) {
        this.song_id = id;
        this.song_name = name;
        this.song_album = album;
        this.song_artist = artist;
        this.song_path = path;
    }


    @Override
    public String toString() {
        return "Song{" +
                "song_id=" + song_id +
                ", song_name='" + song_name + '\'' +
                ", song_path='" + song_path + '\'' +
                ", song_artist='" + song_artist + '\'' +
                ", song_album='" + song_album + '\'' +
                '}';
    }

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
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

}
