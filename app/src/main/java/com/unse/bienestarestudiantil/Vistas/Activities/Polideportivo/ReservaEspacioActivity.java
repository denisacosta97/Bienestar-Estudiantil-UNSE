package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Modelos.*;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.*;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import java.util.ArrayList;
import java.util.Calendar;

public class ReservaEspacioActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnReservar;
    Toolbar mToolbar;
    RecyclerView mRecyclerHoraQuincho, mRecyclerHoraSalon;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    ArrayList<ReservaHorario> mListHorarios, mListHorario2;

    HorariosAdapter mHorariosAdapter, mHorariosAdapter2;

    DatePickerTimeline calendario;

    int tipo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_espacio);

        if(getIntent().getIntExtra(Utils.DATA_RESERVA,-1) != -1){
            tipo = getIntent().getIntExtra(Utils.DATA_RESERVA, -1);
        }
        if(tipo != -1){

            Utils.setFont(getApplicationContext(), (ViewGroup) findViewById(android.R.id.content),
                    "Montserrat-Regular.ttf");

            setToolbar();

            loadViews();

            loadData();

            loadListener();
        }else{
            Utils.showToast(getApplicationContext(), "Error al abrir la actividad");
        }






       /* calendario.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return (calendar.get(Calendar.MONTH) + 1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });*/




    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadData() {
        if(tipo == Utils.TIPO_CANCHA){
            mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            mListHorarios = new ArrayList<>();
            mListHorarios.add(new ReservaHorario(1,1, "10:00","11:00"));
            mListHorarios.add(new ReservaHorario(2,1, "11:00","12:00"));
            mListHorarios.add(new ReservaHorario(1,2, "12:00","13:00"));
            mListHorarios.add(new ReservaHorario(1,1, "13:00","14:00"));
            mListHorarios.add(new ReservaHorario(2,3, "14:00","15:00"));
            mListHorarios.add(new ReservaHorario(1,1, "15:00","16:00"));
            mListHorarios.add(new ReservaHorario(2,2, "16:00","17:00"));
            mRecyclerHoraQuincho.setLayoutManager(mLayoutManager);
            mRecyclerHoraQuincho.setNestedScrollingEnabled(true);
            mHorariosAdapter = new HorariosAdapter(mListHorarios, getApplicationContext(), true);
            mRecyclerHoraQuincho.setAdapter(mHorariosAdapter);

        }else if(tipo == Utils.TIPO_QUINCHO){
            mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            mListHorarios = new ArrayList<>();
            mListHorarios.add(new ReservaHorario(1,1, "10:00","18:00"));
            mListHorarios.add(new ReservaHorario(2,1, "20:00","02:00"));
            mRecyclerHoraQuincho.setLayoutManager(mLayoutManager);
            mRecyclerHoraQuincho.setNestedScrollingEnabled(true);
            mHorariosAdapter = new HorariosAdapter(mListHorarios, getApplicationContext(), false);
            mRecyclerHoraQuincho.setAdapter(mHorariosAdapter);

            mListHorario2 = new ArrayList<>();
            mListHorario2.add(new ReservaHorario(1, 2,"10:00","19:00"));
            mListHorario2.add(new ReservaHorario(2, 3,"21:00","05:00"));
            mLayoutManager2 = new GridLayoutManager(getApplicationContext(), 2);
            mRecyclerHoraSalon.setLayoutManager(mLayoutManager2);
            mRecyclerHoraQuincho.setNestedScrollingEnabled(true);
            mHorariosAdapter2 = new HorariosAdapter(mListHorario2, getApplicationContext(), false);
            mRecyclerHoraSalon.setAdapter(mHorariosAdapter2);

        }


        calendario.setLastVisibleDate(2029, Calendar.DECEMBER, 31);

    }

    private void loadListener() {
        calendario.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {

            }
        });
        final Intent i = new Intent(getApplicationContext(), ConfirmarReservaActivity.class);
        if(tipo == Utils.TIPO_QUINCHO)
            i.putExtra(Utils.DATA_RESERVA, Utils.TIPO_QUINCHO);
        else
            i.putExtra(Utils.DATA_RESERVA, Utils.TIPO_CANCHA);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerHoraSalon);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                startActivity(i);
            }
        });

        ItemClickSupport itemClickSupport2 = ItemClickSupport.addTo(mRecyclerHoraQuincho);
        itemClickSupport2.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                startActivity(i);
            }
        });
    }

    private void loadViews() {
        mToolbar = findViewById(R.id.toolbar);
        calendario = findViewById(R.id.calendario);
       // btnReservar = findViewById(R.id.btnReservar);
        mRecyclerHoraSalon = findViewById(R.id.recycler2);
        mRecyclerHoraQuincho = findViewById(R.id.recycler);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
