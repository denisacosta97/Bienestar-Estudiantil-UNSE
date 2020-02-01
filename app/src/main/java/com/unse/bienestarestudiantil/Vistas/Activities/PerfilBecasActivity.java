package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.InfoBecas;
import com.unse.bienestarestudiantil.R;

public class PerfilBecasActivity extends AppCompatActivity {

    InfoBecas mInfoBecas;
    TextView nameBeca, fechaIni, fechaFin, desc, pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_becas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        if (getIntent().getParcelableExtra(Utils.BECA_NAME) != null) {
            mInfoBecas = getIntent().getParcelableExtra(Utils.BECA_NAME);
        }

        if (mInfoBecas != null) {
            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

    }

    private void loadData() {
        nameBeca.setText(mInfoBecas.getNameBeca());
        fechaIni.setText(mInfoBecas.getFechaInicio());
        fechaFin.setText(mInfoBecas.getFechaFin());
        desc.setText(mInfoBecas.getDesc());
        pdf.setText(mInfoBecas.getPdf());
    }

    private void loadListener() {

    }

    private void loadViews() {
        nameBeca = findViewById(R.id.txtNameBeca);
        fechaIni = findViewById(R.id.txtFechaIni);
        fechaFin = findViewById(R.id.txtFechaFin);
        desc = findViewById(R.id.txtDesc);
        pdf = findViewById(R.id.txtPdf);

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.btnRegisterDep:
//                Intent i = new Intent(getApplicationContext(), RegistroDeporteActivity.class);
//                i.putExtra(Utils.DEPORTE_NAME, mDeporte);
//                startActivity(i);
//                break;
//            case R.id.btnBack:
//                onBackPressed();
//                break;
//        }
//
//    }
}
