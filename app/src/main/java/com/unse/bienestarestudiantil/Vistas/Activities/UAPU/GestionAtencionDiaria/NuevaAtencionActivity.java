package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionAtencionDiaria;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.unse.bienestarestudiantil.R;

import androidx.appcompat.app.AppCompatActivity;

public class NuevaAtencionActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_atencion);


        setToolbar();

        loadViews();

        //loadData();

        //loadListener();



    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Nueva Atención");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

    private void loadViews() {
       // fab = findViewById(R.id.fab);
       // mRecyclerView = findViewById(R.id.recycler);
       // imgIcono = findViewById(R.id.imgFlecha);
    }

}
