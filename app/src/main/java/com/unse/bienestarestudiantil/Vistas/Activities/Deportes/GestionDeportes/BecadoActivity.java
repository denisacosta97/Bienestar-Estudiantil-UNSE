package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.R;

public class BecadoActivity extends AppCompatActivity implements View.OnClickListener{

    Alumno mAlumno;
    ImageView imgIcono, imgPerfil;
    TextView txtName, txtLeg, txtDeporte, txtTotAsistencia;
    Button btnGenerar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_becado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.TORNEO) != null) {
            mAlumno = getIntent().getParcelableExtra(Utils.TORNEO);
        }

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Becados");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnGenerar.setOnClickListener(this);
    }

    private void loadData() {
        imgPerfil.setImageResource(R.drawable.imgdeportes);
        txtName.setText(String.format("%s\n%s", mAlumno.getNombre(), mAlumno.getApellido()));
        txtLeg.setText(mAlumno.getLegajo());
        txtDeporte.setText("-");
        txtTotAsistencia.setText("-");
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        imgPerfil = findViewById(R.id.imgBecado);
        txtName = findViewById(R.id.txtNamApe);
        txtLeg = findViewById(R.id.txtLegajo);
        txtDeporte = findViewById(R.id.txtDeporte);
        txtTotAsistencia = findViewById(R.id.txtTotAsistencia);
        btnGenerar = findViewById(R.id.btnGenerar);
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
