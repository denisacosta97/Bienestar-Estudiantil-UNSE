package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.R;

public class ConfiguracionesActivities extends AppCompatActivity implements View.OnClickListener {

    ImageView btnBack;

    LinearLayout latContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {

    }


    private void loadListener() {
        latContrasenia.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    private void openActivity(int id) {
        Intent intent = null;
        switch (id) {
            case 1:
                intent = new Intent(getApplicationContext(), CambiarContraseniaActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void loadViews() {
        latContrasenia = findViewById(R.id.latContrasenia);
        btnBack = findViewById(R.id.btnBack);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.latContrasenia:
                openActivity(1);
                break;
        }
    }
}
