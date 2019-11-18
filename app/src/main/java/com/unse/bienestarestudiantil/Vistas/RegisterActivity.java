package com.unse.bienestarestudiantil.Vistas;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.LoginActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.MainActivity;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText date;
    Button mRegister, mProfesor, mAlumno, mNoDocente, mEgresado, mParticular;
    ImageButton mScanner;
    ImageView btnBack;
    LinearLayout mLLProfesor, mLLAlumno, mLLEgresado;
    CircleImageView imgUserRegister;
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
            "Licenciatura en Filosofía","Licenciatura en Trabajo Social",
            "Licenciatura en Periodismo","Profesorado en Educación para la Salud",
            "Tecnicatura Sup. Adm. y Gestión Universitaria",
            "Tecnicatura en Educación Intercultural Bilingue"};


    private ZXingScannerView mScannerView;
    private static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        date = findViewById(R.id.txtdate);
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

        mLLProfesor.setVisibility(View.GONE);
        mLLEgresado.setVisibility(View.GONE);
        mLLAlumno.setVisibility(View.GONE);

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

        final Spinner spinner = findViewById(R.id.spinner1);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, facultad);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int position, long arg3) {
                spinner.setSelection(position);
                Spinner spinner2 = findViewById(R.id.spinner2);
                switch (position){
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


//        switch (position){
//            case 0:
//                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, faya);
//                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner2.setAdapter(dataAdapter2);
//                break;
//            case 1:
//                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fceyt);
//                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner2.setAdapter(dataAdapter3);
//                break;
//            case 2:
//                ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fcf);
//                dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner2.setAdapter(dataAdapter4);
//                break;
//            case 3:
//                ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fcm);
//                dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner2.setAdapter(dataAdapter5);
//                break;
//            case 4:
//                ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fhcys);
//                dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner2.setAdapter(dataAdapter6);
//                break;
//        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
          case R.id.txtdate:
                showDateDialog();
                break;
          case R.id.btnregister:
                //Insert función registro DENIS gg
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.imgUserRegister:
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                break;
            case R.id.btnBack:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btnScanner:
                startActivity(new Intent(RegisterActivity.this, BarcodeActivity.class));
                finish();
                break;
            case R.id.btnProfesor:
                mLLProfesor.setVisibility(View.VISIBLE);
                mLLEgresado.setVisibility(View.GONE);
                mLLAlumno.setVisibility(View.GONE);
                break;
            case R.id.btnAlumno:
                mLLAlumno.setVisibility(View.VISIBLE);
                mLLProfesor.setVisibility(View.GONE);
                mLLEgresado.setVisibility(View.GONE);
                break;
            case R.id.btnEgresado:
                mLLEgresado.setVisibility(View.VISIBLE);
                mLLProfesor.setVisibility(View.GONE);
                mLLAlumno.setVisibility(View.GONE);
                break;

        }
    }

    private void showDateDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                date.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imgUserRegister.setImageBitmap(bitmap);
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
