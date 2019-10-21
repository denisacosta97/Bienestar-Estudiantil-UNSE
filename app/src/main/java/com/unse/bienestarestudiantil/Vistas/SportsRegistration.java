package com.unse.bienestarestudiantil.Vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

public class SportsRegistration extends AppCompatActivity {

    Deporte mDeporte;
    EditText edtNombreDep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_registration);

        if (getIntent().getParcelableExtra("dato") != null){
            mDeporte = getIntent().getParcelableExtra("dato");
        }

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        edtNombreDep = findViewById(R.id.edtDeporte);
        edtNombreDep.setText(mDeporte.getName());

    }
}
