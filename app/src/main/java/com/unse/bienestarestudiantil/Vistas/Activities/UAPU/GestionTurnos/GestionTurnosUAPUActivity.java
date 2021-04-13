package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionTurnos;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Interfaces.OnClickUser;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.ServiciosUPA;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoBuscarUsuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoOpciones;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GestionTurnosUAPUActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mAdapter;
    ArrayList<Opciones> mOpciones;
    ImageView imgIcono;
    FloatingActionButton fabAgregar;
    int dni = -1;
    DialogoProcesamiento dialog;
    ArrayList<ServiciosUPA> mServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_turnos_uapu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Turnos");
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 1:
                        startActivity(new Intent(getApplicationContext(), TurnosDiaUAPUActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), TurnosHistoricosUAPUActivity.class));
                        break;

                }
                //Utils.showToast(getApplicationContext(), "Item: "+mOpciones.get(position).getTitulo());
            }
        });
        imgIcono.setOnClickListener(this);
        fabAgregar.setOnClickListener(this);

    }

    private void loadData() {
        mOpciones = new ArrayList<>();
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 1, "Turnos del día", R.drawable.ic_item_arrow, R.color.colorFCEyT));
        mOpciones.add(new Opciones(LinearLayout.HORIZONTAL, 2, "Turnos Históricos", R.drawable.ic_item_arrow, R.color.colorFCEyT));

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OpcionesAdapter(mOpciones, getApplicationContext(), 1);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        fabAgregar = findViewById(R.id.fab);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fab:
                searchUser();
                break;
        }
    }

    private void searchUser() {
        DialogoBuscarUsuario dialogoBuscarUsuario = new DialogoBuscarUsuario(getApplicationContext(),
                getSupportFragmentManager(), null, new OnClickUser() {
            @Override
            public void onUserSelected(int idUsuario, Object text) {
                dni = idUsuario;
                loadInfo();
            }
        });
        dialogoBuscarUsuario.setNoValid(true);
        dialogoBuscarUsuario.show(getSupportFragmentManager(), "dialog_buscar");

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&ad=%s", Utils.URL_SERVICIOS, id, key, 0);
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

                mServicios = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    ServiciosUPA serviciosUPA = ServiciosUPA.mapper(o, ServiciosUPA.BASIC);

                    mServicios.add(serviciosUPA);


                }
                if (mServicios.size() > 0) {
                    showServicios(mServicios);
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    private void showServicios(ArrayList<ServiciosUPA> servicios) {
        final ArrayList<Opciones> opciones = new ArrayList<>();
        for (ServiciosUPA s : servicios) {
            if (s.getTurnos() == 1)
                opciones.add(new Opciones(s.getName()));
        }
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getApplicationContext(), SelectorFechaUPAActivity.class);
                intent.putExtra(Utils.SERVICIO, mServicios.get(pos));
                intent.putExtra(Utils.USER_INFO, dni);
                startActivity(intent);

            }
        }, opciones, getApplicationContext());
        dialogoOpciones.show(getSupportFragmentManager(), "opciones");
    }
}