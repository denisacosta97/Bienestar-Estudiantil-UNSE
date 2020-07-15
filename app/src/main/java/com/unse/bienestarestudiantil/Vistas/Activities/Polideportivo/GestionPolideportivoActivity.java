package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionFinanzas.GestionFinanzasActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso.GestionIngresoActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservas.GestionReservasActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class GestionPolideportivoActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_polideportivo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));

    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id){
                    case 1:
                        startActivity(new Intent(getApplicationContext(), GestionFinanzasActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), GestionReservasActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), GestionIngresoActivity.class));
                        break;
                }
                Utils.showToast(getApplicationContext(), "Item: "+mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(LinearLayout.VERTICAL,1,"Gestión de Finanzas",R.drawable.ic_finanzas, R.color.colorFCEyT ));
        mOpciones.add(new Opciones(LinearLayout.VERTICAL,2, "Gestión de Reservas",R.drawable.ic_reservas, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.VERTICAL,3, "Gestión de Ingreso", R.drawable.ic_ingreso, R.color.colorFCEyT));

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
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }
}
