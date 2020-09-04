package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

public class EspaciosActivity extends AppCompatActivity implements View.OnClickListener {

    Button linearCancha, linearQuincho;
    ImageView imgIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espacios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

    }

    private void loadData() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
    }

    private void loadListener() {
        linearQuincho.setOnClickListener(this);
        linearCancha.setOnClickListener(this);
        imgIcon.setOnClickListener(this);
    }

    private void loadViews() {
        linearCancha = findViewById(R.id.layCancha);
        linearQuincho = findViewById(R.id.laySalon);
        imgIcon = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(), ReservaEspacioActivity.class);;
        switch (v.getId()){
            case R.id.layCancha:
//                i.putExtra(Utils.DATA_RESERVA, Utils.TIPO_CANCHA);
//                startActivity(i);
                startActivity(new Intent(getApplicationContext(), CanchasActivity.class));
                break;
            case R.id.laySalon:
//                i.putExtra(Utils.DATA_RESERVA, Utils.TIPO_QUINCHO);
//                startActivity(i);
                startActivity(new Intent(getApplicationContext(), CalendarReservasActivity.class));
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }

    }
}