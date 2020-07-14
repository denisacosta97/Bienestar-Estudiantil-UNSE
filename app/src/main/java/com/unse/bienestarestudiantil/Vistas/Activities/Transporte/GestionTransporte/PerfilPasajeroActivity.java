package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

public class PerfilPasajeroActivity extends AppCompatActivity implements View.OnClickListener {

    Usuario mUsuario;
    TextView mName, mDni;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pasajero);

        if (getIntent().getParcelableExtra(Utils.USER_NAME) != null) {
            mUsuario = getIntent().getParcelableExtra(Utils.USER_NAME);
        }

        if (mUsuario != null) {
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
        mName.setText("");
        mDni.setText("");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadViews() {
        mName = findViewById(R.id.txtName);
        mDni = findViewById(R.id.txtDni);

        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Pasajero");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgIcon:
                onBackPressed();
                break;
        }
    }

}
