package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.Deportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ControlAsistenciaActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    ArrayList<Deporte> mDeportes;
    DeportesAdapter mDeportesAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    boolean isOff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_asistencia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Asistencia");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadDataRecycler() {
        mDeportes = new ArrayList<>();

        loadInfo();

        mDeportesAdapter = new DeportesAdapter(mDeportes, getApplicationContext(), false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mDeportesAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerDeportes);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (!isOff) {
                    Intent i = new Intent(getApplicationContext(), CalendarAsistenciaActivity.class);
                    i.putExtra(Utils.DEPORTE_NAME, mDeportes.get(position));
                    startActivity(i);
                }else Utils.showToast(getApplicationContext(), getString(R.string.noConexion));
            }
        });

    }

    private void loadViews() {
        reciclerDeportes = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
    }

    public void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValue(Utils.IS_VISIT) ? 1 : 0;
        String URL = String.format("%s?key=%s&id=%s", Utils.URL_DEPORTE_LISTA, key, id);
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
                updateView(1);
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void updateView(int i) {
        switch (i) {
            case 2:
                isOff = false;
                mDeportes.clear();
                break;
            case 1:
                isOff = true;
                mDeportes.clear();
                mDeportes.add(new Deporte(0,1, "Ajedréz", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,2, "Básquet", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,3, "Cestobal", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,4, "Fútbol 11 Masculino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,5, "Fútbol 11 Femenino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,6, "Fútbol Sala Masculino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,7, "Fútbol Sala Femenino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,8, "Hockey", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,9, "Natación", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,10, "Rugby", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,11, "Tenis de Mesa", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,12, "Voleibol Masculino", "", "", "", "", "", "", 0, null));
                mDeportes.add(new Deporte(0,13, "Voleibol Femenino", "", "", "", "", "", "", 0, null));
                mDeportesAdapter.notifyDataSetChanged();
                break;
        }
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
                    updateView(2);
                    loadInfoDeportes(jsonObject.getJSONArray("mensaje"));
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noCredenciales));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            updateView(2);
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfoDeportes(JSONArray mensaje) {
        for (int i = 0; i < mensaje.length(); i++) {
            try {
                JSONObject j = mensaje.getJSONObject(i);
                int val = j.getInt("validez");
                if (val == 1) {
                    Deporte deporte = Deporte.mapper(j);
                    mDeportes.add(deporte);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                updateView(1);
            }

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
