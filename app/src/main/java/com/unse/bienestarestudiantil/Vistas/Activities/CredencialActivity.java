package com.unse.bienestarestudiantil.Vistas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

public class CredencialActivity extends AppCompatActivity {

    TextView mDeporte, mApellido, mNombre, mFacultad, mLegajo, mNombreEquipo, mAnio;
    ImageView mFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credencial);

        mDeporte = findViewById(R.id.txtDeporte);
        mApellido = findViewById(R.id.txtApellido);
        mNombre = findViewById(R.id.txtNombre);
        mFacultad = findViewById(R.id.txtFacultad);
        mLegajo = findViewById(R.id.txtLegajo);
        mNombreEquipo = findViewById(R.id.txtNombreEquipo);
        mAnio = findViewById(R.id.txtAnio);
        mFoto = findViewById(R.id.imgUser);

        mDeporte.setText("Fútbol Sala Masculino");
        mApellido.setText("Ledesma");
        mNombre.setText("Cristian Santiago");
        mFacultad.setText("FCEyT");
        mLegajo.setText("207/15");
        mNombreEquipo.setText("Santiago Lawn Tennis Club");
        mAnio.setText("2019");
        mFoto.setImageResource(R.drawable.imgdeportes);

    }

}


