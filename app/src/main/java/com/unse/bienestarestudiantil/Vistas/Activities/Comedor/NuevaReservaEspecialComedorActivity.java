package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;


public class NuevaReservaEspecialComedorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtPorcion, edtDescripcion, edtDNI, edtCantidad;
    Button btnGuardar;
    int idMenu = -1;
    DialogoProcesamiento dialog;
    ImageView btnBack;
    EditText edtUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva_especial_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadIntent();

        setToolbar();

        loadView();

        loadData();

        loadListener();

    }

    private void loadIntent() {
        if (getIntent().getIntExtra(Utils.ID_MENU, -1) != -1) {
            idMenu = getIntent().getIntExtra(Utils.ID_MENU, -1);
        }
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Reserva especial");
    }

    private void loadListener() {
        btnGuardar.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void loadData() {
        edtPorcion.setText("2");
    }

    private void loadView() {
        edtCantidad = findViewById(R.id.edtCantReservas);
        edtUsuario = findViewById(R.id.edtNombre);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtPorcion = findViewById(R.id.edtPorcion);
        edtDNI = findViewById(R.id.edtDNI);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBack = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnGuardar:
                save();
                break;
        }
    }

    private void save() {
        String descripcion = edtDescripcion.getText().toString();
        String dni = edtDNI.getText().toString();
        if (dni.equals("0")) dni = "1";
        String porcion = edtPorcion.getText().toString();
        Validador validador = new Validador(getApplicationContext());
        String nombre = edtUsuario.getText().toString();
        descripcion = descripcion + " - " + nombre;
        if (!validador.noVacio(descripcion) && validador.validarNumero(edtPorcion)
                && validador.validarNumero(edtDNI) && validador.validarNumero(edtCantidad)) {
            if (idMenu != -1) {
                sendServer(descripcion, porcion, dni, idMenu, edtCantidad.getText().toString());
            } else {
                Utils.showToast(getApplicationContext(),
                        getString(R.string.elegirMenu));
            }
        } else {
            Utils.showToast(getApplicationContext(),
                    getString(R.string.camposInvalidos));
        }
    }

    private void sendServer(String descripcion, String porcion, String dni, int idmenu, String cantidad) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        String key = manager.getValueString(Utils.TOKEN);
        int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = String.format("%s?key=%s&idU=%s&i=%s&im=%s&t=%s&ie=%s&des=%s&p=%s&c=%s",
                Utils.URL_RESERVA_INSERTAR_ESPECIAL
                , key, idLocal, dni, idmenu, 4, idLocal, descripcion, porcion, cantidad);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                procesarRespuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(),
                        getString(R.string.servidorOff));
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
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    loadInfo(jsonObject);
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.reservaError));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.camposInvalidos));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.yaReservoEspecial));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInvalido));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(),
                    getString(R.string.errorInternoAdmin));
        }
    }

    private void loadInfo(JSONObject jsonObject) {
        try {
            if (jsonObject.has("mensaje") && jsonObject.has("dato")) {

                Reserva reserva = Reserva.mapper(jsonObject.getJSONObject("dato"), Reserva.ESPECIALES);

                dialogReserva(true, reserva);
            } else {
                dialogReserva(false, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            dialogReserva(false, null);
        }
    }

    private void dialogReserva(boolean b, final Reserva reserva) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(b ? R.string.reservada : R.string.salioMal))
                .setDescripcion(b ? String.format(getString(R.string.reservaAdminExito), String.valueOf(reserva.getIdReserva()))
                        : getString(R.string.reservaError))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        openDialogInfo(reserva);
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(b ? R.drawable.ic_exito : R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }

    private void openDialogInfo(Reserva reserva) {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setTitulo(getString(R.string.advertencia))
                .setDescripcion(String.format("Su código de reserva es:\nCOM-%s-%s\n¡Anótelo!", Utils.getCode(reserva),
                        reserva.getIdReserva()))
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        finish();
                    }

                    @Override
                    public void no() {

                    }
                })
                .setIcono(R.drawable.ic_advertencia)
                .setTipo(DialogoGeneral.TIPO_ACEPTAR);
        DialogoGeneral dialogoGeneral = builder.build();
        dialogoGeneral.setCancelable(false);
        dialogoGeneral.show(getSupportFragmentManager(), "dialog_ad");
    }
}

