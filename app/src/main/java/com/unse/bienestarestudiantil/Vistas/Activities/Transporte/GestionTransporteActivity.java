package com.unse.bienestarestudiantil.Vistas.Activities.Transporte;

import android.content.Intent;
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
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Colectivo;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Punto;
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer.GestionChoferActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer.GestionServiciosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.Chofer.NuevoServicioActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.EstadisticasTransporteActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionPasajeroActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte.GestionRecorridosActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoOpciones;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GestionTransporteActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    String lat, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_transporte);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión Transporte");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 101:
                        startActivity(new Intent(getApplicationContext(), GestionChoferActivity.class));
                        break;
                    case 102:
                        startActivity(new Intent(getApplicationContext(), GestionRecorridosActivity.class));
                        break;
                    case 103:
                        startActivity(new Intent(getApplicationContext(), GestionPasajeroActivity.class));
                        break;
                    case 104:
                        startActivity(new Intent(getApplicationContext(), EstadisticasTransporteActivity.class));
                        break;
                    case 105:
                        startActivity(new Intent(getApplicationContext(), GestionServiciosActivity.class));
                        break;
                    case 106:
                        getServicios();
                        break;
                    case 107:
                        startActivity(new Intent(getApplicationContext(), RecorridoActivity.class));
                        break;

                }
                Utils.showToast(getApplicationContext(), "Item: " + mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void getServicios() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s", Utils.URL_SERVICIO_CHOFER, key,
                idLocal);
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
                    Utils.showToast(getApplicationContext(), getString(R.string.serviciosNoHay));
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
            if (jsonObject.has("mensaje") && jsonObject.has("datos")) {

                ArrayList<Opciones> listRecorridos = new ArrayList<>();
                ArrayList<Recorrido> recorridos = new ArrayList<>();
                ArrayList<Opciones> listColectivos = new ArrayList<>();
                ArrayList<Colectivo> colectivos = new ArrayList<>();
                JSONArray arrayRecorridos = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < arrayRecorridos.length(); i++) {
                    JSONObject o = arrayRecorridos.getJSONObject(i);
                    String recorrido = o.getString("descripcion");
                    int idRecorrido = Integer.parseInt(o.getString("idrecorrido"));
                    int validez = Integer.parseInt(o.getString("validez"));
                    Opciones opciones = new Opciones(recorrido);
                    Recorrido recor = new Recorrido(idRecorrido, recorrido, validez);
                    recorridos.add(recor);
                    listRecorridos.add(opciones);
                }

                JSONArray arrayVehiculos = jsonObject.getJSONArray("datos");
                for (int i = 0; i < arrayVehiculos.length(); i++) {
                    JSONObject o = arrayVehiculos.getJSONObject(i);
                    String patente = o.getString("patente");
                    int capacidad = Integer.parseInt(o.getString("capacidad"));
                    int validez = Integer.parseInt(o.getString("validez"));
                    Opciones opciones = new Opciones(String.format("%s - C: %s", patente, capacidad));
                    Colectivo colectivo = new Colectivo(validez, capacidad, patente, "");
                    colectivos.add(colectivo);
                    listColectivos.add(opciones);
                }

                openDialogOptions(listRecorridos, listColectivos,
                        recorridos, colectivos);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void openDialogOptions(final ArrayList<Opciones> listRecorridos, final ArrayList<Opciones> listColectivos,
                                   final ArrayList<Recorrido> recorridos, final ArrayList<Colectivo> colectivos) {
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                openDialogColectivo(pos, listColectivos, recorridos, colectivos);
            }
        }, listRecorridos, getApplicationContext());
        dialogoOpciones.show(getSupportFragmentManager(), "opciones_recorridos");
    }

    private void openDialogColectivo(final int idRecorrido,
                                     ArrayList<Opciones> listColectivos,
                                     final ArrayList<Recorrido> recorridos,
                                     final ArrayList<Colectivo> colectivos) {
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                Recorrido recorrido = recorridos.get(idRecorrido);
                Colectivo colectivo = colectivos.get(pos);
                nuevoServicio(recorrido, colectivo);

            }
        }, listColectivos, getApplicationContext());
        dialogoOpciones.show(getSupportFragmentManager(), "opciones_colectivos");
    }

    private void nuevoServicio(Recorrido recorrido, Colectivo colectivo) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&idC=%s&pat=%s&ir=%s&lat=%s&log=%s", Utils.URL_INICIAR_SERVICIO, key,
                idLocal, idLocal, colectivo.getPatente(), recorrido.getIdRecorrido(), lat, log);
        StringRequest requestImage = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaServicio(response);

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

    private void procesarRespuestaServicio(String response) {
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
                    loadInfoServicio(jsonObject);
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

    private void loadInfoServicio(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("datos")
                    && jsonObject.has("posicion")) {

                JSONObject datos = jsonObject.getJSONObject("datos");
                Servicio servicio = Servicio.mapper(datos, Servicio.COMPLETE);
                servicio.setEstado(2);
                datos = jsonObject.getJSONObject("posicion");
                Punto punto = Punto.mapper(datos, Punto.COMPLETE);

                Intent intent = new Intent(getApplicationContext(), NuevoServicioActivity.class);
                intent.putExtra(Utils.SERVICIO, servicio);
                intent.putExtra(Utils.PUNTO, punto);
                startActivity(intent);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }


    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 101, "Gestión de choferes", R.drawable.ic_conductor, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 102, "Gestión de recorridos", R.drawable.ic_transporte, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 103, "Gestión de pasajeros", R.drawable.ic_pasajeros, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 104, "Estadísticas", R.drawable.ic_estadistica, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 105, "Mis Servicios", R.drawable.ic_transporte, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 106, "Nuevo Servicio", R.drawable.ic_conductor, R.color.colorFCEyT));
        mOpciones.add(new Opciones(true, LinearLayout.VERTICAL, 107, "Gestión de colectivos", R.drawable.ic_transporte, R.color.colorFCEyT));


        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpciones, getApplicationContext(), 1);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
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
