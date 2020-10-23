package com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.Modelos.FechasInhabilitadas;
import com.unse.bienestarestudiantil.Modelos.Inscripcion;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.ModificarInscripcionDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ConsultasAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasInhabAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoADFechas;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoActivarDesactivar;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListadoFechasInhabilitadasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<FechasInhabilitadas> mList;
    FechasInhabAdapter mAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    int positionReg = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_fechas_inhabilitadas);

        loadViews();

        loadListener();

        loadData();

        setToolbar();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        loadInfo();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Listado consultas");
    }

    private void loadData() {
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new FechasInhabAdapter(mList, getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        loadInfo();

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));

        String URL = String.format("%s?idU=%s&key=%s&an=%s", Utils.URL_FECHAS_INHAB, id, key, calendar.get(Calendar.YEAR));
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
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
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
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    FechasInhabilitadas fecha = FechasInhabilitadas.mapper(o);
                    mList.add(fecha);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                positionReg = position;
                DialogoADFechas dialog = new DialogoADFechas();
                dialog.setContext(getApplicationContext());
                dialog.setFragmentManager(getSupportFragmentManager());
                dialog.setPosition(position);
                dialog.setFecha(mList.get(position));
                dialog.setOnClickListenerAdapter(new OnClickListenerAdapter() {
                    @Override
                    public void onClick(Object id) {
                    }
                });
                dialog.show(getSupportFragmentManager(), "dialogo_fecha_in");
            }
        });

    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        mRecyclerView = findViewById(R.id.recycler);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnError:
                loadInfo();
                break;
        }
    }
}