package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionPacientes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.Modelos.Lista;
import com.unse.bienestarestudiantil.Modelos.Paciente;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.PacientesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PacientesDiaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PacientesAdapter mAdapter;
    ArrayList<Paciente> mPacientes;
    Paciente mPaciente;
    DialogoProcesamiento dialog;
    ImageView imgRefresh;
    ImageView imgIcono;
    int pos = -1, idServicio = -1;
    OnClickOptionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes_dia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.PACIENTE) != null) {
            mPaciente = getIntent().getParcelableExtra(Utils.PACIENTE);
        }

        loadViews();

        setToolbar();

        loadListener();

        loadData();


    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Pacientes");
        imgRefresh.setVisibility(View.VISIBLE);
    }

    private void loadListener() {
        imgRefresh.setOnClickListener(this);
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                pos = position;
                getInfo();
            }
        });
    }

    private void getInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&iu=%s&id=%s", Utils.URL_INFO_CONSULTA, id, key,
                mPacientes.get(pos).getIdUsuario(), id);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response, 2);
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


    private void loadData() {
        mPacientes = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgRefresh = findViewById(R.id.imgRefresh);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInfo();
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = null;
        if (mPaciente != null)
            URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s&id=%s", Utils.URL_PACIENTES_DIARIA, id, key,
                    mPaciente.getDia(), mPaciente.getMes(), mPaciente.getAnio(), id);
        else
            URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s&id=%s", Utils.URL_PACIENTES_DIARIA, id, key, -1, -1, -1, id);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response, 1);
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

    private void procesarRespuesta(String response, int tipo) {
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
                    loadInfo(jsonObject, tipo);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.noAutorizacion));
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
            if (tipo == 1) {
                if (jsonObject.has("mensaje")) {
                    mPacientes = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Paciente pas = Paciente.mapper(o, Paciente.MEDIUM);
                        mPacientes.add(pas);
                    }
                    idServicio = jsonObject.getInt("servicio");
                    if (mPacientes.size() > 0) {
                        mAdapter = new PacientesAdapter(mPacientes, getApplicationContext(), mListener);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                }
            }else
            if (tipo == 2) {
                if (jsonObject.has("mensaje") && jsonObject.has("datos")) {

                    ArrayList<Lista> listas = new ArrayList<>();

                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Consulta pas = Consulta.mapper(o, Consulta.HISTORIAL);
                        listas.add(pas);
                    }
                    jsonArray = jsonObject.getJSONArray("datos");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);
                        Consulta pas = Consulta.mapper(o, Consulta.HISTORIAL_2);
                        listas.add(pas);
                    }

                    Intent i = new Intent(getApplicationContext(), PerfilPacienteActivity.class);
                    i.putExtra(Utils.IS_ADMIN_MODE, mPaciente != null);
                    i.putExtra(Utils.PACIENTE, mPacientes.get(pos));
                    i.putExtra(Utils.DATA_TURNO, listas);
                    i.putExtra(Utils.SERVICIO, idServicio);
                    startActivity(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.imgRefresh:
                mPacientes.clear();
                loadInfo();
                break;
        }
    }
}