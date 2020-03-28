package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.Databases.AlumnosRepo;
import com.unse.bienestarestudiantil.Databases.EgresadosRepo;
import com.unse.bienestarestudiantil.Databases.ProfesorRepo;
import com.unse.bienestarestudiantil.Databases.UsuariosRepo;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.RecuperarContraseniaActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mInicio;
    ImageView btnBack;
    LinearLayout layoutFondo;
    DialogoProcesamiento dialog;
    EditText edtUser, edtPass;
    VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layoutFondo = findViewById(R.id.backgroundlogin);

        Glide.with(this).load(R.drawable.img_unse2)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        layoutFondo.setBackground(resource);
                    }
                });

        loadViews();

        loadListener();

        loadData();


    }

    private void loadData() {
        // Uri uri = Uri.parse("android.resource://".concat(getPackageName()).concat("/raw/").concat(String.valueOf(R.raw.video_bacl)));
        //mVideoView.setVideoURI(uri);
        //mVideoView.start();

        /*mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null)
            mVideoView.start();
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        mInicio.setOnClickListener(this);
    }

    private void loadViews() {
        mInicio = findViewById(R.id.sesionOn);
        btnBack = findViewById(R.id.btnBack);
        mVideoView = findViewById(R.id.videoView);
        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUser);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sesionOn:
                login();
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.txtPassMissed:
                startActivity(new Intent(LoginActivity.this, RecuperarContraseniaActivity.class));
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

    private void login() {
        String dni = edtUser.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (!dni.equals("") && !pass.equals("")) {
            pass = Utils.crypt(pass);
            String url = String.format("%s?id=%s&pass=%s", Utils.URL_USUARIO_LOGIN, dni, pass);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
            dialog = new DialogoProcesamiento();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "dialog_process");
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

        } else {
            Utils.showToast(getApplicationContext(), "Por favor complete los campos");
        }

    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    Utils.showToast(getApplicationContext(), "¡Sesión iniciada!");
                    JSONObject datos = jsonObject.getJSONObject("datos");
                    JSONObject tipo = jsonObject.getJSONObject("tipo");
                    String token = jsonObject.getJSONObject("token").getString("token");
                    //Insertar BD
                    guardarDatos(datos, tipo, token);
                    PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
                    preferenceManager.setValue(Utils.IS_LOGIN, true);
                    int dni = Integer.parseInt(datos.getString("idUsuario"));
                    preferenceManager.setValue(Utils.MY_ID, dni);
                    preferenceManager.setValue(Utils.TOKEN, token);
                    //Main
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finishAffinity();
                    break;
                case 2:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), "Usuario o contraseña inválidos");
                    break;
                case 4:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), "Usuario deshabilitado, contacta al Administrador");
                    break;
                case 3:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }


    private void guardarDatos(JSONObject datos, JSONObject tipo, String token) {
        try {
            String idUsuario = datos.getString("idUsuario");
            String tipoUsuario = datos.getString("tipoUsuario");
            String nombre = datos.getString("nombre");
            String apellido = datos.getString("apellido");
            String pais = datos.getString("pais");
            String provincia = datos.getString("provincia");
            String localidad = datos.getString("localidad");
            String domicilio = datos.getString("domicilio");
            String barrio = datos.getString("barrio");
            String telefono = datos.getString("telefono");
            String sexo = datos.getString("sexo");
            String mail = datos.getString("mail");
            String checkData = datos.getString("checkData");
            Date fechaReg = Utils.getFechaDateWithHour(datos.getString("fechaRegistro"));
            String foto = "www.jje.com/" + idUsuario;
            Date fechaNac = Utils.getFechaDate(datos.getString("fechaNac"));

            Usuario usuario = new Usuario(Integer.parseInt(idUsuario), Integer.parseInt(tipoUsuario), nombre, apellido, pais,
                    provincia, localidad, domicilio, barrio, telefono, sexo, mail, checkData, foto, fechaNac, fechaReg);
            UsuariosRepo usuariosRepo = new UsuariosRepo(getApplicationContext());
            usuariosRepo.insert(usuario);
            switch (Integer.parseInt(tipoUsuario)) {
                case 1:
                    String carrera = tipo.getString("carrera");
                    String facultad = tipo.getString("facultad");
                    String anio = tipo.getString("anio");
                    String legajo = tipo.getString("legajo");
                    int idRegularidad = Integer.parseInt(
                            tipo.getString("idRegularidad"));
                    String checkDataAlu = tipo.getString("checkData");
                    Alumno alumno = new Alumno(usuario, carrera, facultad, legajo, anio, Integer.parseInt(idUsuario),
                            checkDataAlu, idRegularidad);
                    AlumnosRepo alumnosRepo = new AlumnosRepo(getApplicationContext());
                    alumnosRepo.insert(alumno);
                    break;
                case 2:
                    String profesion = tipo.getString("profesion");
                    String fechaIngreso = tipo.getString("fechaIngreso");
                    String checkDataProf = tipo.getString("checkData");
                    Profesor profesor = new Profesor(usuario, profesion, checkDataProf, Integer.parseInt(idUsuario), fechaIngreso);
                    ProfesorRepo profesorRepo = new ProfesorRepo(getApplicationContext());
                    profesorRepo.insert(profesor);
                    break;
                case 4:
                    String profesionEgre = tipo.getString("profesion");
                    String fechaEgreso = tipo.getString("fechaEgreso");
                    String checkDataEg = tipo.getString("checkData");
                    Egresado egresado = new Egresado(usuario, profesionEgre, checkDataEg, Integer.parseInt(idUsuario), fechaEgreso);
                    EgresadosRepo egresadosRepo = new EgresadosRepo(getApplicationContext());
                    egresadosRepo.insert(egresado);
                    break;
                case 3:
                case 5:
                    //Nadin jeje
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Utils.showToast(getApplicationContext(), "Error de formato");
        }
    }

}
