package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
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
import com.unse.bienestarestudiantil.Modelos.Recorrido;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.RecorridoAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GestionRecorridosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerT;
    ArrayList<Recorrido> mRecorridos;
    RecorridoAdapter mRecorridoAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_recorridos);

        setToolbar();

        loadViews();

        loadDataRecycler();

        loadListener();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de recorridos");

    }

    private void loadViews() {
        mRecyclerT = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerT);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), PerfilRecorridoActivity.class);
                i.putExtra(Utils.RECORRIDO, mRecorridos.get(position));
                startActivity(i);
            }
        });

    }

    private void loadDataRecycler() {
        //mRecorridos = new ArrayList<>();

        loadInfo();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerT.setNestedScrollingEnabled(true);
        mRecyclerT.setLayoutManager(mLayoutManager);

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s", Utils.URL_GET_RECORRIDOS, id, key);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //mProgressBar.setVisibility(View.GONE);
                Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
                        getString(R.string.servidorOff), R.drawable.ic_error);
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
                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
                            getString(R.string.errorInternoAdmin), R.drawable.ic_error);
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
//                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
//                            getString(R.string.noReservas), R.drawable.ic_error);
                    break;
                case 4:
                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
                            getString(R.string.camposInvalidos), R.drawable.ic_error);
                    break;
                case 3:
                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
                            getString(R.string.tokenInvalido), R.drawable.ic_error);
                    break;
                case 100:
                    //No autorizado
                    Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
                            getString(R.string.tokenInexistente), R.drawable.ic_error);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showCustomToast(GestionRecorridosActivity.this, getApplicationContext(),
                    getString(R.string.errorInternoAdmin), R.drawable.ic_error);
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                mRecorridos = new ArrayList<>();

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Recorrido recorrido = Recorrido.mapper(o);

                    mRecorridos.add(recorrido);

                }
                mRecorridoAdapter = new RecorridoAdapter(mRecorridos, getApplicationContext());
                mRecyclerT.setAdapter(mRecorridoAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
