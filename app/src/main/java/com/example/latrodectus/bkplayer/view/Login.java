package com.example.latrodectus.bkplayer.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.latrodectus.bkplayer.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); //By hieu

        Button skip_login = findViewById(R.id.skip_login);
        skip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Main_Screen.class);
                Login.this.startActivity(intent);
            }
        });

    }
}
