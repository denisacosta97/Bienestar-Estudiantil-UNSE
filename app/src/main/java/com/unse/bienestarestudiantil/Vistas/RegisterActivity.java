package com.unse.bienestarestudiantil.Vistas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.LoginActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.MainActivity;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText date, edtNombre, edtApellido, edtDNI, edtSexo, edtMail, edtContra, edtContraConf,
        edtProfesion, edtAnioIngreso, edtProfesion2,edtAnioIngreso2,edtAnioEgreso;
    Button mRegister, mProfesor, mAlumno, mNoDocente, mEgresado, mParticular, edt;
    ImageButton mScanner;
    ImageView btnBack;
    LinearLayout mLLProfesor, mLLAlumno, mLLEgresado;
    String jeje;
    CircleImageView imgUserRegister;
    EditText nombre, apellido, dni, sexo, fechaNac, mail, celular, domicilio, barrio, localidad, provincia, pais,
    pass, repass, profProfesor, anioIngProf, legajo, anioIngAlu, profEgre, anioEgre;
    ArrayList datosUser;
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
            "Licenciatura en Educación para la Salud", "Licenciatura en Obtetricia",
            "Licenciatura en Filosofía", "Licenciatura en Trabajo Social",
            "Licenciatura en Periodismo", "Profesorado en Educación para la Salud",
            "Tecnicatura Sup. Adm. y Gestión Universitaria",
            "Tecnicatura en Educación Intercultural Bilingue"};


    private ZXingScannerView mScannerView;
    private static final int GET_FROM_GALLERY = 3;
    private int TIPO_USER = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Utils.setFont(getApplicationContext(), (ViewGroup)findViewById(android.R.id.content), Utils.MONSERRAT);

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
    }

    private void loadListener() {
        date.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        imgUserRegister.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        mScanner.setOnClickListener(this);
        mProfesor.setOnClickListener(this);
        mAlumno.setOnClickListener(this);
        mNoDocente.setOnClickListener(this);
        mEgresado.setOnClickListener(this);
        mParticular.setOnClickListener(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                spinner.setSelection(position);
                Spinner spinner2 = findViewById(R.id.spinner2);
                switch (position) {
                    case 0:
                        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, faya);
                        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter2);
                        break;
                    case 1:
                        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fceyt);
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter3);
                        break;
                    case 2:
                        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fcf);
                        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter4);
                        break;
                    case 3:
                        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fcm);
                        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(dataAdapter5);
                        break;
                    case 4:
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
        date = findViewById(R.id.txtDate);
        edtNombre = findViewById(R.id.txtNombre);
        edtApellido = findViewById(R.id.txtApellido);
        edtDNI = findViewById(R.id.txtDNI);
        edtSexo = findViewById(R.id.txtSexo);
        edtMail = findViewById(R.id.txtMail);
        edtContra = findViewById(R.id.txtPass);
        edtContraConf = findViewById(R.id.txtRepass);
        edtProfesion = findViewById(R.id.txtProfesion1);
        edtAnioIngreso = findViewById(R.id.txtAnioIngreso);
        edtAnioIngreso2 = findViewById(R.id.txtAnioIngreso2);
        edtProfesion2 = findViewById(R.id.txtProfesion2);
        edtAnioEgreso = findViewById(R.id.txtAnioEgreso);
        mRegister = findViewById(R.id.btnregister);
        imgUserRegister = findViewById(R.id.imgUserRegister);
        btnBack = findViewById(R.id.btnBack);
        mScanner = findViewById(R.id.btnScanner);
        mProfesor = findViewById(R.id.btnProfesor);
        mAlumno = findViewById(R.id.btnAlumno);
        mNoDocente = findViewById(R.id.btnNoDocente);
        mEgresado = findViewById(R.id.btnEgresado);
        mParticular = findViewById(R.id.btnParticular);
        mLLAlumno = findViewById(R.id.linlayAlumno);
        mLLEgresado = findViewById(R.id.linlayEgresado);
        mLLProfesor = findViewById(R.id.linlayProfesor);
        spinner = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtdate:
                showDateDialog();
                break;
            case R.id.btnregister:
                //Insert función registro DENIS gg
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                login();
                //finish();
                break;
            case R.id.imgUserRegister:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnScanner:
                startActivityForResult(new Intent(RegisterActivity.this, BarcodeActivity.class), 123);
                break;
            case R.id.btnProfesor:
                mLLProfesor.setVisibility(View.VISIBLE);
                mLLEgresado.setVisibility(View.GONE);
                mLLAlumno.setVisibility(View.GONE);
                TIPO_USER = 2;
                break;
            case R.id.btnAlumno:
                mLLAlumno.setVisibility(View.VISIBLE);
                mLLProfesor.setVisibility(View.GONE);
                mLLEgresado.setVisibility(View.GONE);
                TIPO_USER = 1;
                break;
            case R.id.btnEgresado:
                mLLEgresado.setVisibility(View.VISIBLE);
                mLLProfesor.setVisibility(View.GONE);
                mLLAlumno.setVisibility(View.GONE);
                TIPO_USER = 4;
                break;
            case R.id.btnParticular:
                mLLEgresado.setVisibility(View.GONE);
                mLLProfesor.setVisibility(View.GONE);
                mLLAlumno.setVisibility(View.GONE);
                TIPO_USER = 5;
            case R.id.btnNoDocente:
                mLLEgresado.setVisibility(View.GONE);
                mLLProfesor.setVisibility(View.GONE);
                mLLAlumno.setVisibility(View.GONE);
                TIPO_USER = 3;
                break;

        }
    }

    private void login() {
        Validador validador = new Validador();

        String fecha = date.getText().toString();
        String nombre = edtNombre.getText().toString();
        String apellido = edtApellido.getText().toString();
        String dni = edtDNI.getText().toString();
        String sexo = edtSexo.getText().toString();
        String mail = edtMail.getText().toString();
        String pass = edtContra.getText().toString();
        String passConf = edtContraConf.getText().toString();
        String profesion = edtProfesion.getText().toString();
        String anioIngreso = edtAnioIngreso.getText().toString();
        String faculta = facultad[spinner.getSelectedItemPosition()];
        String carrera = getCarrera(spinner.getSelectedItemPosition());
        String anioIngreso2 = edtAnioIngreso2.getText().toString();
        String profesion2 = edtProfesion2.getText().toString();
        String anioEgreso = edtAnioEgreso.getText().toString();

        if (validador.noVacio(fecha) && validador.validarNombres(nombre) && validador.validarNombres(apellido) && validador.validarDNI(dni)
        && validador.validarMail(mail) && validador.noVacio(sexo) && validador.noVacio(pass, passConf)){
            if(pass.equals(passConf)){

                if(TIPO_USER == 1){
                     if(validador.noVacio(faculta, carrera, anioIngreso2)){
                         sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                 domicilio, telefono, sexo, Utils.generateToken(dni, nombre, apellido), mail, 1, carrera, facultad, anioIngreso2
                                 , legajo, Utils.crypt(pass)););

                     }else{
                         Utils.showToast(getApplicationContext(),"Por favor, complete los campos");
                     }
                }else if(TIPO_USER == 2){
                    if(validador.noVacio(profesion, anioIngreso)){
                        sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad,
                                domicilio, telefono, sexo, Utils.generateToken(dni, nombre, apellido),mail,
                                2, null,null, anioIngreso, null, Utils.crypt(pass),
                                profesion, null););
                    }else{
                        Utils.showToast(getApplicationContext(),"Por favor, complete los campos");
                    }
                }else if(TIPO_USER == 4){
                    if(validador.noVacio(profesion2, anioEgreso)){
                        sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio,
                                telefono, sexo, Utils.generateToken(dni, nombre, apellido), mail, 4,
                                null, null, null, null, Utils.crypt(pass),
                                profesion2, anioEgreso););

                    }else{
                        Utils.showToast(getApplicationContext(),"Por favor, complete los campos");
                    }
                }else{
                    //NoDocente y Particular
                    sendServer(processString(dni, nombre, apellido, fecha, pais, provincia, localidad, domicilio,
                            telefono, sexo, Utils.generateToken(dni, nombre, apellido),mail, TIPO_USER,
                            null, null, null, null, Utils.crypt(pass), null, null));
                }

            }else{
                Utils.showToast(getApplicationContext(),"Las contraseñas no son identicas");
            }

        }else{
            Utils.showToast(getApplicationContext(),"Por favor, complete todos los datos");
        }

    }

    public String processString(String dni, String nombre, String apellido, String fecha, String pais,
                                String provincia, String localidad, String domicilio, String telefono, String sexo, String key, String mail,
                                int tipo, String carrera, String facultad, String anioIng, String legajo, String contrasenia, String profesion, String anioEgreso){
        String resp = "";
        if(tipo == 1){
              String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s" +
                      "&dom=%s&sex=%s&key=%s&car=%s&fac=%s&anio=%s&leg=%s&pass=%s&fecha=%s" +
                      "&tipo=%s&mail=%s&tel=%s";
              resp = String.format(data,dni,nombre,apellido, fecha,pais, provincia, localidad, domicilio, sexo, key, carrera, facultad,
                      anioIng, legajo, contrasenia,Utils.getFechaName(new Date(System.currentTimeMillis())),"1", mail, telefono);

        }else if(tipo == 2){

            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s"+
                    "&dom=%s&sex=%s&key=%s&pass=%s&fecha=%s&tipo=%s&mail=%s&tel=%s" +
                    "&prof=%s&fechain=%s";
            resp = String.format(data, dni, nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, key, contrasenia, Utils.getFechaName(new Date(System.currentTimeMillis())), tipo, mail, telefono,
                    profesion, anioIng);

        }else if(tipo == 4){
            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s"+
                    "&dom=%s&sex=%s&key=%s&pass=%s&fecha=%s&tipo=%s&mail=%s&tel=%s" +
                    "&prof=%s&fechaeg=%s";
            resp = String.format(data, dni,nombre, apellido, fecha, pais, provincia,
                    localidad, domicilio, sexo, key, contrasenia, Utils.getFechaName(new Date(System.currentTimeMillis())),
                    tipo,mail, telefono,profesion, anioEgreso);
        }else{
            String data = "?id=%s&nom=%s&ape=%s&fechan=%s&pais=%s&prov=%s&local=%s"+
                    "&dom=%s&sex=%s&key=%s&pass=%s&fecha=%s&tipo=%s&mail=%s&tel=%s";
            resp = String.format(data, dni, nombre,apellido, fecha, pais, provincia, localidad,
                    domicilio, sexo, key, contrasenia, Utils.getFechaName(new Date(System.currentTimeMillis())),
                    tipo, mail, telefono);
        }
        return resp;
    }

    public void sendServer(String data){

        String URL = Utils.URL+data;
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        })
                ;
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private String getCarrera(int selectedItemPosition) {
        switch (selectedItemPosition){
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
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado){
                case 1:
                    //Exito
                    break;
                case 2:
                    //Error 1
                    break;
                case 3:
                    //Ya existe
                    break;
                case 4:
                    //Error interno 2
                    break;
                case 10:
                    //No autorizado
                    break;
                case 11:
                    //Autorizador invalido
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
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

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imgUserRegister.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
