package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionPacientes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
import com.unse.bienestarestudiantil.Modelos.Lista;
import com.unse.bienestarestudiantil.Modelos.Paciente;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.ListaGeneralAdapter;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PerfilPacienteActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Paciente mPaciente;
    EditText edtMotivoCons;
    TextView txtDni, txtNomAp, txtCarrera, txtFacultad, txtFechaTurno;
    ImageView imgIcono;
    RecyclerView mRecyclerView;
    ArrayList<Lista> mConsultas;
    ListaGeneralAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Button btnEditar;
    DialogoProcesamiento dialog;
    EditText[] campos;
    boolean isEdit = false, isNew = false;
    int mode = 0, idServicio = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_paciente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.PACIENTE) != null) {
            mPaciente = getIntent().getParcelableExtra(Utils.PACIENTE);
        }

        if (getIntent().getIntExtra(Utils.SERVICIO, 0) != 0) {
            idServicio = getIntent().getIntExtra(Utils.SERVICIO, 0);
        }

        if (getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false)) {
            isNew = getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false);
        }

        if (getIntent().getSerializableExtra(Utils.DATA_TURNO) != null) {
            mConsultas = (ArrayList<Lista>) getIntent().getSerializableExtra(Utils.DATA_TURNO);
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
        txtDni.setText(String.valueOf(mPaciente.getIdUsuario()));
        String name = mPaciente.getNombre() + " " + mPaciente.getApellido();
        txtNomAp.setText(name);
        txtCarrera.setText(mPaciente.getCarrera());
        txtFacultad.setText(mPaciente.getFacultad());
        txtFechaTurno.setText(Utils.getFechaFormat(mPaciente.getFecha()));
        edtMotivoCons.setText(mPaciente.getMotivoconsulta());

        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);
        mAdapter = new ListaGeneralAdapter(mConsultas, getApplicationContext(), ListaGeneralAdapter.HISTORIAL);
        mRecyclerView.setAdapter(mAdapter);


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
        mRecyclerView = findViewById(R.id.recycler);

        btnEditar = findViewById(R.id.btnEditar);
        imgIcono = findViewById(R.id.imgFlecha);

        campos = new EditText[]{edtMotivoCons};
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Paciente");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        if (mode == 0) {
            btnEditar.setText("GUARDAR");
            mode = 1;
        }
        else {
            btnEditar.setText("EDITAR");
            mode = 0;
        }
        editMode(mode);
    }

    private void save() {
        Validador validador = new Validador(getApplicationContext());
        String motivo = edtMotivoCons.getText().toString().trim();
        //Comprobacion que no sean vacios
        if (validador.validarTexto(edtMotivoCons)) {
            sendServer(motivo);
        }

    }

    public void sendServer(final String data) {
        String URL = null;
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        final int id = preferenceManager.getValueInt(Utils.MY_ID);
        final String token = preferenceManager.getValueString(Utils.TOKEN);
        if (!isNew)
            URL = Utils.URL_NUEVA_CONSULTA;
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
                HashMap<String, String> map = new HashMap<>();
                map.put("key", token);
                map.put("idU", String.valueOf(id));
                map.put("id", String.valueOf(id));
                map.put("iu", String.valueOf(mPaciente.getIdUsuario()));
                map.put("is", String.valueOf(idServicio));
                map.put("de", data);
                return map;
            }
        };
        ;
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
                    Utils.showToast(getApplicationContext(), getString(R.string.registrado));
                    isEdit = false;
                    activateEditMode();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorRegistro));
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