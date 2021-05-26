package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Estadisticas;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaEspecial;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadisticasAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ReservasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservasComedorDiaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtDescripcion, txtPorcion, txtAlmuerzo, txtCena, txtPostre;
    DialogoProcesamiento dialog;
    CardView cardMenu;
    Menu mMenus;
    RecyclerView mRecyclerView, mRecyclerViewEspecial;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManagerEspecial;
    LinearLayout latVacio, latEspeciales;
    CardView cardEstadisticas;
    ReservasAdapter mReservasAdapter, mReservasAdapterEspecial;
    ArrayList<Reserva> mReservas, mReservasEspeciales;
    ImageView imgIcono;
    ProgressBar mProgressBar;
    BarChart barCantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_dia_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.MENU) != null) {
            mMenus = getIntent().getParcelableExtra(Utils.MENU);
        }

        loadView();

        loadData();

        loadListener();

        setToolbar();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(mMenus == null ? "Reservas del día" : String.format("%s/%s/%s",
                mMenus.getDia(), mMenus.getMes(), mMenus.getAnio()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadData() {
        mReservas = new ArrayList<>();
        mReservasEspeciales = new ArrayList<>();
        latVacio.setVisibility(View.GONE);
        cardEstadisticas.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mReservasAdapter = new ReservasAdapter(mReservas, getApplicationContext(), ReservasAdapter.ADMIN);
        mReservasAdapterEspecial = new ReservasAdapter(mReservasEspeciales, getApplicationContext(), ReservasAdapter.ESPECIAL);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mLayoutManagerEspecial = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerViewEspecial.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerViewEspecial.setLayoutManager(mLayoutManagerEspecial);
        mRecyclerView.setAdapter(mReservasAdapter);
        mRecyclerViewEspecial.setAdapter(mReservasAdapterEspecial);

        loadInfo();


    }

    private void loadInfo() {
        mProgressBar.setVisibility(View.VISIBLE);
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = "";
        if (mMenus == null)
            URL = String.format("%s?idU=%s&key=%s&m=%s", Utils.URL_RESERVA_HOY, id, key, -1);
        else URL = String.format("%s?idU=%s&key=%s&m=%s&d=%s&me=%s&a=%s", Utils.URL_RESERVA_HOY,
                id, key, 1, mMenus.getDia(), mMenus.getMes(), mMenus.getAnio());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mProgressBar.setVisibility(View.GONE);
                Utils.showToast(getApplicationContext(),
                        getString(R.string.servidorOff));
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
            mProgressBar.setVisibility(View.GONE);
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.noData));
                    cardMenu.setVisibility(View.GONE);
                    latVacio.setVisibility(View.VISIBLE);
                    latEspeciales.setVisibility(View.GONE);
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.camposInvalidos));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(),
                    getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("datos")) {

                mMenus = Menu.mapper(jsonObject.getJSONArray("mensaje").getJSONObject(0), Menu.COMPLETE);

                mReservas = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("datos");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Reserva reserva = Reserva.mapper(o, Reserva.MEDIUM);

                    mReservas.add(reserva);

                }

                mReservasEspeciales = new ArrayList<>();

                jsonArray = jsonObject.getJSONArray("especial");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    ReservaEspecial reserva = ReservaEspecial.mapper(o, ReservaEspecial.MEDIUM);

                    mReservasEspeciales.add(reserva);

                }

                if (mReservas.size() > 0) {
                    mReservasAdapter.change(mReservas);
                    loadEstadisticas();
                } else {
                    latVacio.setVisibility(View.VISIBLE);
                }
                String[] comida = mMenus.getComidas();
                txtAlmuerzo.setText(comida[0]);
                txtCena.setText(comida[1]);
                txtPostre.setText(comida[2]);
                txtPorcion.setText(String.valueOf(mMenus.getPorcion()));

                if (mReservasEspeciales.size() > 0) {
                    mReservasAdapterEspecial.change(mReservasEspeciales);
                } else {
                    latEspeciales.setVisibility(View.GONE);
                }

                if (mReservasEspeciales.size() > 0 || mReservas.size() > 0) {
                    latVacio.setVisibility(View.GONE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadView() {
        txtAlmuerzo = findViewById(R.id.txtAlmuerzo);
        txtCena = findViewById(R.id.txtCena);
        txtPostre = findViewById(R.id.txtPostre);
        txtPorcion = findViewById(R.id.txtPorcion);
        cardMenu = findViewById(R.id.cardMenu);
        latEspeciales = findViewById(R.id.latEspecial);
        mRecyclerViewEspecial = findViewById(R.id.recyclerEspecial);
        barCantidad = findViewById(R.id.barCantidad);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        imgIcono = findViewById(R.id.imgFlecha);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler);
        cardEstadisticas = findViewById(R.id.cardEstadistica);
        latVacio = findViewById(R.id.latVacio);
    }

    private void loadEstadisticas() {

        Estadisticas.initBar(barCantidad);
        //Reservas Ultimos 7 dias
        final ArrayList<BarEntry> entries = new ArrayList<>();
        final ArrayList<String> entryLabels = new ArrayList<String>();
        YAxis leftAxis = barCantidad.getAxisLeft();

        leftAxis.setTextSize(12f);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(false);

        int cantidadRes = 0, cantidadReti = 0, cantidadCancelado = 0, cantidadNoRetirados = 0;
        for (Reserva reserva : mReservas) {
            if (reserva.getDescripcion().equals("RESERVADO")) {
                cantidadRes++;
            } else if (reserva.getDescripcion().equals("CANCELADO")) {
                cantidadCancelado++;
            } else if (reserva.getDescripcion().equals("RETIRADO")) {

                cantidadReti++;
            } else if (reserva.getDescripcion().equals("NO RETIRADO")) {

                cantidadNoRetirados++;
            }
        }
        entries.add(new BarEntry(1, mReservas.size()));
        entryLabels.add("Total");
        entries.add(new BarEntry(2, cantidadReti));
        entryLabels.add("Retiros");
        entries.add(new BarEntry(3, cantidadRes));
        entryLabels.add("Reservas");
        entries.add(new BarEntry(4, cantidadCancelado));
        entryLabels.add("Cancelos");
        entries.add(new BarEntry(5, cantidadNoRetirados));
        entryLabels.add("No Retirados");

        ArrayList<Datos> datos = new ArrayList<>();
        for (BarEntry e : entries) {
            datos.add(new Datos(entryLabels.get(((int) e.getX()) - 1), e.getY()));
        }

        RecyclerView linearLayout = findViewById(R.id.latDatos);
        int[] colors = new int[]{ColorTemplate.rgb("#03C5DA"), ColorTemplate.rgb("#32AC37"),
                ColorTemplate.rgb("#E64A19"),ColorTemplate.rgb("#D32F2F"), ColorTemplate.rgb("#C2185B")};
        EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
        linearLayout.setLayoutManager(manager);
        linearLayout.setNestedScrollingEnabled(true);
        linearLayout.setAdapter(adapter);

        Estadisticas.endBar(barCantidad, entries, colors);

        cardEstadisticas.setVisibility(View.VISIBLE);
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
