package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionUsuarios;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionMedicamentos.EstadisticasMedicamentosActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EstadisticasUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    PieChart mPieFacultad, mPieTipoUsuario;
    BarChart mBarSexo, mBarProvincia;
    ArrayList<Datos> sexo, facultad, tipo, provincia;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_estadistica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getSerializableExtra(Utils.DATA_TIPO) != null) {
            tipo = (ArrayList<Datos>) getIntent().getSerializableExtra(Utils.DATA_TIPO);
        }
        if (getIntent().getSerializableExtra(Utils.DATA_SEXO) != null) {
            sexo = (ArrayList<Datos>) getIntent().getSerializableExtra(Utils.DATA_SEXO);
        }
        if (getIntent().getSerializableExtra(Utils.DATA_FACULTAD) != null) {
            facultad = (ArrayList<Datos>) getIntent().getSerializableExtra(Utils.DATA_FACULTAD);
        }
        if (getIntent().getSerializableExtra(Utils.DATA_PROVINCIA) != null) {
            provincia = (ArrayList<Datos>) getIntent().getSerializableExtra(Utils.DATA_PROVINCIA);
        }

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void loadData() {
        loadBarProvincia(provincia);
        loadBarSexo(sexo);
        loadPieFacultad(facultad);
        loadPieTipo(tipo);
    }

    private void loadBarProvincia(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {

            mBarProvincia.invalidate();
            mBarProvincia.clear();
            mBarProvincia.getDescription().setEnabled(false);
            mBarProvincia.getLegend().setEnabled(false);

            XAxis xAxis2 = mBarProvincia.getXAxis();
            xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis2.setTextSize(12f);
            //Habilita los labels
            xAxis2.setDrawAxisLine(true);
            xAxis2.setDrawGridLines(false);

            YAxis leftAxis, rightAxis;
            leftAxis = mBarProvincia.getAxisLeft();
            rightAxis = mBarProvincia.getAxisRight();

            leftAxis.setTextSize(12f);
            leftAxis.setAxisMinimum(0);
            //leftAxis.setDrawAxisLine(true);
            //leftAxis.setDrawGridLines(false);
            //leftAxis.setDrawLabels(false);


            rightAxis.setAxisMinimum(0);

            rightAxis.setDrawAxisLine(false);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawLabels(false);


            final ArrayList<BarEntry> entries = new ArrayList<>();

            HashMap<String, Float> contador = new HashMap<>();
            for (Datos dat : datos) {
                if (dat.getCantidad() > 1000) {
                    dat.setCantidad(dat.getCantidad() / 1000 + 10);
                }
                contador.put(String.valueOf(dat.getId()), dat.getCantidad());
            }
            int i = 1;
            int[] colores = new int[ColorTemplate.JOYFUL_COLORS.length + ColorTemplate.LIBERTY_COLORS.length
                    + ColorTemplate.MATERIAL_COLORS.length];
            for (int j = 0; j < colores.length; j++) {
                if (j > ColorTemplate.JOYFUL_COLORS.length - 1) {
                    colores[j] = ColorTemplate.LIBERTY_COLORS[j % 5];
                } else {
                    colores[j] = ColorTemplate.JOYFUL_COLORS[j];
                }
            }
            int j = 10;
            LinearLayout linearLayout = findViewById(R.id.latDatos2);
            for (String key : contador.keySet()) {
                entries.add(new BarEntry(i, contador.get(key)));

                LinearLayout layout = new LinearLayout(this);
                LinearLayout.LayoutParams params3 = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params3.setMargins(0, 0, 0, 0);
                layout.setLayoutParams(params3);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout view = new LinearLayout(this);
                LinearLayout.LayoutParams params2 = new
                        LinearLayout.LayoutParams(50,
                        50);
                params2.setMargins(5, 0, 0, 0);
                view.setLayoutParams(params2);
                try {
                    view.setBackgroundColor(colores[i - 1]);
                } catch (IndexOutOfBoundsException e) {
                    view.setBackgroundColor(colores[j - 1]);
                    j++;
                }

                layout.addView(view);


                TextView textView = new TextView(this);
                LinearLayout.LayoutParams params = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 0, 0, 0);
                textView.setLayoutParams(params);
                textView.setText(key);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextSize(12);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_regular);
                textView.setTypeface(typeface);

                layout.addView(textView);
                linearLayout.addView(layout);
                i++;
            }
            linearLayout.invalidate();

            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setColors(colores);
            barDataSet.setValueTextSize(13);
            barDataSet.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            barDataSet.setValueTextColor(Color.rgb(155, 155, 155));
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.9f); // set custom bar width

            mBarProvincia.setData(barData);
            mBarProvincia.setFitBars(true);
            mBarProvincia.invalidate();
            mBarProvincia.setScaleEnabled(true);
            mBarProvincia.setDoubleTapToZoomEnabled(false);
            mBarProvincia.setBackgroundColor(Color.rgb(255, 255, 255));

            xAxis2.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return "";

                }
            });
        } else {
            mBarProvincia.clear();
        }

    }

    private void loadBarSexo(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {

            mBarSexo.invalidate();
            mBarSexo.clear();
            mBarSexo.getDescription().setEnabled(false);
            mBarSexo.getLegend().setEnabled(false);

            XAxis xAxis2 = mBarSexo.getXAxis();
            xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis2.setTextSize(12f);
            //Habilita los labels
            xAxis2.setDrawAxisLine(true);
            xAxis2.setDrawGridLines(false);

            YAxis leftAxis, rightAxis;
            leftAxis = mBarSexo.getAxisLeft();
            rightAxis = mBarSexo.getAxisRight();

            leftAxis.setTextSize(12f);
            leftAxis.setAxisMinimum(0);
            //leftAxis.setDrawAxisLine(true);
            //leftAxis.setDrawGridLines(false);
            //leftAxis.setDrawLabels(false);


            rightAxis.setAxisMinimum(0);

            rightAxis.setDrawAxisLine(false);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawLabels(false);


            final ArrayList<BarEntry> entries = new ArrayList<>();

            HashMap<String, Float> contador = new HashMap<>();
            for (Datos dat : datos) {
                contador.put(String.valueOf(dat.getId()), dat.getCantidad());
            }
            int i = 1;
            int[] colores = new int[ColorTemplate.JOYFUL_COLORS.length + ColorTemplate.LIBERTY_COLORS.length];
            for (int j = 0; j < colores.length; j++) {
                if (j > ColorTemplate.JOYFUL_COLORS.length - 1) {
                    colores[j] = ColorTemplate.LIBERTY_COLORS[j % 5];
                } else {
                    colores[j] = ColorTemplate.JOYFUL_COLORS[j];
                }
            }
            LinearLayout linearLayout = findViewById(R.id.latDatos);
            for (String key : contador.keySet()) {
                entries.add(new BarEntry(i, contador.get(key)));

                LinearLayout layout = new LinearLayout(this);
                LinearLayout.LayoutParams params3 = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params3.setMargins(0, 0, 0, 0);
                layout.setLayoutParams(params3);
                layout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout view = new LinearLayout(this);
                LinearLayout.LayoutParams params2 = new
                        LinearLayout.LayoutParams(50,
                        50);
                params2.setMargins(5, 0, 0, 0);
                view.setLayoutParams(params2);
                view.setBackgroundColor(colores[i - 1]);
                layout.addView(view);


                TextView textView = new TextView(this);
                LinearLayout.LayoutParams params = new
                        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 0, 0, 0);
                textView.setLayoutParams(params);
                textView.setText(key);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                textView.setTextSize(12);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_regular);
                textView.setTypeface(typeface);

                layout.addView(textView);
                linearLayout.addView(layout);
                i++;
            }
            linearLayout.invalidate();

            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            barDataSet.setValueTextSize(13);
            barDataSet.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            barDataSet.setValueTextColor(Color.rgb(155, 155, 155));
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.9f); // set custom bar width

            mBarSexo.setData(barData);
            mBarSexo.setFitBars(true);
            mBarSexo.invalidate();
            mBarSexo.setScaleEnabled(true);
            mBarSexo.setDoubleTapToZoomEnabled(false);
            mBarSexo.setBackgroundColor(Color.rgb(255, 255, 255));

            xAxis2.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return "";

                }
            });
        } else {
            mBarSexo.clear();
        }

    }

    private void loadPieFacultad(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {
            mPieFacultad.invalidate();
            mPieFacultad.clear();
            mPieFacultad.setUsePercentValues(true);
            mPieFacultad.getDescription().setEnabled(true);
            mPieFacultad.setExtraOffsets(5, 10, 5, 5);
            mPieFacultad.getLegend().setEnabled(true);
            mPieFacultad.setDragDecelerationFrictionCoef(0.99f);
            mPieFacultad.setDrawHoleEnabled(true);
            mPieFacultad.setHoleColor(Color.WHITE);
            mPieFacultad.setTransparentCircleRadius(61f);
            mPieFacultad.setEntryLabelTextSize(0);
            mPieFacultad.setEntryLabelColor(Color.BLACK);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            for (Datos key : datos) {
                entradasInfo.add(new PieEntry(key.getCantidad(), key));
            }
            PieDataSet dataSetDatos = new PieDataSet(entradasInfo, "");
            dataSetDatos.setSliceSpace(3f);
            dataSetDatos.setSelectionShift(5f);
            dataSetDatos.setColors(ColorTemplate.JOYFUL_COLORS);
            //dataSetDatos.setColors(new int[]{R.color.colorRed, R.color.colorFCEyT}, getContext());
            PieData pieDataDatos = new PieData(dataSetDatos);
            pieDataDatos.setValueTextSize(16f);
            pieDataDatos.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            pieDataDatos.setValueTextColor(Color.BLACK);
            mPieFacultad.setData(pieDataDatos);
        } else {
            mPieFacultad.clear();
        }
    }

    private void loadPieTipo(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {
            mPieTipoUsuario.invalidate();
            mPieTipoUsuario.clear();
            mPieTipoUsuario.setUsePercentValues(true);
            mPieTipoUsuario.getDescription().setEnabled(true);
            mPieTipoUsuario.setExtraOffsets(5, 10, 5, 5);
            mPieTipoUsuario.getLegend().setEnabled(true);
            mPieTipoUsuario.setDragDecelerationFrictionCoef(0.99f);
            mPieTipoUsuario.setDrawHoleEnabled(true);
            mPieTipoUsuario.setHoleColor(Color.WHITE);
            mPieTipoUsuario.setTransparentCircleRadius(61f);
            mPieTipoUsuario.setEntryLabelTextSize(0);
            mPieTipoUsuario.setEntryLabelColor(Color.BLACK);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            for (Datos key : datos) {
                entradasInfo.add(new PieEntry(key.getCantidad(), key));
            }
            PieDataSet dataSetDatos = new PieDataSet(entradasInfo, "");
            dataSetDatos.setSliceSpace(3f);
            dataSetDatos.setSelectionShift(5f);
            dataSetDatos.setColors(ColorTemplate.JOYFUL_COLORS);
            //dataSetDatos.setColors(new int[]{R.color.colorRed, R.color.colorFCEyT}, getContext());
            PieData pieDataDatos = new PieData(dataSetDatos);
            pieDataDatos.setValueTextSize(16f);
            pieDataDatos.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            pieDataDatos.setValueTextColor(Color.BLACK);
            mPieTipoUsuario.setData(pieDataDatos);
        } else {
            mPieTipoUsuario.clear();
        }
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadViews() {
        mBarProvincia = findViewById(R.id.bar_provincia);
        mBarSexo = findViewById(R.id.bar_sexo);
        mPieTipoUsuario = findViewById(R.id.pie_usuario);
        mPieFacultad = findViewById(R.id.pie_facultad);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Estadisticas");
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

