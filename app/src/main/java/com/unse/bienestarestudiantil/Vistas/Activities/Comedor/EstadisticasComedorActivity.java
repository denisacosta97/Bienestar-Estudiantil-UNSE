package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.unse.bienestarestudiantil.Herramientas.Estadisticas;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadisticasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EstadisticasComedorActivity extends AppCompatActivity implements View.OnClickListener {

    PieChart mPieFacultad, mPieInformacion;
    BarChart mBarUnicos, mBarSieteDias, mBarMeses;
    LineChart mLineRetiro, mLineaReserva;
    ImageView imgIcono;
    Button btnBuscar;
    TextView txtCantidad, txtFechaIni, txtFechaFin, txtCarreras, txtCantidadAlumnos, txtCantidadReservas;
    int[] fechaInicio, fechaFin;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor_estadistica);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void loadData() {
        fechaFin = new int[]{1, 1, 2021};
        fechaInicio = new int[]{1, 1, 2020};
        txtCantidadAlumnos.setText("0");
        txtCantidadReservas.setText("0");
        txtCarreras.setText("Sin información");
        loadPieInformacion(null);
        loadPieFacultad(null, null);
        loadBarUnicos(null);
        loadBarSieteDias(null, null);
        loadBarMeses(null, null);
        loadLineHoraRetiro(null);
        loadLineHoraReserva(null);
        /* txtCantidad.setText("0");
         */

    }

    private void loadBarSieteDias(Map<String, Float> map, List<Menu> menus) {

        if (map != null && map.size() > 0) {

            Estadisticas.initBar(mBarSieteDias);

            final ArrayList<BarEntry> entries = new ArrayList<>();

            ArrayList<Datos> datos = new ArrayList<>();
            String[] fechasSiete = new String[]{"", "", "", "", "", "", ""};
            int[] idMenus = new int[]{0, 0, 0, 0, 0, 0, 0};
            int inicio = menus.size() - 7, fin = menus.size();
            int i = 6, total = 0;
            List<Menu> ultimosSiete = menus.size() > 7 ? menus.subList(inicio, fin) : menus;
            Collections.reverse(ultimosSiete);
            for (Menu menu : ultimosSiete) {
                fechasSiete[i] = String.format("%02d/%02d", menu.getDia(), menu.getMes());
                idMenus[i] = menu.getIdMenu();
                i--;
            }
            i = 1;
            for (Integer id : idMenus) {
                for (String key : map.keySet()) {
                    if (id.toString().equals(key)) {
                        entries.add(new BarEntry(i, map.get(key)));
                        total += map.get(key);
                        Datos dat = new Datos(fechasSiete[i - 1], map.get(key));
                        datos.add(dat);
                        i++;
                        break;
                    }

                }
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos4);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);


            Estadisticas.endBar(mBarSieteDias, entries, colors);

            txtCantidadReservas.setText(String.valueOf(total));

        } else {
            mBarSieteDias.clear();
        }

    }

    private void loadBarMeses(Map<String, Float> map, List<Menu> menus) {

        if (map != null && map.size() > 0) {

            Estadisticas.initBar(mBarMeses);

            final ArrayList<BarEntry> entries = new ArrayList<>();

            int[] reservasMensuales = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int cantidadPorciones = 0;
            for (Menu menu : menus) {

                int reservas = (int) (map.containsKey(String.valueOf(menu.getIdMenu())) ?
                        map.get(String.valueOf(menu.getIdMenu()))
                        : 0);

                cantidadPorciones = cantidadPorciones + (reservas * menu.getPorcion());

                reservasMensuales[menu.getMes() - 1] = reservasMensuales[menu.getMes() - 1] + reservas;

            }

            /*boolean isData = false;
            for (Integer integer : reservasMensuales) {
                if (integer != 0)
                    isData = true;
            }
            if (isData)
                loadReservasPorMeses(reservasMensuales);*/

            int i = 1;
            ArrayList<Datos> datos = new ArrayList<>();
            for (Integer cantidad : reservasMensuales) {
                entries.add(new BarEntry(i, cantidad));
                Datos dat = new Datos(Utils.getMonth(i).substring(0, 3), Float.parseFloat(cantidad.toString()));
                datos.add(dat);
                i++;
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos5);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endBar(mBarMeses, entries, colors);


        } else {
            mBarMeses.clear();
        }

    }

    private void loadBarUnicos(TreeMap<String, Float> map) {

        if (map != null && map.size() > 0) {

            Estadisticas.initBar(mBarUnicos);

            final ArrayList<BarEntry> entries = new ArrayList<>();
            int i = 1;
            ArrayList<Datos> datos = new ArrayList<>();
            for (String key : map.keySet()) {
                entries.add(new BarEntry(i, map.get(key)));
                Datos dat = new Datos(key, map.get(key));
                datos.add(dat);
                i++;
            }
            RecyclerView linearLayout = findViewById(R.id.latDatos);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);


            Estadisticas.endBar(mBarUnicos, entries, colors);

        } else {
            mBarUnicos.clear();
        }

    }

    private void loadPieInformacion(HashMap<String, Float> map) {

        if (map != null && map.size() > 0) {

            Estadisticas.initPie(mPieInformacion);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            ArrayList<Datos> datos = new ArrayList<>();

            entradasInfo.add(new PieEntry(map.get("total") - map.get("completo"), "Info incompleta"));
            entradasInfo.add(new PieEntry(map.get("completo"), "Info completa"));

            Datos dat = new Datos("Info incompleta", entradasInfo.get(0).getValue());
            datos.add(dat);
            dat = new Datos("Info completa", entradasInfo.get(1).getValue());
            datos.add(dat);


            RecyclerView linearLayout = findViewById(R.id.latDatos3);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endPie(mPieInformacion, entradasInfo, colors);

        } else {
            mPieInformacion.clear();
        }
    }

    private void loadPieFacultad(HashMap<String, Float> map, HashMap<String, Float> carreras) {

        if (map != null && map.size() > 0) {

            Estadisticas.initPie(mPieFacultad);

            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            ArrayList<Datos> datos = new ArrayList<>();
            int total = 0;
            for (String key : map.keySet()) {
                entradasInfo.add(new PieEntry(map.get(key), key));
                Datos dat = new Datos(key, map.get(key));
                total += dat.getCantidad();
                datos.add(dat);
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (String s : carreras.keySet()) {
                if (!s.equals("null")) {
                    stringBuilder.append(String.format("%s - %s", String.valueOf(
                            carreras.get(s).intValue()), s));
                    stringBuilder.append("\n");
                }
            }


            RecyclerView linearLayout = findViewById(R.id.latDatos2);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endPie(mPieFacultad, entradasInfo, colors);

            txtCantidadAlumnos.setText(String.valueOf(total));
            txtCarreras.setText(stringBuilder.toString());

        } else {
            mPieFacultad.clear();
        }
    }

    private void loadLineHoraRetiro(Map<Integer, Float> map) {

        if (map != null && map.size() > 0) {

            Estadisticas.initLine(mLineRetiro);

            Map<Integer, Float> horas = new TreeMap<>();
            ArrayList<Datos> datos = new ArrayList<>();
            ArrayList<Entry> entries = new ArrayList<>();
            for (Integer hora : map.keySet()) {
                horas.put(hora,
                        map.get(hora));
                Datos dat = new Datos(hora < 10 ? "0" + hora + ":00" : hora + ":00", map.get(hora));
                dat.setId(dat.getId() + " - "+map.get(hora).intValue());
                datos.add(dat);

            }

            RecyclerView linearLayout = findViewById(R.id.latDatos7);
            int[] colors = new int[]{Utils.getColors()[0]};
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            float max = -1;
            for (Integer h : horas.keySet()) {
                entries.add(new Entry(h, horas.get(h)));
                if (max < horas.get(h)) {
                    max = horas.get(h);
                }
            }

            Estadisticas.endLine(mLineRetiro, entries, max);

        } else {
            mLineRetiro.clear();
        }
    }

    private void loadLineHoraReserva(Map<Integer, Float> map) {

        if (map != null && map.size() > 0) {

            Estadisticas.initLine(mLineaReserva);

            Map<Integer, Float> horas = new TreeMap<>();
            ArrayList<Datos> datos = new ArrayList<>();
            ArrayList<Entry> entries = new ArrayList<>();
            for (Integer hora : map.keySet()) {
                horas.put(hora,
                        map.get(hora));
                Datos dat = new Datos(hora < 10 ? "0" + hora + ":00" : hora + ":00", map.get(hora));
                dat.setId(dat.getId() + " - "+map.get(hora).intValue());
                datos.add(dat);

            }

            float max = -1;
            for (Integer h : horas.keySet()) {
                entries.add(new Entry(h, horas.get(h)));
                if (max < horas.get(h)) {
                    max = horas.get(h);
                }
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos6);
            int[] colors = new int[]{Utils.getColors()[0]};
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endLine(mLineaReserva, entries, max);

        } else {
            mLineaReserva.clear();
        }
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        txtFechaIni.setOnClickListener(this);
        txtFechaFin.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
    }

    private void loadViews() {
        txtCantidadReservas = findViewById(R.id.txtCantidadReservas);
        txtCantidadAlumnos = findViewById(R.id.txtCantidadA);
        txtCarreras = findViewById(R.id.txtCarreras);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtFechaFin = findViewById(R.id.txtFechaFin);
        txtFechaIni = findViewById(R.id.txtFechaIni);

        btnBuscar = findViewById(R.id.btnBuscar);


        mPieInformacion = findViewById(R.id.pie_informacion);
        mPieFacultad = findViewById(R.id.pie_facultad);
        mBarUnicos = findViewById(R.id.bar_top);
        mBarSieteDias = findViewById(R.id.bar_sieteDias);
        mBarMeses = findViewById(R.id.bar_meses);
        mLineaReserva = findViewById(R.id.line_reserva);
        mLineRetiro = findViewById(R.id.line_retiro);

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
        String URL = String.format("%s?idU=%s&key=%s&iu=%s&fi=%s&ff=%s", Utils.URL_ESTADISTICA_COMEDOR, id, key, id, f1, f2);
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
                    loadPieInformacion(null);
                    loadPieFacultad(null, null);
                    loadBarUnicos(null);
                    loadBarSieteDias(null, null);
                    loadBarMeses(null, null);
                    loadLineHoraRetiro(null);
                    loadLineHoraReserva(null);
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
            if (jsonObject.has("mensaje") || jsonObject.has("top")) {

                HashMap<String, Float> facultades = new HashMap<>();
                HashMap<String, Float> carreras = new HashMap<>();
                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    if (!o.isNull("facultad"))
                        if (!facultades.containsKey(o.getString("facultad"))) {
                            facultades.put(o.getString("facultad"), Float.parseFloat(o.getString("cantidad")));
                        } else {
                            facultades.put(o.getString("facultad"), facultades.get(o.getString("facultad")) +
                                    Float.parseFloat(o.getString("cantidad")));
                        }
                    if (!carreras.containsKey(o.getString("carrera")) && !o.isNull("carrera"))
                        carreras.put(o.getString("carrera"), Float.parseFloat(o.getString("cantidad")));
                }

                TreeMap<String, Float> topUser = new TreeMap<>();
                jsonArray = jsonObject.getJSONArray("top");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    topUser.put(String.format("%s %s", o.getString("nombre"),
                            o.getString("apellido")), Float.parseFloat(o.getString("cantidad")));
                }

                HashMap<String, Float> informacion = new HashMap<>();
                jsonArray = jsonObject.getJSONArray("info");
                JSONObject info = jsonArray.getJSONObject(0);
                informacion.put("completo", Float.parseFloat(info.getString("completo")));
                informacion.put("total", Float.parseFloat(info.getString("total")));

                TreeMap<String, Float> reservas = new TreeMap<>();
                jsonArray = jsonObject.getJSONArray("reservas");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    reservas.put(o.getString("idmenu"), Float.parseFloat(o.getString("cantidad")));
                }

                TreeMap<Integer, Float> horaRetiro = new TreeMap<>();
                jsonArray = jsonObject.getJSONArray("horaretiro");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    horaRetiro.put(Integer.parseInt(o.getString("hora")), Float.parseFloat(o.getString("cantidad")));
                }

                TreeMap<Integer, Float> horaReserva = new TreeMap<>();
                jsonArray = jsonObject.getJSONArray("horareserva");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    horaReserva.put(Integer.parseInt(o.getString("hora")), Float.parseFloat(o.getString("cantidad")));
                }

                ArrayList<Menu> mMenus = new ArrayList<>();
                JSONArray menus = jsonObject.getJSONArray("menu");
                for (int i = 0; i < menus.length(); i++) {

                    JSONObject o = menus.getJSONObject(i);

                    Menu menu = Menu.mapper(o, Menu.ESTADISTICA);

                    mMenus.add(menu);

                }

                loadPieInformacion(informacion);
                loadPieFacultad(facultades, carreras);
                loadBarUnicos(topUser);
                loadBarSieteDias(reservas, mMenus);
                loadBarMeses(reservas, mMenus);
                loadLineHoraRetiro(horaRetiro);
                loadLineHoraReserva(horaReserva);

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

