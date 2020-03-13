package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class RecuperarContraseniaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEnviar;
    ImageView imgBack;
    EditText edtxMail, edtDNI;
    TextView txtTengoClave;

    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadListener();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
    }

    private void loadListener() {
        imgBack.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);
        txtTengoClave.setOnClickListener(this);
    }

    private void loadViews() {
        btnEnviar = findViewById(R.id.btnEnviar);
        edtxMail = findViewById(R.id.edtMail);
        edtDNI = findViewById(R.id.edtDNI);
        txtTengoClave = findViewById(R.id.txtTengoClave);
        imgBack = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.txtTengoClave:
                startActivity(new Intent(getApplicationContext(), CambiarContraseniaActivity.class));
                break;
            case R.id.btnEnviar:
                String email = edtxMail.getText().toString().trim();
                String dni = edtDNI.getText().toString().trim();

                Validador validador = new Validador();
                if (!validador.noVacio(email, dni)) {

                    if (validador.validarMail(email)) {

                        if (validador.validarNumero(dni)) {

                            recuperarClave(dni, email);

                        } else
                            Utils.showToast(getApplicationContext(), "Numero de DNI inválido");

                    } else {
                        Utils.showToast(getApplicationContext(), "Campo mail inválido");
                    }

                } else {
                    Utils.showToast(getApplicationContext(), "Hay campos vacíos");
                }
                break;
        }

    }

    private void recuperarClave(String dni, String email) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String token = manager.getValueString(Utils.TOKEN);
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        String URL = String.format("%s?id=%s&ee=%s&ff=%s&key=%s", Utils.URL_REC_CONTRASENIA, dni, email, fecha, token);

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
                    String msj = jsonObject.getString("mensaje");
                    Utils.showLog("PASS NEW", msj);
                    Utils.showToast(getApplicationContext(), "¡Revisa tu mail!");
                    finish();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "No se puedo recuperar la clave, contacta al Administrador");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), "Los datos no coinciden con ninguna cuenta");
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "No está autorizado para realizar ésta operación");
                    break;
                case 200:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), "Error interno, contacta al Administrador");
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

}
