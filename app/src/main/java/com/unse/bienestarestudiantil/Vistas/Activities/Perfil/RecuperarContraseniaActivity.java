package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RecuperarContraseniaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEnviar;
    ImageView imgBack;
    EditText edtxMail, edtDNI;
    TextView txtNoLlega;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasenia);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        update();

        loadListener();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
    }

    private void loadListener() {
        imgBack.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);
        txtNoLlega.setOnClickListener(this);
    }

    private void loadViews() {
        btnEnviar = findViewById(R.id.btnEnviar);
        edtxMail = findViewById(R.id.edtMail);
        edtDNI = findViewById(R.id.edtDNI);
        txtNoLlega = findViewById(R.id.txtNoLlega);
        imgBack = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.txtNoLlega:
                showDialog();
                break;
            case R.id.btnEnviar:
                chequear();
                break;
        }

    }

    private void showDialog() {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(getString(R.string.contraseniaReco))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {

                    }

                    @Override
                    public void no() {

                    }
                })
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog");
    }

    private void chequear() {
        String email = edtxMail.getText().toString().trim();
        String dni = edtDNI.getText().toString().trim();

        Validador validador = new Validador(getApplicationContext());
        if (validador.validarDNI(edtDNI) && validador.validarMail(edtxMail)) {

            recuperarClave(dni, email);

        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
        }
    }

    private void recuperarClave(String dni, String email) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String token = manager.getValueString(Utils.TOKEN);
        String URL = String.format("%s?iu=%s&em=%s", Utils.URL_REC_CONTRASENIA, dni, email);
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
                    Utils.showToast(getApplicationContext(), "¡Revisa tu mail!");
                    PreferenceManager manager = new PreferenceManager(getApplicationContext());
                    Date date = new Date(System.currentTimeMillis());
                    String fecha = Utils.getFechaName(date);
                    manager.setValue(Utils.FECHA_PASS, fecha);
                    update();
                    break;
                case 4:
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.noCambioContrasenia));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.cuentaInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void update() {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String fecha = manager.getValueString(Utils.FECHA_PASS);
        if (!fecha.equals("")) {
            Date date = Utils.getFechaDateWithHour(fecha);
            Date now = new Date(System.currentTimeMillis());
            long diff = now.getTime() - date.getTime();
            long min = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (min < 3) {
                btnEnviar.setEnabled(false);
                btnEnviar.setText(String.format("Disponible en %s MIN", 3));
            } else {
                btnEnviar.setEnabled(true);
                btnEnviar.setText("ENVIAR");
            }
        }
    }

}
