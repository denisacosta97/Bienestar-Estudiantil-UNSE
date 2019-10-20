package com.unse.bienestarestudiantil.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.R;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {

    CardView mHistoriaC, mCredenciales, mInfo, mMensajes, mConfig, mCerrarS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        mHistoriaC = findViewById(R.id.btnHistoriac);
        mCredenciales = findViewById(R.id.btnCredenciales);
        mInfo = findViewById(R.id.btnInfo);
        mMensajes = findViewById(R.id.btnMensajes);
        mConfig = findViewById(R.id.btnConfig);
        mCerrarS = findViewById(R.id.btnCerrarses);
        mHistoriaC.setOnClickListener(this);
        mCredenciales.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        mMensajes.setOnClickListener(this);
        mConfig.setOnClickListener(this);
        mCerrarS.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHistoriac:
                break;
            case R.id.btnCredenciales:
                startActivity(new Intent(UserProfile.this, Credencial.class));
                finish();
                break;
            case R.id.btnInfo:
                startActivity(new Intent(UserProfile.this, InfoAlumno.class));
                finish();
                break;
            case R.id.btnMensajes:
                break;
            case R.id.btnConfig:
                break;
            case R.id.btnCerrarses:
                break;
        }
    }
}
