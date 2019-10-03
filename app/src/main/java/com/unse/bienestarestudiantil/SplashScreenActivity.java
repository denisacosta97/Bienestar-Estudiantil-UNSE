package com.unse.bienestarestudiantil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mProgress;
    Animation mAnimation;
    LinearLayout linearFondo;
    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Utils.setFont(getApplicationContext(), this, "futura_normal.ttf");

        mProgress = findViewById(R.id.splash_screen_progress_bar);
        linearFondo = findViewById(R.id.splashbackgr);

        Glide.with(this).load(R.color.colorPrimary)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        linearFondo.setBackground(resource);
                        linearFondo.startAnimation(mAnimation);
                    }
                });

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginWelcomeActivity.class));
                finish();
            }
        }, 4500);

    }


}
