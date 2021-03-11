package com.example.doscerocuatroocho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }
    public void play(@NonNull View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void scores(@NonNull View view) {
        Intent intent = new Intent(MainActivity.this, Scores.class);
        startActivity(intent);
    }
}