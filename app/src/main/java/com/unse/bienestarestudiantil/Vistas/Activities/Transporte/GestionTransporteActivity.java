package com.unse.bienestarestudiantil.Vistas.Activities.Transporte;

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
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.EstadisticasTransporteActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionChoferActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionColectivosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionPasajeroActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionRecorridosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionServicioActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.MiServicioActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionTransporteActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_transporte);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión transporte");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 101:
                        startActivity(new Intent(getApplicationContext(), GestionChoferActivity.class));
                        break;
                    case 102:
                        startActivity(new Intent(getApplicationContext(), GestionRecorridosActivity.class));
                        break;
                    case 103:
                        startActivity(new Intent(getApplicationContext(), GestionPasajeroActivity.class));
                        break;
                    case 104:
                        startActivity(new Intent(getApplicationContext(), EstadisticasTransporteActivity.class));
                        break;
                    case 105:
                        startActivity(new Intent(getApplicationContext(), GestionServicioActivity.class));
                        break;
                    case 106:
                        startActivity(new Intent(getApplicationContext(), MiServicioActivity.class));
                        break;
                    case 107:
                        startActivity(new Intent(getApplicationContext(), RecorridoActivity.class));
                        break;

                }
                Utils.showToast(getApplicationContext(), "Item: " + mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 101, "Gestión de choferes", R.drawable.ic_conductor, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 102, "Gestión de líneas", R.drawable.ic_transporte, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 103, "Gestión de pasajeros", R.drawable.ic_pasajeros, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 104, "Estadísticas", R.drawable.ic_estadistica, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 105, "Gestión de servicios", R.drawable.ic_transporte, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 106, "Mi servicio", R.drawable.ic_conductor, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 107, "Gestión de colectivos", R.drawable.ic_transporte, R.color.colorFCEyT));


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
