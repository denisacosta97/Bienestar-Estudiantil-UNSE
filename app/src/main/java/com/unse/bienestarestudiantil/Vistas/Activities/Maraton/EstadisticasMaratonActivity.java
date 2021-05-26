package com.unse.bienestarestudiantil.Vistas.Activities.Maraton;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.PuntoConectividad;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadisticasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class EstadisticasMaratonActivity extends AppCompatActivity implements View.OnClickListener {

    PieChart mPieCategoria, mPieFacultad, mPieTipo;
    BarChart mBarDistancia;
    ImageView imgIcono;
    Button btnBuscar;
    TextView txtCantidad, txtFechaIni, txtFechaFin;
    int[] fechaInicio, fechaFin;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maraton_estadistica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void loadData() {
        fechaFin = new int[]{1, 1, 2020};
        fechaInicio = new int[]{1, 1, 2020};
        txtCantidad.setText("0");
        loadBarDistancia(null);
        loadPieCategoria(null);
        loadPieFacultad(null);
        loadPieTipo(null);

    }

    private void loadPieTipo(HashMap<String, Float> map) {

        if (map != null && map.size() > 0) {
            mPieTipo.invalidate();
            mPieTipo.clear();
            mPieTipo.setUsePercentValues(true);
            mPieTipo.getDescription().setEnabled(false);
            mPieTipo.setExtraOffsets(5, 10, 5, 5);
            mPieTipo.getLegend().setEnabled(false);
            mPieTipo.setDragDecelerationFrictionCoef(0.99f);
            mPieTipo.setDrawHoleEnabled(true);
            mPieTipo.setHoleColor(Color.WHITE);
            mPieTipo.setTransparentCircleRadius(61f);
            mPieTipo.setEntryLabelTextSize(0);
            mPieTipo.setEntryLabelColor(Color.BLACK);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            ArrayList<Datos> datos = new ArrayList<>();
            for (String key : map.keySet()) {
                entradasInfo.add(new PieEntry(map.get(key), key));
                Datos dat = new Datos(Utils.categorias[Integer.parseInt(key)-1], map.get(key));
                datos.add(dat);
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos4);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            final DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");

            PieDataSet dataSetDatos = new PieDataSet(entradasInfo, "");
            dataSetDatos.setSliceSpace(3f);
            dataSetDatos.setSelectionShift(5f);
            dataSetDatos.setColors(colors);
            PieData pieDataDatos = new PieData(dataSetDatos);
            pieDataDatos.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return mFormat.format(v) + " %";
                }
            });
            pieDataDatos.setValueTextSize(16f);
            pieDataDatos.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            pieDataDatos.setValueTextColor(Color.BLACK);
            mPieTipo.setData(pieDataDatos);
        } else {
            mPieTipo.clear();
        }
    }

    private void loadPieFacultad(HashMap<String, Float> map) {

        if (map != null && map.size() > 0) {
            mPieFacultad.invalidate();
            mPieFacultad.clear();
            mPieFacultad.setUsePercentValues(true);
            mPieFacultad.getDescription().setEnabled(false);
            mPieFacultad.setExtraOffsets(5, 10, 5, 5);
            mPieFacultad.getLegend().setEnabled(false);
            mPieFacultad.setDragDecelerationFrictionCoef(0.99f);
            mPieFacultad.setDrawHoleEnabled(true);
            mPieFacultad.setHoleColor(Color.WHITE);
            mPieFacultad.setTransparentCircleRadius(61f);
            mPieFacultad.setEntryLabelTextSize(0);
            mPieFacultad.setEntryLabelColor(Color.BLACK);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            ArrayList<Datos> datos = new ArrayList<>();
            for (String key : map.keySet()) {
                entradasInfo.add(new PieEntry(map.get(key), key));
                Datos dat = new Datos(key, map.get(key));
                datos.add(dat);
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos3);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            final DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");

            PieDataSet dataSetDatos = new PieDataSet(entradasInfo, "");
            dataSetDatos.setSliceSpace(3f);
            dataSetDatos.setSelectionShift(5f);
            dataSetDatos.setColors(colors);
            PieData pieDataDatos = new PieData(dataSetDatos);
            pieDataDatos.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return mFormat.format(v) + " %";
                }
            });
            pieDataDatos.setValueTextSize(16f);
            pieDataDatos.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            pieDataDatos.setValueTextColor(Color.BLACK);
            mPieFacultad.setData(pieDataDatos);
        } else {
            mPieFacultad.clear();
        }
    }

    private void loadPieCategoria(HashMap<String, Float> map) {

        if (map != null && map.size() > 0) {
            mPieCategoria.invalidate();
            mPieCategoria.clear();
            mPieCategoria.setUsePercentValues(true);
            mPieCategoria.getDescription().setEnabled(false);
            mPieCategoria.setExtraOffsets(5, 10, 5, 5);
            mPieCategoria.getLegend().setEnabled(false);
            mPieCategoria.setDragDecelerationFrictionCoef(0.99f);
            mPieCategoria.setDrawHoleEnabled(true);
            mPieCategoria.setHoleColor(Color.WHITE);
            mPieCategoria.setTransparentCircleRadius(61f);
            mPieCategoria.setEntryLabelTextSize(0);
            mPieCategoria.setEntryLabelColor(Color.BLACK);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            ArrayList<Datos> datos = new ArrayList<>();
            for (String key : map.keySet()) {
                entradasInfo.add(new PieEntry(map.get(key), key));
                Datos dat = new Datos(Utils.rangoEdad[Integer.parseInt(key) - 1].replace("años", ""), map.get(key));
                datos.add(dat);
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos2);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            final DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");
            PieDataSet dataSetDatos = new PieDataSet(entradasInfo, "");
            dataSetDatos.setSliceSpace(3f);
            dataSetDatos.setSelectionShift(5f);
            dataSetDatos.setColors(colors);
            PieData pieDataDatos = new PieData(dataSetDatos);
            pieDataDatos.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return mFormat.format(v) + " %";
                }
            });
            pieDataDatos.setValueTextSize(16f);
            pieDataDatos.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            pieDataDatos.setValueTextColor(Color.BLACK);
            mPieCategoria.setData(pieDataDatos);
        } else {
            mPieCategoria.clear();
        }
    }

    private void loadBarDistancia(HashMap<String, Float> map) {

        if (map != null && map.size() > 0) {

            mBarDistancia.invalidate();
            mBarDistancia.clear();
            mBarDistancia.getDescription().setEnabled(false);
            mBarDistancia.getLegend().setEnabled(false);

            XAxis xAxis2 = mBarDistancia.getXAxis();
            xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis2.setTextSize(12f);
            //Habilita los labels
            xAxis2.setDrawAxisLine(true);
            xAxis2.setDrawGridLines(false);

            YAxis leftAxis, rightAxis;
            leftAxis = mBarDistancia.getAxisLeft();
            rightAxis = mBarDistancia.getAxisRight();

            leftAxis.setTextSize(12f);
            leftAxis.setAxisMinimum(0);
            rightAxis.setAxisMinimum(0);

            rightAxis.setDrawAxisLine(false);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawLabels(false);


            final ArrayList<BarEntry> entries = new ArrayList<>();
            int i = 1;
            ArrayList<Datos> datos = new ArrayList<>();
            for (String key : map.keySet()) {
                entries.add(new BarEntry(i, map.get(key)));
                Datos dat = new Datos(key+" KM", map.get(key));
                datos.add(dat);
                i++;
            }
            RecyclerView linearLayout = findViewById(R.id.latDatos);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setColors(colors);
            barDataSet.setValueTextSize(13);
            barDataSet.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            barDataSet.setValueTextColor(Color.rgb(155, 155, 155));
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.9f); // set custom bar width

            mBarDistancia.setData(barData);
            mBarDistancia.setFitBars(true);
            mBarDistancia.invalidate();
            mBarDistancia.setScaleEnabled(true);
            mBarDistancia.setDoubleTapToZoomEnabled(false);
            mBarDistancia.setBackgroundColor(Color.rgb(255, 255, 255));

            xAxis2.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return "";

                }
            });
        } else {
            mBarDistancia.clear();
        }

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        txtFechaIni.setOnClickListener(this);
        txtFechaFin.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
    }

    private void loadViews() {
        mBarDistancia = findViewById(R.id.bar_distancia);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtFechaFin = findViewById(R.id.txtFechaFin);
        txtFechaIni = findViewById(R.id.txtFechaIni);
        mPieCategoria = findViewById(R.id.pie_categoria);
        mPieTipo = findViewById(R.id.pie_tipou);
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
            case R.id.btnBuscar:
                buscar();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.txtFechaIni:
                openCalendar(0);
                break;
            case R.id.txtFechaFin:
                openCalendar(1);
                break;
        }
    }

    private void loadInfo(String f1, String f2) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&iu=%s&fi=%s&ff=%s", Utils.URL_MARATON_ESTADISTICA, id, key, id, f1, f2);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    loadBarDistancia(null);
                    loadPieCategoria(null);
                    loadPieFacultad(null);
                    loadPieTipo(null);
                    txtCantidad.setText("0");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") || jsonObject.has("datos")) {

                HashMap<String, Float> facultades = new HashMap<>();
                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    if (o.isNull("facultad") || o.getString("facultad").equals("Sin datos"))
                        if (facultades.containsKey("Sin datos")) {
                            facultades.put("Sin datos", facultades.get("Sin datos") + Float.parseFloat(o.getString("cantidad")));
                        } else {
                            facultades.put("Sin datos", Float.parseFloat(o.getString("cantidad")));
                        }
                    else
                        facultades.put(o.getString("facultad"), Float.parseFloat(o.getString("cantidad")));
                }

                HashMap<String, Float> categoria = new HashMap<>();
                jsonArray = jsonObject.getJSONArray("datos");
                int total = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    categoria.put(o.getString("categoria"), Float.parseFloat(o.getString("cantidad")));
                    total = total + Integer.parseInt(o.getString("cantidad"));
                }

                HashMap<String, Float> distancias = new HashMap<>();
                jsonArray = jsonObject.getJSONArray("datos2");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    distancias.put(o.getString("distancia"), Float.parseFloat(o.getString("cantidad")));
                }

                HashMap<String, Float> tipos = new HashMap<>();
                jsonArray = jsonObject.getJSONArray("datos3");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    tipos.put(o.getString("tipousuario"), Float.parseFloat(o.getString("cantidad")));
                }

                loadPieTipo(tipos);
                loadPieCategoria(categoria);
                loadBarDistancia(distancias);
                loadPieFacultad(facultades);

                txtCantidad.setText(String.valueOf(total));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void buscar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, fechaInicio[0]);
        calendar.set(Calendar.MONTH, fechaInicio[1] - 1);
        calendar.set(Calendar.YEAR, fechaInicio[2]);
        Date fechaI = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, fechaFin[0]);
        calendar.set(Calendar.MONTH, fechaFin[1] - 1);
        calendar.set(Calendar.YEAR, fechaFin[2]);
        Date fechaF = calendar.getTime();
        if (fechaI.before(fechaF) || fechaI.compareTo(fechaF) == 0) {
            //Consulta
            String f1 = String.format("%02d-%02d-%02d", fechaInicio[2], fechaInicio[1], fechaInicio[0]);
            String f2 = String.format("%02d-%02d-%02d", fechaFin[2], fechaFin[1], fechaFin[0]);
            loadInfo(f1, f2);
        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.rangoFechaInvalido));
        }
    }

    private void openCalendar(final int i) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (i == 0) {
                    fechaInicio[0] = dayOfMonth;
                    fechaInicio[1] = monthOfYear + 1;
                    fechaInicio[2] = year;
                    txtFechaIni.setText(String.format("%02d-%02d-%s", fechaInicio[0], fechaInicio[1], fechaInicio[2]));
                } else {
                    fechaFin[0] = dayOfMonth;
                    fechaFin[1] = monthOfYear + 1;
                    fechaFin[2] = year;
                    txtFechaFin.setText(String.format("%02d-%02d-%s", fechaFin[0], fechaFin[1], fechaFin[2]));
                }
            }

        };

        new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
