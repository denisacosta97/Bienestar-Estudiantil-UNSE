package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.RegisterActivity;

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

        Utils.setFont(getApplicationContext(), (ViewGroup)findViewById(android.R.id.content), Utils.MONSERRAT);

        mVideoView = findViewById (R.id.videoView);
        Uri uri = Uri.parse("android.resource://".concat(getPackageName()).concat("/raw/").concat(String.valueOf(R.raw.video_bacl)));
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        loadViews();

        loadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to restart the video after coming from other activity like Sing up
        mVideoView.start();
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
            case R.id.btnBack:
                onBackPressed();
                break;
        }

    }

}
