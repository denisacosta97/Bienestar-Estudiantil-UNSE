package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;

public class PerfilTorneoActivity extends AppCompatActivity implements View.OnClickListener {

    Torneo mTorneo;
    TextView mDesc, mFechas, mLugar, mNombreTorneo;
    ImageView imgIcono;
    Button btnPostular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_torneo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.TORNEO) != null) {
            mTorneo = getIntent().getParcelableExtra(Utils.TORNEO);
        }

        if (mTorneo != null) {
            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

        setToolbar();

    }

    private void loadData() {
        mNombreTorneo.setText(mTorneo.getNameTorneo());
        mLugar.setText(mTorneo.getLugar());
        mFechas.setText(String.format("%s %s", mTorneo.getFechaInicio(), mTorneo.getFechaFin()));
        mDesc.setText(mTorneo.getDesc());

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnPostular.setOnClickListener(this);
    }

    private void loadViews() {
        mNombreTorneo = findViewById(R.id.txtNameTorneo);
        mFechas = findViewById(R.id.txtFechas);
        mLugar = findViewById(R.id.txtLugar);
        mDesc = findViewById(R.id.txtDesc);
        btnPostular = findViewById(R.id.btnPostular);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegisterDep:
                Intent i = new Intent(getApplicationContext(), PostularListaActiviy.class);
                i.putExtra(Utils.DEPORTE_NAME, mTorneo);
                startActivity(i);
                break;
            case R.id.imgIcon:
                onBackPressed();
                break;
        }
    }

}
