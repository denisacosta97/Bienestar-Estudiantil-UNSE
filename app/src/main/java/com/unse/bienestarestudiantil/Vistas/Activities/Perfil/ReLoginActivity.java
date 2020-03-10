package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.LoginActivity;

public class ReLoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnIni;
    RelativeLayout layoutFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layoutFondo = findViewById(R.id.backgroundlogin);

        Glide.with(this).load(R.drawable.img_unse2)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        layoutFondo.setBackground(resource);
                    }
                });

        loadViews();

        loadListener();
    }

    private void loadListener() {
        btnIni.setOnClickListener(this);
    }

    private void loadViews() {
        btnIni = findViewById(R.id.btnIni);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIni:
                startActivity(new Intent(ReLoginActivity.this, LoginActivity.class));
                break;

        }

    }

}
