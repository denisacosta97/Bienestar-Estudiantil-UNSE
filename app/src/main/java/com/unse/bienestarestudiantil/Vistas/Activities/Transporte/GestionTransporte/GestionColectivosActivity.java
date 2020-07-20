package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Colectivo;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ColectivoAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;

public class GestionColectivosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerColectivo;
    ArrayList<Colectivo> mColectivos;
    ColectivoAdapter mColectivoAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_colectivos);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de colectivos");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadDataRecycler() {
        mColectivos = new ArrayList<>();

        mColectivoAdapter = new ColectivoAdapter(mColectivos, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerColectivo.setNestedScrollingEnabled(true);
        mRecyclerColectivo.setLayoutManager(mLayoutManager);
        mRecyclerColectivo.setAdapter(mColectivoAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerColectivo);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
            }
        });

    }

    private void loadViews() {
        mRecyclerColectivo = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mEditText = findViewById(R.id.edtBuscar);
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