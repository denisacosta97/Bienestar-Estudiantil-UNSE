package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionAtencionDiaria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.OnClickUser;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoBuscarUsuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class NuevaAtencionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtInfo;
    EditText edtMotivo;
    Button btnGuardar, btnVerificar;
    int dni = 0;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_atencion);

        setToolbar();

        loadViews();

        loadData();

        loadListener();


    }

    private void loadData() {
    }

    private void loadListener() {
        btnVerificar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Nueva Atención");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnVer:
                verificar();
                break;
            case R.id.btnGuardar:
                guardar();
                break;
        }
    }

    private void guardar() {
        Validador validador = new Validador(getApplicationContext());
        if (dni == 0) {
            Utils.showToast(getApplicationContext(), getString(R.string.primeroBuscar));
        } else if (validador.validarTexto(edtMotivo)) {
            sendServer();
        }

    }

    private void sendServer() {

        final HashMap<String, String> map = new HashMap<>();
        String URL = Utils.URL_ATENCION_NUEVA;
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        final int id = preferenceManager.getValueInt(Utils.MY_ID);
        final String token = preferenceManager.getValueString(Utils.TOKEN);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.put("key", token);
                map.put("idU", String.valueOf(id));
                map.put("ia", String.valueOf(id));
                map.put("iu", String.valueOf(dni));
                map.put("de", edtMotivo.getText().toString());

                return map;
            }
        };
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog");
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
                    Utils.showToast(getApplicationContext(), getString(R.string.registrado));
                    finish();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorAtencion));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));

                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void verificar() {

        DialogoBuscarUsuario dialogoBuscarUsuario = new DialogoBuscarUsuario(getApplicationContext(),
                getSupportFragmentManager(), null, new OnClickUser() {
            @Override
            public void onUserSelected(int idUsuario, Object text) {
                txtInfo.setText(String.format("DNI: %S - Datos: %s", idUsuario,
                        text.toString()));
                dni = idUsuario;
            }
        });
        dialogoBuscarUsuario.setNoValid(true);
        dialogoBuscarUsuario.show(getSupportFragmentManager(), "dialog_buscar");


    }

    private void loadViews() {
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVerificar = findViewById(R.id.btnVer);
        edtMotivo = findViewById(R.id.edtDesc);
        txtInfo = findViewById(R.id.txtInfo);
    }

}
