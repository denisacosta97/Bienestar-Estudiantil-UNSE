package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AlumnosAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.TorneosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GestionTorneoActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerTorneo;
    ArrayList<Torneo> mTorneos;
    TorneosAdapter mTorneosAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_torneo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();

        getAll();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de torneos");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mTorneos = new ArrayList<>();
        //mTorneos.add(new Torneo(0, R.drawable.ic_cup, "UNSE", "Torneo anual de la UNSE", "Polideportivo UNSE","Polideportivo", new Date(14/3/20), new Date(10/4/20)));
        //mTorneos.add(new Torneo(1, R.drawable.ic_cup, "TORNEO JUR", "Torneo JUR desarrollado entre todas las universidades", "Polideportivo UNSE","Polideportivo", new Date(14/3/20), new Date(10/4/20)));

        mTorneosAdapter = new TorneosAdapter(mTorneos, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mRecyclerTorneo.setNestedScrollingEnabled(true);
        mRecyclerTorneo.setLayoutManager(mLayoutManager);
        mRecyclerTorneo.setAdapter(mTorneosAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerTorneo);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilTorneoEditActivity.class);
                i.putExtra(Utils.TORNEO, (Parcelable) mTorneos.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        mRecyclerTorneo = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void getAll() {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        String URL = String.format("%s?key=%s", Utils.URL_TORNEOS_LISTA, key);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), "Error de conexión o servidor fuera de rango");
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
                case 1:
                    //Exito
                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                    load(jsonArray);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "La contraseña actual ingresada es inválida");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

    private void load(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);

            Torneo torneo = new Torneo();
            torneo.setId(j.getInt("idTorneo"));
            torneo.setNameTorneo(j.getString("nombre"));
            torneo.setLugar(j.getString("lugar"));
            torneo.setFechaInicio(Utils.getFechaDate(j.getString("fechaInicio")));
            torneo.setFechaFin(Utils.getFechaDate(j.getString("fechaFin")));
            torneo.setDesc(j.getString("descripcion"));
            torneo.setUbicacion(j.getString("ubicacion"));

            mTorneos.add(torneo);
        }
        mTorneosAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
