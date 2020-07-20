package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Pasajero;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Pasajero;
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.PasajeroAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GestionPasajeroActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerUsuarios;
    PasajeroAdapter mAdapter;
    Button btnBuscar;
    CardView mCardView;
    TextView txtNombre, txtDNI, txtInfo, txtViajes;
    LinearLayout latVacio;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    EditText mEditText;
    String dniNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pasajero);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Pasajeros");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnBuscar.setOnClickListener(this);

    }

    private void loadData() {
        latVacio.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.GONE);
    }

    private void loadViews() {
        txtViajes = findViewById(R.id.txtViajes);
        mCardView = findViewById(R.id.cardInfo);
        latVacio = findViewById(R.id.latVacio);
        txtDNI = findViewById(R.id.txtDni);
        txtNombre = findViewById(R.id.txtName);
        txtInfo = findViewById(R.id.txtInfo);
        mRecyclerUsuarios = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mEditText = findViewById(R.id.edtBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnBuscar:
                buscar();
                break;
        }
    }

    private void buscar() {
        String dni = mEditText.getText().toString();
        Validador validador = new Validador(getApplicationContext());
        if (validador.validarDNI(mEditText)) {
            sendServer(dni);
        } else
            Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
    }

    private void sendServer(final String dni) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&id=%s", Utils.URL_PASAJERO_INFO_SERVICIO, key,
                idLocal, dni);
        StringRequest requestImage = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dniNumber = dni;
                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject, 1);
                    break;
                case 2:
                    loadInfo(jsonObject, 2);
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 5:
                    latVacio.setVisibility(View.VISIBLE);
                    mCardView.setVisibility(View.GONE);
                    txtInfo.setText("Usuario inexistente");
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

    private void loadInfo(JSONObject jsonObject, int tipo) {
        try {
            if (jsonObject.has("mensaje")) {

                ArrayList<Pasajero> mList = new ArrayList<>();
                HashMap<String, Recorrido> recorridoHashMap = new HashMap<>();
                if (tipo == 1) {
                    JSONArray recorridos = jsonObject.getJSONArray("recorrido");
                    for (int i = 0; i < recorridos.length(); i++) {
                        JSONObject o = recorridos.getJSONObject(i);
                        Recorrido pasajero = Recorrido.mapper(o);
                        recorridoHashMap.put(String.valueOf(pasajero.getIdRecorrido()), pasajero);
                    }
                    JSONArray pasajes = jsonObject.getJSONArray("mensaje");
                    for (int i = 0; i < pasajes.length(); i++) {
                        JSONObject o = pasajes.getJSONObject(i);
                        Pasajero pasajero = Pasajero.mapper(o, Pasajero.MEDIUM);
                        pasajero.setNombre(recorridoHashMap.get(String.valueOf(pasajero.getIdRecorrido())).getDescripcion());
                        mList.add(pasajero);
                    }


                }
                String nombre = "NO NOMBRE", apellido = "NO APELLIDO";
                if (jsonObject.has("datos")) {
                    JSONObject datos = jsonObject.getJSONObject("datos");
                    nombre = datos.getString("nombre");
                    apellido = datos.getString("apellido");
                }
                loadData(mList, nombre, apellido);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadData(ArrayList<Pasajero> mList, String nombre, String apellido) {
        latVacio.setVisibility(View.GONE);
        mCardView.setVisibility(View.VISIBLE);
        txtNombre.setText(String.format("%s %s", nombre, apellido));
        txtDNI.setText(dniNumber);
        if (mList.size() > 0) {
            mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
            mRecyclerUsuarios.setLayoutManager(mLayoutManager);
            mRecyclerUsuarios.setHasFixedSize(true);
            mRecyclerUsuarios.setNestedScrollingEnabled(true);
            mAdapter = new PasajeroAdapter(mList, getApplicationContext());
            mRecyclerUsuarios.setAdapter(mAdapter);
            mRecyclerUsuarios.setVisibility(View.VISIBLE);
            txtViajes.setText("Viajes realizados");
        } else {
            mRecyclerUsuarios.setVisibility(View.GONE);
            txtViajes.setText("NO POSEE VIAJES");
        }

    }

}
