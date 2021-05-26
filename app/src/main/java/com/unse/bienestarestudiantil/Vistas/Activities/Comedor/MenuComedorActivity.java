package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class MenuComedorActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar mProgressBar;
    LinearLayout latNoData;
    ImageView imgIcono;
    RecyclerView mRecyclerView;
    FechasAdapter fechasAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DialogoProcesamiento dialog;
    ArrayList<ItemBase> mItems;
    ArrayList<ItemBase> mListOficial;
    FloatingActionButton fabAdd;
    ArrayList<Menu> mMenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Menú");
    }


    private void loadData() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        latNoData.setVisibility(View.GONE);
        fechasAdapter = new FechasAdapter(this, mListOficial, FechasAdapter.TIPO_MENU);
        mRecyclerView.setAdapter(fechasAdapter);
        loadInfo();
    }

    private void loadListener() {
        fabAdd.setOnClickListener(this);
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (mListOficial.get(position) instanceof ItemDato) {
                    Intent i = new Intent(getApplicationContext(), NuevoMenuComedorActivity.class);
                    i.putExtra(Utils.ID_MENU, ((ItemDato) mListOficial.get(position)).getMenu());
                    startActivity(i);
                }
            }
        });
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler);
        latNoData = findViewById(R.id.latNoData);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void loadInfo() {

        mProgressBar.setVisibility(View.VISIBLE);
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&iu=%s", Utils.URL_MENU_LISTADO, id, key, id);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mProgressBar.setVisibility(View.GONE);
                latNoData.setVisibility(View.VISIBLE);
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
            mProgressBar.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    latNoData.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    latNoData.setVisibility(View.GONE);
                    loadInfo(jsonObject);
                    break;
                case 2:
                    latNoData.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    break;
                case 3:
                    latNoData.setVisibility(View.VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 100:
                    latNoData.setVisibility(View.VISIBLE);
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            latNoData.setVisibility(View.VISIBLE);
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                mMenus = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Menu menu = Menu.mapper(o, Menu.COMPLETE);

                    mMenus.add(menu);

                }

                mItems = new ArrayList<>();
                for (Menu m : mMenus) {
                    ItemDato itemDato = new ItemDato();
                    itemDato.setMenu(m);
                    itemDato.setTipo(ItemDato.TIPO_MENU);
                    mItems.add(itemDato);
                }
                mListOficial = new ArrayList<>();
                TreeMap<String, List<ItemBase>> datos = filtrarPorMes(mItems);
                List<String> meses = new ArrayList<>();
                meses.addAll(datos.keySet());
                Collections.sort(meses, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return new BigDecimal(o1).compareTo(new BigDecimal(o2));
                    }
                });
                for (String date : meses) {
                    ItemFecha dateItem = new ItemFecha(Utils.getMonth(Integer.parseInt(date)));
                    mListOficial.add(dateItem);
                    for (ItemBase item : datos.get(date)) {
                        ItemDato generalItem = (ItemDato) item;
                        mListOficial.add(generalItem);
                    }
                }

                fechasAdapter.change(mListOficial);

                if (mMenus.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    latNoData.setVisibility(View.VISIBLE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private TreeMap<String, List<ItemBase>> filtrarPorMes(List<ItemBase> list) {

        TreeMap<String, List<ItemBase>> groupedHashMap = new TreeMap<>();

        for (ItemBase dato : list) {

            ItemDato itemDatoKey = (ItemDato) dato;

            if (itemDatoKey.getTipoDato() == ItemDato.TIPO_MENU) {

                String mes = String.valueOf(itemDatoKey.getMenu().getMes());

                if (!groupedHashMap.containsKey(mes)) {

                    for (ItemBase item : list) {

                        ItemDato itemDato = (ItemDato) item;

                        if (itemDato.getMenu().getMes() == itemDatoKey.getMenu().getMes()) {
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
            case R.id.fabAdd:
                startActivity(new Intent(getApplicationContext(), NuevoMenuComedorActivity.class));
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }

    }
}
