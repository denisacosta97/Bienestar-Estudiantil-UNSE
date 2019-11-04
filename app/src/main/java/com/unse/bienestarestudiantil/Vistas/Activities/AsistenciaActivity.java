package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AsistenciaAdapter;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class AsistenciaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerAsistencia;
    ArrayList<Asistencia> mAsistencias;
    AsistenciaAdapter mAsistenciaAdapter;
    Asistencia mAsistencia;
    TextView mNombreAlu;
    ImageView mFotoAlu;
    CheckBox mCheckBox, mNotCheckedBox;
    Button btnRegister;

    public AsistenciaActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        mRecyclerAsistencia = findViewById(R.id.recycler);

        Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        loadViews();

//        if (getIntent().getParcelableExtra(Utils.ALUMNO_NAME) != null) {
//            mAsistencia = getIntent().getParcelableExtra(Utils.ALUMNO_NAME);
//        }
//
//        if (mAsistencia != null) {
//            loadViews();
//
//            //loadListener();
//
//            loadData();
//        } else {
//            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
//            finish();
//        }

        loadDataRecycler();

    }

    private void loadDataRecycler() {
        mAsistencias = new ArrayList<>();

        mAsistencias.add(new Asistencia(0, R.drawable.ic_basquet, "Cristian Santiago Ledesma"));
        mAsistencias.add(new Asistencia(1, R.drawable.ic_basquet, "Nicolás Maldonado"));
        mAsistencias.add(new Asistencia(2, R.drawable.ic_basquet, "Nicole Mariel Ollea Allende"));
        mAsistencias.add(new Asistencia(3, R.drawable.ic_basquet, "Denis Lionel Acosta"));
        mAsistencias.add(new Asistencia(4, R.drawable.ic_basquet, "Brendita Analía Alfaro"));
        mAsistencias.add(new Asistencia(5, R.drawable.ic_basquet, "Mariangélica Carabajal"));
        mAsistencias.add(new Asistencia(6, R.drawable.ic_basquet, "Florencia Ximena Ríos Luján"));
        mAsistencias.add(new Asistencia(7, R.drawable.ic_basquet, "Debi Jimenez"));
        mAsistencias.add(new Asistencia(8, R.drawable.ic_basquet, "René Fabián Acosta"));
        mAsistencias.add(new Asistencia(9, R.drawable.ic_basquet, "Cristian Santiago Ledesma"));
        mAsistencias.add(new Asistencia(10, R.drawable.ic_basquet, "Nicolás Maldonado"));
        mAsistencias.add(new Asistencia(11, R.drawable.ic_basquet, "Nicole Mariel Ollea Allende"));
        mAsistencias.add(new Asistencia(12, R.drawable.ic_basquet, "Denis Lionel Acosta"));
        mAsistencias.add(new Asistencia(13, R.drawable.ic_basquet, "Brendita Analía Alfaro"));
        mAsistencias.add(new Asistencia(14, R.drawable.ic_basquet, "Mariangélica Carabajal"));
        mAsistencias.add(new Asistencia(15, R.drawable.ic_basquet, "Florencia Ximena Ríos Luján"));
        mAsistencias.add(new Asistencia(16, R.drawable.ic_basquet, "Debi Jimenez"));
        mAsistencias.add(new Asistencia(17, R.drawable.ic_basquet, "René Fabián Acosta"));

        mAsistenciaAdapter = new AsistenciaAdapter(mAsistencias, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerAsistencia.setNestedScrollingEnabled(true);
        mRecyclerAsistencia.setLayoutManager(mLayoutManager);
        mRecyclerAsistencia.setAdapter(mAsistenciaAdapter);
    }

    private void loadData() {
        mFotoAlu.setImageResource(mAsistencia.getIdFotoAlum());
        mNombreAlu.setText(mAsistencia.getName());
    }

    private void loadListener() {
        btnRegister.setOnClickListener(this);
    }

    private void loadViews() {
        mFotoAlu = findViewById(R.id.imgFotoAlumno);
        mNombreAlu = findViewById(R.id.txtNameAlumno);
//        mCheckBox = findViewById(R.id.checkBoxPresente);
//        mNotCheckedBox = findViewById(R.id.checkBoxAusente);

    }

    @Override
    public void onClick(View v) {

    }
}
