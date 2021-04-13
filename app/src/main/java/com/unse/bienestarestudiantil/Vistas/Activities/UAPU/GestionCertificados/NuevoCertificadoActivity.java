package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionCertificados;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.unse.bienestarestudiantil.Modelos.Certificado;
import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoBuscarUsuario;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class NuevoCertificadoActivity extends AppCompatActivity implements View.OnClickListener {

    EditText  edtDescripcion;
    TextView txtInfo;
    TextView txtFecha;
    Button btnCancel, btnEmitir, btnVerificar;
    ImageView imgIcono;
    DialogoProcesamiento dialog;
    int dni = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_certificado);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }

    private void loadData() {

    }

    private void loadListener() {
        btnVerificar.setOnClickListener(this);
        imgIcono.setOnClickListener(this);
        btnEmitir.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        txtFecha.setOnClickListener(this);
    }

    private void loadViews() {
        btnVerificar = findViewById(R.id.btnVer);
        txtInfo = findViewById(R.id.txtInfo);
        btnCancel = findViewById(R.id.btnCancel);
        btnEmitir = findViewById(R.id.btnEmitir);
        txtFecha = findViewById(R.id.txtFecha);
        edtDescripcion = findViewById(R.id.edtDesc);
        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Certificados");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void selectFecha() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String mes, dia;
                month = month + 1;
                if (month < 10) {
                    mes = "0" + month;
                } else
                    mes = String.valueOf(month);
                if (day < 10)
                    dia = "0" + day;
                else
                    dia = String.valueOf(day);
                final String selectedDate = year + "-" + mes + "-" + dia;
                txtFecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnVer:
                verificar();
                break;
            case R.id.btnEmitir:
                save();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.txtFecha:
                selectFecha();
                break;
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

    private void save() {
        Validador validador = new Validador(getApplicationContext());
        String fecha = txtFecha.getText().toString().trim();
        String desc = edtDescripcion.getText().toString().trim();

        if (validador.validarTexto(edtDescripcion)){
            if (validador.validarFechaFormato(fecha) && dni != 0){
                sendServer(dni, fecha, desc);
            }else {
                Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
            }
        }
    }

    public void sendServer(final int dni, final String fecha, final String desc) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = Utils.URL_CERTIFICADOS_NUEVO;
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
                Calendar cal = Calendar.getInstance ();
                cal.setTime(Utils.getFechaDate(fecha));
                int mes = cal.get (Calendar.MONTH) + 1;
                int day = cal.get (Calendar.DATE);
                int year = cal.get (Calendar.YEAR);
                params.put("key", key);
                params.put("idU", String.valueOf(idLocal));
                params.put("ie", String.valueOf(idLocal));
                params.put("iu", String.valueOf(dni));
                params.put("di", String.valueOf(day));
                params.put("me", String.valueOf(mes));
                params.put("an", String.valueOf(year));
                params.put("de", desc);
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

}