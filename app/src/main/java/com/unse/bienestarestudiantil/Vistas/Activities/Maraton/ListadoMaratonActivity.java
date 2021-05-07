package com.unse.bienestarestudiantil.Vistas.Activities.Maraton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Maraton;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Regularidad;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.BecadoActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionUsuarios.EstadisticasUsuarioActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.RegisterActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.InfoUsuarioActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.MaratonAdapter;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListadoMaratonActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Maraton> mList;
    MaratonAdapter mAdapter;
    ImageView imgIcono;
    TextView txtInsc;
    DialogoProcesamiento dialog;
    CardView mCardView;
    EditText edtBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_maraton);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(getString(R.string.tituloListaUsuarios));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new MaratonAdapter(mList, getApplicationContext(), null);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        loadInfo();

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s", Utils.URL_USUARIOS_LISTA, id, key);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response, 1);


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

    private void procesarRespuesta(String response, int tipo) {
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
                    loadInfo(jsonObject, tipo);
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

    private void loadInfo(JSONObject jsonObject, int tipo) {
        try {
            if (tipo == 1) {
                if (jsonObject.has("mensaje")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);

                        //Maraton mar = Maraton.mapper(o, Maraton.COMPLETE);

                        //mList.add(mar);

                    }
                    txtInsc.setText(String.format("%s", mList.size()));
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                }
            } else if (tipo == 2) {
                if (jsonObject.has("mensaje")) {


                    ArrayList<Datos> provincia = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("mensaje");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);

                        Datos dato = Datos.mapper(o, Datos.PROVINCIA);
                        provincia.add(dato);
                    }

                    ArrayList<Datos> tipoUsuarios = new ArrayList<>();
                    jsonArray = jsonObject.getJSONArray("datos");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);

                        Datos dato = Datos.mapper(o, Datos.PROVINCIA);
                        tipoUsuarios.add(dato);
                    }

                    ArrayList<Datos> facultad = new ArrayList<>();
                    jsonArray = jsonObject.getJSONArray("datos2");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);

                        Datos dato = Datos.mapper(o, Datos.FACULTAD);
                        facultad.add(dato);
                    }

                    ArrayList<Datos> sexo = new ArrayList<>();
                    jsonArray = jsonObject.getJSONArray("datos3");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);

                        Datos dato = Datos.mapper(o, Datos.PROVINCIA);
                        sexo.add(dato);
                    }

                    Intent intent = new Intent(getApplicationContext(), EstadisticasUsuarioActivity.class);
                    intent.putExtra(Utils.DATA_PROVINCIA, provincia);
                    intent.putExtra(Utils.DATA_FACULTAD, facultad);
                    intent.putExtra(Utils.DATA_SEXO, sexo);
                    intent.putExtra(Utils.DATA_TIPO, tipoUsuarios);
                    startActivity(intent);

                }
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
                onClick(position, id);
            }
        });
        edtBuscar.addTextChangedListener(new TextWatcher() {
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

    private void onClick(int position, long id) {
        if (position < mList.size() || id != 0) {
            Maraton mar = mList.get(position);
            //openUser(mar.getIdUsuario());
        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoExiste));
        }
    }

    private void openUser(int idUsuario) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idU=%s", Utils.URL_USUARIO_BY_ID, id, key, idUsuario);
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
                    //Exito
                    loadInfoUsuario(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.noData));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
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
        try {
            if (jsonObject.has("mensaje")) {
                Usuario usuario = Usuario.mapper(jsonObject, Usuario.COMPLETE);
                Usuario tipo = null;
                switch (usuario.getTipoUsuario()) {
                    case Utils.TIPO_ALUMNO:
                        tipo = Alumno.mapper(jsonObject, usuario);
                        break;
                    case Utils.TIPO_PROFESOR:
                        tipo = Profesor.mapper(jsonObject, usuario);
                        break;
                    case Utils.TIPO_EGRESADO:
                        tipo = Egresado.mapper(jsonObject, usuario);
                        break;
                    case Utils.TIPO_NODOCENTE:
                    case Utils.TIPO_PARTICULAR:
                        tipo = usuario;
                        break;
                }
                Intent intent = new Intent(getApplicationContext(), InfoUsuarioActivity.class);
                intent.putExtra(Utils.USER_INFO, tipo);
                intent.putExtra(Utils.IS_ADMIN_MODE, true);
                if (jsonObject.has("reg")) {

                    ArrayList<Regularidad> regularidads = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("reg");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        int idRegularidad, anio, validez;
                        String fecha;
                        fecha = object.getString("fechaInicio");
                        idRegularidad = Integer.parseInt(object.getString("idRegularidad"));
                        anio = Integer.parseInt(object.getString("anio"));
                        validez = Integer.parseInt(object.getString("validez"));

                        Regularidad regularidad = new Regularidad(idRegularidad, anio, fecha, validez);

                        regularidads.add(regularidad);

                    }
                    intent.putExtra(Utils.LIST_REGULARIDAD, regularidads);

                }
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadViews() {
        txtInsc = findViewById(R.id.txtInsc);
        imgIcono = findViewById(R.id.imgFlecha);
        mRecyclerView = findViewById(R.id.recycler);
        mCardView = findViewById(R.id.cardSearch);
        edtBuscar = findViewById(R.id.edtBuscar);
    }

    @Override
    public void onBackPressed() {
        if (edtBuscar.getText().length() > 0) {
            edtBuscar.setText("");
        } else super.onBackPressed();
    }

    private void buscar(String txt) {
        Pattern pattern = Pattern.compile(Utils.PATRON_LEGAJO);
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mAdapter.filtrar(txt, Utils.LIST_LEGAJO);
            return;
        }
        pattern = Pattern.compile(Utils.PATRON_DNI);
        matcher = pattern.matcher(txt);
        if (matcher.find()) {
            if (txt.length() > 4) {
                mAdapter.filtrar(txt, Utils.LIST_DNI);
                return;
            } else {
                mAdapter.filtrar(txt, Utils.LIST_LEGAJO);
                return;
            }
        }
        pattern = Pattern.compile(Utils.PATRON_NOMBRES);
        matcher = pattern.matcher(txt);
        if (matcher.find()) {
            mAdapter.filtrar(txt, Utils.LIST_NOMBRE);
            return;
        } else {
            mAdapter.filtrar(txt, Utils.LIST_RESET);
            return;
        }
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