package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import com.unse.bienestarestudiantil.Modelos.Inscripcion;
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.ModificarInscripcionDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static android.view.View.GONE;

public class InscripcionesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView btnBack;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FechasAdapter mFechasAdapter;
    ArrayList<ItemBase> mList, mListOficial;
    DialogoProcesamiento dialog;
    LinearLayout latError, latVacio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscripciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        setToolbar();

        loadListener();

        loadInfo(R.drawable.ic_error, "Error al obtener las inscripciones, por favor intenta nuevamente",
                R.drawable.ic_vacio, "No existe ninguna inscripcion realizada a un deporte o beca");

        updateView(-1);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Inscripciones");
        Utils.changeColorDrawable(btnBack, getApplicationContext(), R.color.colorPrimaryDark);
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                procesarClick(position);
            }
        });

    }

    private void procesarClick(int position) {
        Intent intent = null;
        ItemBase itemBase = mListOficial.get(position);
        ItemDato itemDato = (ItemDato) itemBase;
        switch (itemDato.getInscripcion().getTipo()) {
            case Inscripcion.TIPO_BECA:
               // intent = new Intent(getApplicationContext(), null);
                int id = itemDato.getInscripcion().getIdConvocatoria();
                //intent.putExtra(Utils.CREDENCIAL, id);
                //startActivity(intent);
                break;
            case Inscripcion.TIPO_DEPORTE:
                int id2 = itemDato.getInscripcion().getIdInscripcion();
                intent = new Intent(getApplicationContext(), ModificarInscripcionDeporteActivity.class);
                intent.putExtra(Utils.CREDENCIAL, id2);
                startActivity(intent);
                break;
        }
    }

    private void loadData() {
        mList = new ArrayList<>();
        mListOficial = new ArrayList<>();

        mFechasAdapter = new FechasAdapter(getApplicationContext(), mListOficial, FechasAdapter.TIPO_INSCRIPCIONES);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mFechasAdapter);

        loadInfo();


    }

    private void loadInfo(int iconError, String text, int iconVacio, String vacio) {
        ((ImageView) findViewById(R.id.imgIconError)).setBackground(getResources().getDrawable(iconError));
        ((ImageView) findViewById(R.id.imgIconVacio)).setBackground(getResources().getDrawable(iconVacio));
        ((TextView) findViewById(R.id.txtError)).setText(text);
        ((TextView) findViewById(R.id.txtVacio)).setText(vacio);
        ((Button) findViewById(R.id.btnError)).setOnClickListener(this);

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s", Utils.URL_INSCRIPCIONES_GENERALES, id, key);
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
                updateView(2);
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
            updateView(2);
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.get("becas") != null && jsonObject.get("becas") instanceof JSONArray) {

                JSONArray jsonArray = jsonObject.getJSONArray("becas");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);

                    int id = Integer.parseInt(o.getString("idInscripcion"));
                    int idUsuario = Integer.parseInt(o.getString("idUsuario"));
                    int idConvocatoria = Integer.parseInt(o.getString("idConvocatoria"));
                    int anio = Integer.parseInt(o.getString("anio"));
                    int validez = Integer.parseInt(o.getString("validez"));
                    int idEstado = Integer.parseInt(o.getString("idEstado"));
                    String estado = o.getString("nombreE");
                    String titulo = o.getString("nombreB");

                    Inscripcion inscripcion = new Inscripcion(id, titulo, anio, idUsuario, idConvocatoria,
                            validez, Inscripcion.TIPO_BECA, idEstado, estado);

                    ItemDato dato = new ItemDato();
                    dato.setInscripcion(inscripcion);
                    dato.setTipo(ItemDato.TIPO_INSCRIPCION);

                    mList.add(dato);

                }

            } else {

                JSONObject o = jsonObject.getJSONObject("becas");

                int id = Integer.parseInt(o.getString("idInscripcion"));
                int idUsuario = Integer.parseInt(o.getString("idUsuario"));
                int idConvocatoria = Integer.parseInt(o.getString("idConvocatoria"));
                int anio = Integer.parseInt(o.getString("anio"));
                int validez = Integer.parseInt(o.getString("validez"));
                String estado = o.getString("nombreE");
                int idEstado = Integer.parseInt(o.getString("idEstado"));
                String titulo = o.getString("nombreB");

                Inscripcion inscripcion = new Inscripcion(id, titulo, anio, idUsuario, idConvocatoria,
                        validez, Inscripcion.TIPO_BECA, idEstado, estado);

                ItemDato dato = new ItemDato();
                dato.setInscripcion(inscripcion);
                dato.setTipo(ItemDato.TIPO_INSCRIPCION);

                mList.add(dato);

            }

            if (jsonObject.get("deportes") != null && jsonObject.get("deportes") instanceof JSONArray) {

                JSONArray jsonArray = jsonObject.getJSONArray("deportes");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);

                    int id = Integer.parseInt(o.getString("idInscripcion"));
                    int idUsuario = Integer.parseInt(o.getString("idUsuario"));
                    int idConvocatoria = Integer.parseInt(o.getString("idTemporada"));
                    int anio = Integer.parseInt(o.getString("anio"));
                    int validez = Integer.parseInt(o.getString("validez"));
                    int idEstado = Integer.parseInt(o.getString("idEstado"));
                    String estado = o.getString("nombreE");

                    String titulo = o.getString("nombre");

                    Inscripcion inscripcion = new Inscripcion(id, titulo, anio, idUsuario, idConvocatoria,
                            validez, Inscripcion.TIPO_DEPORTE, idEstado, estado);

                    ItemDato dato = new ItemDato();
                    dato.setInscripcion(inscripcion);
                    dato.setTipo(ItemDato.TIPO_INSCRIPCION);

                    mList.add(dato);

                }

            } else {
                JSONObject o = jsonObject.getJSONObject("deportes");

                int id = Integer.parseInt(o.getString("idInscripcion"));
                int idUsuario = Integer.parseInt(o.getString("idUsuario"));
                int idConvocatoria = Integer.parseInt(o.getString("idTemporada"));
                int anio = Integer.parseInt(o.getString("anio"));
                int validez = Integer.parseInt(o.getString("validez"));
                String estado = o.getString("nombreE");
                int idEstado = Integer.parseInt(o.getString("idEstado"));

                String titulo = o.getString("nombre");

                Inscripcion inscripcion = new Inscripcion(id, titulo, anio, idUsuario, idConvocatoria,
                        validez, Inscripcion.TIPO_DEPORTE, idEstado, estado);

                ItemDato dato = new ItemDato();
                dato.setInscripcion(inscripcion);
                dato.setTipo(ItemDato.TIPO_INSCRIPCION);

                mList.add(dato);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            updateView(2);
        }
        HashMap<String, List<ItemBase>> groupedHashMap = groupDataIntoHashMap(mList);

        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        };

        List<String> anios = new ArrayList<>();
        anios.addAll(groupedHashMap.keySet());

        Collections.sort(anios, comparator);

        for (String date : anios) {
            ItemFecha dateItem = new ItemFecha(date);
            mListOficial.add(dateItem);

            for (ItemBase item : groupedHashMap.get(date)) {
                ItemDato generalItem = (ItemDato) item;
                mListOficial.add(generalItem);
            }
        }
        mFechasAdapter.change(mListOficial);

        updateView(1);

    }

    private HashMap<String, List<ItemBase>> groupDataIntoHashMap(List<ItemBase> list) {

        HashMap<String, List<ItemBase>> groupedHashMap = new HashMap<>();

        for (ItemBase dato : list) {

            ItemDato itemDatoReserva = (ItemDato) dato;

            String key = String.valueOf(itemDatoReserva.getInscripcion().getAnio());

            if (groupedHashMap.containsKey(key)) {
                groupedHashMap.get(key).add(dato);
            } else {
                List<ItemBase> nuevaLista = new ArrayList<>();
                nuevaLista.add(dato);
                groupedHashMap.put(key, nuevaLista);
            }
        }
        return groupedHashMap;
    }

    private void updateView(int b) {
        if (b == 1) {
            mRecyclerView.setVisibility(View.VISIBLE);
            latError.setVisibility(GONE);
            latVacio.setVisibility(GONE);
        } else if (b == 2) {
            mRecyclerView.setVisibility(View.GONE);
            latError.setVisibility(View.VISIBLE);
            latVacio.setVisibility(GONE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            latError.setVisibility(GONE);
            latVacio.setVisibility(View.VISIBLE);
        }
    }

    private void loadViews() {
        btnBack = findViewById(R.id.imgFlecha);
        mRecyclerView = findViewById(R.id.recycler);
        latError = findViewById(R.id.layoutError);
        latVacio = findViewById(R.id.layoutVacio);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnError:
                loadInfo();
                break;
        }
    }
}

