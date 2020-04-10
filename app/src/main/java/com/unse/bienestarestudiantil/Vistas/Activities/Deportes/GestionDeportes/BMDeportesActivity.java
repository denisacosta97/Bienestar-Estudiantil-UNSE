package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BMDeportesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    ArrayList<Deporte> mDeportes;
    ArrayList<Profesor> mProfesors;
    DeportesAdapter mDeportesAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmdeportes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        getAll();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Modificar información");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mDeportes = new ArrayList<>();
        mProfesors = new ArrayList<>();

        mDeportesAdapter = new DeportesAdapter(mDeportes, getApplicationContext(), false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mDeportesAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerDeportes);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), EditDeportesActivity.class);
                i.putExtra(Utils.DEPORTE_NAME, mDeportes.get(position));
                i.putExtra(Utils.DEPORTE_NAME_PROF, mProfesors.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        reciclerDeportes = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    private void getAll() {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        String URL = String.format("%s?key=%s", Utils.URL_DEPORTE_LISTA, key);

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

            Deporte deporte = new Deporte();
            deporte.setIdDep(j.getInt("idDeporte"));
            deporte.setName(j.getString("nombre"));
            deporte.setDesc(j.getString("descripcion"));
            deporte.setDias(j.getString("diaEntreno"));
            deporte.setHorario(j.getString("horario"));
            deporte.setLugar(j.getString("lugar"));
            deporte.setIconDeporte(j.getInt("idDeporte"));
            mDeportes.add(deporte);

            Profesor profesor = new Profesor();
            profesor.setIdProfesor(j.getInt("idEntrenador"));
            profesor.setFechaIngreso(j.getString("fechaIngreso"));
            profesor.setNombre(j.getString("nombreP"));
            profesor.setApellido(j.getString("apellido"));

            mProfesors.add(profesor);

        }
        mDeportesAdapter.notifyDataSetChanged();
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
