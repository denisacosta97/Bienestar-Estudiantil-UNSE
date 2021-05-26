package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.GeneratePDFTask;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.PerfilActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoOpciones;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BecadosComedorActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fabAdd, fabPDF;
    ArrayList<Usuario> mUsuarios;
    RecyclerView.LayoutManager mLayoutManager;
    DialogoProcesamiento dialog;
    LinearLayout latVacio;
    RecyclerView recyclerUsuarios;
    ImageView imgIcono;
    UsuariosAdapter mUsuariosAdapter;
    ProgressBar mProgressBar;
    EditText edtBuscar;
    RolViewModel mRolViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        loadData();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Becados");
    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        fabPDF.setOnClickListener(this);
        fabAdd.setOnClickListener(this);
        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                buscar(s.toString());
            }
        });

    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        fabPDF = findViewById(R.id.fabPDF);
        latVacio = findViewById(R.id.latVacio);
        edtBuscar = findViewById(R.id.edtBuscar);
        recyclerUsuarios = findViewById(R.id.recycler);
        fabAdd = findViewById(R.id.fabAdd);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void buscar(String txt) {
        Pattern pattern;
        pattern = Pattern.compile("([0-9]+){1,8}");
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_DNI);
            return;
        } else {
            pattern = Pattern.compile("[a-zA-Z_ ]+");
            matcher = pattern.matcher(txt);
            if (matcher.find()) {
                mUsuariosAdapter.filtrar(txt, Utils.LIST_NOMBRE);
                return;
            } else {
                mUsuariosAdapter.filtrar(txt, Utils.LIST_RESET);
                return;
            }
        }

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s", Utils.URL_USUARIOS_LISTA_COMEDOR, id, key);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                latVacio.setVisibility(VISIBLE);
                mProgressBar.setVisibility(GONE);
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
            mProgressBar.setVisibility(GONE);
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    latVacio.setVisibility(VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    latVacio.setVisibility(VISIBLE);
                    break;
                case 3:
                    latVacio.setVisibility(VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    latVacio.setVisibility(VISIBLE);
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            latVacio.setVisibility(VISIBLE);
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje")) {

                JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject o = jsonArray.getJSONObject(i);

                    Usuario usuario = Usuario.mapper(o, Usuario.BASIC);

                    mUsuarios.add(usuario);

                }
                mUsuariosAdapter.setList(mUsuarios);
                mUsuariosAdapter.notifyDataSetChanged();
            } else {
                latVacio.setVisibility(VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadData() {
        mRolViewModel = new RolViewModel(getApplicationContext());
        latVacio.setVisibility(GONE);
        mUsuarios = new ArrayList<>();
        mProgressBar.setVisibility(VISIBLE);
        mUsuariosAdapter = new UsuariosAdapter(mUsuarios, getApplicationContext(), Utils.TIPO_USUARIO);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerUsuarios.setNestedScrollingEnabled(true);
        recyclerUsuarios.setLayoutManager(mLayoutManager);
        recyclerUsuarios.setAdapter(mUsuariosAdapter);

        loadInfo();

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerUsuarios);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                processClick(search(position, (int) id));

            }
        });


    }

    public Usuario search(int position, int id) {
        for (Usuario usuario : mUsuarios) {
            if (usuario.getIdUsuario() == id)
                return usuario;
        }
        return null;
    }

    private void processClick(Usuario usuario) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&id=%s&idU=%s", Utils.URL_USUARIO_BY_ID_COMEDOR, key, idLocal,
                usuario.getIdUsuario());
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaUsuario(response);


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

    private void procesarRespuestaUsuario(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    loadInfoUsuario(jsonObject);
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

    private void loadInfoUsuario(JSONObject jsonObject) {
        Usuario usuario = Usuario.mapper(jsonObject, Usuario.COMPLETE);
        Alumno alumno = Alumno.mapper(jsonObject, usuario);
        Intent i = new Intent(getApplicationContext(), InfoBecadoComedorActivity.class);
        i.putExtra(Utils.IS_ADMIN_MODE, true);
        i.putExtra(Utils.USER_INFO, alumno.getCarrera() != null ? alumno : usuario);
        startActivity(i);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fabAdd:
                Intent i = new Intent(getApplicationContext(), NuevoBecadoComedorActivity.class);
                startActivity(i);
                break;
            case R.id.fabPDF:
                if (mUsuarios != null && mUsuarios.size() > 0) {
                    if (Utils.isPermissionGranted(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (Utils.isPermissionGranted(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            ArrayList<Opciones> opciones = new ArrayList<>();
                            opciones.add(new Opciones("SOLO ACTIVOS"));
                            opciones.add(new Opciones("INCLUIR INACTIVOS"));
                            DialogoOpciones dialogoOpciones = new DialogoOpciones(new OnClickOptionListener() {
                                @Override
                                public void onClick(int pos) {
                                    procesarClick(pos);

                                }
                            }, opciones, getApplicationContext());
                            dialogoOpciones.show(getSupportFragmentManager(), "opciones");
                        } else {
                            showPermission();
                        }

                    } else {
                        showPermission();
                    }
                } else {
                    Utils.showToast(getApplication(), getString(R.string.noData));
                }

                break;

        }
    }

    private void showPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private void procesarClick(int pos) {
        DialogoProcesamiento dialogoProcesamiento = new DialogoProcesamiento();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        if (pos == 0) {
            for (Usuario usuario : mUsuarios) {
                if (usuario.getValidez() == 1)
                    usuarios.add(usuario);
            }
            new GeneratePDFTask(3, usuarios, dialogoProcesamiento, getApplicationContext()).execute();
        } else {
            new GeneratePDFTask(3, mUsuarios, dialogoProcesamiento, getApplicationContext()).execute();
        }
        dialogoProcesamiento.show(getSupportFragmentManager(), "jeje");

    }
}

