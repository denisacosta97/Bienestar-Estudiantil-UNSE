package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class NuevoBecadoComedorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtNombre, edtApellido, edtDNI, edtLegajo, edtAnioIngreso;
    Spinner mSpinnerFacultad, mSpinnerCarrera;
    ImageView btnBack;
    ArrayAdapter<String> carreraAdapter;
    ArrayAdapter<String> facultadAdapter;
    DialogoProcesamiento dialog;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_becado_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setToolbar();

        loadViews();

        loadData();

        loadListener();
    }

    private void loadListener() {
        btnRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        mSpinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mSpinnerFacultad.setSelection(position);
                switch (position) {
                    case 0:
                        //FAyA
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.faya);
                        carreraAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 1:
                        //FCEyT
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.fceyt);
                        carreraAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 2:
                        //FCF
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.fcf);
                        carreraAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 3:
                        //FCM
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.fcm);
                        carreraAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 4:
                        //FHyCS
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.fhcys);
                        carreraAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerCarrera.setAdapter(carreraAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        mSpinnerCarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                facultadAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.style_spinner, Utils.facultad);
                facultadAdapter.setDropDownViewResource(R.layout.style_spinner);
                mSpinnerFacultad.setAdapter(facultadAdapter);
                carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.style_spinner, Utils.faya);
                carreraAdapter.setDropDownViewResource(R.layout.style_spinner);
                mSpinnerCarrera.setAdapter(carreraAdapter);
            }
        }).start();
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
    }

    private void loadViews() {
        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtDNI = findViewById(R.id.edtDNI);
        edtAnioIngreso = findViewById(R.id.edtAnioIngrAlu);
        edtLegajo = findViewById(R.id.edtLegajo);
        btnBack = findViewById(R.id.imgFlecha);
        mSpinnerCarrera = findViewById(R.id.spinner2);
        mSpinnerFacultad = findViewById(R.id.spinner1);
        btnRegister = findViewById(R.id.btnRegister);
    }

    private String getCarrera(int selectedItemPosition) {
        switch (selectedItemPosition) {
            case 0:
                return Utils.faya[mSpinnerCarrera.getSelectedItemPosition()];
            case 1:
                return Utils.fceyt[mSpinnerCarrera.getSelectedItemPosition()];
            case 2:
                return Utils.fcf[mSpinnerCarrera.getSelectedItemPosition()];
            case 3:
                return Utils.fcm[mSpinnerCarrera.getSelectedItemPosition()];
            case 4:
                return Utils.fhcys[mSpinnerCarrera.getSelectedItemPosition()];
        }
        return "";
    }


    private void save() {
        Validador validador = new Validador(getApplicationContext());

        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String dni = edtDNI.getText().toString().trim();
        String faculta = Utils.facultad[mSpinnerFacultad.getSelectedItemPosition()].trim();
        String carrera = getCarrera(mSpinnerFacultad.getSelectedItemPosition()).trim();
        String anioIngreso2 = edtAnioIngreso.getText().toString().trim();
        String legajo = edtLegajo.getText().toString().trim();

        if (validador.validarDNI(edtDNI)
                && !validador.validarNombresEdt(edtNombre, edtApellido)) {
            sendServer(processString(dni, nombre, apellido, carrera, faculta, anioIngreso2
                    , legajo));

        } else Utils.showToast(getApplicationContext(),
                getString(R.string.camposInvalidos));
    }

    public HashMap<String, String> processString(String dni, String nombre, String apellido, String carrera,
                                                 String facultad, String anioIng, String legajo) {
        HashMap<String, String> resp = new HashMap<>();
        resp.put("idU", dni);
        resp.put("nom", nombre);
        resp.put("ape", apellido);
        resp.put("car", carrera);
        resp.put("fac", facultad);
        resp.put("anio", anioIng);
        resp.put("leg", legajo);

        return resp;
    }

    public void sendServer(final HashMap<String, String> data) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = Utils.URL_USUARIO_INSERTAR_COMEDOR;
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
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                data.put("key", key);
                data.put("id", String.valueOf(idLocal));
                return data;
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
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.registrado)
                    );
                    finish();
                    break;
                case 2:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.errorRegistro));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.camposInvalidos));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(),
                            getString(R.string.usuarioExiste));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                save();
                break;
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }
}

