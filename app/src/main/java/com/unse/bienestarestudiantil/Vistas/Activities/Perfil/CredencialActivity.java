package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.unse.bienestarestudiantil.Herramientas.Credencial.CredencialView;
import com.unse.bienestarestudiantil.R;

public class CredencialActivity extends AppCompatActivity {

    Button btnAceptar;
    CredencialView mCredencialView;
    boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credencial_test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();

    }

    private void loadListener() {
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBack){
                    mCredencialView.showFront();
                    isBack = false;
                }else{
                    mCredencialView.showBack();
                    isBack = true;
                }
            }
        });
    }

    private void loadData() {
    }

    private void loadViews() {
        btnAceptar = findViewById(R.id.btnAceptar);
        mCredencialView = findViewById(R.id.credencial);
    }
}
