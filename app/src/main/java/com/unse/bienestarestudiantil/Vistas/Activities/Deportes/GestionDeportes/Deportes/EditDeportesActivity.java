package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes.Deportes;

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
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.RegistroDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoDropDeporte;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoDropTorneo;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditDeportesActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Deporte mDeporte;
    EditText mHorario, mDia, mEntrenador, edtxLugar;
    TextView mNombreDep;
    ImageView mIcon, btnBack;
    Button btnModificar, btnGuardar, btnBorrar;
    DialogoProcesamiento dialog;
    EditText[] campos;
    boolean isEdit = false;
    int mode = 0, TIPO_USER = -1;
    int[] iconDeporte = {R.drawable.ic_ajedrez, R.drawable.ic_basquet, R.drawable.ic_becas,
            R.drawable.ic_futbol_masc, R.drawable.ic_futbol_fem, R.drawable.ic_futbol_masc,
            R.drawable.ic_futbol_masc, R.drawable.ic_hockey, R.drawable.ic_natacion,
            R.drawable.ic_rugby, R.drawable.ic_tenis_mesa, R.drawable.ic_voley_masc,
            R.drawable.ic_voley_fem};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deportes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null) {
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }
        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME_PROF) != null)

            mDeporte.setProfesor((Profesor) getIntent().getParcelableExtra(Utils.DEPORTE_NAME_PROF));

        if (mDeporte != null) {
            loadViews();

            loadListener();

            loadData();

            editMode(0);
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }
    }

    private void loadData() {
        mEntrenador.setText(String.format("%s %s", mDeporte.getProfesor().getNombre(), mDeporte.getProfesor().getApellido()));
        mHorario.setText(mDeporte.getHorario());
        mDia.setText(mDeporte.getDias());
        mNombreDep.setText(mDeporte.getName());
        edtxLugar.setText(mDeporte.getLugar());
        mIcon.setImageResource(mDeporte.getIconDeporte());
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        btnModificar.setOnClickListener(this);
        btnGuardar.setOnClickListener(this);
        btnBorrar.setOnClickListener(this);
    }

    private void loadViews() {
        mHorario = findViewById(R.id.edtxHorarios);
        mDia = findViewById(R.id.edtxDia);
        mEntrenador = findViewById(R.id.edtxEntrenador);
        mNombreDep = findViewById(R.id.txtNameDeporte);
        edtxLugar = findViewById(R.id.edtxLugar);
        mIcon = findViewById(R.id.imgvIcon);
        btnModificar = findViewById(R.id.btnEditar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBack = findViewById(R.id.btnBack);

        btnGuardar.setVisibility(View.GONE);

        campos = new EditText[]{mHorario, mDia, mEntrenador, edtxLugar};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditar:
                btnModificar.setVisibility(View.GONE);
                btnBorrar.setVisibility(View.GONE);
                btnGuardar.setVisibility(View.VISIBLE);
                activateEditMode();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnGuardar:
                btnGuardar.setVisibility(View.GONE);
                btnModificar.setVisibility(View.VISIBLE);
                btnBorrar.setVisibility(View.VISIBLE);
                //GUARDAR DATOS MODIFICADOS
                if (isEdit) {
                    save();
                    activateEditMode();
                    return;
                }
                break;
            case R.id.btnBorrar:
                DialogoDropDeporte dialogoDropDeporte = new DialogoDropDeporte();
                dialogoDropDeporte.loadData(mDeporte);
                dialogoDropDeporte.show(getSupportFragmentManager(),"dialog_deporte");
                break;
        }

    }

    private void activateEditMode() {
        if (mode == 0)
            mode = 1;
        else
            mode = 0;
        editMode(mode);
    }

    private void save() {
        Validador validador = new Validador(getApplicationContext());

        String horario = mHorario.getText().toString().trim();
        String dia = mDia.getText().toString().trim();
        String entrenador = mEntrenador.getText().toString().trim();
        String lugar = edtxLugar.getText().toString().trim();
        String fecha = Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis()));

        if (entrenador.equals(String.valueOf(mDeporte.getProfesor().getIdProfesor()))){
            entrenador = "0";
        }

        String token = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);

        //Comprobacion que no sean vacios
        if (!validador.noVacio(horario) && !validador.noVacio(dia) && !validador.noVacio(entrenador)
                && !validador.noVacio(lugar)) {

            //Comprobacion del tipo d edatos
            if (validador.validarHora(horario)) {

                //Comprobacion de tamaños
                if (validador.lengthMore(horario) && validador.lengthMore(dia)
                        && validador.lengthMore(entrenador) && validador.lengthMore(lugar)) {
                    sendServer(processString(horario, dia, entrenador, token, fecha, lugar));
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

    private String processString(String horario, String dia, String entrenador, String token,
                                 String fecha, String lugar) {
        String data = "?idD=%s&nn=%s&dsc=%s&lgr=%s&di=%s&hr=%s&idE=%s&fi=%s&lat=%s&lon=%s&key=%s";
        return String.format(data, mDeporte.getIdDep(), mDeporte.getName(), mDeporte.getDesc(),
                lugar, dia, horario, entrenador, fecha, mDeporte.getLat(), mDeporte.getLon(), token);
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

    private void checkDisponibility() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(System.currentTimeMillis()));

        int anio = calendar.get(Calendar.YEAR);

        int id = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);

        String URL = String.format("%s?id=%s&anio=%s&idUs=%s&key=%s",
                Utils.URL_DEPORTE_TEMPORADA, mDeporte.getIdDep(), anio, id,
                new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN));

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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
                    i.putExtra(Utils.DEPORTE_NAME, mDeporte);
                    int id = jsonObject.getInt("id");
                    i.putExtra(Utils.DEPORTE_ID, id);
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
}
