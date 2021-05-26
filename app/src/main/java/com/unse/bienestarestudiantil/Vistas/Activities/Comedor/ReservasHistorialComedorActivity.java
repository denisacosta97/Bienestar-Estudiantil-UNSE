package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.ItemBase;
import com.unse.bienestarestudiantil.Modelos.ItemDato;
import com.unse.bienestarestudiantil.Modelos.ItemFecha;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.ReservaEspecial;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.FechasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoOpciones;
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

public class ReservasHistorialComedorActivity extends AppCompatActivity implements View.OnClickListener {

    DialogoProcesamiento dialog;
    ArrayList<Menu> mMenus;
    ArrayList<ItemBase> mItems;
    ArrayList<ItemBase> mListOficial;
    FloatingActionButton fabPDF;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    FechasAdapter adapter;

    ImageView imgIcono;
    ProgressBar mProgressBar;
    int numberMes;
    String mes;
    String[] meses = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reserva_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Historial de reservas");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        loadInfo();

        adapter = new FechasAdapter(this, mListOficial, FechasAdapter.TIPO_COMEDOR);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void loadInfo() {
        mProgressBar.setVisibility(View.VISIBLE);
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?idU=%s&key=%s&m=%s", Utils.URL_RESERVA_HISTORIAL, id, key, 1);
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
            mProgressBar.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.noData));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(),
                    getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                mMenus = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Menu menu = Menu.mapper(o, Menu.SIMPLE);

                    mMenus.add(menu);
                }
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
            adapter.change(mListOficial);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadListener() {
        fabPDF.setOnClickListener(this);
        imgIcono.setOnClickListener(this);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (mListOficial.get(position) instanceof ItemDato) {
                    Intent i = new Intent(getApplicationContext(), ReservasComedorDiaActivity.class);
                    i.putExtra(Utils.MENU, ((ItemDato) mListOficial.get(position)).getMenu());
                    startActivity(i);
                }
            }
        });
    }

    private void loadViews() {
        fabPDF = findViewById(R.id.fabPDF);
        imgIcono = findViewById(R.id.imgFlecha);
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler);
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
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fabPDF:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        ArrayList<Opciones> opciones = new ArrayList<>();
        opciones.add(new Opciones("REPORTE MENSUAL"));
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                procesarClick(pos);

            }
        }, opciones, getApplicationContext());
        dialogoOpciones.show(getSupportFragmentManager(), "opciones");
    }

    private void procesarClick(int pos) {
        switch (pos) {
            case 0:
                openMonthDialog();
                break;
        }
    }

    private void openMonthDialog() {
        final ArrayList<Opciones> opciones = new ArrayList<>();
        for (String s : meses) {
            opciones.add(new Opciones(s.toUpperCase()));
        }
        DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
            @Override
            public void onClick(int pos) {
                mes = opciones.get(pos).getTitulo();
                procesarClickMes(pos);

            }
        }, opciones, getApplicationContext());
        dialogoOpciones.show(getSupportFragmentManager(), "opciones");
    }

    private void procesarClickMes(int pos) {
        numberMes = pos;
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&me=%s", Utils.URL_DATOS_RESERVA_MENSUAL, id, key, (pos + 1));
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaMes(response);


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

    private void procesarRespuestaMes(String response) {
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
                    loadInfoMes(jsonObject);
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

    private void loadInfoMes(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("reserva") &&
                    jsonObject.has("alumnos")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                ArrayList<Menu> menus = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Menu menu = Menu.mapper(o, Menu.REPORTE);

                    menus.add(menu);
                }

                jsonArray = jsonObject.getJSONArray("alumnos");

                ArrayList<Usuario> alumnos = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    String id = o.getString("idalumno");
                    String facultad = o.getString("facultad");
                    String nombre = o.getString("nombre");
                    String apellido = o.getString("apellido");
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(Integer.parseInt(id));
                    alumno.setFacultad(facultad);
                    alumno.setNombre(nombre);
                    alumno.setApellido(apellido);
                    alumno.setIdUsuario(alumno.getIdAlumno());

                    alumnos.add(alumno);
                }

                jsonArray = jsonObject.getJSONArray("reserva");

                ArrayList<Reserva> reservas = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Reserva reserva = Reserva.mapper(o, Reserva.REPORTE_MENSUAL);

                    reservas.add(reserva);
                }

                jsonArray = jsonObject.getJSONArray("reservaE");

                ArrayList<ReservaEspecial> reservasE = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    ReservaEspecial reserva = ReservaEspecial.mapper(o, Reserva.HISTORIAL);

                    reservasE.add(reserva);
                }

                if (menus.size() > 0 && reservas.size() > 0) {
                    openActivity(menus, alumnos, reservas, reservasE);
                } else {
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                }

            } else {
                Utils.showToast(getApplicationContext(), getString(R.string.noData));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openActivity(ArrayList<Menu> menus, ArrayList<Usuario> alumnos, ArrayList<Reserva> reservas,
                              ArrayList<ReservaEspecial> reservasE) {
       Intent intent = new Intent(getApplicationContext(), ResumenPDFComedorActivity.class);
        intent.putExtra(Utils.ID_MENU, menus);
        intent.putExtra(Utils.ALUMNO_NAME, alumnos);
        intent.putExtra(Utils.RESERVA, reservas);
        intent.putExtra(Utils.RESERVA_ESPECIALES, reservasE);
        intent.putExtra(Utils.MESNAME, mes);
        intent.putExtra(Utils.NUMERO_MES, numberMes);
        startActivity(intent);
    }
}