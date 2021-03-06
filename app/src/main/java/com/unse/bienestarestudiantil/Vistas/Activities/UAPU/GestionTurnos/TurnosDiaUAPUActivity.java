package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionTurnos;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.TurnosUAPU;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TurnosDiaUAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TurnosDiaUAPUActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TurnosDiaUAdapter mAdapter;
    ArrayList<TurnosUAPU> mTurnos;
    DialogoProcesamiento dialog;
    ImageView imgRefresh;
    ImageView imgIcono;
    OnClickOptionListener mListener;
    TurnosUAPU mTurnosUAPU;
    LinearLayout mLayoutVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnos_dia_uapu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DATA_TURNO) != null) {
            mTurnosUAPU = getIntent().getParcelableExtra(Utils.DATA_TURNO);
        }

        loadViews();

        setToolbar();

        loadListener();

        loadData();

        loadInfo();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        ((TextView) findViewById(R.id.txtTitulo)).setText(mTurnosUAPU == null ? "Turnos del día" :
                String.format("%s/%s/%s", mTurnosUAPU.getDia(), mTurnosUAPU.getMes(),
                        mTurnosUAPU.getAnio()));
        if (mTurnosUAPU == null)
            imgRefresh.setVisibility(View.VISIBLE);
        else
            imgRefresh.setVisibility(View.GONE);
    }

    private void loadListener() {
        imgRefresh.setOnClickListener(this);
        imgIcono.setOnClickListener(this);
    }


    private void loadData() {
        mTurnos = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TurnosDiaUAdapter(mTurnos, getApplicationContext(), mListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgRefresh = findViewById(R.id.imgRefresh);
        imgIcono = findViewById(R.id.imgFlecha);
        mLayoutVacio = findViewById(R.id.layoutVacio);
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = null;
        if (mTurnosUAPU == null)
            URL = String.format("%s?idU=%s&key=%s&iu=%s&di=%s&me=%s&an=%s", Utils.URL_TURNOS_DIA_UAPU, id, key, id, -1, -1, -1);
        else
            URL = String.format("%s?idU=%s&key=%s&iu=%s&di=%s&me=%s&an=%s", Utils.URL_TURNOS_DIA_UAPU, id, key, id, mTurnosUAPU.getDia()
                    , mTurnosUAPU.getMes(), mTurnosUAPU.getAnio());

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
                    updateView(0);
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

                    TurnosUAPU turno = TurnosUAPU.mapper(o, TurnosUAPU.COMPLETE);
                    mTurnos.add(turno);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();

    }

    private void updateView(int i) {
        switch (i) {
            case 0:
                mRecyclerView.setVisibility(View.GONE);
                mLayoutVacio.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.imgRefresh:
                mTurnos.clear();
                loadInfo();
                break;
        }
    }
}