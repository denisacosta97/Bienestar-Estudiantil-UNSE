package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

public class PerfilDeporteActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    TextView mHorario, mDia, mEntrenador, mNombreDep;
    ImageView mIcon, btnBack;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_deporte);

        Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null) {
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }

        if (mDeporte != null) {
            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }


    }

    private void loadData() {
        mEntrenador.setText(mDeporte.getEntrenador());
        mHorario.setText(mDeporte.getHorario());
        mDia.setText(mDeporte.getDias());
        mNombreDep.setText(mDeporte.getName());
        mIcon.setImageResource(mDeporte.getIconDeporte());
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    private void loadViews() {
        mHorario = findViewById(R.id.txtHorarios);
        mDia = findViewById(R.id.txtDia);
        mEntrenador = findViewById(R.id.txtEntrenador);
        mNombreDep = findViewById(R.id.txtNameDeporte);
        mIcon = findViewById(R.id.imgvIcon);
        btnRegister = findViewById(R.id.btnRegisterDep);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegisterDep:
                Intent i = new Intent(getApplicationContext(), RegistroDeporteActivity.class);
                i.putExtra(Utils.DEPORTE_NAME, mDeporte);
                startActivity(i);
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }

    }
}
