package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Modelos.ReservaCancha;
import com.unse.bienestarestudiantil.R;

import java.util.Date;

public class CanchasSelectActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Button btnCancelar, btnReservar;
    TextView txtCancha, txtHora, txtFecha, txtPrecio;
    EditText edtNombre, edtDni;
    Spinner spinnerCat;
    ReservaCancha reservaCancha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canchas_select);

        if (getIntent().getParcelableExtra(Utils.CANCHA) != null) {
            reservaCancha = getIntent().getParcelableExtra(Utils.CANCHA);
        }

        setToolbar();

        loadView();

        loadData();

        loadListener();
    }

    private void loadListener() {
        btnReservar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                spinnerCat.setSelection(position);
                float precio = calcularPrecio(position);
                txtPrecio.setText(String.valueOf(precio));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    private void loadView() {
        btnCancelar = findViewById(R.id.btnCancelar);
        btnReservar = findViewById(R.id.btnReservar);
        txtHora = findViewById(R.id.txtHora);
        txtCancha = findViewById(R.id.txtCancha);
        txtFecha = findViewById(R.id.txtFecha);
        txtPrecio = findViewById(R.id.txtPrecio);
        edtNombre = findViewById(R.id.edtNombre);
        edtDni = findViewById(R.id.edtDni);
        spinnerCat = findViewById(R.id.spinnerCateg);

    }

    private void loadData() {
        txtCancha.setText(reservaCancha.getCancha());
        txtHora.setText(reservaCancha.getHora());
        txtFecha.setText(reservaCancha.getFechaReserva());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.style_spinner, Utils.categorias);
        dataAdapter.setDropDownViewResource(R.layout.style_spinner);
        spinnerCat.setAdapter(dataAdapter);

    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtTitulo)).setText("Reserva");
    }

    private float calcularPrecio(int categ) {
        float precioTotal = 0;
        int turno = reservaCancha.getTurno();

        switch (categ) {
            case 1:
            case 0:
            case 7:
            case 6: //Estudiante
                //Afiliados
                if(turno == 0){
                    precioTotal = 350;
                } else
                    precioTotal = 700;
                break;
            case 5:
                //Particular
                if(turno == 0){
                    precioTotal = 600;
                } else
                    precioTotal = 1200;
                break;
            default:
                //Docentes, Nodocentes y Egresados
                if(turno == 0){
                    precioTotal = 450;
                } else
                    precioTotal = 900;
                break;
        }
        return precioTotal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnCancelar:
                finish();
                break;
            case R.id.btnReservar:
                save();
                break;
        }
    }

    private void save() {
        Validador validador = new Validador(getApplicationContext());

        String nombre = edtNombre.getText().toString().trim();
        String dni = edtDni.getText().toString().trim();
        String cancha = txtCancha.getText().toString().trim();
        String hora = txtHora.getText().toString().trim();
        String fechaReserva = txtFecha.getText().toString().trim();
        String precio = txtPrecio.getText().toString().trim();
        String categ = Utils.categorias[spinnerCat.getSelectedItemPosition()].trim();

        String fecha = Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis()));
        String token = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);
    }
}