package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.RegistroDeporteActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditDeportesActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    TextView mHorario, mDia, mEntrenador, mNombreDep;
    ImageView mIcon, btnBack;
    Button btnRegister;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deportes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null) {
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }

        if (mDeporte != null) {
            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelta a intentar");
            finish();
        }
    }

    private void loadData() {
        mEntrenador.setText("");
        mHorario.setText(mDeporte.getHorario());
        mDia.setText(mDeporte.getDias());
        mNombreDep.setText(mDeporte.getName());
        mIcon.setImageResource(mDeporte.getIconDeporte());
    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    private void loadViews() {
        mHorario = findViewById(R.id.txtHorarios);
        mDia = findViewById(R.id.txtDia);
        mEntrenador = findViewById(R.id.txtEntrenador);
        mNombreDep = findViewById(R.id.txtNameDeporte);
        mIcon = findViewById(R.id.imgvIcon);
        btnRegister = findViewById(R.id.btnRegisterDep);
        btnBack = findViewById(R.id.btnBack);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegisterDep:
                checkDisponibility();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }

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

}
