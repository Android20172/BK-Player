package com.example.latrodectus.bkplayer.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.latrodectus.bkplayer.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); //By hieu
    }
}
