package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionIngreso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.ItemDatoPileta;
import com.unse.bienestarestudiantil.Modelos.ItemFechaPileta;
import com.unse.bienestarestudiantil.Modelos.ItemListado;
import com.unse.bienestarestudiantil.Modelos.PiletaIngreso;
import com.unse.bienestarestudiantil.Modelos.PiletaIngresoParcial;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.IngresosPoliHistoricosAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ListadoIngresosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class IngresosHistoricosActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    IngresosPoliHistoricosAdapter adapter;
    DialogoProcesamiento dialog;
    PiletaIngreso piletaIngresos, poliIngresos;

    List<ItemListado> listado = new ArrayList<>();
    List<ItemListado> listaOficial = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos_historicos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {

        loadInfo();

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private HashMap<String, List<ItemListado>> groupDataIntoHashMap(List<ItemListado> list) {

        HashMap<String, List<ItemListado>> groupedHashMap = new HashMap<>();

        for (ItemListado dato : list) {

            ItemDatoPileta piletaIngreso = (ItemDatoPileta) dato;

            //String key = piletaIngreso.getPiletaIngreso().getFecha();
            String key = Utils.getFechaOnlyDay(Utils.getFechaDateWithHour(piletaIngreso.getPiletaIngreso().getFecha()));

            if (groupedHashMap.containsKey(key)) {
                groupedHashMap.get(key).add(dato);
            } else {
                List<ItemListado> nuevaLista = new ArrayList<>();
                nuevaLista.add(dato);
                groupedHashMap.put(key, nuevaLista);
            }
        }

        return groupedHashMap;
    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {

            }
        });

    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);

    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtTitulo)).setText("Listado de Ingresos");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&ie=%s", Utils.URL_INGRESO_TEMPORADA, id, key, id);
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
                Utils.showToast(getApplicationContext(),
                        getString(R.string.servidorOff));
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
                    Utils.showToast(getApplicationContext(),getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(),getString(R.string.noData));
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

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    PiletaIngreso piletaIngreso = PiletaIngreso.mapper(o, 0);
                    //piletaIngresos.add(piletaIngreso);
                    listado.add(new ItemDatoPileta(piletaIngreso));
                }

                JSONArray jsonArray1 = jsonObject.getJSONArray("datos");

                for (int j = 0; j < jsonArray1.length() ; j++) {
                    JSONObject o = jsonArray1.getJSONObject(j);

                    PiletaIngreso poliIngreso = PiletaIngreso.mapper(o, 1);
                    //poliIngresos.add(poliIngreso);
                    listado.add(new ItemDatoPileta(poliIngreso));
                }

                HashMap<String, List<ItemListado>> groupedHashMap = groupDataIntoHashMap(listado);

                for (String date : groupedHashMap.keySet()) {
                    ItemFechaPileta dateItem = new ItemFechaPileta();
                    dateItem.setFecha(date);
                    listaOficial.add(dateItem);

                    for (ItemListado item : groupedHashMap.get(date)) {
                        ItemDatoPileta generalItem = (ItemDatoPileta) item;
                        listaOficial.add(generalItem);
                    }
                }

                adapter = new IngresosPoliHistoricosAdapter(getApplicationContext(), listaOficial);
                mRecyclerView.setAdapter(adapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}