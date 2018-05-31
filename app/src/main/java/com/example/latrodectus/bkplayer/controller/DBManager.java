package com.example.latrodectus.bkplayer.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.util.Log;
import android.widget.Toast;

import com.example.latrodectus.bkplayer.model.Song;
import com.example.latrodectus.bkplayer.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Music";

    private static final String TABLE_NAME = "Song";

    private static final String song_id = "song_id";
    private static final String song_name = "song_name";
    private static final String song_artist = "song_artist";
    private static final String song_album = "song_album";
    private static final String song_path = "song_path";

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DBManager", "DBManager");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                song_id + " INTEGER PRIMARY KEY, " +
                song_path + " TEXT, " +
                song_name + " TEXT, " +
                song_artist + " TEXT, " +
                song_album + " TEXT);";

        db.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfully", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();

    }

    public void addSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(song_id, getSongCount() + 1);
        values.put(song_path, song.getSong_path());
        values.put(song_name, song.getSong_name());
        values.put(song_artist, song.getSong_artist());
        values.put(song_album, song.getSong_album());

        db.insert(TABLE_NAME, "Unknown", values);

        db.close();
    }

    public Song getSongByPath(String path) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(true, TABLE_NAME,
                new String[]{song_id, song_path, song_name, song_artist, song_album},
                song_path + " = ? and ",
                new String[]{path},
                null, null, null,
                null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Song song = new Song(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );

        cursor.close();
        db.close();

        return song;
    }

    public Song getSongById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(true, TABLE_NAME,
                new String[]{song_id, song_path, song_name, song_artist, song_album},
                song_id + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null,
                null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Song song = new Song(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );

        cursor.close();
        db.close();

        return song;
    }

    public int Update(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(song_id, getSongCount() + 1);
        values.put(song_path, song.getSong_path());
        values.put(song_name, song.getSong_name());
        values.put(song_artist, song.getSong_artist());
        values.put(song_album, song.getSong_album());

        return db.update(TABLE_NAME, values, song_id + " = ?",
                new String[]{String.valueOf(song.getSong_id())});
    }

    public List<Song> getAllSong() {
        List<Song> songList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Song song = new Song(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                songList.add(song);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return songList;
    }

    public void deleteSong(Song song) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, song_path + " = ?",
                new String[] {song.getSong_path()});

        db.close();
    }

    public int getSongCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME; //Có thể dùng select count(*) from...
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}
