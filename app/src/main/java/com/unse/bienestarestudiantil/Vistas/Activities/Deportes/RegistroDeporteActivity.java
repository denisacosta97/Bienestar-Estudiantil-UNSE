package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

public class RegistroDeporteActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    EditText edtNombreDep, nombre, apellido, dni, edad, fechaNac, domicilio, barrio, carrera, legajo,
    facultad, ciudad, provincia, pais, anioIng, cantMatAprob, face, insta, mail, tel, actvFis, lugarActiv, objet;
    CheckBox chbxwhatsApp, chbxsi, chbxno, chbxcont, chbxmedia, chbxbaja;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_deporte);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null){
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }

        //Utils.setFont(getApplicationContext(), (ViewGroup)findViewById(android.R.id.content), Utils.MONSERRAT);

        loadViews();

        loadListener();

        loadData();

    }

    private void loadListener() {
        btnBack.setOnClickListener(this);
    }

    private void loadData() {
        edtNombreDep.setText(mDeporte.getName());

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

        chbxwhatsApp = findViewById(R.id.chbxWhats);
        chbxsi = findViewById(R.id.chbxSi);
        chbxno = findViewById(R.id.chbxNo);
        chbxcont = findViewById(R.id.chbxContin);
        chbxbaja = findViewById(R.id.chbxBaja);
        chbxmedia = findViewById(R.id.chbxMedia);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
