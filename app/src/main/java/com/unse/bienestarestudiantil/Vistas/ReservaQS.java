package com.unse.bienestarestudiantil.Vistas;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;
import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.SwipeViewPoliModel;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.SwipeViewPoliAdapter;

public class ReservaQS extends AppCompatActivity {

    ViewPager viewPager;
    SwipeViewPoliAdapter adapter;
    List<SwipeViewPoliModel> models;
    Button btnReservar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_qs);

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        DatePickerTimeline timeline = findViewById(R.id.timeline);
        timeline.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
            @Override
            public CharSequence getLabel(Calendar calendar, int index) {
                return (calendar.get(Calendar.MONTH) + 1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
            }
        });

        timeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {

            }
        });

        //timeline.setFirstVisibleDate(2019, Calendar.JANUARY, 1);
        timeline.setLastVisibleDate(2029, Calendar.DECEMBER, 31);
        timeline.getSelectedDay();
        timeline.getSelectedYear();


        models = new ArrayList<>();
        models.add(new SwipeViewPoliModel(R.drawable.imgborrar2, "Salón", "$4200" ,"Salón de X por Y dimensiones para tus eventos, cuenta con cosa1, cosa2, cosa3..."));
        models.add(new SwipeViewPoliModel(R.drawable.imgborrar1, "Quincho 1", "$2000" ,"El quincho 1 tiene la mejor ubicación, cerca de la pileta y baños, cuenta con asador y cosa1, cosa2..."));
        models.add(new SwipeViewPoliModel(R.drawable.imgborrar2, "Quincho 2", "$3500", "El quincho 2 tiene la mejor ubicación, cerca de la pileta y baños, cuenta con asador y cosa1, cosa2..."));
        models.add(new SwipeViewPoliModel(R.drawable.imgborrar1, "Quincho 3", "$3200", "El quincho 1 tiene la mejor ubicación, cerca de la pileta y baños, cuenta con asador y cosa1, cosa2..."));

        adapter = new SwipeViewPoliAdapter(models, this);

        btnReservar = findViewById(R.id.btnReservar);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                btnReservar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(ReservaQS.this, ReservarActivity.class);
                        if(position == 0)
                            myIntent.putExtra("nombre", "Salón");
                        else
                            myIntent.putExtra("nombre", "Quincho " + position);
                        startActivity(myIntent);
                    }
                });

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
