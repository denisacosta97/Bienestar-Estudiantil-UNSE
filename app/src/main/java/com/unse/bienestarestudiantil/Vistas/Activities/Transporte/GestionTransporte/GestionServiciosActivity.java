package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Pasajero;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ServiciosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GestionServiciosActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout latFecha, latVacio, latDatos;
    TextView txtFecha, txtFechaPanel;
    Button btnBuscar;
    ImageView imgBack;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ServiciosAdapter mServiciosAdapter;
    ArrayList<Servicio> mList;
    ArrayList<Pasajero> mPasajeros;
    DialogoProcesamiento dialog;

    int diaF = -1, mesF = -1, anioF = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_servicios);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadListener() {
        imgBack.setOnClickListener(this);
        latFecha.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                buscarPasajeros(position);
            }
        });
    }

    private void buscarPasajeros(int position) {
        Servicio servicio = mList.get(position);
        Date fecha = Utils.getFechaDateWithHour(servicio.getFechaInicio());
        Date fechaFin = !servicio.getFechaFin().equals("null") ?
                Utils.getFechaDateWithHour(servicio.getFechaFin())
                : null;
        ArrayList<Pasajero> pasajeros = new ArrayList<>();
        for (Pasajero pas : mPasajeros) {
            Date fechaRegistro = Utils.getFechaDateWithHour(pas.getFechaLocal());
            if (fechaRegistro.before(fechaFin) && fecha.before(fechaRegistro))
                pasajeros.add(pas);
        }
        Intent intent = new Intent(getApplicationContext(), InfoServicioActivity.class);
        intent.putExtra(Utils.SERVICIO, servicio);
        intent.putExtra(Utils.PASAJERO, pasajeros);
        startActivity(intent);

    }

    private void loadData() {
        latDatos.setVisibility(View.GONE);
        latVacio.setVisibility(View.GONE);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
    }

    private void loadViews() {
        imgBack = findViewById(R.id.imgFlecha);
        txtFecha = findViewById(R.id.txtFecha);
        txtFechaPanel = findViewById(R.id.txtFechas);
        latFecha = findViewById(R.id.latFecha);
        latVacio = findViewById(R.id.latNoData);
        btnBuscar = findViewById(R.id.btnBuscar);
        mRecyclerView = findViewById(R.id.recycler);
        latDatos = findViewById(R.id.latDatos);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Servicios");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.latFecha:
                elegirFechaNacimiento();
                break;
            case R.id.btnBuscar:
                buscar();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

    private void buscar() {
        if (diaF != -1 && mesF != -1 && anioF != -1) {
            sendServer(diaF, mesF, anioF);
        } else
            Utils.showToast(getApplicationContext(), getString(R.string.primeroFecha));
    }

    private void sendServer(int diaF, int mesF, int anioF) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&d=%s&m=%s&a=%s",
                Utils.URL_SERVICIOS_BY_FECHA, key,
                idLocal, diaF, mesF, anioF);
        StringRequest requestImage = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                latVacio.setVisibility(View.VISIBLE);
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();
            }
        });
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(requestImage);

    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    latVacio.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfoServicio(jsonObject);
                    break;
                case 2:
                    latVacio.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.serviciosNoHay));
                    break;
                case 3:
                    latVacio.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    latVacio.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    latVacio.setVisibility(View.VISIBLE);
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            latVacio.setVisibility(View.VISIBLE);
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfoServicio(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("datos")) {

                mList = new ArrayList<>();
                mPasajeros = new ArrayList<>();

                JSONArray datos = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject object = datos.getJSONObject(i);
                    Servicio servicio = Servicio.mapper(object, Servicio.COMPLETE);
                    mList.add(servicio);
                }
                datos = jsonObject.getJSONArray("datos");
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject object = datos.getJSONObject(i);
                    Pasajero pasajero = Pasajero.mapper(object, Pasajero.COMPLETE);
                    mPasajeros.add(pasajero);
                }

                if (mList.size() > 0) {
                    latVacio.setVisibility(View.GONE);
                    latDatos.setVisibility(View.VISIBLE);
                    mServiciosAdapter = new ServiciosAdapter(mList, getApplicationContext(), 2);
                    mRecyclerView.setAdapter(mServiciosAdapter);
                    txtFechaPanel.setText(String.format("%02d/%02d/%02d", diaF, mesF, anioF));
                } else {
                    latVacio.setVisibility(View.VISIBLE);
                    latDatos.setVisibility(View.GONE);
                }
            }

        } catch (JSONException e) {
            latVacio.setVisibility(View.VISIBLE);
            latDatos.setVisibility(View.GONE);
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }


    private void elegirFechaNacimiento() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String mes, dia;
                month = month + 1;
                if (month < 10) {
                    mes = "0" + month;
                } else
                    mes = String.valueOf(month);
                if (day < 10)
                    dia = "0" + day;
                else
                    dia = String.valueOf(day);
                diaF = day;
                mesF = month;
                anioF = year;
                final String selectedDate = dia + "-" + mes + "-" + year;
                txtFecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }
}