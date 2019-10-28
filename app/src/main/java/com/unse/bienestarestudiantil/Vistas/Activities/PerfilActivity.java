package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    CardView mHistoriaC, mCredenciales, mInfo, mMensajes, mConfig, mCerrarS;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Utils.setFont(getApplicationContext(), (ViewGroup)findViewById(android.R.id.content),Utils.MONSERRAT);

        setToolbar();

        loadViews();

        loadListener();
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loadListener() {
        mHistoriaC.setOnClickListener(this);
        mCredenciales.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        mMensajes.setOnClickListener(this);
        mConfig.setOnClickListener(this);
        mCerrarS.setOnClickListener(this);
    }

    private void loadViews() {
        mHistoriaC = findViewById(R.id.btnHistoriac);
        mCredenciales = findViewById(R.id.btnCredenciales);
        mInfo = findViewById(R.id.btnInfo);
        mMensajes = findViewById(R.id.btnMensajes);
        mConfig = findViewById(R.id.btnConfig);
        mCerrarS = findViewById(R.id.btnCerrarses);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnHistoriac:
                break;
            case R.id.btnCredenciales:
                startActivity(new Intent(PerfilActivity.this, CredencialActivity.class));
                break;
            case R.id.btnInfo:
                startActivity(new Intent(PerfilActivity.this, InfoAlumnoActivity.class));
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
