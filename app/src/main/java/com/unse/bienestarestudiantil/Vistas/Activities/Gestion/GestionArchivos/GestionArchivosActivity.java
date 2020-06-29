package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionArchivos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
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
import com.unse.bienestarestudiantil.Herramientas.PDF.DownloadPDF;
import com.unse.bienestarestudiantil.Herramientas.PDF.LoadInfoPDF;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Archivo;
import com.unse.bienestarestudiantil.Modelos.Area;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ArchivosAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class GestionArchivosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView, mRecyclerViewArchivos;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManagerArchivos;
    ArrayList<Archivo> mList;
    ArrayList<Categoria> mCategorias;
    ArrayList<Area> mAreas;
    CategoriasAdapter mCategoriasAdapter;
    ArchivosAdapter mAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    LinearLayout mLayoutError, mLayoutVacio;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_archivos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        updateView(0);

        loadData();

        setToolbar();

        loadInfo(R.drawable.ic_error, getString(R.string.archivosError),
                R.drawable.ic_vacio, getString(R.string.archivosVacio));

    }

    private void loadInfo(int iconError, String text, int iconVacio, String vacio) {
        ((ImageView) findViewById(R.id.imgIconError)).setBackground(getResources().getDrawable(iconError));
        ((ImageView) findViewById(R.id.imgIconVacio)).setBackground(getResources().getDrawable(iconVacio));
        ((TextView) findViewById(R.id.txtError)).setText(text);
        ((TextView) findViewById(R.id.txtVacio)).setText(vacio);
        ((Button) findViewById(R.id.btnError)).setOnClickListener(this);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(getString(R.string.tituloArchivos));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mCategorias = new ArrayList<>();
        mList = new ArrayList<>();
        mAreas = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManagerArchivos = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        //mAdapter = new ArchivosAdapter(mList, getApplicationContext(), Utils.TIPO_USUARIO);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewArchivos.setHasFixedSize(true);
        mRecyclerViewArchivos.setLayoutManager(mLayoutManagerArchivos);

        loadInfo();

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s", Utils.URL_ARCHIVOS_LISTA, id, key);
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

    private void updateView(int i) {
        switch (i) {
            case 0:
                mRecyclerView.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.GONE);
                mLayoutVacio.setVisibility(View.VISIBLE);
                break;
            case 1:
                mRecyclerView.setVisibility(View.VISIBLE);
                mLayoutError.setVisibility(View.GONE);
                mLayoutVacio.setVisibility(View.GONE);
                break;
            case 2:
                mRecyclerView.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.VISIBLE);
                mLayoutVacio.setVisibility(View.GONE);
                break;
        }
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
                    loadInfo(jsonObject);
                    updateView(0);
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
            if (jsonObject.has("mensaje") && jsonObject.get("mensaje") instanceof JSONArray) {
                JSONArray mensaje = jsonObject.getJSONArray("mensaje");
                for (int i = 0; i < mensaje.length(); i++) {
                    try {
                        JSONObject object = mensaje.getJSONObject(i);

                        Archivo archivo = Archivo.toMapper(object);

                        mList.add(archivo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        updateView(2);
                    }
                }
            }
            if (jsonObject.has("mensaje2") && jsonObject.get("mensaje2") instanceof JSONArray) {
                JSONArray categorias = jsonObject.getJSONArray("mensaje2");
                for (int i = 0; i < categorias.length(); i++) {
                    try {
                        JSONObject object = categorias.getJSONObject(i);

                        int id = Integer.parseInt(object.getString("idArea"));
                        String nombre = object.getString("nombre");

                        Area area = new Area(id, nombre);

                        mAreas.add(area);


                        nombre = nombre.replace("Área", "").trim();

                        Categoria categoria = new Categoria(id, nombre);

                        mCategorias.add(categoria);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        updateView(2);
                    }
                }
                mAreas.add(0, new Area(9, "General"));
                mCategorias.add(0, new Categoria(-1, "General", true));
                updateView(1);
                mAdapter = new ArchivosAdapter(mList, getApplicationContext());
                mCategoriasAdapter = new CategoriasAdapter(mCategorias, getApplicationContext());
                if (mList.size() != 0) {
                    mRecyclerViewArchivos.setAdapter(mCategoriasAdapter);
                } else mRecyclerViewArchivos.setVisibility(View.GONE);
                mRecyclerView.setAdapter(mAdapter);
                mCategoriasAdapter.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();

            } else {
                updateView(-1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        fab.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        ItemClickSupport itemClickSupportCategorias = ItemClickSupport.addTo(mRecyclerViewArchivos);
        itemClickSupportCategorias.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                resetear();
                mCategorias.get(position).setEstado(true);
                if (id == -1) {
                    mAdapter.filtrar(-1);
                } else {
                    mAdapter.filtrar((int) id);
                }
                mCategoriasAdapter.notifyDataSetChanged();
            }
        });
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                onClick(position, id);
            }
        });

    }

    private void resetear() {
        for (Categoria c : mCategorias) {
            c.setEstado(false);
        }
    }

    private void onClick(int position, long id) {
        if (position < mList.size() || id != 0) {
            Archivo archivo = getArchivos((int) id);
            assert archivo != null;
            openArchivo(archivo);
        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoExiste));
        }
    }

    private Archivo getArchivos(int id) {
        for (Archivo a : mList) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    private void openArchivo(Archivo archivo) {
        Intent intent = new Intent(getApplicationContext(), NuevoArchivoActivity.class);
        intent.putExtra(Utils.ARCHIVO_NAME, archivo);
        intent.putExtra(Utils.LIST_REGULARIDAD, mAreas);
        startActivity(intent);

    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        mRecyclerView = findViewById(R.id.recycler);
        mLayoutVacio = findViewById(R.id.layoutVacio);
        mLayoutError = findViewById(R.id.layoutError);
        mRecyclerViewArchivos = findViewById(R.id.recyclerCategorias);
        fab = findViewById(R.id.fab);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                createFile();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnError:
                loadInfo();
                break;
        }
    }

    private void createFile() {
        Intent intent = new Intent(getApplicationContext(), NuevoArchivoActivity.class);
        intent.putExtra(Utils.IS_ADMIN_MODE, true);
        intent.putExtra(Utils.LIST_REGULARIDAD, mAreas);
        startActivity(intent);
    }
}
