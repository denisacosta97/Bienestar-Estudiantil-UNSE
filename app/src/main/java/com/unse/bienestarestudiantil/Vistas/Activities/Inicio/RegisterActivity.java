package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Categoria;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Perfil.UploadPictureActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.BarcodeActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.unse.bienestarestudiantil.Herramientas.Utils.GET_FROM_DNI;
import static com.unse.bienestarestudiantil.Herramientas.Utils.facultad;
import static com.unse.bienestarestudiantil.Herramientas.Utils.faya;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fceyt;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fcf;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fcm;
import static com.unse.bienestarestudiantil.Herramientas.Utils.fhcys;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFechaNac, edtNombre, edtApellido, edtDNI, edtSexo, edtMail, edtContra, edtContraConf,
            edtProfesionProf, edtAnioIngresoProf, edtProfesionEgre, edtAnioIngresoAlu, edtAnioEgresoEgre,
            edtDomicilio, edtProvincia, edtTelefono, edtPais, edtLocalidad, edtLegajoAlu, edtBarrio;
    Button mRegister;
    ImageButton mScanner;
    ImageView btnBack;
    LinearLayout mLLProfesor, mLLAlumno, mLLEgresado;
    RecyclerView recyclerTipoUsuario;
    CategoriasAdapter adapterCategorias;
    RecyclerView.LayoutManager mManager;
    ArrayList<Categoria> mList;
    DialogoProcesamiento dialog;
    Spinner spinnerFacultad, spinnerCarrera;
    HashMap<String, String> param = new HashMap<>();

    ArrayAdapter<String> facultadAdapter, carreraAdapter;

    private int tipoUsuario = 1, idDNI = 0, isFoto = 0, isData = 0;
    boolean dobleConfirmacion = false, isToDNI = false, isAdminMode = false;

    Timer timer;
    TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        isAdmin();

        loadViews();

        setToolbar();


        loadData();

        loadListener();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        DrawableCompat.setTint(btnBack.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
    }

    private void isAdmin() {
        if (getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false)) {
            isAdminMode = getIntent().getBooleanExtra(Utils.IS_ADMIN_MODE, false);
        }
    }

    private void loadViews() {
        edtFechaNac = findViewById(R.id.edtFecha);
        edtNombre = findViewById(R.id.edtNombre);
        edtApellido = findViewById(R.id.edtApellido);
        edtDNI = findViewById(R.id.edtDNI);
        edtSexo = findViewById(R.id.edtSexo);
        edtMail = findViewById(R.id.edtEmail);
        edtContra = findViewById(R.id.edtPass);
        edtContraConf = findViewById(R.id.edtRepass);
        edtProfesionProf = findViewById(R.id.edtProfesion1);
        edtAnioIngresoProf = findViewById(R.id.edtAnioIngProf);
        edtAnioIngresoAlu = findViewById(R.id.edtAnioIngrAlu);
        edtProfesionEgre = findViewById(R.id.edtProfesionEgre);
        edtAnioEgresoEgre = findViewById(R.id.edtAnioEgresoEgre);
        mRegister = findViewById(R.id.btnregister);
        btnBack = findViewById(R.id.imgFlecha);
        mScanner = findViewById(R.id.btnScanner);
        mLLAlumno = findViewById(R.id.linlayAlumno);
        mLLEgresado = findViewById(R.id.linlayEgresado);
        mLLProfesor = findViewById(R.id.linlayProfesor);
        spinnerFacultad = findViewById(R.id.spinner1);
        spinnerCarrera = findViewById(R.id.spinner2);
        edtProvincia = findViewById(R.id.edtProvincia);
        edtTelefono = findViewById(R.id.edtCelu);
        edtLocalidad = findViewById(R.id.edtLocalidad);
        edtDomicilio = findViewById(R.id.edtDomicilio);
        edtLegajoAlu = findViewById(R.id.edtLegajo);
        edtPais = findViewById(R.id.edtPais);
        edtBarrio = findViewById(R.id.edtBarrio);
        recyclerTipoUsuario = findViewById(R.id.recycler);
    }

    private void loadData() {
        facultadAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facultad);
        facultadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFacultad.setAdapter(facultadAdapter);
        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, faya);
        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarrera.setAdapter(carreraAdapter);

        mList = new ArrayList<>();
        mList.add(new Categoria(1, "Alumno"));
        mList.add(new Categoria(2, "Profesor"));
        mList.add(new Categoria(3, "NoDocente"));
        mList.add(new Categoria(4, "Egresado"));
        mList.add(new Categoria(5, "Particular"));
        mList.get(0).setEstado(true);
        mLLAlumno.setVisibility(View.VISIBLE);
        mLLProfesor.setVisibility(View.GONE);
        mLLEgresado.setVisibility(View.GONE);

        mManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTipoUsuario.setHasFixedSize(true);
        recyclerTipoUsuario.setLayoutManager(mManager);
        adapterCategorias = new CategoriasAdapter(mList, getApplicationContext());
        recyclerTipoUsuario.setAdapter(adapterCategorias);

    }

    private void resetEstado() {
        for (Categoria c : mList) {
            c.setEstado(false);
        }
    }

    private void loadListener() {
        edtFechaNac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtFechaNac.setError(null);
            }
        });
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(recyclerTipoUsuario);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                switch ((int) id) {
                    case 2:
                        mLLProfesor.setVisibility(View.VISIBLE);
                        mLLEgresado.setVisibility(View.GONE);
                        mLLAlumno.setVisibility(View.GONE);
                        break;
                    case 1:
                        mLLAlumno.setVisibility(View.VISIBLE);
                        mLLProfesor.setVisibility(View.GONE);
                        mLLEgresado.setVisibility(View.GONE);
                        break;
                    case 4:
                        mLLEgresado.setVisibility(View.VISIBLE);
                        mLLProfesor.setVisibility(View.GONE);
                        mLLAlumno.setVisibility(View.GONE);
                        break;
                    case 5:
                    case 3:
                        mLLEgresado.setVisibility(View.GONE);
                        mLLProfesor.setVisibility(View.GONE);
                        mLLAlumno.setVisibility(View.GONE);
                        break;
                }
                tipoUsuario = (int) id;
                resetEstado();
                mList.get(position).setEstado(true);
                adapterCategorias.notifyDataSetChanged();
            }
        });
        edtFechaNac.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        mScanner.setOnClickListener(this);
        spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                spinnerFacultad.setSelection(position);
                switch (position) {
                    case 0:
                        //FAyA
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, faya);
                        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 1:
                        //FCEyT
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, fceyt);
                        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 2:
                        //FCF
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, fcf);
                        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 3:
                        //FCM
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, fcm);
                        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(carreraAdapter);
                        break;
                    case 4:
                        //FHyCS
                        carreraAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fhcys);
                        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCarrera.setAdapter(carreraAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtFecha:
                selectFechaNac();
                break;
            case R.id.btnregister:
                register();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnScanner:
                startActivityForResult(new Intent(this, BarcodeActivity.class), GET_FROM_DNI);
                break;

        }
    }

    private void register() {
        Validador validador = new Validador(getApplicationContext());

        String fecha = edtFechaNac.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String dni = edtDNI.getText().toString().trim();
        String sexo = edtSexo.getText().toString().trim();
        String mail = edtMail.getText().toString().trim();
        String pass = edtContra.getText().toString().trim();
        String passConf = edtContraConf.getText().toString().trim();
        String profesion = edtProfesionProf.getText().toString().trim();
        String anioIngreso = edtAnioIngresoProf.getText().toString().trim();
        String faculta = facultad[spinnerFacultad.getSelectedItemPosition()].trim();
        String carrera = getCarrera(spinnerFacultad.getSelectedItemPosition()).trim();
        String anioIngreso2 = edtAnioIngresoAlu.getText().toString().trim();
        String profesion2 = edtProfesionEgre.getText().toString().trim();
        String anioEgreso = edtAnioEgresoEgre.getText().toString().trim();
        String domicilio = edtDomicilio.getText().toString().trim();
        String telefono = edtTelefono.getText().toString().trim();
        String pais = edtPais.getText().toString().trim();
        String provincia = edtProvincia.getText().toString().trim();
        String localidad = edtLocalidad.getText().toString().trim();
        String legajo = edtLegajoAlu.getText().toString().trim();
        String barrio = edtBarrio.getText().toString().trim();

        if (validador.validarDNI(edtDNI) && validador.validarTelefono(edtTelefono) && validador.validarMail(edtMail)
                && validador.validarFecha(edtFechaNac) && validador.validarContraseña(edtContra, edtContraConf)
                && validador.validarDomicilio(edtDomicilio)
                && !validador.validarNombresEdt(edtNombre, edtApellido, edtPais,
                edtProvincia, edtLocalidad, edtBarrio)) {
            idDNI = Integer.parseInt(dni);
            switch (tipoUsuario) {
                case Utils.TIPO_ALUMNO:
                    if (!validador.noVacio(faculta) && !validador.noVacio(carrera) &&
                            validador.validarAnio(edtAnioIngresoAlu) && validador.validarLegajo(edtLegajoAlu)) {
                        sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                domicilio, barrio, telefono, sexo, "u",
                                mail, 1, carrera, faculta, anioIngreso2
                                , legajo, pass, null, null));
                    }
                    break;
                case Utils.TIPO_PROFESOR:
                    if (validador.validarNombresEdt(edtProfesionProf) && validador.validarAnio(edtAnioIngresoProf)) {
                        sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                domicilio, barrio, telefono, sexo, "u", mail,
                                2, null, null, anioIngreso, null, pass,
                                profesion, null));
                    }
                    break;
                case Utils.TIPO_EGRESADO:
                    if (validador.validarNombresEdt(edtProfesionEgre) && validador.validarAnio(edtAnioEgresoEgre)) {
                        sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio, barrio,
                                telefono, sexo, "u", mail, 4,
                                null, null, null, null, pass,
                                profesion2, anioEgreso));
                    }
                    break;
                case Utils.TIPO_NODOCENTE:
                case Utils.TIPO_PARTICULAR:
                    sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio,
                            barrio, telefono, sexo, "u", mail, tipoUsuario,
                            null, null, null, null, pass,
                            null, null));
                    break;
            }

        } else Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));


    }

    public String processString(String dni, String nombre, String apellido, String fecha, String pais,
                                String provincia, String localidad, String domicilio, String barrio,
                                String telefono, String sexo, String key, String mail,
                                int tipo, String carrera, String facultad, String anioIng,
                                String legajo, String contrasenia, String profesion, String anioEgreso) {
        String resp = "";
        String fechaModifCreacion = Utils.getFechaName(new Date(System.currentTimeMillis()));
        //Falta pass y key
        if (tipo == 1) {
            resp = String.format(Utils.dataAlumno, dni, nombre, apellido, fecha, pais, provincia, localidad,
                    domicilio, sexo, carrera, facultad, anioIng, legajo, tipo, mail, telefono,
                    barrio, fechaModifCreacion, 0);
            param.put("idU", String.valueOf(dni));
            param.put("nom", nombre);
            param.put("ape", apellido);
            param.put("fechan", fecha);
            param.put("pais", pais);
            param.put("prov", provincia);
            param.put("local", localidad);
            param.put("dom", domicilio);
            param.put("sex", sexo);
            param.put("car", carrera);
            param.put("fac", facultad);
            param.put("anio", anioIng);
            param.put("leg", legajo);
            param.put("tipo", String.valueOf(tipo));
            param.put("mail", mail);
            param.put("tel", telefono);
            param.put("barr", barrio);
            param.put("idReg", "0");

        } else if (tipo == 2) {
            resp = String.format(Utils.dataProfesor, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, tipo, mail, telefono,
                    profesion, anioIng, barrio, fechaModifCreacion);

        } else if (tipo == 4) {
            resp = String.format(Utils.dataEgresado, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, tipo, mail, telefono,
                    profesion, anioEgreso, barrio, fechaModifCreacion);
        } else {
            resp = String.format(Utils.dataPartiNoDoc, dni, nombre, apellido, fecha, pais, provincia, localidad,
                    domicilio, sexo, tipo, mail, telefono, barrio, fecha);
        }
        param.put("pass", contrasenia);
        //resp = String.format("%s&key=%s&pass=%s", resp, key, contrasenia);
        return resp;
    }


    @Override
    public void onBackPressed() {
        if (dobleConfirmacion) {
            super.onBackPressed();
            return;
        }
        dobleConfirmacion = true;
        Utils.showToast(getApplicationContext(), getString(R.string.presionaDeNuevo));

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                dobleConfirmacion = false;
            }
        }, 2000);
    }

    public void sendServer(String data) {
        String URL = Utils.URL_USUARIO_INSERTAR;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                error.printStackTrace();
                String location = error.networkResponse.headers.get("Location");
                Utils.showLog("dff", location);
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                param.put("key","jeje");
                return param;
            }
        };
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private String getCarrera(int selectedItemPosition) {
        switch (selectedItemPosition) {
            case 0:
                return faya[spinnerCarrera.getSelectedItemPosition()];
            case 1:
                return fceyt[spinnerCarrera.getSelectedItemPosition()];
            case 2:
                return fcf[spinnerCarrera.getSelectedItemPosition()];
            case 3:
                return fcm[spinnerCarrera.getSelectedItemPosition()];
            case 4:
                return fhcys[spinnerCarrera.getSelectedItemPosition()];
        }
        return "";
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
                    //Aqui ir a foto de perfil
                    Intent intent = new Intent(getApplicationContext(), UploadPictureActivity.class);
                    intent.putExtra(Utils.MY_ID, idDNI);
                    intent.putExtra(Utils.IS_ADMIN_MODE, isAdminMode);
                    intent.putExtra(Utils.TIPO_REGISTRO, getIntent().getBooleanExtra(Utils.TIPO_REGISTRO, false));
                    finish();
                    startActivity(intent);
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioRegistrado));
                    break;
                case 2:
                    //Error 1
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioNoRegistro));
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.usuarioExiste));
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorCredencialToken));
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void selectFechaNac() {
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
                final String selectedDate = year + "-" + mes + "-" + dia;
                edtFechaNac.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_FROM_DNI:
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList<String> resultados = data.getStringArrayListExtra(Utils.BARCODE);
                    completarDatos(resultados);
                } else {
                    Utils.showToast(getApplicationContext(), getString(R.string.errorDNI));
                }
                break;
        }
    }

    private void completarDatos(ArrayList<String> resultados) {
        if (resultados.size() != 0) {
            edtApellido.setText(Utils.getStringCamel(resultados.get(0)));
            edtNombre.setText(Utils.getStringCamel(resultados.get(1)));
            edtSexo.setText(Utils.getStringCamel(resultados.get(2)));
            edtDNI.setText(resultados.get(3));
            String fecha = resultados.get(5);
            String result = "";
            Pattern numero = Pattern.compile("[0-9]+");
            Matcher matcher = numero.matcher(fecha);
            while (matcher.find()) {
                result = "-" + matcher.group(0) + result;
            }
            result = result.substring(1);
            edtFechaNac.setText(result);

            edtFechaNac.setEnabled(false);
            edtDNI.setEnabled(false);
            edtApellido.setEnabled(false);
            edtSexo.setEnabled(false);
            edtNombre.setEnabled(false);
            isToDNI = !isToDNI;
        }
    }

}
