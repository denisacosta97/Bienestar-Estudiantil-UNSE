package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionMedicamentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.unse.bienestarestudiantil.Modelos.Medicamento;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicamentosHistoricosActivity extends AppCompatActivity implements View.OnClickListener {

    DialogoProcesamiento dialog;
    ArrayList<Medicamento> mMedicamentos;
    ArrayList<ItemBase> mItems;
    ArrayList<ItemBase> mListOficial;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FechasAdapter adapter;
    ImageView imgIcono;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_historicos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Historial de Turnos");
    }

    private void loadData() {
        loadInfo();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s", Utils.URL_MEDICAM_ALL, id, key);
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
                mMedicamentos = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    Medicamento turno = Medicamento.mapper(o, Medicamento.LOW);
                    mMedicamentos.add(turno);
                }

                mItems = new ArrayList<>();
                for (Medicamento m : mMedicamentos) {
                    ItemDato itemDato = new ItemDato();
                    itemDato.setMedicamento(m);
                    itemDato.setTipo(ItemDato.TIPO_MEDICAM_UAPU);
                    mItems.add(itemDato);
                }

                mListOficial = new ArrayList<>();
                HashMap<String, List<ItemBase>> datos = filtrarPorMes(mItems);
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
                adapter = new FechasAdapter(getApplicationContext(), mListOficial, FechasAdapter.TIPO_TURNO);
                mRecyclerView.setAdapter(adapter);
                //adapter.change(mListOficial);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (mListOficial.get(position) instanceof ItemDato) {
                    Intent i = new Intent(getApplicationContext(), MedicamentosDiaActivity.class);
                    Medicamento med = ((ItemDato) mListOficial.get(position)).getMedicamento();
                    i.putExtra(Utils.DATA_TURNO, med);
                    startActivity(i);
                }
            }
        });
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler);
    }

    private HashMap<String, List<ItemBase>> filtrarPorMes(List<ItemBase> list) {
        HashMap<String, List<ItemBase>> groupedHashMap = new HashMap<>();
        for (ItemBase dato : list) {
            ItemDato itemDatoKey = (ItemDato) dato;
            if (itemDatoKey.getTipoDato() == ItemDato.TIPO_MEDICAM_UAPU) {
                String mes = String.valueOf(itemDatoKey.getMedicamento().getMes());
                if (!groupedHashMap.containsKey(mes)) {
                    for (ItemBase item : list) {
                        ItemDato itemDato = (ItemDato) item;
                        if (itemDato.getMedicamento().getMes() == itemDatoKey.getMedicamento().getMes()) {
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