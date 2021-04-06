package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionAtencionDiaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AtencionesActivity extends AppCompatActivity implements View.OnClickListener {

    OpcionesAdapter mAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ImageView imgIcono;
    ArrayList<Opciones> mList;
    DialogoProcesamiento dialog;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atenciones);


        setToolbar();

        loadViews();

        loadData();

        loadListener();


    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 1:
                        startActivity(new Intent(getApplicationContext(), AtencionesDiariasActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), AtencionesHistoricasActivity.class));
                        break;


                }
            }
        });
        imgIcono.setOnClickListener(this);
        fab.setOnClickListener(this);

    }

    private void loadViews() {
        fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void loadData() {

        mList = new ArrayList<>();
        mList.add(new Opciones(LinearLayout.VERTICAL, 1, "Registro del día", R.drawable.ic_reserva_libre_turno, R.color.colorFCEyT));
        mList.add(new Opciones(LinearLayout.VERTICAL, 2, "Atenciones Históricas", R.drawable.ic_becas, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mList, getApplicationContext(), 1);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fab:
                newAtencion();
                break;
        }
    }

    private void newAtencion() {
        Intent intent = new Intent(getApplicationContext(), NuevaAtencionActivity.class);
        startActivity(intent);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Atención Diaria");
    }

}