package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Databases.AlumnoViewModel;
import com.unse.bienestarestudiantil.Databases.UsuarioViewModel;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroDeporteActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    EditText edtNombreDep, edtNombre, edtApellido, edtDni, edtEdad, edtFechaNac, edtDomicilio,
            edtBarrio, edtCarrera, edtLegajo,
            edtFacultad, edtCiudad, edtProvincia, edtPais, edtAnioIngeso, edtCantMaterias, edtFacebook,
            edtInstagram, edtMail, edtTelefono, edtActividadFisica,
            edtLugarActividad, edtObjetivo, edtPeso, edtAltura;
    CheckBox checkwhatsApp, checksi, checkno, checkcont, checkmedia, checkbaja;
    LinearLayout latActividad;
    ImageView btnBack;
    Button btnRegistrar;
    String peso, altura;
    DialogoProcesamiento dialog;
    boolean isWsp = false, isActividad = false;
    int intensidad = -1, anio = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_deporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null) {
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }
        if (getIntent().getIntExtra(Utils.DEPORTE_TEMPORADA, -1) != -1) {
            anio = getIntent().getIntExtra(Utils.DEPORTE_TEMPORADA, -1);
        }

        loadViews();

        loadListener();

        loadData();

    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
        checkwhatsApp.setOnClickListener(this);
        checkno.setOnClickListener(this);
        checksi.setOnClickListener(this);
        checkbaja.setOnClickListener(this);
        checkcont.setOnClickListener(this);
        checkmedia.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

    }

    private void loadData() {
        checkno.setChecked(true);
        latActividad.setVisibility(View.GONE);

        UsuarioViewModel usuarioViewModel = new UsuarioViewModel(getApplicationContext());
        int idLocal = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);

        Usuario usuario = usuarioViewModel.getById(idLocal);
        edtNombreDep.setText(mDeporte.getName());
        edtNombre.setText(usuario.getNombre());
        edtApellido.setText(usuario.getApellido());
        edtDni.setText(String.valueOf(usuario.getIdUsuario()));
        edtEdad.setText(String.valueOf(Utils.getEdad(Utils.getFechaDate(usuario.getFechaNac()))));
        edtFechaNac.setText(Utils.getFechaNameWithinHour(Utils.getFechaDate(usuario.getFechaNac())));
        edtDomicilio.setText(usuario.getDomicilio());
        edtBarrio.setText(usuario.getBarrio());
        edtCiudad.setText(usuario.getLocalidad());
        edtProvincia.setText(usuario.getProvincia());
        edtPais.setText(usuario.getPais());
        edtMail.setText(usuario.getMail());
        edtTelefono.setText(usuario.getTelefono());

        EditText[] lock = new EditText[]{edtNombreDep, edtNombre, edtApellido, edtDni, edtEdad, edtFechaNac, edtDomicilio,
            edtBarrio, edtCiudad, edtProvincia, edtPais, edtMail, edtTelefono};

        for (EditText edt : lock){
            edt.setEnabled(false);
        }

        if (usuario.getTipoUsuario() == Utils.TIPO_ALUMNO) {
            AlumnoViewModel alumnoViewModel = new AlumnoViewModel(getApplicationContext());
            Alumno alumno = alumnoViewModel.getById(usuario.getIdUsuario());
            edtFacultad.setText(alumno.getFacultad());
            edtCarrera.setText(alumno.getCarrera());
            edtLegajo.setText(alumno.getLegajo());
            edtAnioIngeso.setText(alumno.getAnio());

            edtFacultad.setEnabled(false);
            edtCarrera.setEnabled(false);
            edtLegajo.setEnabled(false);
            edtAnioIngeso.setEnabled(false);
        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoAlumno));
            edtCarrera.setText(" ");
            edtLegajo.setText(" ");
            edtFacultad.setText(" ");
            edtAnioIngeso.setText(" ");
            edtCantMaterias.setText("0");
        }


    }

    private void loadViews() {
        edtNombreDep = findViewById(R.id.edtDeporte);
        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtDni = findViewById(R.id.edxtDni);
        edtEdad = findViewById(R.id.edtxEdad);
        edtFechaNac = findViewById(R.id.txtDateAlumno);
        edtDomicilio = findViewById(R.id.edtxDomicilio);
        edtBarrio = findViewById(R.id.edtxBarrio);
        edtCarrera = findViewById(R.id.edxtCarrera);
        edtLegajo = findViewById(R.id.edtxLegajo);
        edtFacultad = findViewById(R.id.edtxFacultad);
        edtCiudad = findViewById(R.id.edxtCiudad);
        edtProvincia = findViewById(R.id.edtxProvincia);
        edtPais = findViewById(R.id.txtPais);
        edtAnioIngeso = findViewById(R.id.edxtAnoIng);
        edtCantMaterias = findViewById(R.id.edtxCantMat);
        edtFacebook = findViewById(R.id.txtFacebook);
        edtInstagram = findViewById(R.id.txtInstagram);
        edtMail = findViewById(R.id.txtMail);
        edtTelefono = findViewById(R.id.edxtTel);
        edtActividadFisica = findViewById(R.id.edtxPreg2);
        edtLugarActividad = findViewById(R.id.edtxPreg3);
        edtObjetivo = findViewById(R.id.edtxPreg4);
        btnBack = findViewById(R.id.imgFlecha);

        edtPeso = findViewById(R.id.edxtPeso);
        edtAltura = findViewById(R.id.edtxAltura);

        checkwhatsApp = findViewById(R.id.chbxWhats);
        checksi = findViewById(R.id.chbxSi);
        checkno = findViewById(R.id.chbxNo);
        checkcont = findViewById(R.id.chbxContin);
        checkbaja = findViewById(R.id.chbxBaja);
        checkmedia = findViewById(R.id.chbxMedia);

        latActividad = findViewById(R.id.disablePreg);
        btnRegistrar = findViewById(R.id.btnregister);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregister:
                register();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.chbxBaja:
                checkmedia.setChecked(false);
                checkcont.setChecked(false);
                checkbaja.setChecked(true);
                intensidad = 1;
                break;
            case R.id.chbxContin:
                checkmedia.setChecked(false);
                checkcont.setChecked(true);
                checkbaja.setChecked(false);
                intensidad = 2;
                break;
            case R.id.chbxMedia:
                checkmedia.setChecked(true);
                checkcont.setChecked(false);
                checkbaja.setChecked(false);
                intensidad = 3;
                break;
            case R.id.chbxNo:
                checkno.setChecked(true);
                checksi.setChecked(false);
                isActividad = false;
                latActividad.setVisibility(View.GONE);
                edtActividadFisica.setVisibility(View.GONE);
                break;
            case R.id.chbxSi:
                checksi.setChecked(true);
                checkno.setChecked(false);
                isActividad = true;
                latActividad.setVisibility(View.VISIBLE);
                edtActividadFisica.setVisibility(View.VISIBLE);
                break;
            case R.id.chbxWhats:
                if (isWsp) {
                    isWsp = false;
                    checkwhatsApp.setChecked(isWsp);
                } else {
                    isWsp = true;
                    checkwhatsApp.setChecked(isWsp);
                }
                break;
        }
    }

    private void register() {
        String nom = edtNombre.getText().toString().trim();
        String ape = edtApellido.getText().toString().trim();
        String doc = edtDni.getText().toString().trim();
        String eda = edtEdad.getText().toString().trim();
        String fecha = edtFechaNac.getText().toString().trim();
        String dom = edtDomicilio.getText().toString().trim();
        String barr = edtBarrio.getText().toString().trim();
        String local = edtCiudad.getText().toString().trim();
        String prov = edtProvincia.getText().toString().trim();
        String pai = edtPais.getText().toString().trim();
        String carr = edtCarrera.getText().toString().trim();
        String leg = edtLegajo.getText().toString().trim();
        String fac = edtFacultad.getText().toString().trim();
        String anioIn = edtAnioIngeso.getText().toString().trim();
        String cantMa = edtCantMaterias.getText().toString().trim();
        String facebook = edtFacebook.getText().toString().trim();
        String inst = edtInstagram.getText().toString().trim();
        String email = edtMail.getText().toString().trim();
        String telef = edtTelefono.getText().toString().trim();
        peso = edtPeso.getText().toString().trim();
        altura = edtAltura.getText().toString().trim();
        String cuales = edtActividadFisica.getText().toString().trim();
        String lug = edtLugarActividad.getText().toString().trim();
        String objetivo = edtObjetivo.getText().toString().trim();

        Validador validador = new Validador(getApplicationContext());

        if (validador.validarDNI(edtDni) && validador.validarNombres(edtNombre)
                && validador.validarNombres(edtApellido) && validador.validarNumero(edtEdad)
                && validador.validarFecha(edtFechaNac)
                && validador.validarTexto(edtDomicilio) && validador.validarTexto(edtBarrio)
                && validador.validarTexto(edtCiudad) && validador.validarTexto(edtProvincia)
                && validador.validarTexto(edtPais) && validador.validarTexto(edtCarrera)
                && validador.validarLegajo(edtLegajo) && validador.validarNumero(edtAnioIngeso)
                && validador.validarNumero(edtCantMaterias)
                && validador.validarTexto(edtFacultad) && validador.validarMail(edtMail)
                && validador.validarTelefono(edtTelefono) && validador.validarTexto(edtObjetivo)
        ) {
            if (facebook.equals("")) {
                facebook = " ";
            }
            if (inst.equals("")) {
                inst = " ";
            }
            if (validador.validadPeso(peso) && validador.validadAltura(altura)) {

                HashMap<String, String> datos = new HashMap<>();
                datos.put("iu", "");
                datos.put("id", String.valueOf(mDeporte.getIdDep()));
                datos.put("cm", cantMa);
                datos.put("fa", facebook);
                datos.put("in", inst);
                datos.put("ws", String.valueOf(isWsp ? 1 : 2));
                datos.put("ob", objetivo);
                datos.put("pe", peso);
                datos.put("al", altura);
                datos.put("cu", cuales.equals("") ? " " : cuales);
                datos.put("int", String.valueOf(intensidad));
                datos.put("lu", lug.equals("") ? " ": lug);
                if (isActividad) {
                    if (validador.validarTexto(edtLugarActividad) && validador.validarTexto(edtActividadFisica)) {
                        sendServer(datos);

                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.noActividadCompleta));
                    }
                } else {
                    sendServer(datos);
                }


            } else {
                Utils.showToast(getApplicationContext(), getString(R.string.noAlturaPeso));
            }

        } else
            Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
    }

    private void sendServer(final HashMap<String, String> datos) {
        String URL = Utils.URL_DEPORTE_INSCRIPCION;
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        final int id = preferenceManager.getValueInt(Utils.MY_ID);
        final String token = preferenceManager.getValueString(Utils.TOKEN);
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
                datos.put("key", token);
                datos.put("idU", String.valueOf(id));
                datos.put("iu", String.valueOf(id));
                return datos;
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
                    finish();
                    Utils.showToast(getApplicationContext(), getString(R.string.inscripcionExitosa));
                    break;
                case 2:
                    //Error
                    Utils.showToast(getApplicationContext(), getString(R.string.inscripcionErronea));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.inscripcionYaRegistrada));
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
        }
    }
}
