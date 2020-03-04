package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.GestionArchivosAdapter;

import java.util.ArrayList;

public class GestionArchivosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerAsistencia;
    ArrayList<Archivo> mArchivos;
    GestionArchivosAdapter mGestionArchivosAdapter;
    TextView mNombreAlu, idAlumno;
    LinearLayout mLinearLayout;
    Archivo mArchivo;
    ImageView imgFlecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_archivos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgFlecha, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mArchivos = new ArrayList<>();

        mArchivos.add(new Archivo(1,"Incripción al deporte","15/12/2019","inscripcion_deporte"));
        mArchivos.add(new Archivo(2,"Orden de cobro para tesorería","03/12/2019","orden_cobro"));
        mArchivos.add(new Archivo(3,"Reserva de quincho","12/12/2019","reserva_quincho"));

        mGestionArchivosAdapter = new GestionArchivosAdapter(mArchivos, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerAsistencia.setNestedScrollingEnabled(true);
        mRecyclerAsistencia.setLayoutManager(mLayoutManager);
        mRecyclerAsistencia.setHasFixedSize(true);
        mRecyclerAsistencia.setAdapter(mGestionArchivosAdapter);

        mRecyclerAsistencia.setFocusable(false);
        mLinearLayout.requestFocus();

        mLinearLayout.requestFocus();

    }


    private void loadListener() {
        imgFlecha.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerAsistencia = findViewById(R.id.recycler);
        idAlumno = findViewById(R.id.idAlumno);
        mNombreAlu = findViewById(R.id.txtNameAlumno);
        mLinearLayout = findViewById(R.id.layout);
        imgFlecha = findViewById(R.id.imgFlecha);
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
