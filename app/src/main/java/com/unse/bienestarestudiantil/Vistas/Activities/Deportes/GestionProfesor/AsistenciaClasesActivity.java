package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionProfesor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AsistenciaClasesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AsistenciaClasesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    AsistenciaClasesAdapter mAdapter;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_clases);

        Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {

            }
        });
        imgIcono.setOnClickListener(this);
    }

    private void loadData() {


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));

        mAdapter = new AsistenciaClasesAdapter(getApplicationContext(), mRecyclerView);

        List<AsistenciaClasesAdapter.Meses> sections =
                new ArrayList<AsistenciaClasesAdapter.Meses>();

        //Sections
        sections.add(new AsistenciaClasesAdapter.Meses(0,"Marzo"));
        sections.add(new AsistenciaClasesAdapter.Meses(5,"Abril"));
        sections.add(new AsistenciaClasesAdapter.Meses(12,"Mayo"));
        sections.add(new AsistenciaClasesAdapter.Meses(14,"Junio"));
        sections.add(new AsistenciaClasesAdapter.Meses(20,"Julio"));

        //Add your adapter to the sectionAdapter
        AsistenciaClasesAdapter.Meses[] dummy = new AsistenciaClasesAdapter.Meses[sections.size()];
        AsistenciaClasesAdapter mSectionedAdapter = new
                AsistenciaClasesAdapter(getApplicationContext(),
                mRecyclerView,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);

    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
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

