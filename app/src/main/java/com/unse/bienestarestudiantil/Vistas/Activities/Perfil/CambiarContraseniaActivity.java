package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.RegistroDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CambiarContraseniaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCambiar;
    TextView txtRecuperar;
    EditText edtActual, edtxNewPass, edtxRepass;

    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();
    }

    private void loadListener() {
        btnCambiar.setOnClickListener(this);
        txtRecuperar.setOnClickListener(this);
    }

    private void loadViews() {
        btnCambiar = findViewById(R.id.btnCambiar);
        edtActual = findViewById(R.id.edtActual);
        edtxNewPass = findViewById(R.id.edtNewPass);
        edtxRepass = findViewById(R.id.edtRepeatPass);
        txtRecuperar = findViewById(R.id.txtPassMissed);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCambiar:
                String actual = edtActual.getText().toString();
                String newPass = edtxNewPass.getText().toString();
                String rePass = edtxRepass.getText().toString();

                Validador validador = new Validador();
                if (!validador.noVacio(actual, newPass, rePass)){
                    if (newPass.equals(rePass)){
                        procesarContraseña(actual, newPass);

                    }else{
                        Utils.showToast(getApplicationContext(), "Las nuevas contraseñas no coinciden");
                    }

                }else{
                    Utils.showToast(getApplicationContext(), "¡Hay campos en blanco!");
                }
                break;
            case R.id.txtPassMissed:
                Intent intent = new Intent(getApplicationContext(), RecuperarContraseniaActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void procesarContraseña(String c1, String c2) {
        String key = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
        int id = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        c1 = Utils.crypt(c1);
        c2 = Utils.crypt(c2);
        String URL = String.format("%s?id=%s&cc=%s&nn=%s&ff=%s&key=%s", Utils.URL_CAMBIO_CONTRASENIA, id, c1, c2, fecha, key);

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
                    Utils.showToast(getApplicationContext(),"Contraseña actualizada");
                    finish();
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

}
