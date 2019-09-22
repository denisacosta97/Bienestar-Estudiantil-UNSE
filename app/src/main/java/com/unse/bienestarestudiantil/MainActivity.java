package com.unse.bienestarestudiantil;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Categoria> mCategorias;
    CategoriasAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler);
        mCategorias = new ArrayList<>();
        mCategorias.add(new Categoria("Comedor Universitario"));
        mCategorias.add(new Categoria("Deportes"));
        mCategorias.add(new Categoria("Transporte"));
        mCategorias.add(new Categoria("Becas"));
        mCategorias.add(new Categoria("Polideportivo"));
        mCategorias.add(new Categoria("Residencia"));
        mCategorias.add(new Categoria("Cyber Estudiantil"));

        mAdapter = new CategoriasAdapter(mCategorias, getApplicationContext());

        mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

    }
}
