package com.unse.bienestarestudiantil.Vistas.Activities.Gestion;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class EstadisticasActivity extends AppCompatActivity implements View.OnClickListener {

    PieChart pieEstadisticas, pieSexo, pieEdad;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        setToolbar();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }


    private void loadData() {
      dataInscriptos();
      dataSexo();
      dataEdad();
    }

    private void dataEdad() {
        pieEdad.setUsePercentValues(true);
        pieEdad.getDescription().setEnabled(false);
        pieEdad.setExtraOffsets(5,10,5,5);

        pieEdad.setDragDecelerationFrictionCoef(0.99f);
        pieEdad.setDrawHoleEnabled(true);
        pieEdad.setHoleColor(Color.WHITE);
        pieEdad.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yEntrada = new ArrayList<>();

        yEntrada.add(new PieEntry(55f, "18-25"));
        yEntrada.add(new PieEntry(10f, "26-32"));
        yEntrada.add(new PieEntry(5f, "32-40"));
        yEntrada.add(new PieEntry(2f, "+40"));

        PieDataSet dataSet = new PieDataSet(yEntrada, "Edades");
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(3f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        Description description = new Description();
        description.setText("Edad de inscriptos");
        description.setTextSize(10f);
        description.setTextColor(getResources().getColor(R.color.colorAccent));
        pieEdad.setDescription(description);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
        //data.setValueFormatter(new IValueFormatter);

        pieEdad.setData(data);
        pieEdad.animateY(2000, Easing.EasingOption.EaseInOutCubic);
    }

    private void dataSexo() {
        pieSexo.setUsePercentValues(true);
        pieSexo.getDescription().setEnabled(false);
        pieSexo.setExtraOffsets(5,10,5,5);

        pieSexo.setDragDecelerationFrictionCoef(0.99f);
        pieSexo.setDrawHoleEnabled(true);
        pieSexo.setHoleColor(Color.WHITE);
        pieSexo.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yEntrada = new ArrayList<>();

        yEntrada.add(new PieEntry(55f, "M"));
        yEntrada.add(new PieEntry(13f, "F"));

        PieDataSet dataSet = new PieDataSet(yEntrada, "Sexo");
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(3f);
        pieSexo.setDrawSliceText(false);
        dataSet.setColors(Color.CYAN,Color.MAGENTA);

        Description description = new Description();
        description.setText("Sexo de inscriptos");
        description.setTextSize(10f);
        description.setTextColor(getResources().getColor(R.color.colorAccent));
        pieSexo.setDescription(description);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
        //data.setValueFormatter(new IValueFormatter);

        pieSexo.setData(data);
        pieSexo.animateY(2000, Easing.EasingOption.EaseInOutCubic);
    }

    private void dataInscriptos() {
        pieEstadisticas.setUsePercentValues(true);
        pieEstadisticas.getDescription().setEnabled(false);
        pieEstadisticas.setExtraOffsets(5,10,5,5);

        pieEstadisticas.setDragDecelerationFrictionCoef(0.99f);
        pieEstadisticas.setDrawHoleEnabled(true);
        pieEstadisticas.setHoleColor(Color.WHITE);
        pieEstadisticas.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yEntrada = new ArrayList<>();

        yEntrada.add(new PieEntry(55f, "FCEyT"));
        yEntrada.add(new PieEntry(23f, "FAyA"));
        yEntrada.add(new PieEntry(12f, "FHCSyS"));
        yEntrada.add(new PieEntry(7f, "FM"));
        yEntrada.add(new PieEntry(17f, "FCF"));

        PieDataSet dataSet = new PieDataSet(yEntrada, "Facultades");
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(3f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        Description description = new Description();
        description.setText("Alumnos inscriptos a Deportes");
        description.setTextSize(10f);
        description.setTextColor(getResources().getColor(R.color.colorAccent));
        pieEstadisticas.setDescription(description);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
        //data.setValueFormatter(new IValueFormatter);

        pieEstadisticas.setData(data);
        pieEstadisticas.animateY(2000, Easing.EasingOption.EaseInOutCubic);
    }

    private void loadViews() {
        pieEstadisticas = findViewById(R.id.pieChart);
        pieEdad = findViewById(R.id.pieChartEdad);
        pieSexo = findViewById(R.id.pieChartSexo);
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
