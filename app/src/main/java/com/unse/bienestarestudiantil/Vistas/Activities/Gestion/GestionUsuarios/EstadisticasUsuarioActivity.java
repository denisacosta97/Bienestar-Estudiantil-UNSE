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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Estadisticas;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionMedicamentos.EstadisticasMedicamentosActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadisticasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EstadisticasUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    PieChart mPieFacultad;
    BarChart mBarSexo, mBarProvincia, mBarTipoUsuario;
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
        loadBarTipo(tipo);
    }

    private void loadBarProvincia(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {

            Estadisticas.initBar(mBarProvincia);

            YAxis rightAxis = mBarProvincia.getAxisRight();
            rightAxis.setAxisMinimum(0);

            final ArrayList<BarEntry> entries = new ArrayList<>();

            int i = 0;
            for (Datos dat : datos) {
                String id = dat.getId();
                id = id.replaceAll("[0123456789]", "");
                id = id.trim();
                if (id.charAt(0) == '-')
                    id = id.substring(1);
                dat.setId(String.format("%s - %s", id.trim(), dat.getCantidad().intValue()));
                if (dat.getCantidad() > 1000) {
                    dat.setCantidad(dat.getCantidad() / 1000 + 10);
                }
                entries.add(new BarEntry(i, dat.getCantidad()));

                i++;
            }


            RecyclerView linearLayout = findViewById(R.id.latDatos4);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 2);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endBar(mBarProvincia, entries, colors);

        } else {
            mBarProvincia.clear();
        }

    }

    private void loadBarSexo(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {

            Estadisticas.initBar(mBarSexo);

            YAxis rightAxis = mBarSexo.getAxisRight();
            rightAxis.setAxisMinimum(0);

            final ArrayList<BarEntry> entries = new ArrayList<>();

            int i = 0;
            for (Datos dat : datos) {
                entries.add(new BarEntry(i, dat.getCantidad()));
                i++;

            }

            RecyclerView linearLayout = findViewById(R.id.latDatos2);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);


            Estadisticas.endBar(mBarSexo, entries, colors);
        } else {
            mBarSexo.clear();
        }

    }

    private void loadPieFacultad(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {

            Estadisticas.initPie(mPieFacultad);


            ArrayList<PieEntry> entradasInfo = new ArrayList<>();
            for (Datos key : datos) {
                entradasInfo.add(new PieEntry(key.getCantidad(), key));
                key.setId(String.format("%s - %s", key.getId(), key.getCantidad().intValue()));
            }

            RecyclerView linearLayout = findViewById(R.id.latDatos3);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endPie(mPieFacultad, entradasInfo, colors);

        } else {
            mPieFacultad.clear();
        }
    }

    private void loadBarTipo(ArrayList<Datos> datos) {

        if (datos != null && datos.size() > 0) {

            Estadisticas.initBar(mBarTipoUsuario);


            YAxis rightAxis = mBarSexo.getAxisRight();
            rightAxis.setAxisMinimum(0);

            final ArrayList<BarEntry> entries = new ArrayList<>();

            int i = 0;
            for (Datos dat : datos) {
                entries.add(new BarEntry(i, dat.getCantidad()));
                i++;

            }

            RecyclerView linearLayout = findViewById(R.id.latDatos);
            int[] colors = Utils.getColors();
            EstadisticasAdapter adapter = new EstadisticasAdapter(datos, getApplicationContext(), colors);
            RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 3);
            linearLayout.setLayoutManager(manager);
            linearLayout.setNestedScrollingEnabled(true);
            linearLayout.setAdapter(adapter);

            Estadisticas.endBar(mBarTipoUsuario, entries, colors);

        } else {
            mBarTipoUsuario.clear();
        }
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadViews() {
        mBarProvincia = findViewById(R.id.bar_provincia);
        mBarSexo = findViewById(R.id.bar_sexo);
        mBarTipoUsuario = findViewById(R.id.bar_usuario);
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

