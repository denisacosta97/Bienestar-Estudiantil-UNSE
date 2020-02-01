package com.unse.bienestarestudiantil.Vistas.Activities.Becas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TurnosAdapter;

import java.util.ArrayList;

public class TurnosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    TurnosAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Turno> mList;
    FloatingActionButton fabMas;
    ImageView imgIcono;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos);

        setToolbar();

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {
        mList = new ArrayList<>();
        mList.add(new Turno("BECAS COMEDOR 2019", "Presentación de papeles para becas comedor 2019", Turno.ESTADO.CONFIRMADO.name(),
                "09:05:00", "09:10:00", "24/02/2019"));
        mList.add(new Turno("BECAS UNSE 2020", "Presentación de papeles para becas comedor 2019", Turno.ESTADO.AUSENTE.name(),
                "09:05:00", "09:10:00", "24/02/2019"));
        mList.add(new Turno("BECAS COMEDOR 2020", "Presentación de papeles para becas comedor 2019", Turno.ESTADO.CONFIRMADO.name(),
                "09:05:00", "09:10:00", "2/10/2020"));
        mList.add(new Turno("DEPORTES 2020", "Presentación de papeles para becas comedor 2019", Turno.ESTADO.AUSENTE.name(),
                "09:05:00", "09:10:00", "24/04/2020"));
        mList.add(new Turno("AYUDA ECONOMICA 2020", "Presentación de papeles para becas comedor 2019", Turno.ESTADO.PENDIENTE.name(),
                "09:05:00", "09:10:00", "24/03/2020"));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new TurnosAdapter(mList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        fabMas.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        fabMas = findViewById(R.id.floatingMas);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.floatingMas:
                startActivity(new Intent(getApplicationContext(), TipoTurnosActivity.class));
                break;
        }
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorAccent));
        Utils.changeColorDrawable(((ImageView) findViewById(R.id.imgFlecha)), getApplicationContext(), R.color.colorAccent);

    }
}
