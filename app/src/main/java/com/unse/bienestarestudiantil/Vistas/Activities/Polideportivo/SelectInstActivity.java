package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Instalacion;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.InstalacionesAdapter;

import java.util.ArrayList;

public class SelectInstActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    RecyclerView mRecyclerView1, mRecyclerView2, mRecyclerView3;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Instalacion> sum, marron, gris;
    InstalacionesAdapter mInstalacionesAdapter;
    ArrayList<Reserva> arraylist;

    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_inst);

        setToolbar();

        if (getIntent().getParcelableArrayListExtra("listOcupados") != null) {
            arraylist = getIntent().getParcelableArrayListExtra("listOcupados");
        }

        if (getIntent().getStringExtra("fecha") != null) {
            fecha = getIntent().getStringExtra("fecha");
        }


        loadViews();

        loadData();

    }

    private void loadData() {
        //SUM
        sum = new ArrayList<>();
        //Quincho marrón
        marron = new ArrayList<>();
        //Quincho gris
        gris = new ArrayList<>();

        sum.add(new Instalacion(0, 0, "Turno: 10hs a 19hs", R.drawable.ic_chek, "Libre", 0));
        sum.add(new Instalacion(0, 0, "Turno: 20hs a 05hs", R.drawable.ic_chek, "Libre", 1));

        marron.add(new Instalacion(1, 0, "Turno: 10hs a 19hs", R.drawable.ic_chek, "Libre", 0));
        marron.add(new Instalacion(1, 0, "Turno: 20hs a 05hs", R.drawable.ic_chek, "Libre", 1));
        
        gris.add(new Instalacion(2, 0, "Turno: 10hs a 19hs", R.drawable.ic_chek, "Libre", 0));
        gris.add(new Instalacion(2, 0, "Turno: 20hs a 05hs", R.drawable.ic_chek, "Libre", 1));


        //loadReservas();

        mInstalacionesAdapter = new InstalacionesAdapter(sum, getApplicationContext());
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView1.setLayoutManager(mLayoutManager);
        mRecyclerView1.setAdapter(mInstalacionesAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView1);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Instalacion instalacion = sum.get(position);
                instalacion.setInstalacion(0);
                if (instalacion.getEstado() == 0){
                    Intent i = new Intent(getApplicationContext(), ConfirmarReservaActivity.class);
//                    i.putExtra(Utils.NUM_INST, instalacion);
//                    i.putExtra("fecha", fecha);
                    startActivity(i);
                }else{
                    Utils.showToast(getApplicationContext(), "¡Dia y horario ya reservado!");
                }


            }
        });


        mInstalacionesAdapter = new InstalacionesAdapter(gris, getApplicationContext());
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView2.setLayoutManager(mLayoutManager);
        mRecyclerView2.setAdapter(mInstalacionesAdapter);

        ItemClickSupport itemClickSupport2 = ItemClickSupport.addTo(mRecyclerView2);
        itemClickSupport2.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Instalacion instalacion = gris.get(position);
                instalacion.setInstalacion(2);
                if (instalacion.getEstado() == 0){
                    Intent i = new Intent(getApplicationContext(), ConfirmarReservaActivity.class);
                    i.putExtra(Utils.NUM_INST, instalacion);
                    i.putExtra("fecha", fecha);
                    startActivity(i);
                }else{
                    Utils.showToast(getApplicationContext(), "¡Dia y horario ya reservado!");
                }

            }
        });

        mInstalacionesAdapter = new InstalacionesAdapter(marron, getApplicationContext());
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView3.setLayoutManager(mLayoutManager);
        mRecyclerView3.setAdapter(mInstalacionesAdapter);

        ItemClickSupport itemClickSupport3 = ItemClickSupport.addTo(mRecyclerView3);
        itemClickSupport3.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Instalacion instalacion = marron.get(position);
                instalacion.setInstalacion(1);
                if (instalacion.getEstado() == 0){
                    Intent i = new Intent(getApplicationContext(), ConfirmarReservaActivity.class);
//                    i.putExtra(Utils.NUM_INST, instalacion);
//                    i.putExtra("fecha", fecha);
                    startActivity(i);
                }else{
                    Utils.showToast(getApplicationContext(), "¡Dia y horario ya reservado!");
                }

            }
        });

    }

//    private void loadReservas() {
//        for (Reserva re : arraylist) {
//            if (re.getInstalacion() == 0) {
//                if (re.getFechaReserva().contains("10hs")) {
//                    sum.set(0, new Instalacion(0, 1, "Turno: 10hs a 19hs", R.drawable.ic_not, "Reservado", 0));
//                } else {
//                    if(re.getFechaReserva().contains("10hs")) {
//                        sum.set(0, new Instalacion(0, 0, "Turno: 10hs a 19hs", R.drawable.ic_chek, "Libre", 0));
//                    }
//                }
//                if (re.getFechaReserva().contains("20hs")) {
//                    sum.set(1, new Instalacion(0, 1, "Turno: 20hs a 05hs", R.drawable.ic_not, "Reservado", 0));
//                } else {
//                    if(re.getFechaReserva().contains("20hs")) {
//                        sum.set(1, new Instalacion(0, 0, "Turno: 20hs a 05hs", R.drawable.ic_chek, "Libre", 0));
//                    }
//                }
//            }
//
//            if (re.getInstalacion() == 1) {
//                if (re.getFechaReserva().contains("10hs")) {
//                    marron.set(0, new Instalacion(1, 1, "Turno: 10hs a 19hs", R.drawable.ic_not, "Reservado", 0));
//                } else {
//                    if(re.getFechaReserva().contains("10hs")) {
//                        marron.set(0, new Instalacion(1, 0, "Turno: 10hs a 19hs", R.drawable.ic_chek, "Libre", 0));
//                    }
//                }
//                if (re.getFechaReserva().contains("20hs")) {
//                    marron.set(1, new Instalacion(1, 1, "Turno: 20hs a 05hs", R.drawable.ic_not, "Reservado", 0));
//                } else {
//                    if(re.getFechaReserva().contains("20hs")) {
//                        marron.set(1, new Instalacion(1, 0, "Turno: 20hs a 05hs", R.drawable.ic_chek, "Libre", 0));
//                    }
//                }
//            }
//
//
//            if (re.getInstalacion() == 2) {
//                if (re.getFechaReserva().contains("10hs")) {
//                    gris.set(0, new Instalacion(2, 1, "Turno: 10hs a 19hs", R.drawable.ic_not, "Reservado", 0));
//                } else {
//                    if(re.getFechaReserva().contains("10hs")) {
//                        gris.set(0, new Instalacion(2, 0, "Turno: 10hs a 19hs", R.drawable.ic_chek, "Libre", 0));
//                    }
//                }
//                if (re.getFechaReserva().contains("20hs")) {
//                    gris.set(1, new Instalacion(2, 1, "Turno: 20hs a 05hs", R.drawable.ic_not, "Reservado", 0));
//                } else {
//                    if(re.getFechaReserva().contains("20hs")) {
//                        gris.set(1, new Instalacion(2, 0, "Turno: 20hs a 05hs", R.drawable.ic_chek, "Libre", 0));
//                    }
//                }
//            }
//
//        }
//    }

    private void loadViews() {
        mRecyclerView1 = findViewById(R.id.recycler1);
        mRecyclerView3 = findViewById(R.id.recycler3);
        mRecyclerView2 = findViewById(R.id.recycler2);

    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView) findViewById(R.id.txtTitulo)).setText("Instalaciones");
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
