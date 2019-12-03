package com.unse.bienestarestudiantil.Vistas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProfesorDeportes;
<<<<<<< Updated upstream
=======
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoReservas;
>>>>>>> Stashed changes

public class PerfilProfesorActivity extends AppCompatActivity implements View.OnClickListener {

    CardView mAlumnos, mAsistencia, mInfo, mMensajes, mConfig, mCerrarS;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_profesor);

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


    private void loadListener() {
        mAlumnos.setOnClickListener(this);
        mAsistencia.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        mMensajes.setOnClickListener(this);
        mConfig.setOnClickListener(this);
        mCerrarS.setOnClickListener(this);
    }

    private void loadViews() {
        mAlumnos = findViewById(R.id.btnAlumnos);
        mAsistencia = findViewById(R.id.btnAsistencia);
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
            case R.id.btnAlumnos:
                break;
            case R.id.btnAsistencia:
                DialogoProfesorDeportes dialogoProfesorDeportes = new DialogoProfesorDeportes();
                dialogoProfesorDeportes.show(getSupportFragmentManager(),"dialog_resevas");
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
