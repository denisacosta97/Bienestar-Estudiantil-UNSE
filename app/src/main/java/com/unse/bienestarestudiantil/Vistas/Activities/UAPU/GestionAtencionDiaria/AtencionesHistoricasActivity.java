package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionAtencionDiaria;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.AtencionDiaria;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AtencionesHistoricasActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FechasAdapter mAdapter;
    ArrayList<AtencionDiaria> mList;
    ImageView imgIcono;
    DialogoProcesamiento dialog;

    ArrayList<ItemBase> mItems;
    ArrayList<ItemBase> mListOficial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion_diaria_historial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadData() {
        loadInfo(1);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Registros Históricos");
    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                ItemBase itemBase = mListOficial.get(position);
                if (!(itemBase instanceof ItemFecha)) {
                    ItemDato itemDato = (ItemDato) itemBase;
                    AtencionDiaria atencionDiaria = itemDato.getAtencionDiaria();
                    Intent intent = new Intent(getApplicationContext(), AtencionesDiariasActivity.class);
                    intent.putExtra(Utils.USER_INFO, atencionDiaria);
                    startActivity(intent);
                }

            }
        });
        imgIcono.setOnClickListener(this);

    }

    private void loadInfo(int i) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = null;
        if (i == 1) {
            URL = String.format("%s?idU=%s&key=%s&iu=%s", Utils.URL_ATENCION_HISTORICA, id, key, id);
        } else {
            URL = String.format("%s?idU=%s&key=%s&di=%s&me=%s&an=%s", Utils.URL_ATENCION_DIARIA, id, key,
                    -1, -1, -1);
        }

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

                mList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    AtencionDiaria atencionDiaria = AtencionDiaria.mapper(o, AtencionDiaria.FECHA);

                    mList.add(atencionDiaria);

                }

            }

            mItems = new ArrayList<>();
            for (AtencionDiaria m : mList) {
                ItemDato itemDato = new ItemDato();
                itemDato.setAtencionDiaria(m);
                itemDato.setTipo(ItemDato.TIPO_ATENCION);
                mItems.add(itemDato);
            }
            mListOficial = new ArrayList<>();
            TreeMap<String, List<ItemBase>> datos = filtrarPorMes(mItems);
            List<String> meses = new ArrayList<>();
            meses.addAll(datos.keySet());
            for (String date : meses) {
                ItemFecha dateItem = new ItemFecha(Utils.getMonth(Integer.parseInt(date)));
                mListOficial.add(dateItem);
                for (ItemBase item : datos.get(date)) {
                    ItemDato generalItem = (ItemDato) item;
                    mListOficial.add(generalItem);
                }
            }

            if (mListOficial.size() > 0) {
                mAdapter = new FechasAdapter(this, mListOficial, FechasAdapter.TIPO_ATENCION_2);
                mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setNestedScrollingEnabled(false);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private TreeMap<String, List<ItemBase>> filtrarPorMes(List<ItemBase> list) {

        TreeMap<String, List<ItemBase>> groupedHashMap = new TreeMap<>();

        for (ItemBase dato : list) {

            ItemDato itemDatoKey = (ItemDato) dato;

            if (itemDatoKey.getTipoDato() == ItemDato.TIPO_ATENCION) {

                String mes = String.valueOf(itemDatoKey.getAtencionDiaria().getMes());

                if (!groupedHashMap.containsKey(mes)) {

                    for (ItemBase item : list) {

                        ItemDato itemDato = (ItemDato) item;

                        if (itemDato.getAtencionDiaria().getMes() == itemDatoKey.getAtencionDiaria().getMes()) {
                            if (groupedHashMap.containsKey(mes)) {
                                groupedHashMap.get(mes).add(itemDato);
                            } else {
                                List<ItemBase> nuevaLista = new ArrayList<>();
                                nuevaLista.add(itemDato);
                                groupedHashMap.put(mes, nuevaLista);
                            }
                        }
                    }
                }
            }

        }
        return groupedHashMap;
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
