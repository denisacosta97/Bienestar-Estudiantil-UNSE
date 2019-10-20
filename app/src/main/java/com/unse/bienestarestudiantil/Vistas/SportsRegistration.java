package com.unse.bienestarestudiantil.Vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.R;

public class SportsRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_registration);

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] letra = {"Ajedréz","Basquet","Cestobol","Fútbol 11 (Masculino)","Fútbol 11 (Femenino)","Fútbol Sala (Masculino)","Fútbol Sala (Femenino)","Hockey","Natación","Rugby","Tenis de mesa","Voleibol (Masculino)","Voleibol (Femenino)"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

    }
}
