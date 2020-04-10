package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PerfilDeporteActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    TextView mHorario, mDia, mEntrenador, mNombreDep;
    ImageView mIcon, btnBack;
    Button btnRegister;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_deporte);
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
        mEntrenador.setText(String.format("%s %s", mDeporte.getProfesor().getNombre(), mDeporte.getProfesor().getApellido()));
        mHorario.setText(mDeporte.getHorario());
        mDia.setText(mDeporte.getDias());
        mNombreDep.setText(mDeporte.getName());
        Glide.with(mIcon.getContext()).load(Utils.getIconDeporte(mDeporte.getIdDep())).into(mIcon);
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

        String URL = String.format("%s?idD=%s&anio=%s&id=%s&key=%s",
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
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
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
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    Intent i = new Intent(getApplicationContext(), RegistroDeporteActivity.class);
                    i.putExtra(Utils.DEPORTE_NAME, mDeporte);
                    int id = jsonObject.getInt("id");
                    i.putExtra(Utils.DEPORTE_ID, id);
                    startActivity(i);
                    break;

                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.deportesSinConvocatoria));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                   break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(), getString(R.string.deporteCerroConvocatoria));
                    break;
                case 6:
                    Utils.showToast(getApplicationContext(), getString(R.string.deporteYaInscripto));
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
