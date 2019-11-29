package com.unse.bienestarestudiantil.Vistas.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

public class CredencialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credencial);
        Utils.setFont(getApplicationContext(),(ViewGroup)findViewById(android.R.id.content), Utils.MONSERRAT);

    }

}


