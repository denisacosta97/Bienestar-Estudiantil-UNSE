package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Databases.AlumnoViewModel;
import com.unse.bienestarestudiantil.Databases.EgresadoViewModel;
import com.unse.bienestarestudiantil.Databases.ProfesorViewModel;
import com.unse.bienestarestudiantil.Databases.RolViewModel;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Egresado;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Rol;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.RecuperarContraseniaActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button mInicio;
    ImageView btnBack;
    DialogoProcesamiento dialog;
    EditText edtUser, edtPass;
    TextView txtWelcome;
    VideoView mVideoView;
    UsuarioViewModel mUsuarioViewModel;
    int dniNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        loadData();

        changeTextWelcome();

    }

    public void changeTextWelcome() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 6 && timeOfDay <= 12) {
            txtWelcome.setText("¡Buen día!");
        } else if (timeOfDay >= 13 && timeOfDay <= 19) {
            txtWelcome.setText("¡Buenas tardes!");
        } else if (timeOfDay >= 20)
            txtWelcome.setText("¡Buenas noches!");
    }

    private void loadData() {
        mUsuarioViewModel = new UsuarioViewModel(getApplicationContext());
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
        //btnBack.setOnClickListener(this);
        mInicio.setOnClickListener(this);
    }

    private void loadViews() {
        mInicio = findViewById(R.id.sesionOn);
        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUser);
        txtWelcome = findViewById(R.id.txtWelcome);
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
        }
    }

    private void login() {
        String dniN = edtUser.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (!dniN.equals("") && !pass.equals("")) {
            //pass = Utils.crypt(pass);
            try{
                dniNumber = Integer.parseInt(dniN);
            }catch (NumberFormatException e){

            }
            String url = String.format("%s?id=%s&pass=%s", Utils.URL_USUARIO_LOGIN, dniN, pass);
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    procesarRespuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
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
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    //Utils.showToast(getApplicationContext(), getString(R.string.sesionIniciada));
                    String token = jsonObject.getJSONObject("mensaje").getString("token");

                    //Insertar BD
                    //Usuario user = guardarDatos(jsonObject);
                    //Roles
                    //saveRoles(jsonObject, user.getIdUsuario());
                    PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
                    //preferenceManager.setValue(Utils.IS_LOGIN, true);
                    //int dni = user.getIdUsuario();
                    preferenceManager.setValue(Utils.MY_ID, dniNumber);
                    preferenceManager.setValue(Utils.TOKEN, token);
                    //preferenceManager.setValue(Utils.IS_VISIT, false);
                    //Main
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //finishAffinity();
                    getData(token);
                    break;
                case 2:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioInvalido));
                    break;
                case 5:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioInhabilitado));
                    break;
                case 4:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    public void getData(String token){
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String url = String.format("%s?idU=%s&key=%s&id=%s", Utils.URL_USUARIO_LOGIN_DATA, id, key, dniNumber);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuestaLogin(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                dialog.dismiss();

            }
        });
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuestaLogin(String response) {
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
                    Utils.showToast(getApplicationContext(), getString(R.string.sesionIniciada));
                    //Insertar BD
                    Usuario user = guardarDatos(jsonObject);
                    //Roles
                    saveRoles(jsonObject, user.getIdUsuario());
                    PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
                    preferenceManager.setValue(Utils.IS_LOGIN, true);
                    int dni = user.getIdUsuario();
                    preferenceManager.setValue(Utils.MY_ID, dniNumber);
                    preferenceManager.setValue(Utils.IS_VISIT, false);
                    //Main
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finishAffinity();
                    break;
                case 2:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioInvalido));
                    break;
                case 5:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioInhabilitado));
                    break;
                case 4:
                    //Usuario invalido
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }


    private void saveRoles(JSONObject jsonObject, int id) {
        RolViewModel rolViewModel = new RolViewModel(getApplicationContext());
        if (jsonObject.has("roles")){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("roles");
                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);

                    String rol = object.getString("idrol");

                    Rol ro = new Rol(Integer.parseInt(rol), id, "test");

                    rolViewModel.insert(ro);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private Usuario guardarDatos(JSONObject datos) {
        Usuario usuario = null;
        try {
            usuario = Usuario.mapper(datos, Usuario.COMPLETE);
            mUsuarioViewModel.insert(usuario);
            switch (usuario.getTipoUsuario()) {
                case 1:
                    Alumno alumno = Alumno.mapper(datos, usuario);
                    AlumnoViewModel alumnoViewModel = new AlumnoViewModel(getApplicationContext());
                    alumnoViewModel.insert(alumno);
                    break;
                case 2:
                    Profesor profesor = Profesor.mapper(datos, usuario);
                    ProfesorViewModel profesorViewModel = new ProfesorViewModel(getApplicationContext());
                    profesorViewModel.insert(profesor);
                    break;
                case 4:
                    Egresado egresado = Egresado.mapper(datos, usuario);
                    EgresadoViewModel egresadoViewModel = new EgresadoViewModel(getApplicationContext());
                    egresadoViewModel.insert(egresado);
                    break;
                case 3:
                case 5:
                    //Nadin jeje
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

}

