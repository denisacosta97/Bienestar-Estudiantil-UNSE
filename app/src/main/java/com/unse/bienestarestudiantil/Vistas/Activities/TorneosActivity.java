package com.unse.bienestarestudiantil.Vistas.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TorneosAdapter;

import java.util.ArrayList;

public class TorneosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerAsistencia;
    ArrayList<Torneo> mTorneos;
    TorneosAdapter mTorneosAdapter;
    Torneo mTorneo;
    TextView mNombreAlu, idAlumno;
    Button btnFinalizar;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torneos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mRecyclerAsistencia = findViewById(R.id.recycler);

        loadViews();

        loadListener();

        loadDataRecycler();

        setToolbar();
    }

    private void loadDataRecycler() {
        mTorneos = new ArrayList<>();

//        mTorneos.add(new Torneo(0, R.drawable.ic_cup, "Buenos Aires", "Torneo JUR", "Torneo de las Universidades de Argentina.", "10/11/2019", "20/11/2019"));
//        mTorneos.add(new Torneo(0, R.drawable.ic_cup, "Santiago del Estero", "Torneo de la UNSE", "Torneo de las Universidades de Argentina.", "10/11/2019", "20/11/2019"));
//        mTorneos.add(new Torneo(0, R.drawable.ic_cup, "Buenos Aires", "Torneo regional", "Torneo de las Universidades de Argentina.", "10/11/2019", "20/11/2019"));

        mTorneosAdapter = new TorneosAdapter(mTorneos, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerAsistencia.setNestedScrollingEnabled(true);
        mRecyclerAsistencia.setLayoutManager(mLayoutManager);
        mRecyclerAsistencia.setAdapter(mTorneosAdapter);

        if (mTorneos.size() == 0) { //Código para usar los custom toast.
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.toast_layout));

            ImageView image = layout.findViewById(R.id.image);
            image.setImageResource(R.drawable.ic_chek);
            TextView text2 = layout.findViewById(R.id.text);
            text2.setText("No hay torneos disponibles.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, 30);
            toast.setDuration(Toast.LENGTH_LONG + 4);
            toast.setView(layout);
            toast.show();
        }

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerAsistencia);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilTorneoActivity.class);
                i.putExtra(Utils.TORNEO, mTorneos.get(position));
                startActivity(i);
            }
        });

    }

    private void loadData() {
        idAlumno.setText(mTorneo.getId());
        mNombreAlu.setText(mTorneo.getNameTorneo());
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadViews() {
        idAlumno = findViewById(R.id.idAlumno);
        mNombreAlu = findViewById(R.id.txtNameAlumno);
        btnFinalizar = findViewById(R.id.btnFinalizar);
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