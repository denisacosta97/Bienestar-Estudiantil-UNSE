package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Maraton.CargaDatosMaratonActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Maraton.EstadisticasMaratonActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Maraton.ListadoMaratonActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionComedorActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Comedor");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 801:
                        startActivity(new Intent(getApplicationContext(), DiariaComedorActivity.class));
                        break;
                    case 802:
                        startActivity(new Intent(getApplicationContext(), ReservasComedorActivity.class));
                        break;
                    case 803:
                        startActivity(new Intent(getApplicationContext(), BecadosComedorActivity.class));
                        break;
                    case 804:
                        startActivity(new Intent(getApplicationContext(), MenuComedorActivity.class));
                        break;
                    case 805:
                        startActivity(new Intent(getApplicationContext(), OpinionesComedorActivity.class));
                        break;
                    case 806:
                        startActivity(new Intent(getApplicationContext(), EstadisticasComedorActivity.class));
                        break;
                }
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 801, "Gestión Diaria", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 802, "Gestión de Reservas", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 803, "Gestión de Becados", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 804, "Gestión de Menú", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 805, "Gestión de Opiniones", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 806, "Estadísticas", R.drawable.ic_item_arrow, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(filtrarOpciones(mOpciones), getApplicationContext(), 1);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Opciones> filtrarOpciones(ArrayList<Opciones> opciones) {
        ArrayList<Opciones> finals = new ArrayList<>();
        RolViewModel datos = new RolViewModel();
        ArrayList<Rol> roles = datos.getAll();
        for (Opciones e : opciones) {
            for (Rol rol : roles) {
                if (e.getId() == rol.getIdRol()) {
                    finals.add(e);
                }

            }

        }

        return finals;
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

