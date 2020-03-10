package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.MainActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mInicio;
    ImageView btnBack;
    RelativeLayout layoutFondo;
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layoutFondo = findViewById(R.id.backgroundlogin);

        Glide.with(this).load(R.drawable.img_unse2)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        layoutFondo.setBackground(resource);
                    }
                });

        mVideoView = findViewById (R.id.videoView);
        Uri uri = Uri.parse("android.resource://".concat(getPackageName()).concat("/raw/").concat(String.valueOf(R.raw.video_bacl)));
        //mVideoView.setVideoURI(uri);
        //mVideoView.start();

        /*mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/

        loadViews();

        loadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to restart the video after coming from other activity like Sing up
        //mVideoView.start();
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
    }

    private void loadViews() {
        mInicio = findViewById(R.id.sesionOn);
        btnBack = findViewById(R.id.btnBack);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sesionOn:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.txtPassMissed:
                startActivity(new Intent(LoginActivity.this, PassMissedActivity.class));
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

}
