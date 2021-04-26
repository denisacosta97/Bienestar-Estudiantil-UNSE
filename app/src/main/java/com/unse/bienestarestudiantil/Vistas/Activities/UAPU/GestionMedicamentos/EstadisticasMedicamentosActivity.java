package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionMedicamentos;

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
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.Modelos.PuntoConectividad;
import com.unse.bienestarestudiantil.R;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class EstadisticasMedicamentosActivity extends AppCompatActivity implements View.OnClickListener {

    PieChart mPieFacultad;
    BarChart mBarMedicamento, mBarEstado;
    LineChart mLineHorario;
    ImageView imgIcono;
    Button btnBuscar;
    TextView txtCantidad, txtFechaIni, txtFechaFin;
    int[] fechaInicio, fechaFin;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_estadistica);
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
        loadBarEstado(null);
        loadBarMedicamento(null);
        loadPieFacultad(null);
        loadLineHorario(null);
    }

    private void loadBarEstado(ArrayList<Medicamento> puntoConectividads) {

        if (puntoConectividads != null && puntoConectividads.size() > 0) {

            mBarEstado.invalidate();
            mBarEstado.clear();
            mBarEstado.getDescription().setEnabled(false);
            mBarEstado.getLegend().setEnabled(false);

            XAxis xAxis2 = mBarEstado.getXAxis();
            xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis2.setTextSize(12f);
            //Habilita los labels
            xAxis2.setDrawAxisLine(true);
            xAxis2.setDrawGridLines(false);

            YAxis leftAxis, rightAxis;
            leftAxis = mBarEstado.getAxisLeft();
            rightAxis = mBarEstado.getAxisRight();

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
            final ArrayList<String> entryLabels = new ArrayList<>();

            HashMap<String, Integer> contador = new HashMap<>();
            for (Medicamento medicamento : puntoConectividads) {
                contador.put(String.valueOf(medicamento.getDescripcion()), medicamento.getCantidad());
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

            mBarEstado.setData(barData);
            mBarEstado.setFitBars(true);
            mBarEstado.invalidate();
            mBarEstado.setScaleEnabled(true);
            mBarEstado.setDoubleTapToZoomEnabled(false);
            mBarEstado.setBackgroundColor(Color.rgb(255, 255, 255));

            xAxis2.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return "";

                }
            });
        } else {
            mBarEstado.clear();
        }

    }

    private void loadLineHorario(ArrayList<Medicamento> horarios) {

        if (horarios != null && horarios.size() > 0) {
            mLineHorario.getDescription().setEnabled(false);
            mLineHorario.setExtraOffsets(5, 10, 5, 5);
            mLineHorario.getLegend().setEnabled(true);
            mLineHorario.setDragDecelerationFrictionCoef(0.99f);

            Map<Integer, Integer> horas = new TreeMap<>();
            for (int i = 8; i <= 19; i++) {
                horas.put(i, 0);
            }
            ArrayList<Entry> entries = new ArrayList<>();
            for (Medicamento hora : horarios) {
                Calendar calendar = Calendar.getInstance();
                Date date = Utils.getFechaDateWithHour(hora.getFechaHora());
                calendar.setTime(date);
                if (calendar.get(Calendar.HOUR_OF_DAY) <= 8) {
                    horas.put(8, horas.get(8) + 1);
                } else if (calendar.get(Calendar.HOUR_OF_DAY) >= 19) {
                    horas.put(19, horas.get(19) + 1);
                } else {
                    horas.put(calendar.get(Calendar.HOUR_OF_DAY),
                            horas.get(calendar.get(Calendar.HOUR_OF_DAY)) + 1);
                }
            }

            int max = -1;
            for (Integer h : horas.keySet()) {
                entries.add(new Entry(h, horas.get(h)));
                if (max < horas.get(h)) {
                    max = horas.get(h);
                }
            }

            YAxis leftAxis = mLineHorario.getAxisLeft();
            leftAxis.setTextSize(12f);
            leftAxis.setAxisMinimum(-1);
            leftAxis.setAxisMaximum(max + 1);

            LineDataSet lineDataSet = new LineDataSet(entries, "Registros por Horario");
            lineDataSet.setColor(ColorTemplate.JOYFUL_COLORS[0]);
            //lineDataSet.setDrawCircles(false);
            lineDataSet.setCircleRadius(5);
            lineDataSet.setCircleColor(Color.BLACK);
            lineDataSet.setValueTextSize(0);
            lineDataSet.setLineWidth(2);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet);

            LineData lineData = new LineData(dataSets);
            mLineHorario.setData(lineData);
            mLineHorario.invalidate();
        } else {
            mLineHorario.clear();
        }
    }

    private void loadPieFacultad(HashMap<String, Integer> map) {

        if (map != null && map.size() > 0) {
            mPieFacultad.invalidate();
            mPieFacultad.clear();
            mPieFacultad.setUsePercentValues(true);
            mPieFacultad.getDescription().setEnabled(false);
            mPieFacultad.setExtraOffsets(5, 10, 5, 5);
            mPieFacultad.getLegend().setEnabled(true);
            mPieFacultad.setDragDecelerationFrictionCoef(0.99f);
            mPieFacultad.setDrawHoleEnabled(true);
            mPieFacultad.setHoleColor(Color.WHITE);
            mPieFacultad.setTransparentCircleRadius(61f);
            mPieFacultad.setEntryLabelTextSize(0);
            mPieFacultad.setEntryLabelColor(Color.BLACK);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            for (String key : map.keySet()) {
                entradasInfo.add(new PieEntry(map.get(key), key));
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

    private void loadBarMedicamento(ArrayList<Medicamento> medicamentos) {

        if (medicamentos != null && medicamentos.size() > 0) {

            mBarMedicamento.invalidate();
            mBarMedicamento.clear();
            mBarMedicamento.getDescription().setEnabled(false);
            mBarMedicamento.getLegend().setEnabled(false);

            XAxis xAxis2 = mBarMedicamento.getXAxis();
            xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis2.setTextSize(12f);
            //Habilita los labels
            xAxis2.setDrawAxisLine(true);
            xAxis2.setDrawGridLines(false);

            YAxis leftAxis, rightAxis;
            leftAxis = mBarMedicamento.getAxisLeft();
            rightAxis = mBarMedicamento.getAxisRight();

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
            final ArrayList<String> entryLabels = new ArrayList<>();

            int medCli = 0, medSexual = 0;
            for (Medicamento medicamento : medicamentos) {
                if (medicamento.getTipoMedicamento().equals("0")) {
                    medCli++;
                } else if (medicamento.getTipoMedicamento().equals("1")) {
                    medSexual++;
                }
            }

            entries.add(new BarEntry(1, medCli));
            entries.add(new BarEntry(2, medSexual));

            entryLabels.add("Clínica Médica");
            entryLabels.add("Salud Sexual y Reprod.");

            BarDataSet barDataSet = new BarDataSet(entries, "");
            barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            barDataSet.setValueTextSize(13);
            barDataSet.setValueTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            barDataSet.setValueTextColor(Color.rgb(155, 155, 155));
            BarData barData = new BarData(barDataSet);
            barData.setBarWidth(0.9f); // set custom bar width

            mBarMedicamento.setData(barData);
            mBarMedicamento.setFitBars(true);
            mBarMedicamento.invalidate();
            mBarMedicamento.setScaleEnabled(true);
            mBarMedicamento.setDoubleTapToZoomEnabled(false);
            mBarMedicamento.setBackgroundColor(Color.rgb(255, 255, 255));

            xAxis2.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return "";

                }
            });
        } else {
            mBarMedicamento.clear();
        }

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        txtFechaIni.setOnClickListener(this);
        txtFechaFin.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
    }

    private void loadViews() {
        mBarEstado = findViewById(R.id.bar_estado);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtFechaFin = findViewById(R.id.txtFechaFin);
        txtFechaIni = findViewById(R.id.txtFechaIni);
        mBarMedicamento = findViewById(R.id.bar);
        mLineHorario = findViewById(R.id.line);
        mPieFacultad = findViewById(R.id.pie);
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
        String URL = String.format("%s?idU=%s&key=%s&iu=%s&fi=%s&ff=%s", Utils.URL_MEDICAM_ESTADISTICA, id, key, id, f1, f2);
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
                    loadBarMedicamento(null);
                    loadPieFacultad(null);
                    loadLineHorario(null);
                    loadBarEstado(null);
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
                HashMap<String, Integer> facultades = new HashMap<>();
                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    facultades.put(o.getString("facultad"), Integer.parseInt(o.getString("cantidad")));
                }
                ArrayList<Medicamento> medicamentos = new ArrayList<>();
                jsonArray = jsonObject.getJSONArray("datos");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Medicamento medicamento = Medicamento.mapper(o, Medicamento.LOW2);
                    medicamentos.add(medicamento);
                }

                ArrayList<Medicamento> estados = new ArrayList<>();
                jsonArray = jsonObject.getJSONArray("datos2");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Medicamento medicamento = Medicamento.mapper(o, Medicamento.LOW3);
                    estados.add(medicamento);
                }

                loadBarEstado(estados);
                loadBarMedicamento(medicamentos);
                loadLineHorario(medicamentos);
                loadPieFacultad(facultades);

                txtCantidad.setText(String.valueOf(medicamentos.size()));
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

        new DatePickerDialog(EstadisticasMedicamentosActivity.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
