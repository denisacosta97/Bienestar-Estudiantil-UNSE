package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaHorario;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.PerfilDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AsistenciaAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.GestionReservasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoReservas;

import java.util.ArrayList;

public class AdministrarReservasActivity extends AppCompatActivity {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerReserva;
    ArrayList<Reserva> mReservas;
    ArrayList<ReservaHorario> mReservaHorarios;
    GestionReservasAdapter mReservasAdapter;
    ImageView mIcon;
    TextView mTitulo, mRangoHorario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_reservas);

        mRecyclerReserva = findViewById(R.id.recycler);

        Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content), Utils.MONSERRAT);

        loadViews();

        loadDataRecycler();

    }

    private void loadDataRecycler() {
        mReservas = new ArrayList<>();

        mReservas.add(new Reserva(0, R.drawable.ic_reserva_espera, "A CONFIRMAR", new ReservaHorario(1,1, "10:00","11:00")));
        mReservas.add(new Reserva(1, R.drawable.ic_reserva_ocupado, "OCUPADO", new ReservaHorario(2,1, "11:00","12:00")));
        mReservas.add(new Reserva(2, R.drawable.ic_reserva_ocupado, "OCUPADO", new ReservaHorario(1,2, "12:00","13:00")));
        mReservas.add(new Reserva(3, R.drawable.ic_reserva_espera, "A CONFIRMAR", new ReservaHorario(2,2, "16:00","17:00")));

        mReservasAdapter = new GestionReservasAdapter(mReservas, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerReserva.setNestedScrollingEnabled(true);
        mRecyclerReserva.setLayoutManager(mLayoutManager);
        mRecyclerReserva.setAdapter(mReservasAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerReserva);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Item: "+mReservas.get(position).getTitulo());
                DialogoReservas dialogoReservas = new DialogoReservas();
                dialogoReservas.loadDataFromReserva(mReservas.get(position));
                dialogoReservas.show(getSupportFragmentManager(),"dialog_resevas");
            }
        });

    }


    private void loadViews() {
        mIcon = findViewById(R.id.imgIcono);
        mTitulo = findViewById(R.id.txtTitulo);
        mRangoHorario = findViewById(R.id.txtRangoHorario);

    }

}
