package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

public class RegistroDeporteActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    EditText edtNombreDep, nombre, apellido, dni, edad, fechaNac, domicilio, barrio, carrera, legajo,
    facultad, ciudad, provincia, pais, anioIng, cantMatAprob, face, insta, mail, tel, actvFis,
            lugarActiv, objet, mPeso, mAltura ;
    CheckBox checkwhatsApp, checksi, checkno, checkcont, checkmedia, checkbaja;
    LinearLayout actividad;
    ImageView btnBack;
    Button btnRegistrar;
    boolean isWsp = false, isActividad = false;
    int intensidad = 1;
    String peso, altura;
    DialogoProcesamiento dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_deporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null){
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
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
        actividad.setVisibility(View.GONE);

        UsuarioViewModel usuarioViewModel = new UsuarioViewModel(getApplicationContext());
        int idLocal = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);

        Usuario usuario = usuarioViewModel.getById(idLocal);
        edtNombreDep.setText(mDeporte.getName());
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellido());
        dni.setText(String.valueOf(usuario.getIdUsuario()));
        edad.setText(String.valueOf(Utils.getEdad(Utils.getFechaDate(usuario.getFechaNac()))));
        fechaNac.setText(Utils.getFechaNameWithinHour(Utils.getFechaDate(usuario.getFechaNac())));
        domicilio.setText(usuario.getDomicilio());
        barrio.setText(usuario.getBarrio());
        ciudad.setText(usuario.getLocalidad());
        provincia.setText(usuario.getProvincia());
        pais.setText(usuario.getPais());
        mail.setText(usuario.getMail());
        tel.setText(String.valueOf(usuario.getTelefono()));

        if (usuario.getTipoUsuario() == 1){
            AlumnoViewModel alumnoViewModel = new AlumnoViewModel(getApplicationContext());
            Alumno alumno = alumnoViewModel.getById(usuario.getIdUsuario());
            facultad.setText(alumno.getFacultad());
            carrera.setText(alumno.getCarrera());
            legajo.setText(alumno.getLegajo());
            anioIng.setText(alumno.getAnio());
        }else{
            Utils.showToast(getApplicationContext(), "No eres alumno, se rellena con campos por defecto");
            carrera.setText("N/A");
            legajo.setText("N/A");
            facultad.setText("N/A");
            anioIng.setText("N/A");
            cantMatAprob.setText("0");
        }


    }

    private void loadViews() {
        edtNombreDep = findViewById(R.id.edtDeporte);
        nombre = findViewById(R.id.edtNombre);
        apellido = findViewById(R.id.edtApellido);
        dni = findViewById(R.id.edxtDni);
        edad = findViewById(R.id.edtxEdad);
        fechaNac = findViewById(R.id.txtDateAlumno);
        domicilio = findViewById(R.id.edtxDomicilio);
        barrio = findViewById(R.id.edtxBarrio);
        carrera = findViewById(R.id.edxtCarrera);
        legajo = findViewById(R.id.edtxLegajo);
        facultad = findViewById(R.id.edtxFacultad);
        ciudad = findViewById(R.id.edxtCiudad);
        provincia = findViewById(R.id.edtxProvincia);
        pais = findViewById(R.id.txtPais);
        anioIng = findViewById(R.id.edxtAnoIng);
        cantMatAprob = findViewById(R.id.edtxCantMat);
        face = findViewById(R.id.txtFacebook);
        insta = findViewById(R.id.txtInstagram);
        mail = findViewById(R.id.txtMail);
        tel = findViewById(R.id.edxtTel);
        actvFis = findViewById(R.id.edtxPreg2);
        lugarActiv = findViewById(R.id.edtxPreg3);
        objet = findViewById(R.id.edtxPreg4);
        btnBack = findViewById(R.id.btnBack);

        mPeso = findViewById(R.id.edxtPeso);
        mAltura = findViewById(R.id.edtxAltura);

        checkwhatsApp = findViewById(R.id.chbxWhats);
        checksi = findViewById(R.id.chbxSi);
        checkno = findViewById(R.id.chbxNo);
        checkcont = findViewById(R.id.chbxContin);
        checkbaja = findViewById(R.id.chbxBaja);
        checkmedia = findViewById(R.id.chbxMedia);

        actividad = findViewById(R.id.disablePreg);
        btnRegistrar = findViewById(R.id.btnregister);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
                actividad.setVisibility(View.GONE);
                actvFis.setVisibility(View.GONE);
                break;
            case R.id.chbxSi:
                checksi.setChecked(true);
                checkno.setChecked(false);
                isActividad = true;
                actividad.setVisibility(View.VISIBLE);
                actvFis.setVisibility(View.VISIBLE);
                break;
            case R.id.chbxWhats:
                if (isWsp){
                    isWsp = false;
                    checkwhatsApp.setChecked(isWsp);
                }else{
                    isWsp = true;
                    checkwhatsApp.setChecked(isWsp);
                }
                break;
        }
    }

    private void register() {
        int idDeporte = mDeporte.getIdDep();
        String nom = nombre.getText().toString().trim();
        String ape = apellido.getText().toString().trim();
        String doc = dni.getText().toString().trim();
        String eda = edad.getText().toString().trim();
        String fecha = fechaNac.getText().toString().trim();
        String dom = domicilio.getText().toString().trim();
        String barr = barrio.getText().toString().trim();
        String local = ciudad.getText().toString().trim();
        String prov = provincia.getText().toString().trim();
        String pai = pais.getText().toString().trim();
        String carr = carrera.getText().toString().trim();
        String leg = legajo.getText().toString().trim();
        String fac = facultad.getText().toString().trim();
        String anioIn = anioIng.getText().toString().trim();
        String cantMa = cantMatAprob.getText().toString().trim();
        String facebook = face.getText().toString().trim();
        String inst = insta.getText().toString().trim();
        String email = mail.getText().toString().trim();
        String telef = tel.getText().toString().trim();
        peso = mPeso.getText().toString().trim();
        altura = mAltura.getText().toString().trim();
        String cuales = actvFis.getText().toString().trim();
        String lug = lugarActiv.getText().toString().trim();
        String objetivo = objet.getText().toString().trim();
        int idTemporada = getIntent().getIntExtra(Utils.DEPORTE_ID, -1);
        //is wsp, is actividad, is intensidad
        Validador validador = new Validador(getApplicationContext());

        if (!validador.noVacio(nom, ape, dom, barr, local, prov, pai, carr, leg, fac, peso, altura, anioIn, objetivo, facebook, inst, fecha)){
            if (validador.validarDNI(doc) && validador.validarNumero(eda) && validador.validarNumero(cantMa)
                && validador.validarNumero(telef)){
                if (validador.validarMail(email)){

                    String datos = "?idT=%s&idU=%s&cm=%s&fa=%s&ins=%s&wsp=%s&isw=%s&obj=%s&cual=%s&inte=%s&lug=%s&key=%s";

                    if (isActividad){
                        if (!validador.noVacio(cuales, lug)){
                            datos = String.format("?idT=%s&id=%s&cm=%s&fa=%s&ins=%s&wsp=%s&isw=%s&obj=%s&cual=%s&inte=%s&lug=%s&pes=%s&alt=%s&key=%s", idTemporada, doc, Integer.parseInt(cantMa), facebook, inst, telef,
                                    isWsp ? 1 : 2, objetivo, cuales, intensidad, lug, peso, altura, new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN));

                            sendServer(datos);
                        }else{
                            Utils.showToast(getApplicationContext(),"Debe indicar cuales actividades realizó");
                        }

                    }else{
                        datos = String.format("?idT=%s&id=%s&cm=%s&fa=%s&ins=%s&wsp=%s&isw=%s&obj=%s&cual=%s&inte=%s&lug=%s&pes=%s&alt=%s&key=%s", idTemporada, doc, Integer.parseInt(cantMa), facebook, inst, telef,
                                isWsp ? 1 : 2, objetivo, "N/A", -1, "N/A", peso, altura, new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN));

                        sendServer(datos);
                    }
                }else{
                    Utils.showToast(getApplicationContext(),"Mail inválido");
                }

            }else{
                Utils.showToast(getApplicationContext(), "Hay campos numéricos invalidos");
            }
        }else{
            Utils.showToast(getApplicationContext(), "Hay campos sin completar");
        }

    }


    private void sendServer(String datos) {
        String URL = Utils.URL_DEPORTE_INSCRIPCION+datos;

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
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    break;
                case 1:
                    //Exito
                   finish();
                   Utils.showToast(getApplicationContext(),getString(R.string.inscripcionExitosa));
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
                case 100:
                    //No autorizado
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), "Error desconocido, contacta al Administrador");
        }
    }
}
