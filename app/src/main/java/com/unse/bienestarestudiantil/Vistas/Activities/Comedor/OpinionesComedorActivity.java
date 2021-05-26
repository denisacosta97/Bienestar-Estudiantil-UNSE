package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Sugerencia;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpinionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OpinionesComedorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayout latVacio;
    DialogoProcesamiento dialog;
    TextView txtTitulo;
    ArrayList<Sugerencia> mSugerencias;
    RecyclerView recyclerOpiniones;
    OpinionesAdapter mOpinionesAdapter;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opiniones_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Opiniones");
    }


    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        txtTitulo = findViewById(R.id.txtTitulo);
        latVacio = findViewById(R.id.latVacio);
        recyclerOpiniones = findViewById(R.id.recycler);
        mProgressBar = findViewById(R.id.progress_bar);
    }


    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s", Utils.URL_SUGERENCIA_LISTA, id, key);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                latVacio.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
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
            mProgressBar.setVisibility(View.GONE);
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
                    loadInfo(jsonObject);
                    break;
                case 2:
                    latVacio.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    latVacio.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    latVacio.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            latVacio.setVisibility(View.VISIBLE);
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                mSugerencias = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Sugerencia sugerencia = Sugerencia.mapper(o, Sugerencia.MEDIUM);

                    mSugerencias.add(sugerencia);

                }
                mOpinionesAdapter.change(mSugerencias);
                mOpinionesAdapter.notifyDataSetChanged();
                txtTitulo.setVisibility(View.VISIBLE);
            } else {
                latVacio.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadData() {
        txtTitulo.setVisibility(View.GONE);
        latVacio.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mSugerencias = new ArrayList<>();
        mOpinionesAdapter = new OpinionesAdapter(mSugerencias, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerOpiniones.setNestedScrollingEnabled(true);
        recyclerOpiniones.setLayoutManager(mLayoutManager);
        recyclerOpiniones.setAdapter(mOpinionesAdapter);

        loadInfo();

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
