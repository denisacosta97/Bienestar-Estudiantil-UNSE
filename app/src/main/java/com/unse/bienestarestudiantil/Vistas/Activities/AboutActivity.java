package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void loadData() {

    }

    private void loadListener() {
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Sobre nosotros");
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
