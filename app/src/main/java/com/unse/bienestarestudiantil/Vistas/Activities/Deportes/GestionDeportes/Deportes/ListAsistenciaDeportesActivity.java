package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.Deportes;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Asistencia;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AsistenciaAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ListAsistenciaDeportesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerAsistencia;
    ArrayList<Asistencia> mAsistencia;
    ArrayList<Usuario> mUsuarios;
    AsistenciaAdapter mAsistenciaAdapter;
    ImageView imgIcono;
    TextView numAus, numPres;
    Date fecha;

    int pres = 0;
    int aus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_asistencia_deportes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableArrayListExtra(Utils.ASISTENCIA) != null) {
            mAsistencia = getIntent().getParcelableArrayListExtra(Utils.ASISTENCIA);
        }

        if (getIntent().getParcelableArrayListExtra(Utils.USER_INFO) != null) {
            mUsuarios = getIntent().getParcelableArrayListExtra(Utils.USER_INFO);
        }

        fecha = new Date();
        fecha.setTime(getIntent().getLongExtra("fecha", -1));

        setToolbar();

        loadViews();

        loadData();

        loadListener();

        loadDataRecycler();
    }

    private void loadData() {
        getPAMethod();
        numPres.setText("" + pres);
        numAus.setText("" + aus);

    }

    private void getPAMethod() {

        for(Asistencia asistencia : mAsistencia){
            String asis = asistencia.getAsistencia();
            if(asis.equals("P") || asis.equals("p")){
                pres++;
            }
            else {
                if (asis.equals("A") || asis.equals("a")) {
                    aus++;
                }
            }
        }

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Asistencia");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadDataRecycler() {

        mAsistenciaAdapter = new AsistenciaAdapter(mAsistencia, mUsuarios, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        reciclerAsistencia.setNestedScrollingEnabled(true);
        reciclerAsistencia.setLayoutManager(mLayoutManager);
        reciclerAsistencia.setAdapter(mAsistenciaAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerAsistencia);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
//                Intent i = new Intent(getApplicationContext(), AsistenciaActivity.class);
//                i.putExtra(Utils.DEPORTE_NAME, mAsistencia.get(position));
//                startActivity(i);
                Toast.makeText(ListAsistenciaDeportesActivity.this, "Funca", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadViews() {
        reciclerAsistencia = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        numPres = findViewById(R.id.txtNumPresentes);
        numAus = findViewById(R.id.txtNumAusentes);
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
