package com.unse.bienestarestudiantil.Vistas.Activities.Ciber.GestionCiber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Impresion;
import com.unse.bienestarestudiantil.R;

public class AgregarImpresionActivity extends AppCompatActivity implements View.OnClickListener {

    Impresion impresion;
    EditText edtDni, edtCant, edtPrecio, edtDescripcion;
    Button btnCancel, btnGuardar;
    ImageView imgIcono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_impresion);

        loadViews();

        loadListener();

        loadData();

        setToolbar();

    }


    private void loadData() {

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }

    private void loadViews() {
        btnCancel = findViewById(R.id.btnCancel);
        btnGuardar = findViewById(R.id.btnGuardar);

        edtDni = findViewById(R.id.edtDni);
        edtCant = findViewById(R.id.edtCantPag);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtDescripcion = findViewById(R.id.edtDesc);

        imgIcono = findViewById(R.id.imgFlecha);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("Impresiones");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgIcon:
                onBackPressed();
                break;
        }
    }

}