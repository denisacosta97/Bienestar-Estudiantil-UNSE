package com.unse.bienestarestudiantil.Vistas.Activities.Becas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesSimpleAdapter;

import java.util.ArrayList;

public class SelectorFechaActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    OpcionesSimpleAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Opciones> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_fecha);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {
        mList = new ArrayList<>();
        mList.add(new Opciones(1, "BECA APUNTES 2020", 0, 0));
        mList.add(new Opciones(2, "BECA COMEDOR 2020", 0, 0));
        mList.add(new Opciones(3, "BECA DEPORTES 2020", 0, 0));
        mList.add(new Opciones(4, "BECA AYUDA ECONOMICA 2020", 0, 0));
        mList.add(new Opciones(5, "BECA MERITO AL ESTADIO 2020", 0, 0));
        mList.add(new Opciones(6, "BECA EXCELENCIA 2020", 0, 0));
        mList.add(new Opciones(7, "BECA RESIDENCIA 2020", 0, 0));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new OpcionesSimpleAdapter(mList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {

            }
        });
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
    }


}
