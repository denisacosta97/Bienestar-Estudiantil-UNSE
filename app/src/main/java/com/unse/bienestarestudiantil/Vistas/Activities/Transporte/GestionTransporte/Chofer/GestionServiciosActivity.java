package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer;

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
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ServiciosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GestionServiciosActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgBack;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ServiciosAdapter mServiciosAdapter;
    ArrayList<Servicio> mList;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_servicio);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadListener() {
        imgBack.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                procesarClick(position);
            }
        });
    }

    private void procesarClick(int position) {
        Servicio servicio = mList.get(position);
        if (servicio.getEstado() == 2) {
            //Abrir activity
            Intent intent = new Intent(getApplicationContext(), NuevoServicioActivity.class);
            intent.putExtra(Utils.SERVICIO, servicio);
            startActivity(intent);
        } else if (servicio.getEstado() == 3) {
            //Abrir dialogo de fin
            openDialogAdvertencia(servicio, position);
        }
        //Nada
    }

    private void openDialogAdvertencia(final Servicio servicio, final int position) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(getString(R.string.servicioFinalizarAntiguo))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        finalizarServicio(servicio, position);
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }

    private void finalizarServicio(Servicio servicio, final int position) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&is=%s", Utils.URL_FINALIZAR_SERVICIO, key,
                idLocal, servicio.getIdServicio());
        StringRequest requestImage = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaServicio(response, position);

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

    private void procesarRespuestaServicio(String response, int position) {
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
                    mList.get(position).setEstado(1);
                    mServiciosAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.serviciosNoHay));
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
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadData() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        loadInfo();
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&idC=%s", Utils.URL_SERVICIOS_CHOFER, key,
                idLocal, idLocal);
        StringRequest requestImage = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.choferNoServicio));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
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

                mList = new ArrayList<>();

                JSONArray servicios = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < servicios.length(); i++) {
                    JSONObject o = servicios.getJSONObject(i);
                    Servicio servicio = Servicio.mapper(o, Servicio.MEDIUM);
                    if (servicio.getFechaFin().equals("null")) {
                        if (mList.size() > 0) {
                            servicio.setEstado(3);
                        } else {
                            servicio.setEstado(2);
                        }
                    } else {
                        servicio.setEstado(1);
                    }
                    mList.add(servicio);
                }

                if (mList.size() > 0) {
                    mServiciosAdapter = new ServiciosAdapter(mList, getApplicationContext());
                    mRecyclerView.setAdapter(mServiciosAdapter);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadViews() {
        imgBack = findViewById(R.id.imgFlecha);
        mRecyclerView = findViewById(R.id.recycler);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Mis Servicios");

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