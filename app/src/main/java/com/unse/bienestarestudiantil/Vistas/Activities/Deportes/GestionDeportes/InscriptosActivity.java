package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapters;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InscriptosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    UsuariosAdapters mAdapter;
    ArrayList<Deporte> mDeporte;
    ArrayList<Usuario> mUsuarios;
    ImageView imgIcono, imgBuscador;
    TextInputLayout tilBuscador;
    EditText edtBuscar;
    FloatingActionButton fab;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscriptos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null) {
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }

        setToolbar();

        loadViews();

        loadListener();

        loadDataRecycler();

        getAll();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Inscriptos");

    }

    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Utils.showToast(getApplicationContext(), "Item: " + mUsuarios.get(position).getNombre());
            }
        });
        imgIcono.setOnClickListener(this);

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilBuscador.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {
                buscar(s.toString());
            }
        });

        imgIcono.setOnClickListener(this);

    }

    private void buscar(String txt) {
        Pattern pattern = Pattern.compile("[0-9]+(\\/|-)[0-9]*");
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()){
            mAdapter.filtrar(txt, Utils.LIST_LEGAJO);
            return;
        }
        pattern = Pattern.compile("([0-9]+){5,8}");
        matcher = pattern.matcher(txt);
        if (matcher.find()){
            if (txt.length() > 4){
                mAdapter.filtrar(txt, Utils.LIST_DNI);
                return;
            }else{
                mAdapter.filtrar(txt, Utils.LIST_LEGAJO);
                return;
            }
        }
        pattern = Pattern.compile("[a-zA-Z_ ]+");
        matcher = pattern.matcher(txt);
        if (matcher.find()){
            mAdapter.filtrar(txt, Utils.LIST_NOMBRE);
            return;
        } else {
            mAdapter.filtrar(txt, Utils.LIST_RESET);
            return;
        }
    }

    private void loadDataRecycler() {
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mUsuarios = new ArrayList<>();

        mAdapter = new UsuariosAdapters(mUsuarios, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Utils.changeColorDrawable(imgBuscador, getApplicationContext(), R.color.colorPrimary);

    }

    private void loadViews() {
        mRecyclerView = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        fab = findViewById(R.id.fab);
        imgBuscador = findViewById(R.id.imgBuscar);
        tilBuscador = findViewById(R.id.til_buscar);
        edtBuscar = findViewById(R.id.edtBuscar);
    }

    private void getAll() {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        String URL = String.format("%s?key=%s", Utils.URL_ISCRIP_LISTA, key);

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
                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");
                    load(jsonArray);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "La contraseña actual ingresada es inválida");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

    private void load(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(j.getInt("idUsuario"));
            usuario.setNombre(j.getString("nombre"));
            usuario.setApellido(j.getString("apellido"));
            usuario.setFechaNac(j.getString("fechaNac"));
            usuario.setNombre(j.getString("nombre"));

            mUsuarios.add(usuario);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.fab:

                break;
        }
    }

}
