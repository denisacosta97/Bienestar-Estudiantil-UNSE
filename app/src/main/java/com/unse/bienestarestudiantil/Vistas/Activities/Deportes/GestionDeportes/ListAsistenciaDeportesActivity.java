package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.AsistenciaActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;

import java.util.ArrayList;

public class ListAsistenciaDeportesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    ArrayList<Deporte> mDeportes;
    DeportesAdapter mDeportesAdapter;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_asistencia_deportes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Alumnos inscriptos");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mDeportes = new ArrayList<>();
//        mDeportes.add(new Deporte(0, R.drawable.ic_ajedrez, "Ajedréz", "Rubén Corvalán", "martes, miércoles y viernes", "21:00hs"));
//        mDeportes.add(new Deporte(1, R.drawable.ic_basquet, "Básquet", "José Emilio López", "martes y jueves", "21:30hs"));
//        mDeportes.add(new Deporte(2, R.drawable.ic_becas, "Cestobol", "Yesica Kofler", "lunes, miércoles, viernes","19:00hs"));
//        mDeportes.add(new Deporte(3, R.drawable.ic_futbol_masc, "Fútbol 11 Masculino", "José Grecco", "lunes, miércoles y viernes","20:30hs a 23:00hs"));
//        mDeportes.add(new Deporte(4, R.drawable.ic_futbol_fem, "Fútbol 11 Femenino", "José Grecco", "lunes, miércoles y viernes","18:30hs a 20:30hs"));
//        mDeportes.add(new Deporte(5, R.drawable.ic_futbol_masc, "Fútbol Sala Masculino", "Luís Delveliz", "lunes. miércoles y viernes","19:00hs"));
//        mDeportes.add(new Deporte(6, R.drawable.ic_futbol_masc, "Fútbol Sala Femenino", "Luján Cortés", "lunes, miércoles y viernes","19:00hs"));
//        mDeportes.add(new Deporte(7, R.drawable.ic_hockey, "Hockey", "Omar Zorribas", "lunes, martes, jueves y viernes","17:00hs, 16:00hs, 10:00hs, 12:00hs y 17:00hs"));
//        mDeportes.add(new Deporte(8, R.drawable.ic_natacion, "Natación", "Matías Umaño", "lunes, miércoles, viernes","15:00hs, 16:00hs y 17:00hs"));
//        mDeportes.add(new Deporte(9, R.drawable.ic_rugby, "Rugby", "César Muratore", "martes, jueves y sábado","21:30hs y 16:00hs"));
//        mDeportes.add(new Deporte(10, R.drawable.ic_tenis_mesa, "Tenis de Mesa", "Rodrigo Costa Mayuli", "martes, jueves y sábado","19:30hs"));
//        mDeportes.add(new Deporte(11, R.drawable.ic_voley_masc, "Voleibol Masculino", "Diego Moreno", "lunes, miércoles y viernes","21:30hs"));
//        mDeportes.add(new Deporte(12, R.drawable.ic_voley_fem, "Voleibol Femenino", "Maryne Sanchez", "lunes, miércoles y viernes","21:30hs"));

        mDeportesAdapter = new DeportesAdapter(mDeportes, getApplicationContext(), false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mDeportesAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerDeportes);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AsistenciaActivity.class);
                i.putExtra(Utils.DEPORTE_NAME, mDeportes.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        reciclerDeportes = findViewById(R.id.recycler);
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
