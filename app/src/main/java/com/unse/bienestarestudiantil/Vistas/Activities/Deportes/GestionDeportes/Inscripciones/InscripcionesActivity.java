package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.Inscripciones;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.Modelos.Temporada;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class InscripcionesActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    ArrayList<Deporte> mDeportes;
    ArrayList<Temporada> mTemporadas;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    RadioGroup mGroup;
    RadioButton rbtDeportes, rbtAnio;
    int modo = 1;
    FechasAdapter mFechasAdapter;
    ArrayList<ItemBase> mList, mListOficial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abm_inscriptos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void loadData() {
        mDeportes = new ArrayList<>();
        mTemporadas = new ArrayList<>();
        mList = new ArrayList<>();
        mListOficial = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mFechasAdapter = new FechasAdapter(getApplicationContext(), mListOficial, FechasAdapter.TIPO_DEPORTES_INSCRIPCIONES);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mFechasAdapter);
        loadInfo();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Inscripciones");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        rbtAnio.setOnClickListener(this);
        rbtDeportes.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerDeportes);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                ItemBase itemBase = mListOficial.get(position);
                if (itemBase.getTipo() == ItemBase.TIPO_FECHA)
                    return;
                switch (modo) {
                    case 1:
                        if (itemBase.getTipo() == ItemBase.TIPO_DATO) {
                            ItemDato itemDato = (ItemDato) itemBase;
                            if (itemDato.getTipo() == ItemDato.TIPO_TEMPORADA) {
                                String idDeport = buscarId(position);
                                if (idDeport != null) {
                                    Deporte deporte = buscarDeporte(Integer.parseInt(idDeport));
                                    if (deporte != null) {
                                        int idDeporte = deporte.getIdDep();
                                        int anio = ((ItemDato) itemBase).getTemporada().getAnio();
                                        int idTemporada = ((ItemDato) itemBase).getTemporada().getIdTemporada();
                                        String deporteName = deporte.getName();
                                        Intent i = new Intent(getApplicationContext(), ListaInscriptosActivity.class);
                                        i.putExtra(Utils.DEPORTE_ID, idDeporte);
                                        i.putExtra(Utils.ANIO, anio);
                                        i.putExtra(Utils.NAME_GENERAL, idTemporada);
                                        i.putExtra(Utils.DEPORTE_NAME, deporteName);
                                        startActivity(i);
                                    }
                                }
                            }


                        } else return;
                        break;
                    case 2:
                        if (itemBase.getTipo() == ItemBase.TIPO_DATO) {
                            ItemDato itemDato = (ItemDato) itemBase;
                            String anio = buscarAnio(position);
                            if (anio != null){
                                Temporada temporada = buscarTemporada(Integer.parseInt(anio),
                                        Integer.parseInt(itemDato.getIdValue()));
                                if (temporada != null) {
                                    int idDeporte = ((ItemDato) itemBase).getDeporte().getIdDep();
                                    int idTemporada = temporada.getIdTemporada();
                                    String deporteName = ((ItemDato) itemBase).getDeporte().getName();
                                    Intent i = new Intent(getApplicationContext(), ListaInscriptosActivity.class);
                                    i.putExtra(Utils.DEPORTE_ID, idDeporte);
                                    i.putExtra(Utils.ANIO, anio);
                                    i.putExtra(Utils.NAME_GENERAL, idTemporada);
                                    i.putExtra(Utils.DEPORTE_NAME, deporteName);
                                    startActivity(i);
                                }
                            }



                        } else return;
                        break;

                }
            }
        });
    }

    private String buscarId(int position) {
        List<ItemBase> listSort = new ArrayList<>();
        List<ItemBase> list = mListOficial.subList(0, position + 1);
        for (int i = list.size() - 1; i >= 0; i--) {
            listSort.add(list.get(i));
        }
        for (ItemBase itemBase : listSort) {
            if (itemBase.getTipo() == ItemBase.TIPO_FECHA) {
                ItemFecha itemDato = (ItemFecha) itemBase;
                return itemDato.getId();
            }
        }
        return null;
    }

    private Temporada buscarTemporada(int anio, int id) {
        for (Temporada temporada : mTemporadas){
            if (temporada.getAnio() == anio && temporada.getIdDeporte() == id){
                return temporada;
            }
        }
        return null;
    }

    private String buscarAnio(int position) {
        List<ItemBase> listSort = new ArrayList<>();
        List<ItemBase> list = mListOficial.subList(0, position + 1);
        for (int i = list.size() - 1; i >= 0; i--) {
            listSort.add(list.get(i));
        }
        for (ItemBase itemBase : listSort) {
            if (itemBase.getTipo() == ItemBase.TIPO_FECHA) {
                ItemFecha itemDato = (ItemFecha) itemBase;
                return itemDato.getAnio();
            }
        }
        return null;
    }

    private Deporte buscarDeporte(int id) {
        for (Deporte deporte : mDeportes) {
            if (deporte.getIdDep() == id)
                return deporte;
        }
        return null;
    }

    private void loadViews() {
        reciclerDeportes = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mGroup = findViewById(R.id.rbGrupoIns);
        rbtAnio = findViewById(R.id.rbtAnio);
        rbtDeportes = findViewById(R.id.rbtDeporte);

    }

    private void loadInfo() {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        String URL = String.format("%s?key=%s&t=%s", Utils.URL_DEPORTE_LISTA, key, 1);
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
                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                    JSONArray temporadas = jsonObject.getJSONArray("temporadas");
                    load(jsonArray, temporadas);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void load(JSONArray jsonArray, JSONArray temporadas) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject j = jsonArray.getJSONObject(i);
                Deporte deporte = Deporte.mapper(j);
                ItemDato dato = new ItemDato();
                dato.setDeporte(deporte);
                dato.setTipo(ItemDato.TIPO_DEPORTE);
                mList.add(dato);
                mDeportes.add(deporte);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < temporadas.length(); j++) {
            try {
                JSONObject tem = temporadas.getJSONObject(j);
                Temporada temporada = Temporada.mapper(tem);
                ItemDato dato = new ItemDato();
                dato.setTemporada(temporada);
                dato.setTipo(ItemDato.TIPO_TEMPORADA);
                mList.add(dato);
                mTemporadas.add(temporada);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        updateList(1);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.rbtAnio:
                updateList(2);
                break;
            case R.id.rbtDeporte:
                updateList(1);
                break;
        }
    }

    private void updateList(int i) {
        modo = i;
        HashMap<String, List<ItemBase>> datos = null;
        switch (i) {
            case 1:
                datos = filtrarListaPorDeporte(mList);
                break;
            case 2:
                datos = filtrarListaPorAnio(mList);
                break;
        }
        List<String> anios = new ArrayList<>();
        anios.addAll(datos.keySet());
        if (modo == 2)
            Collections.sort(anios, Collections.reverseOrder());
        else Collections.sort(anios);
        mListOficial.clear();
        for (String date : anios) {
            ItemFecha dateItem = new ItemFecha(date);
            mListOficial.add(dateItem);
            for (ItemBase item : datos.get(date)) {
                ItemDato generalItem = (ItemDato) item;
                mListOficial.add(generalItem);
                dateItem.setId(generalItem.getIdValue());
            }
        }
        mFechasAdapter.change(mListOficial);
    }

    private HashMap<String, List<ItemBase>> filtrarListaPorDeporte(List<ItemBase> list) {

        HashMap<String, List<ItemBase>> groupedHashMap = new HashMap<>();

        for (ItemBase dato : list) {

            ItemDato itemDatoKey = (ItemDato) dato;

            if (itemDatoKey.getTipoDato() == ItemDato.TIPO_DEPORTE) {

                String idDeporte = String.valueOf(itemDatoKey.getDeporte().getName());

                for (ItemBase item : list) {

                    ItemDato itemDato = (ItemDato) item;

                    if (itemDato.getTipoDato() == ItemDato.TIPO_TEMPORADA) {

                        if (itemDato.getTemporada().getIdDeporte() == itemDatoKey.getDeporte().getIdDep()) {
                            if (groupedHashMap.containsKey(idDeporte)) {
                                groupedHashMap.get(idDeporte).add(itemDato);
                            } else {
                                List<ItemBase> nuevaLista = new ArrayList<>();
                                nuevaLista.add(itemDato);
                                groupedHashMap.put(idDeporte, nuevaLista);
                            }
                        }
                    }
                }
            }


        }
        return groupedHashMap;
    }

    private HashMap<String, List<ItemBase>> filtrarListaPorAnio(List<ItemBase> list) {

        HashMap<String, List<ItemBase>> groupedHashMap = new HashMap<>();

        for (ItemBase dato : list) {

            ItemDato itemDatoKey = (ItemDato) dato;

            if (itemDatoKey.getTipoDato() == ItemDato.TIPO_TEMPORADA) {

                String key = String.valueOf(itemDatoKey.getTemporada().getAnio());

                for (ItemBase item : list) {

                    ItemDato itemDato = (ItemDato) item;

                    if (itemDato.getTipoDato() == ItemDato.TIPO_DEPORTE) {

                        if (itemDato.getDeporte().getIdDep() == itemDatoKey.getTemporada().getIdDeporte())
                            if (groupedHashMap.containsKey(key)) {
                                groupedHashMap.get(key).add(itemDato);
                            } else {
                                List<ItemBase> nuevaLista = new ArrayList<>();
                                nuevaLista.add(itemDato);
                                groupedHashMap.put(key, nuevaLista);
                            }
                    }
                }
            }


        }
        return groupedHashMap;
    }

}
