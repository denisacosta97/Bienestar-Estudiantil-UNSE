package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.Inscripciones.InscripcionesActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionGeneralDeportesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gestion_deportes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión deportes");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 101:
                        startActivity(new Intent(getApplicationContext(), InscripcionesActivity.class));
                        break;
                    case 102:
                        startActivity(new Intent(getApplicationContext(), GestionBecadosActivity.class));
                        break;
                    case 103:
                        startActivity(new Intent(getApplicationContext(), GestionDeportesActivity.class));
                        break;
                    case 104:
                        startActivity(new Intent(getApplicationContext(), GestionProfeActivity.class));
                        break;
                    case 105:
                        startActivity(new Intent(getApplicationContext(), GestionTorneoActivity.class));
                        break;

                }
                Utils.showToast(getApplicationContext(), "Item: " + mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 101, "Inscripciones", R.drawable.ic_usuarios, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 102, "Gestión de Becados", R.drawable.ic_becas, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 103, "Gestión de Deportes", R.drawable.ic_deportes, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 104, "Gestión de Profesores", R.drawable.ic_entrenador, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 105, "Gestión de Torneos", R.drawable.ic_cup, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpciones, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
