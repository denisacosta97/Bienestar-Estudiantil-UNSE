package com.unse.bienestarestudiantil.Vistas.Activities.Gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber.EstadisticasCiberActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber.GestionImpresionesActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber.NuevoIngresoDialog;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionNoticias.AddNoticiaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionNoticias.ListadoNoticiasActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionNoticiasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_noticias);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de noticias");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 101:
                        startActivity(new Intent(getApplicationContext(), AddNoticiaActivity.class));
                        break;
                    case 102:
                        startActivity(new Intent(getApplicationContext(),  ListadoNoticiasActivity.class));
                        break;
                    case 103:
                        startActivity(new Intent(getApplicationContext(), EstadisticasCiberActivity.class));
                        break;
                }
                Utils.showToast(getApplicationContext(), "Item: " + mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 101, "Nueva noticia", R.drawable.ic_add, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 102, "Listado de noticias", R.drawable.ic_listado, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 103, "Estadísticas", R.drawable.ic_estadisticas_color, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpciones, getApplicationContext(), 1);
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