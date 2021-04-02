package com.unse.bienestarestudiantil.Vistas.Activities.UAPU;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.Modelos.Impresion;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

public class AddConsultaUAPUActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtDni, edtNomAp, edtMotivo, edtDescripcion;
    Button btnCancel, btnGuardar;
    ImageView imgIcono;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consulta_uapu);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }


    private void loadData() {

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void loadViews() {
        btnCancel = findViewById(R.id.btnCancel);
        btnGuardar = findViewById(R.id.btnGuardar);
        edtDni = findViewById(R.id.edtDni);
        edtNomAp = findViewById(R.id.edtNomAp);
        edtMotivo = findViewById(R.id.edtMotivo);
        edtDescripcion = findViewById(R.id.edtDesc);
        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Consulta");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
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
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void save() {
        String dni = edtDni.getText().toString();
        String nom = edtNomAp.getText().toString();
        String motivo = edtMotivo.getText().toString();
        String desc = edtDescripcion.getText().toString();
        Validador validador = new Validador(getBaseContext());
        if (!validador.noVacio(dni) && !validador.noVacio(nom) && !validador.noVacio(motivo)) {
            sendServer(dni, nom, motivo, desc);
        }
    }

    private void sendServer(String dni, String cantP, String precio, String desc) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int id = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&iu=%s&de=%s&ca=%s&pr=%s",
                Utils.URL_REGISTRAR_IMPR, key, id, dni, desc, cantP, precio);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta2(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.servidorOff), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta2(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Toast.makeText(getApplicationContext(), getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "Usuario agregado", Toast.LENGTH_SHORT).show();
                    //Exito
                    //loadInfo(jsonObject);
                    finish();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "Error interno", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), getString(R.string.camposInvalidos), Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), getString(R.string.tokenInvalido), Toast.LENGTH_SHORT).show();
                    break;
                case 100:
                    //No autorizado
                    Toast.makeText(getApplicationContext(), getString(R.string.tokenInexistente), Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.errorInternoAdmin), Toast.LENGTH_SHORT).show();
        }
    }

}