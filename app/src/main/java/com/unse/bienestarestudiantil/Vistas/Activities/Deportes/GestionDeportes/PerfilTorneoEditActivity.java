package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.unse.bienestarestudiantil.Modelos.Torneo;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.RegistroDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class PerfilTorneoEditActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Torneo mTorneo;
    EditText mDesc, mFechaInicio, mFechaFin, mLugar, mNombreTorneo;
    ImageView imgIcono;
    Button btnBorrar, btnEditar;
    DialogoProcesamiento dialog;
    EditText[] campos;
    boolean isEdit = false;
    int mode = 0, TIPO_USER = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torneo_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.TORNEO) != null) {
            mTorneo = getIntent().getParcelableExtra(Utils.TORNEO);
        }

        if (mTorneo != null) {
            loadViews();

            loadListener();

            loadData();

            editMode(0);
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }

        setToolbar();

    }

    private void loadData() {
        mNombreTorneo.setText(mTorneo.getNameTorneo());
        mLugar.setText(mTorneo.getLugar());
        mFechaInicio.setText(String.format("%s", mTorneo.getFechaInicio()));
        mFechaFin.setText(String.format("%s", mTorneo.getFechaFin()));
        mDesc.setText(mTorneo.getDesc());

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnBorrar.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    private void loadViews() {
        mNombreTorneo = findViewById(R.id.edtxNameTorneo);
        mFechaInicio = findViewById(R.id.edtxFechaInicio);
        mFechaFin = findViewById(R.id.edtxFechaFin);
        mLugar = findViewById(R.id.edtxLugar);
        mDesc = findViewById(R.id.edtxDesc);

        btnBorrar = findViewById(R.id.btnBorrar);
        btnEditar = findViewById(R.id.btnEditar);
        imgIcono = findViewById(R.id.imgFlecha);

        campos = new EditText[]{mDesc, mFechaInicio, mFechaFin, mLugar, mNombreTorneo};
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditar:
                activateEditMode();
                break;
            case R.id.btnBorrar:
                //Función para borrar gg
                break;
            case R.id.imgIcon:
                onBackPressed();
                break;
        }
    }

    private void editMode(int mode) {
        for (EditText e : campos) {
            if (mode == 0) {
                e.setEnabled(false);
                e.setBackgroundColor(getResources().getColor(R.color.transparente));
                e.removeTextChangedListener(null);
            } else {
                e.setEnabled(true);
                e.setBackground(getResources().getDrawable(R.drawable.edit_text_logreg));
                e.addTextChangedListener(this);
            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isEdit = true;
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void activateEditMode() {
        if (isEdit) {
            save();
            return;

        }
        if (mode == 0)
            mode = 1;
        else
            mode = 0;
        editMode(mode);
    }

    private void save() {
        Validador validador = new Validador();

        String name = mNombreTorneo.getText().toString().trim();
        String fechaIni = mFechaInicio.getText().toString().trim();
        String fechaFin = mFechaFin.getText().toString().trim();
        String desc = mDesc.getText().toString().trim();
        String lugar = mLugar.getText().toString().trim();
        String fecha = Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis()));

        String token = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);

        //Comprobacion que no sean vacios
        if (!validador.noVacio(name, fechaIni, fechaFin, desc, lugar)) {

            //Comprobacion del tipo d edatos
            if (validador.noVacio(name, fechaIni, fechaFin, desc, lugar)) {

                //Comprobacion de tamaños
                if (validador.lengthMore(name) && validador.lengthMore(fechaIni)
                        && validador.lengthMore(fechaFin) && validador.lengthMore(desc)
                        && validador.lengthMore(lugar)) {
                    sendServer(processString(name, fechaIni, fechaFin, desc, lugar, token, fecha));
                } else {
                    Utils.showToast(getApplicationContext(), "Hay campos con información inválida");
                }

            } else {
                Utils.showToast(getApplicationContext(), "Hay campos invalidos, corrijalos");
            }

        } else {
            Utils.showToast(getApplicationContext(), "Por favor, complete todos los campos");
        }

    }

    private String processString(String nombre, String fechaIni, String fechaFin, String desc, String lugar,
                                 String token, String fecha) {
        String data = "?idT=%s&nn=%s&lgr=%s&fIn=%s&fFi=%s&dsc=%s&ubi=%s&lat=%s&lon=%s&key=%s";
        return String.format(data, mTorneo.getId(), nombre, lugar, fechaIni, fechaFin, desc,
                mTorneo.getUbicacion(), token, fecha);
    }

    public void sendServer(String data) {
        String URL = Utils.URL_USUARIO_ACTUALIZAR + data;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
                    Intent i = new Intent(getApplicationContext(), RegistroDeporteActivity.class);
                    i.putExtra(Utils.TORNEO, mTorneo);
                    int id = jsonObject.getInt("id");
                    i.putExtra(Utils.TORNEO, id);
                    startActivity(i);
                    break;
                case 4://No existe
                case 2:
                    //Sin Convocatoria
                    Utils.showToast(getApplicationContext(), "El deporte seleccionado no tiene convocatoria vigente");
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), "No se puede procesar la tarea solicitada");
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), "Ya se encuentra inscripto en la actividad");
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
