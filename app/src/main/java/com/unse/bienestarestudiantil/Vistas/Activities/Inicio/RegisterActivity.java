package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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
import com.unse.bienestarestudiantil.Vistas.Adaptadores.CategoriasAdapter;
import com.unse.bienestarestudiantil.Vistas.BarcodeActivity;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoProcesamiento;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.unse.bienestarestudiantil.Herramientas.Utils.GET_FROM_DNI;
import static com.unse.bienestarestudiantil.Herramientas.Utils.GET_FROM_GALLERY;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText date, edtNombre, edtApellido, edtDNI, edtSexo, edtMail, edtContra, edtContraConf,
            edtProfesionProf, edtAnioIngresoProf, edtProfesionEgre, edtAnioIngresoAlu, edtAnioEgresoEgre,
            edtDomicilio, edtProvincia, edtTelefono, edtPais, edtLocalidad, edtLegajoAlu, edtBarrio;
    Button mRegister;//, mProfesor, mAlumno, mNoDocente, mEgresado, mParticular;
    ImageButton mScanner;
    ImageView btnBack;
    Bitmap mBitmap = null;
    LinearLayout mLLProfesor, mLLAlumno, mLLEgresado;
    CircleImageView imgUserRegister;
    RecyclerView recyclerTipoUsuario;
    CategoriasAdapter adapterCategorias;
    RecyclerView.LayoutManager mManager;
    ArrayList<Categoria> mList;
    DialogoProcesamiento dialog;
    Spinner spinner, spinner2;
    String[] facultad = {"FAyA", "FCEyT", "FCF", "FCM", "FHCSyS"};
    String[] faya = {"Ingeniería Agronómica", "Ingeniería en Alimentos", "Licenciatura en Biotecnología",
            "Licenciatura en Química", "Profesorado en Química", "Tecnicatura en Apicultura"};
    String[] fceyt = {"Ingeniería Civil", "Ingeniería Electromecánica", "Ingeniería Electrónica",
            "Ingeniería Eléctrica", "Ingeniería en Agrimensura", "Ingeniería Hidráulica",
            "Ingeniería Industrial", "Ingeniería Vial", "Licenciatura en Hidrología Subterránea",
            "Licenciatura en Matemática", "Licenciatura en Sistemas de Información",
            "Profesorado en Física", "Profesorado en Informática", "Profesorado en Matemática",
            "Programador Universitario en Informática", "Tecnicatura Universitaria Vial",
            "Tecnicatura Universitaria en Construcciones",
            "Tecnicatura Universitaria en Hidrología Subterránea",
            "Tecnicatura Universitaria en Organización y Control de la Producción"};
    String[] fcf = {"Ingeniería Forestal", "Ingeniería en Industrias Forestales",
            "Licenciatura en Ecología y Conservación del Ambiente",
            "Tecnicatura Universitaria Fitosanitarista",
            "Tecnicatura Universitaria en Viveros y Plantaciones Forestales",
            "Tecnicatura Universitaria en Aserraderos y Carpintería Industrial"};

    String[] fcm = {"Medicina"};

    String[] fhcys = {"Licenciatura en Administración", "Contador Público Nacional",
            "Licenciatura en Letras", "Licenciatura en Sociología", "Licenciatura en Enfermería",
            "Licenciatura en Educación para la Salud", "Licenciatura en Obstetricia",
            "Licenciatura en Filosofía", "Licenciatura en Trabajo Social",
            "Licenciatura en Periodismo", "Profesorado en Educación para la Salud",
            "Tecnicatura Sup. Adm. y Gestión Universitaria",
            "Tecnicatura en Educación Intercultural Bilingue"};

    private int TIPO_USER = 1;
    boolean doubleBackToExitPressedOnce = false;
    boolean isTODNI = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();

    }

    private void loadData() {
        mLLProfesor.setVisibility(View.GONE);
        mLLEgresado.setVisibility(View.GONE);
        mLLAlumno.setVisibility(View.GONE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facultad);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, faya);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        mList = new ArrayList<>();
        mList.add(new Categoria(1, "Alumno"));
        mList.add(new Categoria(2, "Profesor"));
        mList.add(new Categoria(3, "NoDocente"));
        mList.add(new Categoria(4, "Egresado"));
        mList.add(new Categoria(5, "Particular"));
        mList.get(0).setEstado(true);
        mLLAlumno.setVisibility(View.VISIBLE);

        mManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerTipoUsuario.setHasFixedSize(true);
        recyclerTipoUsuario.setLayoutManager(mManager);
        adapterCategorias = new CategoriasAdapter(mList, getApplicationContext());
        recyclerTipoUsuario.setAdapter(adapterCategorias);

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
                        mLLEgresado.setVisibility(View.GONE);
                        mLLProfesor.setVisibility(View.GONE);
                        mLLAlumno.setVisibility(View.GONE);
                    case 3:
                        mLLEgresado.setVisibility(View.GONE);
                        mLLProfesor.setVisibility(View.GONE);
                        mLLAlumno.setVisibility(View.GONE);
                        break;
                }
                TIPO_USER = (int) id;
                resetEstado();
                mList.get(position).setEstado(true);
                adapterCategorias.notifyDataSetChanged();
            }
        });

    }

    private void resetEstado() {
        for (Categoria c : mList) {
            c.setEstado(false);
        }
    }

    private void loadListener() {
        date.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        imgUserRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        mScanner.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                spinner.setSelection(position);
                switch (position) {
                    case 0:
                        //FAyA
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, faya);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter2);
                        break;
                    case 1:
                        //FCEyT
                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fceyt);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter3);
                        break;
                    case 2:
                        //FCF
                        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fcf);
                        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter4);
                        break;
                    case 3:
                        //FCM
                        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fcm);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter5);
                        break;
                    case 4:
                        //FHyCS
                        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fhcys);
                        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter6);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    private void loadViews() {
        date = findViewById(R.id.txtdate);
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
        imgUserRegister = findViewById(R.id.imgUserRegister);
        btnBack = findViewById(R.id.btnBack);
        mScanner = findViewById(R.id.btnScanner);
        mLLAlumno = findViewById(R.id.linlayAlumno);
        mLLEgresado = findViewById(R.id.linlayEgresado);
        mLLProfesor = findViewById(R.id.linlayProfesor);
        spinner = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        edtProvincia = findViewById(R.id.edtProvincia);
        edtTelefono = findViewById(R.id.edtCelu);
        edtLocalidad = findViewById(R.id.edtLocalidad);
        edtDomicilio = findViewById(R.id.edtDomicilio);
        edtLegajoAlu = findViewById(R.id.edtLegajo);
        edtPais = findViewById(R.id.edtPais);
        edtBarrio = findViewById(R.id.edtBarrio);
        recyclerTipoUsuario = findViewById(R.id.recycler);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtdate:
                showDateDialog();
                break;
            case R.id.btnregister:
                login();
                break;
            case R.id.imgUserRegister:
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnScanner:
                startActivityForResult(new Intent(this,
                        BarcodeActivity.class), GET_FROM_DNI);
                break;

        }
    }

    private void login() {
        Validador validador = new Validador();

        String fecha = date.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String dni = edtDNI.getText().toString().trim();
        String sexo = edtSexo.getText().toString().trim();
        String mail = edtMail.getText().toString().trim();
        String pass = edtContra.getText().toString().trim();
        String passConf = edtContraConf.getText().toString().trim();
        String profesion = edtProfesionProf.getText().toString().trim();
        String anioIngreso = edtAnioIngresoProf.getText().toString().trim();
        String faculta = facultad[spinner.getSelectedItemPosition()].trim();
        String carrera = getCarrera(spinner.getSelectedItemPosition()).trim();
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

        //Comprobacion que no sean vacios
        if (!validador.noVacio(fecha, nombre, apellido, dni, sexo, mail, pass, passConf
                , domicilio, telefono, pais, provincia, localidad, barrio)) {

            //Comprobacion del tipo d edatos
            if (validador.validarNombre(nombre) && validador.validarNombre(apellido) && validador.validarDNI(dni)
                    && validador.validarMail(mail) && sexo.length() == 1 && validador.validarNumero(telefono) &&
                    !validador.validarNombres(pais, provincia, localidad)) {

                //Comprobacion de tamaños
                if (validador.lengthMore(domicilio) && validador.lengthMore(pais) && validador.lengthMore(provincia) && validador.lengthMore(localidad)
                        && validador.lengthMore(barrio)) {

                    //Comprobacion de contraseña
                    if (pass.equals(passConf)) {

                        if (TIPO_USER == 1) {
                            if (!validador.noVacio(faculta, carrera, anioIngreso2)) {
                                sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                        domicilio, barrio, telefono, sexo, Utils.generateToken(dni, nombre, apellido), mail, 1, carrera, faculta, anioIngreso2
                                        , legajo, Utils.crypt(pass), null, null));

                            } else {
                                Utils.showToast(getApplicationContext(), "Por favor, complete los campos");
                            }
                        } else if (TIPO_USER == 2) {
                            if (!validador.noVacio(profesion, anioIngreso)) {
                                sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                        domicilio, barrio, telefono, sexo, Utils.generateToken(dni, nombre, apellido), mail,
                                        2, null, null, anioIngreso, null, Utils.crypt(pass),
                                        profesion, null));
                            } else {
                                Utils.showToast(getApplicationContext(), "Por favor, complete los campos");
                            }
                        } else if (TIPO_USER == 4) {
                            if (!validador.noVacio(profesion2, anioEgreso)) {
                                sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio, barrio,
                                        telefono, sexo, Utils.generateToken(dni, nombre, apellido), mail, 4,
                                        null, null, null, null, Utils.crypt(pass),
                                        profesion2, anioEgreso));

                            } else {
                                Utils.showToast(getApplicationContext(), "Por favor, complete los campos");
                            }
                        } else {
                            //NoDocente y Particular
                            sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio,
                                    barrio, telefono, sexo, Utils.generateToken(dni, nombre, apellido), mail, TIPO_USER,
                                    null, null, null, null, Utils.crypt(pass), null, null));
                        }


                    } else {
                        Utils.showToast(getApplicationContext(), "Las contraseñas no son identicas");
                    }

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

    public String processString(String dni, String nombre, String apellido, String
            fecha, String pais,
                                String provincia, String localidad, String domicilio, String barrio, String telefono, String
                                        sexo, String key, String mail,
                                int tipo, String carrera, String facultad, String anioIng, String legajo, String
                                        contrasenia, String profesion, String anioEgreso) {
        String resp = "";
        if (tipo == 1) {
            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
                    "&dom=%s&sex=%s&key=%s&car=%s&fac=%s&anio=%s&leg=%s&pass=%s&fecha=%s" +
                    "&tipo=%s&mail=%s&tel=%s&barr=%s&fechaR=%s";
            resp = String.format(data, dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio, sexo, key, carrera, facultad,
                    anioIng, legajo, contrasenia, Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis())),
                    "1", mail, telefono, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));

        } else if (tipo == 2) {

            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
                    "&dom=%s&sex=%s&key=%s&pass=%s&fecha=%s&tipo=%s&mail=%s&tel=%s" +
                    "&prof=%s&fechain=%s&barr=%s&fechaR=%s";
            resp = String.format(data, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, key, contrasenia, Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis())), tipo, mail, telefono,
                    profesion, anioIng, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));

        } else if (tipo == 4) {
            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
                    "&dom=%s&sex=%s&key=%s&pass=%s&fecha=%s&tipo=%s&mail=%s&tel=%s" +
                    "&prof=%s&fechaeg=%s&barr=%s&fechaR=%s";
            resp = String.format(data, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, key, contrasenia, Utils.getFechaName(new Date(System.currentTimeMillis())),
                    tipo, mail, telefono, profesion, anioEgreso, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));
        } else {
            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
                    "&dom=%s&sex=%s&key=%s&pass=%s&fecha=%s&tipo=%s&mail=%s&tel=%s&barr=%s&fechaR=%s";
            resp = String.format(data, dni, nombre, apellido, fecha, pais, provincia, localidad,
                    domicilio, sexo, key, contrasenia, Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis())),
                    tipo, mail, telefono, barrio, Utils.getFechaName(new Date(System.currentTimeMillis())));
        }
        return resp;
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Utils.showToast(getApplicationContext(), "Presiona de nuevo para salir");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void sendServer(String data) {


        String URL = Utils.URL + data;
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
        StringRequest requestImage = new StringRequest(Request.Method.POST, Utils.URL_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int re = response.length();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parm = new HashMap<>();
                parm.put("id", edtDNI.getText().toString());
                String info = convertImage(mBitmap != null ? mBitmap : ((BitmapDrawable) imgUserRegister.getDrawable()).getBitmap());
                parm.put("img", info);
                return parm;
            }
        };
        //Abro dialogo para congelar pantalla
        dialog = new DialogoProcesamiento();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "dialog_process");
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(requestImage);
    }

    private String getCarrera(int selectedItemPosition) {
        switch (selectedItemPosition) {
            case 0:
                return faya[spinner2.getSelectedItemPosition()];
            case 1:
                return fceyt[spinner2.getSelectedItemPosition()];
            case 2:
                return fcf[spinner2.getSelectedItemPosition()];
            case 3:
                return fcm[spinner2.getSelectedItemPosition()];
            case 4:
                return fhcys[spinner2.getSelectedItemPosition()];
        }
        return "";
    }

    private void procesarRespuesta(String response) {
        try {
            dialog.dismiss();
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case 1:
                    //Exito
                    Utils.showToast(getApplicationContext(), "Registro exitoso!");
                    finish();
                    //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Utils.showToast(getApplicationContext(), "Inicia sesión para confirmar");
                    break;
                case 2:
                    //Error 1
                    Utils.showToast(getApplicationContext(), "Error interno al intentar registrarte");
                    break;
                case 3:
                    //Ya existe
                    Utils.showToast(getApplicationContext(), "Ya existe un usuario con el DNI ingresado, por favor verifica");
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

    private void showDateDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "-" + (month + 1) + "-" + day;
                date.setText(selectedDate);
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
                    Utils.showToast(getApplicationContext(), "Error al obtener datos desde el scanner");
                }
                break;
            case GET_FROM_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    //Bitmap bitmap = null;
                    try {
                        mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        imgUserRegister.setImageBitmap(Bitmap.createScaledBitmap(mBitmap, 500, 500, false));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Utils.showToast(getApplicationContext(), "Debe elegir una foto desde su galería");
                }
                break;
        }
    }

    private String convertImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] img = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(img, Base64.DEFAULT);

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
            date.setText(result);

            date.setEnabled(false);
            edtDNI.setEnabled(false);
            edtApellido.setEnabled(false);
            edtSexo.setEnabled(false);
            edtNombre.setEnabled(false);
            isTODNI = !isTODNI;
        }
    }

}