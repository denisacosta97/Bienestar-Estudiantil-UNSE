package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Interfaces.CustomItemClickListener;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AsistenciaAdapter;

import java.util.ArrayList;

public class AsistenciaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerAsistencia;
    ArrayList<Asistencia> mAsistencias;
    ArrayList<Usuario> mUsuarios;
    AsistenciaAdapter mAsistenciaAdapter;
    TextView mNombreAlu, idAlumno;
    Button btnFinalizar;
    LinearLayout mLinearLayout;
    ImageView imgFlecha;
    Asistencia mAsistencia;
    CustomItemClickListener listner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Asistencia");
        Utils.changeColorDrawable(imgFlecha, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mAsistencias = new ArrayList<>();
        mUsuarios = new ArrayList<>();

//        mAsistencias.add(new Asistencia(0, R.drawable.ic_basquet, "Cristian Santiago Ledesma"));
//        mAsistencias.add(new Asistencia(1, R.drawable.ic_basquet, "Nicolás Maldonado"));
//        mAsistencias.add(new Asistencia(2, R.drawable.ic_basquet, "Nicole Mariel Ollea Allende"));
//        mAsistencias.add(new Asistencia(3, R.drawable.ic_basquet, "Denis Lionel Acosta"));
//        mAsistencias.add(new Asistencia(4, R.drawable.ic_basquet, "Brendita Analía Alfaro"));
//        mAsistencias.add(new Asistencia(5, R.drawable.ic_basquet, "Mariangélica Carabajal"));
//        mAsistencias.add(new Asistencia(6, R.drawable.ic_basquet, "Florencia Ximena Ríos Luján"));
//        mAsistencias.add(new Asistencia(7, R.drawable.ic_basquet, "Debi Jimenez"));
//        mAsistencias.add(new Asistencia(8, R.drawable.ic_basquet, "René Fabián Acosta"));
//        mAsistencias.add(new Asistencia(9, R.drawable.ic_basquet, "Cristian Santiago Ledesma"));
//        mAsistencias.add(new Asistencia(10, R.drawable.ic_basquet, "Nicolás Maldonado"));
//        mAsistencias.add(new Asistencia(11, R.drawable.ic_basquet, "Nicole Mariel Ollea Allende"));
//        mAsistencias.add(new Asistencia(12, R.drawable.ic_basquet, "Denis Lionel Acosta"));
//        mAsistencias.add(new Asistencia(13, R.drawable.ic_basquet, "Brendita Analía Alfaro"));
//        mAsistencias.add(new Asistencia(14, R.drawable.ic_basquet, "Mariangélica Carabajal"));
//        mAsistencias.add(new Asistencia(15, R.drawable.ic_basquet, "Florencia Ximena Ríos Luján"));
//        mAsistencias.add(new Asistencia(16, R.drawable.ic_basquet, "Debi Jimenez"));
//        mAsistencias.add(new Asistencia(17, R.drawable.ic_basquet, "René Fabián Acosta"));

        mAsistenciaAdapter = new AsistenciaAdapter(mAsistencias, mUsuarios, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerAsistencia.setNestedScrollingEnabled(true);
        mRecyclerAsistencia.setLayoutManager(mLayoutManager);
        mRecyclerAsistencia.setHasFixedSize(true);
        mRecyclerAsistencia.setAdapter(mAsistenciaAdapter);

        mRecyclerAsistencia.setFocusable(false);
        mLinearLayout.requestFocus();

        mLinearLayout.requestFocus();
    }

    private void loadListener() {
        btnFinalizar.setOnClickListener(this);
        imgFlecha.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerAsistencia = findViewById(R.id.recycler);
        idAlumno = findViewById(R.id.idAlumno);
        mNombreAlu = findViewById(R.id.txtNameAlumno);
        mLinearLayout = findViewById(R.id.layout);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        imgFlecha = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnFinalizar:
                break;
        }
    }
}
