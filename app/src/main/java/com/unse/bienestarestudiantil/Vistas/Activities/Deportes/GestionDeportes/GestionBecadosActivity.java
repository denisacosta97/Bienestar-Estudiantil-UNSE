package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;

import java.util.ArrayList;

public class GestionBecadosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerAlumnos;
    ArrayList<Usuario> mAlumno;
    UsuariosAdapter mUsuariosAdapter;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_becados);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de becados");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mAlumno = new ArrayList<>();
        //mAlumno.add(new Alumno(0, R.drawable.ic_ajedrez, "Ajedréz", "Rubén Corvalán", "martes, miércoles y viernes", "21:00hs"));

        mUsuariosAdapter = new UsuariosAdapter(mAlumno, getApplicationContext(), Utils.TIPO_ESTUDIANTE);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mRecyclerAlumnos.setNestedScrollingEnabled(true);
        mRecyclerAlumnos.setLayoutManager(mLayoutManager);
        mRecyclerAlumnos.setAdapter(mUsuariosAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerAlumnos);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), BecadoActivity.class);
                i.putExtra(Utils.ALUMNO_NAME, (Parcelable) mAlumno.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        mRecyclerAlumnos = findViewById(R.id.recycler);
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
