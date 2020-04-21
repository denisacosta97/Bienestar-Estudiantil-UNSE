package com.unse.bienestarestudiantil.Vistas.Activities.Gestion;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.GestionGeneralDeportesActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionRoles.GestionRolesActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionUsuarios.GestionUsuariosActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionSistemaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones, mOpcionesFinal;
    ArrayList<String> ids;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_sistema);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                switch ((int) id) {
                    case 1000:
                        startActivity(new Intent(getApplicationContext(), GestionUsuariosActivity.class));
                        break;
                    case 900:
                        startActivity(new Intent(getApplicationContext(), GestionRolesActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(), EstadisticasActivity.class));
                        break;
                    case 2:
                        //startActivity(new Intent(getApplicationContext(), AsistenciaClasesActivity.class));
                        break;
                    case 100:
                        startActivity(new Intent(getApplicationContext(), GestionGeneralDeportesActivity.class));
                        break;

                }
                Utils.showToast(getApplicationContext(), "Item: " + mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);
    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpcionesFinal = new ArrayList<>();
        ids = new ArrayList<>();

        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 1000, "Gestión de Usuarios", R.drawable.ic_user, R.color.colorPrimary));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 1, "Gestión de Estadisticas", R.drawable.ic_estadistica, R.color.colorGreyDark));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 900, "Gestión de Roles", R.drawable.ic_usuarios, R.color.colorGreyDark));

        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 100, "Gestión Deportes", R.drawable.ic_config, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 200, "Gestión Polideportivo", R.drawable.ic_config, R.color.colorOrange));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 300, "Gestión UPA", R.drawable.ic_config, R.color.colorAccent));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 400, "Gestión Área Becas", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 500, "Gestión Cyber", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 600, "Gestión Transporte", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 700, "Gestión Residencia", R.drawable.ic_config, R.color.colorGreen));
        mOpciones.add(new Opciones(true,LinearLayout.HORIZONTAL, 800, "Gestión Comedor", R.drawable.ic_config, R.color.colorGreen));

        mOpcionesFinal.addAll(mOpciones);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpcionesFinal, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

//        filtrarOpciones();

        mAdapter.notifyDataSetChanged();
    }

    private void filtrarOpciones() {
        for (Opciones e : mOpciones) {
            if (ids.contains(String.valueOf(e.getId())))
                mOpcionesFinal.add(e);
        }
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


