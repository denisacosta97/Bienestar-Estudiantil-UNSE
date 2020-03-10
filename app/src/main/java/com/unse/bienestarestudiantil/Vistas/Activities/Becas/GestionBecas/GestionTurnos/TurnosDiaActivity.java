package com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas.GestionTurnos;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TurnosDiasAdapter;

import java.util.ArrayList;

public class TurnosDiaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TurnosDiasAdapter mAdapter;
    ArrayList<Turno> mTurnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos_dia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));

    }


    private void loadListener() {


    }

    private void loadData() {
        mTurnos = new ArrayList<>();
        mTurnos.add(new Turno("Beca Comedor", "Beca comedor para comer comida", "Pendiente", "08:00", "08:15", "01/02/2020", "39986583", "Cristian", "Ledesma"));
        mTurnos.add(new Turno("Beca Deporte", "Beca comedor para comer comida", "Pendiente", "08:00", "08:15", "01/02/2020", "39986583", "Cristian", "Ledesma"));
        mTurnos.add(new Turno("Beca Residencia", "Beca comedor para comer comida", "Pendiente", "08:00", "08:15", "01/02/2020", "39986583", "Cristian", "Ledesma"));
        mTurnos.add(new Turno("Beca Comedor", "Beca comedor para comer comida", "Pendiente", "08:00", "08:15", "01/02/2020", "39986583", "Cristian", "Ledesma"));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TurnosDiasAdapter(mTurnos, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}