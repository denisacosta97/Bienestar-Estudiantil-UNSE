package com.unse.bienestarestudiantil.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.ReservaHorario;
import com.unse.bienestarestudiantil.Modelos.Espacio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EspaciosAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservaEspacioActivity extends AppCompatActivity {


    Button btnReservar;
    Toolbar mToolbar;
    RecyclerView mRecyclerViewHorario;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ReservaHorario> mListHorarios;

    HorariosAdapter mHorariosAdapter;

    DatePickerTimeline calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_qs);

        Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content),
                "Montserrat-Regular.ttf");

        setSupportActionBar(mToolbar);

        loadViews();

        loadData();

        loadListener();




       /* calendario.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return (calendar.get(Calendar.MONTH) + 1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });*/




    }

    private void loadData() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mListHorarios = new ArrayList<>();
        mListHorarios.add(new ReservaHorario(1, "10:00","14:00"));
        mListHorarios.add(new ReservaHorario(1, "14:00","14:00"));
        mListHorarios.add(new ReservaHorario(2, "10:00","18:00"));
        mListHorarios.add(new ReservaHorario(3, "14:00","19:00"));
        mListHorarios.add(new ReservaHorario(2, "10:00","13:00"));
        mListHorarios.add(new ReservaHorario(1, "14:00","12:00"));
        mListHorarios.add(new ReservaHorario(2, "10:00","21:00"));
        mRecyclerViewHorario.setLayoutManager(mLayoutManager);
        mRecyclerViewHorario.setNestedScrollingEnabled(true);
        mHorariosAdapter = new HorariosAdapter(mListHorarios, getApplicationContext());
        mRecyclerViewHorario.setAdapter(mHorariosAdapter);


        calendario.setLastVisibleDate(2029, Calendar.DECEMBER, 31);

    }

    private void loadListener() {
        calendario.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {

            }
        });
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerViewHorario);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), ConfirmarReservaActivity.class));
            }
        });
    }

    private void loadViews() {
        mToolbar = findViewById(R.id.toolbar);
        calendario = findViewById(R.id.calendario);
        btnReservar = findViewById(R.id.btnReservar);
        mRecyclerViewHorario = findViewById(R.id.recycler);
    }

}
