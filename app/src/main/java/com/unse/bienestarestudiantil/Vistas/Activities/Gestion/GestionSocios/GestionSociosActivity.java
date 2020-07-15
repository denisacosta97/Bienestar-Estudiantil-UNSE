package com.unse.bienestarestudiantil.Vistas.Activities.Gestion.GestionSocios;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.CredencialSocio;
import com.unse.bienestarestudiantil.Modelos.Familiar;
import com.unse.bienestarestudiantil.Modelos.Socio;
import com.unse.bienestarestudiantil.Modelos.SuscripcionSocio;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.UsuariosAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoBuscaUsuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GestionSociosActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    UsuariosAdapter mAdapter;
    ArrayList<Usuario> mList;
    //ArrayList<Rol> mRoles;
    ImageView imgIcono;
    Usuario mUsuario;
    DialogoProcesamiento dialog;
    CardView mCardView;
    EditText mEditText;
    LinearLayout mLayoutError, mLayoutVacio;
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_socios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

        updateView(0);

        loadInfo(R.drawable.ic_error, getString(R.string.sociosError),
                R.drawable.ic_vacio, getString(R.string.sociosVacio));
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
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de Socios");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        mList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new UsuariosAdapter(mList, getApplicationContext(), Utils.TIPO_SOCIO);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        loadInfo();

    }

    private void loadInfo() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s", Utils.URL_SOCIO_LISTA, id, key);
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
                mCardView.setVisibility(View.GONE);
                mLayoutError.setVisibility(View.GONE);
                mLayoutVacio.setVisibility(View.VISIBLE);
                break;
            case 1:
                mRecyclerView.setVisibility(View.VISIBLE);
                mCardView.setVisibility(View.VISIBLE);
                mLayoutError.setVisibility(View.GONE);
                mLayoutVacio.setVisibility(View.GONE);
                break;
            case 2:
                mRecyclerView.setVisibility(View.GONE);
                mCardView.setVisibility(View.GONE);
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
            if (jsonObject.has("mensaje")){

                JSONArray usuarios = jsonObject.getJSONArray("mensaje");

                for (int i = 0; i < usuarios.length(); i++) {

                    JSONObject object = usuarios.getJSONObject(i);

                    Usuario usuario = Usuario.mapper(object, Usuario.BASIC);
                    int validez = Integer.parseInt(object.getString("validez"));
                    String fechaRegistro = object.getString("fechaRegistro");
                    Socio socio = new Socio(usuario, fechaRegistro, validez);
                    mList.add(socio);
                }
                mAdapter.setList(mList);
                updateView(1);


            }
        } catch (JSONException e) {
            e.printStackTrace();
            updateView(2);
        }
        mAdapter.notifyDataSetChanged();

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        fab.setOnClickListener(this);
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                onClick(position, id);
            }
        });
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

    private void onClick(int position, long id) {
        if (position < mList.size() || id != 0) {
            mUsuario = mList.get(position);
            openUser(mUsuario.getIdUsuario());
            Utils.showToast(getApplicationContext(), String.format("%s %s", mUsuario.getNombre(), mUsuario.getApellido()));//openUser(mUsuario.getIdUsuario());
        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoExiste));
        }
    }

    private void openUser(int idUsuario) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?id=%s&key=%s&idU=%s", Utils.URL_SOCIO_PARTIULAR, id, key, idUsuario);
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuestaSocio(response);


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

    private void procesarRespuestaSocio(String response) {
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
                    loadInfoSocio(jsonObject);
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

    private void loadInfoSocio(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("hijos") &&
            jsonObject.has("insc") && jsonObject.has("cred")) {

                JSONObject datos = jsonObject.getJSONObject("mensaje");
                Usuario usuario = Usuario.mapper(datos, Usuario.BASIC);
                int validez = Integer.parseInt(datos.getString("validez"));
                String fechaRegistro = datos.getString("fechaRegistro");

                Socio socio = new Socio(usuario,fechaRegistro, validez);

                JSONArray hijos = jsonObject.getJSONArray("hijos");

                ArrayList<Familiar> hijosList = new ArrayList<>();

                for (int i = 0;i<hijos.length(); i++){
                    JSONObject object  = hijos.getJSONObject(i);
                    String dni = object.getString("idHijo");
                    String nombre = object.getString("nombre");
                    String apellido = object.getString("apellido");
                    String relacion = object.getString("relacion");
                    String fechaNac = object.getString("fechaNac");
                    String fechaRegistroH = object.getString("fechaRegistro");
                    int validezH = Integer.parseInt(object.getString("validez"));

                    Familiar familiar = new Familiar(Integer.parseInt(dni), nombre, apellido, relacion,
                            fechaNac, fechaRegistroH, validezH);
                    hijosList.add(familiar);
                }

                JSONArray inscripciones = jsonObject.getJSONArray("insc");
                ArrayList<SuscripcionSocio> suscripcionSocios = new ArrayList<>();

                for (int i = 0; i<inscripciones.length();i++){
                    JSONObject object = inscripciones.getJSONObject(i);

                     String idSuscripcion = object.getString("idSuscripcion");
                     String idIdentificacion = object.getString("idIdentificacion");
                     String idSocio = object.getString("idSocio");
                     int validezI = Integer.parseInt(object.getString("validez"));
                     int estado = Integer.parseInt(object.getString("estado"));
                     int anio = Integer.parseInt(object.getString("anio"));
                     String fechaRegistroI = object.getString("fechaRegistro");

                     SuscripcionSocio suscripcionSocio = new SuscripcionSocio(Integer.parseInt(idSuscripcion), Integer.parseInt(idIdentificacion),
                             Integer.parseInt(idSocio), validezI, estado, anio,
                             fechaRegistroI);
                     suscripcionSocios.add(suscripcionSocio);

                }

                JSONArray credenciales = jsonObject.getJSONArray("cred");

                ArrayList<CredencialSocio> credencialSocios = new ArrayList<>();

                for (int i = 0; i<credenciales.length(); i++){
                    JSONObject object = credenciales.getJSONObject(i);

                    String idCredencialSocio = object.getString("idCredencialSocio");
                    String idSocio = object.getString("idSocio");
                    String idSuscripcionSocio = object.getString("idSuscripcionSocio");
                    String fechaCreacion = object.getString("fechaCreacion");
                    String descripcion = object.getString("descripcion");
                    int validezC = Integer.parseInt(object.getString("validez"));
                    int anioC = Integer.parseInt(object.getString("anio"));

                    String titulo = String.format("CREDENCIAL #%s/%s", idCredencialSocio, String.valueOf(anioC).substring(2));

                    CredencialSocio credencialSocio = new CredencialSocio(Integer.parseInt(idCredencialSocio), validezC, anioC,
                            titulo, fechaCreacion, Integer.parseInt(idSocio),0,descripcion, Integer.parseInt(idSuscripcionSocio),
                            Integer.parseInt(idCredencialSocio));

                    credencialSocios.add(credencialSocio);
                }

                Intent intent = new Intent(getApplicationContext(), EditarSocioActivity.class);
                intent.putExtra(Utils.USER_INFO, socio);
                intent.putExtra(Utils.LIST_HIJOS, hijosList);
                intent.putExtra(Utils.LIST_SUSCROP, suscripcionSocios);
                intent.putExtra(Utils.LIST_CRED, credencialSocios);
                startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        mRecyclerView = findViewById(R.id.recycler);
        mCardView = findViewById(R.id.cardSearch);
        mEditText = findViewById(R.id.edtBuscar);
        mLayoutVacio = findViewById(R.id.layoutVacio);
        mLayoutError = findViewById(R.id.layoutError);
        fab = findViewById(R.id.fab);
    }

    @Override
    public void onBackPressed() {
        if (mEditText.getText().length() > 0) {
            mEditText.setText("");
        } else super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                createUser();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnError:
                loadInfo();
                break;
        }
    }

    private void createUser() {
        DialogoBuscaUsuario dialogoBuscaUsuario = new DialogoBuscaUsuario();
        dialogoBuscaUsuario.setContext(getApplicationContext());
        dialogoBuscaUsuario.setFragmentManager(getSupportFragmentManager());
      //  dialogoBuscaUsuario.setRoles(mRoles);
        dialogoBuscaUsuario.setUsuariosLista(mList);
        //dialogoBuscaUsuario.show(getSupportFragmentManager(),"dialog_biscar");
    }

}
