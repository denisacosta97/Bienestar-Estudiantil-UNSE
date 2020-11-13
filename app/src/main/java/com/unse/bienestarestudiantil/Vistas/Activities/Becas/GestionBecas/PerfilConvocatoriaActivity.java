package com.unse.bienestarestudiantil.Vistas.Activities.Becas.GestionBecas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.unse.bienestarestudiantil.Modelos.Convocatoria;
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoDropTorneo;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PerfilConvocatoriaActivity extends AppCompatActivity implements View.OnClickListener {

    Convocatoria mConvocatoria;
    TextView mBeca, mFechaDesde, mFechaHasta, mDispo;
    Button btnDeshabilitar, btnHabilitar, btnEditar;
    DialogoProcesamiento dialog;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_convocatoria);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.NUM_CONVOC) != null) {
            mConvocatoria = getIntent().getParcelableExtra(Utils.NUM_CONVOC);
        }

        if (mConvocatoria != null) {
            loadViews();

            loadListener();

            loadData();

        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

        setToolbar();

    }

    private void loadData() {
        mBeca.setText(mConvocatoria.getNombre());
        mFechaDesde.setText(mConvocatoria.getFechaInicio());
        mFechaHasta.setText(mConvocatoria.getFechaFin());
        int dispo = mConvocatoria.getDisponibilidad();
        if(dispo == 0){
            btnHabilitar.setVisibility(View.VISIBLE);
            mDispo.setText("Deshabilitada");
        }
        else
            btnHabilitar.setVisibility(View.GONE);
            mDispo.setText("Habilitada");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnDeshabilitar.setOnClickListener(this);
        btnHabilitar.setOnClickListener(this);
        mFechaDesde.setOnClickListener(this);
        mFechaHasta.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    private void loadViews() {
        mBeca = findViewById(R.id.txtName);
        mFechaDesde = findViewById(R.id.txtFechaDesde);
        mFechaHasta = findViewById(R.id.txtFechaHasta);
        mDispo = findViewById(R.id.txtDispo);
        btnDeshabilitar = findViewById(R.id.btnDeshabilitar);
        btnHabilitar = findViewById(R.id.btnHabilitar);
        imgIcono = findViewById(R.id.imgFlecha);
        btnEditar = findViewById(R.id.btnEditar);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Beca");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDeshabilitar:
                btnDeshabilitar.setVisibility(View.GONE);
                btnHabilitar.setVisibility(View.VISIBLE);
                save(0);
                break;
            case R.id.btnHabilitar:
                btnDeshabilitar.setVisibility(View.VISIBLE);
                btnHabilitar.setVisibility(View.GONE);
                save(1);
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.txtFechaDesde:
                selectFecha(0);
                break;
            case R.id.txtFechaHasta:
                selectFecha(1);
                break;
            case R.id.btnEditar:
                save(1);
                break;
        }
    }

    private void selectFecha(final int iof) {
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
                if(iof == 0)
                    mFechaDesde.setText(selectedDate);
                else
                    mFechaHasta.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    private void save(final int dispo) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int id = manager.getValueInt(Utils.MY_ID);
        String URL = Utils.URL_BAJA_CONVOCATORIA;
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
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("key", key);
                param.put("idU", String.valueOf(id));
                param.put("ib", String.valueOf(mConvocatoria.getIdBeca()));
                param.put("fi", String.valueOf(mFechaDesde.getText()));
                param.put("ff", String.valueOf(mFechaHasta.getText()));
                param.put("an", String.valueOf(mConvocatoria.getAnio()));
                param.put("di", String.valueOf(dispo));
                return param;
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
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), "Error al actualizar");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

}