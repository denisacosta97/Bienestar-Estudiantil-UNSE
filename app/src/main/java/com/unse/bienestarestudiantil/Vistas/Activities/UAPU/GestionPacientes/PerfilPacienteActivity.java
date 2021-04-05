package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionPacientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
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
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Paciente;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class PerfilPacienteActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Paciente mPaciente;
    EditText edtMotivoCons, edtxTratamiento;
    TextView txtDni, txtNomAp, txtCarrera, txtFacultad, txtFechaTurno;
    ImageView imgIcono;
    Button btnEditar;
    DialogoProcesamiento dialog;
    EditText[] campos;
    boolean isEdit = false;
    int mode = 0, TIPO_USER = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_paciente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.PACIENTE) != null) {
            mPaciente = getIntent().getParcelableExtra(Utils.PACIENTE);
        }

        if (mPaciente != null) {
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
        txtDni.setText(mPaciente.getIdUsuario());
        String name = mPaciente.getNombre() + " " + mPaciente.getApellido();
        txtNomAp.setText(name);
        txtCarrera.setText(mPaciente.getCarrera());
        txtFacultad.setText(mPaciente.getFacultad());
        txtFechaTurno.setText(mPaciente.getFecha());
        edtMotivoCons.setText(mPaciente.getMotivoconsulta());
        edtxTratamiento.setText(mPaciente.getTratamiento());

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    private void loadViews() {
        txtDni = findViewById(R.id.txtDni);
        txtNomAp = findViewById(R.id.txtNomAp);
        txtCarrera = findViewById(R.id.txtCarrera);
        txtFacultad = findViewById(R.id.txtFacultad);
        txtFechaTurno = findViewById(R.id.txtFechaTurno);
        edtMotivoCons = findViewById(R.id.edtMotivoCons);
        edtxTratamiento = findViewById(R.id.edtxTratamiento);

        btnEditar = findViewById(R.id.btnEditar);
        imgIcono = findViewById(R.id.imgFlecha);

        campos = new EditText[]{edtMotivoCons, edtxTratamiento};
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Torneo");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditar:
                btnEditar.setText("Guardar");
                activateEditMode();
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
        Validador validador = new Validador(getApplicationContext());
        String motivo = edtMotivoCons.getText().toString().trim();
        String tratat = edtxTratamiento.getText().toString().trim();

        String fecha = Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis()));

        String token = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);

        //Comprobacion que no sean vacios
        if (!validador.noVacio(motivo)) {

            //Comprobacion del tipo d edatos
            if (true/*validador.noVacio(name, fechaIni, fechaFin, desc, lugar)*/) {
                sendServer(processString(motivo, tratat));
            } else {
                Utils.showToast(getApplicationContext(), "Hay campos invalidos, corrijalos");
            }

        } else {
            Utils.showToast(getApplicationContext(), "Por favor, complete todos los campos");
        }

    }

    private String processString(String motivo, String tratar) {
        String data = "?idT=%s&nn=%s&lgr=%s&fin=%s&ffi=%s&dsc=%s&lat=%s&lon=%s&dis=%s&val=%s&key=%s";
        //return String.format(data, mTorneo.getId(), nombre, lugar, fechaIni, fechaFin, desc, token);
        return "";
    }

    public void sendServer(String data) {
        String URL = Utils.URL_TORNEOS_ACTUALIZAR + data;
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
                    btnEditar.setText("Editar");
                    //finish();
                    break;
                case 4://No existe
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