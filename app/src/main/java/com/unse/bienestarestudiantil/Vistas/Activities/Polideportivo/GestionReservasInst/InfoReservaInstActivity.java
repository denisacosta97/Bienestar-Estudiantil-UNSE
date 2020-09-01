package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservasInst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.CuotaEspacio;
import com.unse.bienestarestudiantil.Modelos.PiletaIngreso;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaEspacio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso.ListadoIngresosPiletaActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CuotasEspaciosAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ListadoIngresosAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ReservaEspacioAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoDropTorneo;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class InfoReservaInstActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    ReservaEspacio reservaEspacio, reservas;
    ArrayList<CuotaEspacio> cuota;
    DialogoProcesamiento dialog;
    TextView txtIdReserva, txtEspacio, txtFechaEv, txtFechaRes, txtHoraIni, txtHoraFin, txtEstado,
            txtPrecio, txtNomAp, txtDni, txtCategoria;
    Button btnAddCuota, btnCancelar, btnConfirmar;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    CuotasEspaciosAdapter adapter;
    LinearLayout linlayNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_reserva_inst);

        if (getIntent().getParcelableExtra(Utils.RESERVA) != null) {
            reservaEspacio = getIntent().getParcelableExtra(Utils.RESERVA);
        }

        if (reservaEspacio != null) {
            loadViews();

            loadListener();

            loadInfo();

        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

        setToolbar();

    }

    private void loadData() {
        txtIdReserva.setText(String.valueOf(reservas.getIdReserva()));
        txtEspacio.setText(reservas.getNombreEspacio());
        txtFechaEv.setText(String.format("%s", reservas.getFechaEv()));
        txtFechaRes.setText(String.format("%s", reservas.getFechaRes()));
        txtHoraIni.setText(String.format("%s", reservas.getHoraI()));
        txtHoraFin.setText(String.format("%s", reservas.getHoraF()));
        txtEstado.setText(reservas.getDescRes());
        txtPrecio.setText(reservas.getPrecio());
        String name = reservas.getNombre() + " " + reservas.getApellido();
        txtNomAp.setText(name);
        txtDni.setText(String.valueOf(reservas.getIdUsuario()));
        txtCategoria.setText(reservas.getDescTipo());

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

    }

    private void loadListener() {
        btnConfirmar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnAddCuota.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        txtIdReserva = findViewById(R.id.txtIdReserva);
        txtEspacio = findViewById(R.id.txtEspacio);
        txtFechaEv = findViewById(R.id.txtFechaEv);
        txtFechaRes = findViewById(R.id.txtFechaRes);
        txtHoraIni = findViewById(R.id.txtHoraIni);
        txtHoraFin = findViewById(R.id.txtHoraFin);
        txtEstado = findViewById(R.id.txtEstado);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtNomAp = findViewById(R.id.txtNomAp);
        txtDni = findViewById(R.id.txtDni);
        txtCategoria = findViewById(R.id.txtCategoria);
        btnAddCuota = findViewById(R.id.btnAddCuota);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        linlayNo = findViewById(R.id.linlayNo);
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtTitulo)).setText("Información de reserva");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgIcon:
                onBackPressed();
                break;
            case R.id.btnAddCuota:
                break;
            case R.id.btnCancelar:
                break;
            case R.id.btnConfirmar:
                break;
        }
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&ir=%s", Utils.URL_RESERVA_ESPACIOS, id, key, reservaEspacio.getIdReserva());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mProgressBar.setVisibility(View.GONE);
                Utils.showCustomToast(InfoReservaInstActivity.this, getApplicationContext(),
                        getString(R.string.servidorOff), R.drawable.ic_error);
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
                    Utils.showCustomToast(InfoReservaInstActivity.this, getApplicationContext(),
                            getString(R.string.errorInternoAdmin), R.drawable.ic_error);
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Utils.showCustomToast(InfoReservaInstActivity.this, getApplicationContext(),
                            getString(R.string.camposInvalidos), R.drawable.ic_error);
                    break;
                case 3:
                    Utils.showCustomToast(InfoReservaInstActivity.this, getApplicationContext(),
                            getString(R.string.tokenInvalido), R.drawable.ic_error);
                    break;
                case 100:
                    //No autorizado
                    Utils.showCustomToast(InfoReservaInstActivity.this, getApplicationContext(),
                            getString(R.string.tokenInexistente), R.drawable.ic_error);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showCustomToast(InfoReservaInstActivity.this, getApplicationContext(),
                    getString(R.string.errorInternoAdmin), R.drawable.ic_error);
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                cuota = new ArrayList<>();

                JSONObject jsonObject1 = jsonObject.getJSONObject("mensaje");

                reservas = ReservaEspacio.mapper(jsonObject1, 1);

                JSONArray jsonArray1 = jsonObject.getJSONArray("datos");

                for (int j = 0; j < jsonArray1.length() ; j++) {
                    JSONObject o = jsonArray1.getJSONObject(j);

                    CuotaEspacio cuotaEspacio = CuotaEspacio.mapper(o);
                    cuota.add(cuotaEspacio);
                }

                loadData();
                if(cuota.size() != 0) {
                    adapter = new CuotasEspaciosAdapter(cuota, getApplicationContext());
                    mRecyclerView.setAdapter(adapter);
                } else
                    linlayNo.setVisibility(View.VISIBLE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}