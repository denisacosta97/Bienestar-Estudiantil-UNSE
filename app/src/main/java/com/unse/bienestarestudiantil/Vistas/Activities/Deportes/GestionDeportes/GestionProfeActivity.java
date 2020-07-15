package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestionProfeActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerAlumnos;
    ArrayList<Usuario> mUsuarios;
    ArrayList<Profesor> mProfesors;
    ArrayList<Deporte> mDeportes;
    UsuariosAdapter mUsuariosAdapter;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_profe);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();

        //loadInfo(R.drawable.ic_error, getString(R.string.usuarioListError),R.drawable.ic_vacio, getString(R.string.usuarioListVacio));

        loadDataRecycler();

        getAll();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de profesores");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscar(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadDataRecycler() {
        mProfesors = new ArrayList<>();
        mUsuarios = new ArrayList<>();
        mDeportes = new ArrayList<>();

        mUsuariosAdapter = new UsuariosAdapter(mUsuarios, getApplicationContext(),  Utils.TIPO_ESTUDIANTE);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mRecyclerAlumnos.setNestedScrollingEnabled(true);
        mRecyclerAlumnos.setLayoutManager(mLayoutManager);
        mRecyclerAlumnos.setAdapter(mUsuariosAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerAlumnos);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), InfoProfesorActivity.class);
                i.putExtra(Utils.USER_NAME, mUsuarios.get(position));
                i.putExtra(Utils.DEPORTE_NAME_PROF, mProfesors.get(position));
                i.putExtra(Utils.DEPORTE_NAME, mDeportes.get(position));
                startActivity(i);
            }
        });

    }

    private void loadViews() {
        mRecyclerAlumnos = findViewById(R.id.recycler);
        imgIcono = findViewById(R.id.imgFlecha);
        mEditText = findViewById(R.id.edtBuscar);
    }

    private void getAll() {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        String URL = String.format("%s?key=%s", Utils.URL_PROFES, key);

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

            Deporte deporte = new Deporte();
            deporte.setIdDep(j.getInt("idDeporte"));
            deporte.setName(j.getString("nombreD"));
            mDeportes.add(deporte);

            Usuario usuario = Usuario.mapper(j, Usuario.BASIC);
            usuario.setBarrio(j.getString("barrio"));
            usuario.setFechaNac(j.getString("fechaNac"));
            usuario.setDomicilio(j.getString("domicilio"));
            usuario.setTelefono(j.getString("telefono"));
            mUsuarios.add(usuario);

            Profesor profesor = new Profesor();
            profesor.setIdProfesor(j.getInt("idEntrenador"));
            profesor.setFechaIngreso(j.getString("fechaIngreso"));
            mProfesors.add(profesor);

        }
        mUsuariosAdapter.notifyDataSetChanged();
    }

    private void buscar(String txt) {
        Pattern pattern = Pattern.compile(Utils.PATRON_NUMEROS);
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_DNI);
            return;
        }
        pattern = Pattern.compile(Utils.PATRON_NOMBRES);
        matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_NOMBRE);
        } else {
            mUsuariosAdapter.filtrar(txt, Utils.LIST_RESET);
        }
    }

    private void loadInfo(int iconError, String text, int iconVacio, String vacio) {
        (findViewById(R.id.imgIconError)).setBackground(getResources().getDrawable(iconError));
        (findViewById(R.id.imgIconVacio)).setBackground(getResources().getDrawable(iconVacio));
        ((TextView) findViewById(R.id.txtError)).setText(text);
        ((TextView) findViewById(R.id.txtVacio)).setText(vacio);
        (findViewById(R.id.btnError)).setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        if (mEditText.getText().length() > 0) {
            mEditText.setText("");
        } else super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }

}
