package com.example.doscerocuatroocho;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity  extends AppCompatActivity {

    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        im = (ImageView) findViewById(R.id.logoSplash);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.blink_anim);
                im.startAnimation(animation);
                Timer tiempo = new Timer();

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                };
                tiempo.schedule(task, 3000);
            }
        });
    }
}



