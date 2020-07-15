package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.LoginActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CambiarContraseniaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCambiar;
    ImageView btnBack;
    TextView txtRecuperar;
    EditText edtActual, edtxNewPass, edtxRepass, edtDNI;
    LinearLayout layoutFondo;

    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasenia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        //layoutFondo = findViewById(R.id.backgroundlogin);

        /*Glide.with(this).load(R.drawable.img_unse2)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        layoutFondo.setBackground(resource);
                    }
                });*/


        loadViews();

        loadListener();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
    }

    private void loadListener() {
        btnCambiar.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        txtRecuperar.setOnClickListener(this);
    }

    private void loadViews() {
        btnBack = findViewById(R.id.imgFlecha);
        btnCambiar = findViewById(R.id.btnCambiar);
        edtActual = findViewById(R.id.edtActual);
        edtxNewPass = findViewById(R.id.edtNewPass);
        edtxRepass = findViewById(R.id.edtRepeatPass);
        txtRecuperar = findViewById(R.id.txtPassMissed);
        edtDNI = findViewById(R.id.edtDNI);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCambiar:
                String dni = edtDNI.getText().toString().trim();
                String actual = edtActual.getText().toString();
                String newPass = edtxNewPass.getText().toString();
                String rePass = edtxRepass.getText().toString();

                Validador validador = new Validador(getApplicationContext());
                if (false/*!validador.noVacio(actual, newPass, rePass)*/) {
                    if (newPass.equals(rePass)) {
                        if (validador.validarContraseña(rePass)) {
                            if (validador.validarDNI(dni)) {
                                procesarContraseña(dni, actual, newPass);
                            } else {
                                Utils.showToast(getApplicationContext(), "DNI inválido");
                            }
                        } else
                            Utils.showToast(getApplicationContext(), "La contraseña debe ser mínimo de 4 caracteres");


                    } else {
                        Utils.showToast(getApplicationContext(), "Las nuevas contraseñas no coinciden");
                    }

                } else {
                    Utils.showToast(getApplicationContext(), "¡Hay campos en blanco!");
                }
                break;
            case R.id.txtPassMissed:
                Intent intent = new Intent(getApplicationContext(), RecuperarContraseniaActivity.class);
                startActivity(intent);
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;

        }

    }

    private void procesarContraseña(String dni, String c1, String c2) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = Integer.parseInt(dni);
        String fecha = Utils.getFechaName(new Date(System.currentTimeMillis()));
        //c1 = Utils.crypt(c1);
        //c2 = Utils.crypt(c2);
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
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    Utils.showToast(getApplicationContext(), getString(R.string.contraseniaActualizada));
                    DialogoGeneral.Builder builder =new DialogoGeneral.Builder(getApplicationContext())
                            .setTitulo("Contraseña reestablecida")
                            .setDescripcion("Por favor vuelve a iniciar sesón")
                            .setIcono(R.drawable.ic_chek)
                            .setListener(new YesNoDialogListener() {
                                @Override
                                public void yes() {
                                    finishAffinity();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    PreferenceManager manager = new PreferenceManager(getApplicationContext());
                                    manager.setValue(Utils.IS_LOGIN, false);
                                }

                                @Override
                                public void no() {

                                }
                            })
                            .setColorBackground(R.color.colorGreen)
                            .setColorButtonNo(R.color.colorGreen)
                            .setColorButtonSi(R.color.colorGreen)
                            .setTipo(DialogoGeneral.TIPO_ACEPTAR);
                    DialogoGeneral dialogoGeneral = builder.build();
                    dialogoGeneral.setCancelable(false);
                    dialogoGeneral.show(getSupportFragmentManager(), "dialog_gral");
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.contraseniaActualInvalida));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }

}
