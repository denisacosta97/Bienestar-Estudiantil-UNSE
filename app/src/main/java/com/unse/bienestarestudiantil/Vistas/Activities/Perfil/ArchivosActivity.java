package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUriExposedException;
import android.os.StrictMode;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import java.util.Date;

import static android.view.View.GONE;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ArchivosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager, mLayoutManagerCategoria;
    RecyclerView mRecyclerAsistencia, mRecyclerViewCategorias;
    ArrayList<Archivo> mArchivos;
    ArrayList<Categoria> mCategorias;
    CategoriasAdapter mCategoriasAdapter;
    ArchivosAdapter mArchivosAdapter;
    LinearLayout latError, latVacio;
    ImageView imgFlecha;
    DialogoProcesamiento dialog;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

        updateView(-1);

        loadInfo(R.drawable.ic_error, "Error al obtener los archivos, por favor intenta nuevamente",
                R.drawable.ic_vacio, "No existe ningún archivo disponible para descargar");

        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgFlecha, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mArchivos = new ArrayList<>();
        mCategorias = new ArrayList<>();

        loadArchivos();

        mArchivosAdapter = new ArchivosAdapter(mArchivos, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerAsistencia.setNestedScrollingEnabled(true);
        mRecyclerAsistencia.setLayoutManager(mLayoutManager);
        mRecyclerAsistencia.setHasFixedSize(true);
        mRecyclerAsistencia.setAdapter(mArchivosAdapter);

        mCategoriasAdapter = new CategoriasAdapter(mCategorias, getApplicationContext());
        mLayoutManagerCategoria = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCategorias.setNestedScrollingEnabled(true);
        mRecyclerViewCategorias.setLayoutManager(mLayoutManagerCategoria);
        mRecyclerViewCategorias.setHasFixedSize(true);
        mRecyclerViewCategorias.setAdapter(mCategoriasAdapter);

        ItemClickSupport itemClickSupportCat = ItemClickSupport.addTo(mRecyclerViewCategorias);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerAsistencia);

        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                procesarClick(position, id, getApplicationContext());
            }
        });

        itemClickSupportCat.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                resetear();
                mCategorias.get(position).setEstado(true);
                if (id == -1) {
                    mArchivosAdapter.filtrar(-1);
                } else {
                    mArchivosAdapter.filtrar((int) id);
                }
                mCategoriasAdapter.notifyDataSetChanged();
            }
        });

    }

    public Archivo getArchivo(int id) {
        for (Archivo archivo : mArchivos) {
            if (archivo.getId() == id)
                return archivo;
        }
        return null;
    }

    private void procesarClick(int position, final long id, Context context) {
        final Archivo archivo = getArchivo((int) id);
        if (archivo != null) {
            Object[] isExist = Utils.exist(archivo, getApplicationContext());
            if (!(boolean) isExist[0]) {
                download(archivo);
            } else {
                String dia;
                if ((Long) isExist[1] != 0) {
                    dia = Utils.getFechaNameWithinHour(new Date((Long) isExist[1]));
                } else
                    dia = "";
                DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                        .setTitulo(String.format("El documento ya se encuentra disponible en tu almacenamiento" +
                                " con fecha %s. ¿Desea volver a descargarlo o abrir el documento disponible?", dia))
                        .setListener(new YesNoDialogListener() {
                            @Override
                            public void yes() {
                                download(archivo);
                            }

                            @Override
                            public void no() {
                                openFile(archivo);
                            }
                        })
                        .setIcono(R.drawable.ic_find)
                        .setTipo(DialogoGeneral.TIPO_LIBRE)
                        .setTextButtonSi("DESCARGAR")
                        .setTextButtonNo("ABRIR");
                DialogoGeneral dialogoMensaje = builder.build();
                dialogoMensaje.show(getSupportFragmentManager(), "dialog_yes");

            }
        }

    }

    public void completeFile(final Archivo archivo) {
        LoadInfoPDF loadInfoPDF = new LoadInfoPDF(getApplicationContext(), archivo, new YesNoDialogListener() {
            @Override
            public void yes() {
                openFile(archivo);
            }

            @Override
            public void no() {
                Utils.showToast(getApplicationContext(), getString(R.string.documentoNoCompletado));
                DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                        .setDescripcion("No fue posible completar el documento, pero puedes " +
                                "visualizarlo desde tu gestor de archivos en la carpeta BIENESTAR")
                        .setIcono(R.drawable.ic_advertencia)
                        .setListener(new YesNoDialogListener() {
                            @Override
                            public void yes() {

                            }

                            @Override
                            public void no() {

                            }
                        })
                        .setTipo(DialogoGeneral.TIPO_ACEPTAR);
                DialogoGeneral dialogoGeneral = builder.build();
                dialogoGeneral.show(getSupportFragmentManager(), "dialog_pdf");
            }
        }, getSupportFragmentManager());
        loadInfoPDF.execute();
    }

    private void openFile(Archivo archivo) {

        File file = new File(Utils.getDirectoryPath(true, getApplicationContext()) + archivo.getNombreArchivo());

        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            Uri apkURI = FileProvider.getUriForFile(
                    getApplicationContext(),
                    getApplicationContext()
                            .getPackageName() + ".provider", file);
            intent.setDataAndType(apkURI, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(intent);
            } catch (FileUriExposedException e) {
                Utils.showToast(getApplicationContext(), "Error interno al abrir el archivo");
            } catch (ActivityNotFoundException e) {
                Utils.showToast(getApplicationContext(), "No hay aplicaciones disponibles para abrir PDF");
                DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                        .setDescripcion("No fue posible completar el documento, pero puedes " +
                                "visualizarlo desde tu gestor de archivos en la carpeta BIENESTAR")
                        .setIcono(R.drawable.ic_advertencia)
                        .setListener(new YesNoDialogListener() {
                            @Override
                            public void yes() {

                            }

                            @Override
                            public void no() {

                            }
                        })
                        .setTipo(DialogoGeneral.TIPO_ACEPTAR);
                DialogoGeneral dialogoGeneral = builder.build();
                dialogoGeneral.show(getSupportFragmentManager(), "dialog_pdf");

            }
        } else
            Utils.showToast(getApplicationContext(), "El archivo fue eliminado durante el proceso, reintente");
    }

    private void download(final Archivo archivo) {
        DownloadPDF downloadPDF = new DownloadPDF(getApplicationContext(), archivo.getNombreArchivo(),
                getSupportFragmentManager(), new YesNoDialogListener() {
            @Override
            public void yes() {
                Utils.showToast(getApplicationContext(), "Descargado!");
                completeFile(archivo);
            }

            @Override
            public void no() {
                Utils.showToast(getApplicationContext(), "Error!");
            }
        }, true);
        String URL = String.format("%s%s", Utils.URL_ARCHIVOS, archivo.getNombreArchivo());
        downloadPDF.execute(URL);
    }

    private void resetear() {
        for (Categoria c : mCategorias) {
            c.setEstado(false);
        }
    }

    private void loadArchivos() {
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
                Utils.showToast(getApplicationContext(), "Error de conexión o servidor fuera de rango");
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
                case 1:
                    //Exito
                    loadInfo(jsonObject.getJSONArray("mensaje"), jsonObject.getJSONArray("mensaje2"));
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "No existen datos");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case -1:
                    Utils.showToast(getApplicationContext(), "Error interno, contacta al Administrador");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
            updateView(2);
        }
    }

    private void loadInfo(JSONArray mensaje, JSONArray mensaje2) {
        if (mensaje.length() > 0) {
            for (int i = 0; i < mensaje.length(); i++) {
                try {
                    JSONObject object = mensaje.getJSONObject(i);

                    Archivo archivo = Archivo.toMapper(object);

                    if (archivo.getValidez() == 1)
                        mArchivos.add(archivo);

                } catch (JSONException e) {
                    e.printStackTrace();
                    updateView(2);
                }
            }
            for (int i = 0; i < mensaje2.length(); i++) {
                try {
                    JSONObject object = mensaje2.getJSONObject(i);

                    int id = Integer.parseInt(object.getString("idArea"));
                    String nombre = object.getString("nombre");

                    nombre = nombre.replace("Área", "").trim();

                    Categoria categoria = new Categoria(id, nombre);

                    mCategorias.add(categoria);


                } catch (JSONException e) {
                    e.printStackTrace();
                    updateView(2);
                }
            }
            mCategorias.add(0, new Categoria(-1, "General", true));
            updateView(1);
            mArchivosAdapter.setArchivosCopia(mArchivos);
            mCategoriasAdapter.notifyDataSetChanged();
            mArchivosAdapter.notifyDataSetChanged();

        } else {
            updateView(-1);
        }
    }


    private void loadListener() {
        imgFlecha.setOnClickListener(this);
    }

    private void loadViews() {
        mRecyclerAsistencia = findViewById(R.id.recycler);
        mRecyclerViewCategorias = findViewById(R.id.recyclerCategorias);
        imgFlecha = findViewById(R.id.imgFlecha);
        latError = findViewById(R.id.layoutError);
        latVacio = findViewById(R.id.layoutVacio);
    }

    private void loadInfo(int iconError, String text, int iconVacio, String vacio) {
        ((ImageView) findViewById(R.id.imgIconError)).setBackground(getResources().getDrawable(iconError));
        ((ImageView) findViewById(R.id.imgIconVacio)).setBackground(getResources().getDrawable(iconVacio));
        ((TextView) findViewById(R.id.txtError)).setText(text);
        ((TextView) findViewById(R.id.txtVacio)).setText(vacio);
        ((Button) findViewById(R.id.btnError)).setOnClickListener(this);

    }

    private void updateView(int b) {
        if (b == 1) {
            mRecyclerAsistencia.setVisibility(View.VISIBLE);
            latError.setVisibility(GONE);
            latVacio.setVisibility(GONE);
        } else if (b == 2) {
            mRecyclerAsistencia.setVisibility(View.GONE);
            latError.setVisibility(View.VISIBLE);
            latVacio.setVisibility(GONE);
        } else {
            mRecyclerAsistencia.setVisibility(View.GONE);
            latError.setVisibility(GONE);
            latVacio.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnError:
                loadArchivos();
                break;
        }

    }
}
