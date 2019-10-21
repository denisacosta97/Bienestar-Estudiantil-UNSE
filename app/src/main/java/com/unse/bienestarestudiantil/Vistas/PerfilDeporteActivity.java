package com.unse.bienestarestudiantil.Vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

public class PerfilDeporteActivity extends AppCompatActivity {

    Deporte mDeporte;
    TextView mHorario, mDia, mEntrenador, mNombreDep;
    ImageView mIcon;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_deporte);

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        if (getIntent().getParcelableExtra("dato") != null){
            mDeporte = getIntent().getParcelableExtra("dato");
        }

        mHorario = findViewById(R.id.txtHorarios);
        mDia = findViewById(R.id.txtDia);
        mEntrenador = findViewById(R.id.txtEntrenador);
        mNombreDep = findViewById(R.id.txtNameDeporte);
        mIcon = findViewById(R.id.imgvIcon);
        btnRegister = findViewById(R.id.btnRegisterDep);

        mEntrenador.setText(mDeporte.getEntrenador());
        mHorario.setText(mDeporte.getHorario());
        mDia.setText(mDeporte.getDias());
        mNombreDep.setText(mDeporte.getName());
        mIcon.setImageResource(mDeporte.getIconDeporte());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerfilDeporteActivity.this, SportsRegistration.class);
                i.putExtra("dato", mDeporte);
                startActivity(i);
            }
        });

    }
}
