package com.unse.bienestarestudiantil.Vistas.Activities.UAPU;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionAtencionDiaria.GestionAtencionesActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionCertificados.GestionCertificadosUActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionDoctores.GestionDoctoresActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionMedicamentos.GestionMedicamentosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionPacientes.GestionPacientesUActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionServicios.GestionServiciosUActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionTurnos.GestionTurnosUAPUActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionUAPUActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_uapu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de UAPU");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 1:
                        startActivity(new Intent(getApplicationContext(), GestionTurnosUAPUActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), GestionAtencionesActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), GestionServiciosUActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(), GestionDoctoresActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(getApplicationContext(), GestionPacientesUActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getApplicationContext(), GestionCertificadosUActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getApplicationContext(), GestionMedicamentosActivity.class));
                        break;
                }
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,1,"Turnos",R.drawable.ic_item_arrow, R.color.colorFCEyT ));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,2, "Atenciones",R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,3, "Servicios",R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,4, "Gestión Doctores",R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,5, "Pacientes",R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,6, "Certificados",R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,7, "Medicamentos",R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL,8, "Estadisticas",R.drawable.ic_item_arrow, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpciones, getApplicationContext(),1);
        mRecyclerView.setAdapter(mAdapter);
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