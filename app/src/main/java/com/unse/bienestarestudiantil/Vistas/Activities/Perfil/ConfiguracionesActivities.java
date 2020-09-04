package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.R;

public class ConfiguracionesActivities extends AppCompatActivity implements View.OnClickListener {

    ImageView btnBack;

    LinearLayout latContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuraciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
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
        btnBack = findViewById(R.id.imgFlecha);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.latContrasenia:
                openActivity(1);
                break;
        }
    }
}
