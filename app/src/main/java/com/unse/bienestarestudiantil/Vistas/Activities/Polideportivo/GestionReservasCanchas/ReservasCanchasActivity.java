package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservasCanchas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import com.unse.bienestarestudiantil.Modelos.ReservaCancha;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ReservaCanchaAdapter;

import java.util.ArrayList;

public class ReservasCanchasActivity extends AppCompatActivity {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerReserva;
    ArrayList<ReservaCancha> mReservas;
    ReservaCanchaAdapter mReservasAdapter;
    ImageView mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_canchas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadDataRecycler();

    }

    private void loadDataRecycler() {
        mReservas = new ArrayList<>();

        mReservas.add(new ReservaCancha(0, 39986583, "16:00", "20/08/20", "Fútbol 5", "18/08/20", 450));
        mReservas.add(new ReservaCancha(0, 39986583, "16:00", "20/08/20", "Fútbol 5", "18/08/20", 450));
        mReservas.add(new ReservaCancha(0, 39986583, "16:00", "20/08/20", "Fútbol 5", "18/08/20", 450));
        mReservas.add(new ReservaCancha(0, 39986583, "16:00", "20/08/20", "Fútbol 5", "18/08/20", 450));

        mReservasAdapter = new ReservaCanchaAdapter(mReservas, this);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerReserva.setNestedScrollingEnabled(true);
        mRecyclerReserva.setLayoutManager(mLayoutManager);
        mRecyclerReserva.setAdapter(mReservasAdapter);

    }


    private void loadViews() {
        mRecyclerReserva = findViewById(R.id.recycler);
        mIcon = findViewById(R.id.imgIcono);
    }
}