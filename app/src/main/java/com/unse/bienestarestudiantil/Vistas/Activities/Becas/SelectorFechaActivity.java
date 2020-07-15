package com.unse.bienestarestudiantil.Vistas.Activities.Becas;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Horario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.HorariosAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SelectorFechaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    HorariosAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Horario> mList;
    CalendarView mCalendarView;
    CardView mCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_fecha);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {
        mList = new ArrayList<>();
        Date fecha = new Date(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        for(int i = 0; i<32;i++){
            Horario horario = new Horario(i,0,"","");
            String horaInicio = Utils.getHora(calendar.getTime());
            horario.setHoraInicio(horaInicio);
            calendar.add(Calendar.MINUTE, 15);
            fecha = calendar.getTime();
            String horaFin = Utils.getHora(fecha);
            horario.setHoraFin(horaFin);
            mList.add(horario);
        }
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        adapter = new HorariosAdapter(mList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {

            }
        });
        mCardView.setOnClickListener(this);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                if (!Utils.isDateHabilited(calendar)){

                }else{
                    Utils.showToast(getApplicationContext(), "Día no habilitado para turnos");
                }
            }
        });
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        mCalendarView = findViewById(R.id.calendario);
        mCardView = findViewById(R.id.cardContinuar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardContinuar:
                startActivity(new Intent(getApplicationContext(), SelectorReceptoresActivity.class));
                break;
        }
    }
}
