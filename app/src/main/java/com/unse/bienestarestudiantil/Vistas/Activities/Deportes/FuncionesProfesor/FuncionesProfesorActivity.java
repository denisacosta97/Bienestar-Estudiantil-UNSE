package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.FuncionesProfesor;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.AsistenciaActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.TorneosActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

public class FuncionesProfesorActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_profesor);
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
                switch ((int) id){
                    case 1:
                        startActivity(new Intent(getApplicationContext(), ListadoAlumnosActivity.class));
                        break;
                    case 2:
                       // startActivity(new Intent(getApplicationContext(), AsistenciaClasesActivity.class));
                        startActivity(new Intent(getApplicationContext(), AsistenciaActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getApplicationContext(), TorneosActivity.class));
                        break;
                }
                Utils.showToast(getApplicationContext(), "Item: "+mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);
    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(LinearLayout.VERTICAL,1,"Listado de Alumnos",R.drawable.ic_usuarios, R.color.colorFCEyT ));
        mOpciones.add(new Opciones(LinearLayout.VERTICAL,2, "Asistencias",R.drawable.ic_listado, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.VERTICAL,3, "Gestión de Torneos", R.drawable.ic_torneo, R.color.colorFCEyT));

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
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
