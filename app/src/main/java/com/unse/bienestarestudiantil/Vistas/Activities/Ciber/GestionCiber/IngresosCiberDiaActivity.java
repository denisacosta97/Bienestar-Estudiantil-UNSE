package com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.unse.bienestarestudiantil.Modelos.Impresion;
import com.unse.bienestarestudiantil.Modelos.IngresoCiber;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ImpresionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.IngresosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IngresosCiberDiaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    EditText mEditText;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    IngresosAdapter ingresosAdapter;
    ArrayList<IngresoCiber> mIngresos;
    DialogoProcesamiento dialog;
    IngresoCiber ingresoCiber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos_ciber_dia);

        if (getIntent().getParcelableExtra(Utils.DATA_TURNO) != null)
            ingresoCiber = getIntent().getParcelableExtra(Utils.DATA_TURNO);

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();

        loadInfo();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Ingresos del día");
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mEditText = findViewById(R.id.edtBuscar);
    }

    private void loadDataRecycler() {
        mIngresos = new ArrayList<>();

        ingresosAdapter = new IngresosAdapter(mIngresos, getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(ingresosAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                //Intent i = new Intent(getApplicationContext(), PerfilPasajeroActivity.class);
                //i.putExtra(Utils.IMPRESION, mImpresiones.get(position));
                //startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = "";
        if (ingresoCiber == null)
            URL = String.format("%s?idU=%s&key=%s", Utils.URL_LISTA_INGRESOS_HOY, id, key);
        else URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s", Utils.URL_LISTA_INGRESOS, id, key,
                ingresoCiber.getDia(), ingresoCiber.getMes(), ingresoCiber.getAnio());
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

                    IngresoCiber ingr = IngresoCiber.mapper(o, IngresoCiber.MEDIUM);
                    mIngresos.add(ingr);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ingresosAdapter.notifyDataSetChanged();

    }
}