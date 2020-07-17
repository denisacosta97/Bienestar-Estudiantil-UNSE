package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.RecorridoAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import java.util.ArrayList;

public class GestionRecorridosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerT;
    ArrayList<Recorrido> mRecorridos;
    RecorridoAdapter mRecorridoAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_recorridos);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de recorridos");

    }

    private void loadViews() {
        mRecyclerT = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mRecorridos = new ArrayList<>();

        mRecorridoAdapter = new RecorridoAdapter(mRecorridos, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mRecyclerT.setNestedScrollingEnabled(true);
        mRecyclerT.setLayoutManager(mLayoutManager);
        mRecyclerT.setAdapter(mRecorridoAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerT);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilRecorridoActivity.class);
                i.putExtra(Utils.RECORRIDO, mRecorridos.get(position));
                startActivity(i);
            }
        });

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
