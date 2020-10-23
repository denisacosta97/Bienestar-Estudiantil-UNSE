package com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AgregarConsultaActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    EditText edtDni, edtConsulta;
    Button btnGuardar;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_consulta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();

        setToolbar();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Agregar consulta");

    }


    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
    }


    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);
        edtConsulta = findViewById(R.id.edtConsulta);
        edtDni = findViewById(R.id.edtDni);
        btnGuardar = findViewById(R.id.btnGuardar);
    }


    private void save() {
        Validador validador = new Validador(getApplicationContext());
        String dni = edtDni.getText().toString().trim();
        String consulta = edtConsulta.getText().toString().trim();

        if (!validador.noVacio(dni) && !validador.noVacio(consulta)) {
            if(validador.validarDNI(dni)){
                sendServer(dni, consulta);
            }

        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
        }
    }

    public void sendServer(final String dni, final String consulta) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s", Utils.URL_INSERTAR_CONSULTA);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("key", key);
                params.put("idU", String.valueOf(idLocal));
                params.put("iu", String.valueOf(dni));
                params.put("de", consulta);
                params.put("ir", String.valueOf(idLocal));
                return params;
            }
        };
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
                    Utils.showToast(getApplicationContext(), getString(R.string.exito));
                    finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnGuardar:
                save();
                break;
        }
    }
}