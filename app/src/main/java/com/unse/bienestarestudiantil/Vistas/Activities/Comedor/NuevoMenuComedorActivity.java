package com.unse.bienestarestudiantil.Vistas.Activities.Comedor;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Menu;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NuevoMenuComedorActivity extends AppCompatActivity implements
        View.OnClickListener {

    EditText edtPorcion, edtAlmuerzo, edtCena, edtPostre, edtFecha;
    Button btnGuardar, btnVolver;
    int diaF = -1, mesF = -1, anioF = -1;
    DialogoProcesamiento dialog;
    Menu menu;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_menu_comedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.ID_MENU) != null) {
            menu = getIntent().getParcelableExtra(Utils.ID_MENU);
        }

        setToolbar();

        loadView();

        loadData();

        loadListener();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(menu == null ? "Nuevo menú" :
                String.format("%s/%s/%s", menu.getDia(), menu.getMes(), menu.getAnio()));
    }

    private void loadListener() {
        btnGuardar.setOnClickListener(this);
        edtFecha.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    private void loadData() {
        if (menu == null) {
            edtPorcion.setText("2");
        } else {
            edtFecha.setEnabled(false);
            edtFecha.setText(String.format("%s/%s/%s", menu.getDia(), menu.getMes(), menu.getAnio()));
            edtPorcion.setText(String.valueOf(menu.getPorcion()));
            String[] comida = menu.getComidas();
            edtAlmuerzo.setText(comida[0]);
            edtCena.setText(comida[1]);
            edtPostre.setText(comida[2]);
        }
    }

    private void loadView() {
        btnVolver = findViewById(R.id.btnBack);
        edtFecha = findViewById(R.id.edtFecha);
        edtPorcion = findViewById(R.id.edtPorcion);
        edtAlmuerzo = findViewById(R.id.edtAlmuerzo);
        edtCena = findViewById(R.id.edtCena);
        edtPostre = findViewById(R.id.edtPostre);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBack = findViewById(R.id.imgFlecha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnGuardar:
                save();
                break;
            case R.id.edtFecha:
                elegirFechaNacimiento();
                break;
        }
    }

    private void elegirFechaNacimiento() {
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
                diaF = day;
                mesF = month;
                anioF = year;
                final String selectedDate = year + "-" + mes + "-" + dia;
                edtFecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    private void save() {
        String almuerzo = edtAlmuerzo.getText().toString();
        String cena = edtCena.getText().toString();
        String postre = edtPostre.getText().toString();
        if (almuerzo.equals(""))
            almuerzo = " ";
        if (cena.equals(""))
            cena = " ";
        if (postre.equals(""))
            postre = " ";
        String descripcion = almuerzo + "$" + cena + "$" + postre;
        String porcion = edtPorcion.getText().toString();
        Validador validador = new Validador(getApplicationContext());
        if (!validador.validarNombresEdt(edtAlmuerzo) && validador.validarNumero(edtPorcion)) {
            if (menu != null || (diaF != -1 && mesF != -1 && anioF != -1)) {
                sendServer(descripcion, porcion, diaF, mesF, anioF);
            } else {
                Utils.showToast(getApplicationContext(),
                        getString(R.string.primeroFecha));
            }
        } else {
            Utils.showToast(getApplicationContext(),
                    getString(R.string.camposInvalidos));
        }
    }

    private void sendServer(final String descripcion, final String porcion, final int diaF,
                            final int mesF, final int anioF) {
        PreferenceManager manager = new PreferenceManager(getApplicationContext());
        final String key = manager.getValueString(Utils.TOKEN);
        final int idLocal = manager.getValueInt(Utils.MY_ID);
        String URL = menu == null ? Utils.URL_MENU_NUEVO : Utils.URL_MENU_ACTUALIZAR;
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
                HashMap<String, String> data = new HashMap<>();
                data.put("key", key);
                data.put("idU", String.valueOf(idLocal));
                if (menu == null) {
                    data.put("d", String.valueOf(diaF));
                    data.put("m", String.valueOf(mesF));
                    data.put("a", String.valueOf(anioF));
                } else data.put("im", String.valueOf(menu.getIdMenu()));
                data.put("de", String.valueOf(descripcion));
                data.put(menu == null ? "p" : "po", String.valueOf(porcion));
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
                            menu == null ? getString(R.string.registrado) : getString(R.string.actualizado));
                    if (menu == null) finish();
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
                            getString(R.string.yaExiste));
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
}
