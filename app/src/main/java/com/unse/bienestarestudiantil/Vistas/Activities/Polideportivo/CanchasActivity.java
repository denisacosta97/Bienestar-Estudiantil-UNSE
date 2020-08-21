package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.Modelos.ReservaCancha;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Dialogos.DialogoGeneral;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import java.util.Date;

public class CanchasActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Spinner mSpinnerHora, mSpinnerCanchas;
    ArrayAdapter<String> canchasAdapter, horaAdapter;
    Button btnDia, btnNoche, btnBuscar;
    EditText edtFecha;
    ReservaCancha reservaCanchas;
    int turno = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canchas);

        setToolbar();

        loadView();

        loadData();

        loadListener();
    }

    private void loadListener() {
        btnBuscar.setOnClickListener(this);
        btnDia.setOnClickListener(this);
        btnNoche.setOnClickListener(this);
        edtFecha.setOnClickListener(this);

        mSpinnerCanchas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mSpinnerCanchas.setSelection(position);
                if (turno != -1) {
                    if (turno == 0) {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.horaCanchasDía);
                        dataAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerHora.setAdapter(dataAdapter);
                    } else {
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.style_spinner, Utils.horaCanchasNoche);
                        dataAdapter.setDropDownViewResource(R.layout.style_spinner);
                        mSpinnerHora.setAdapter(dataAdapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        mSpinnerHora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadView() {
        mSpinnerHora = findViewById(R.id.spinnerHora);
        mSpinnerCanchas  = findViewById(R.id.spinnerCancha);
        btnDia = findViewById(R.id.btnDia);
        btnNoche = findViewById(R.id.btnNoche);
        edtFecha = findViewById(R.id.edtFecha);
        btnBuscar = findViewById(R.id.btnBuscar);
    }

    private void loadData() {
        canchasAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.style_spinner, Utils.canchas);
        canchasAdapter.setDropDownViewResource(R.layout.style_spinner);
        mSpinnerCanchas.setAdapter(canchasAdapter);
        btnDia.getBackground().setColorFilter(Color.parseColor("#F3F2F2"), PorterDuff.Mode.SRC_OVER);
        btnNoche.getBackground().setColorFilter(Color.parseColor("#F3F2F2"), PorterDuff.Mode.SRC_OVER);
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        findViewById(R.id.imgFlecha).setVisibility(View.VISIBLE);
        findViewById(R.id.imgFlecha).setOnClickListener(this);
        ((TextView)findViewById(R.id.txtTitulo)).setText("Reserva");
    }

    private void selectFecha() {
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
                edtFecha.setText(selectedDate);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");

    }

    private int buscarDisponibilidad() {
        Validador validador = new Validador(getApplicationContext());
        String hora = "";
        String fechaReserva = edtFecha.getText().toString().trim();
        String cancha = Utils.canchas[mSpinnerCanchas.getSelectedItemPosition()].trim();

        if (turno == 0) {
            hora = Utils.horaCanchasDía[mSpinnerCanchas.getSelectedItemPosition()].trim();
        } else if(turno == 1){
            hora = Utils.horaCanchasNoche[mSpinnerCanchas.getSelectedItemPosition()].trim();
        }

        String fecha = Utils.getFechaNameWithinHour(new Date(System.currentTimeMillis()));
        String token = new PreferenceManager(getApplicationContext()).getValueString(Utils.TOKEN);

        reservaCanchas = new ReservaCancha(-1, turno, hora, fechaReserva, cancha,
                fecha, -1);

        return 0;
    }

    private void showDialogs() {
        DialogoGeneral.Builder builder = new DialogoGeneral.Builder(getApplicationContext())
                .setDescripcion(getString(R.string.reservaOk))
                .setIcono(R.drawable.ic_chek)
                .setTipo(DialogoGeneral.TIPO_SI_NO)
                .setListener(new YesNoDialogListener() {
                    @Override
                    public void yes() {
                        Intent i = new Intent(getApplicationContext(), CanchasSelectActivity.class);
                        i.putExtra(Utils.CANCHA, reservaCanchas);
                        startActivity(i);
                    }

                    @Override
                    public void no() {
                        finish();
                    }
                })
                .setTipo(DialogoGeneral.TIPO_SI_NO);
        final DialogoGeneral mensaje = builder.build();
        mensaje.setCancelable(false);
        mensaje.show(getSupportFragmentManager(), "dialog_error");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnDia:
                mSpinnerHora.setAdapter(null);
                turno = 0;
                btnDia.getBackground().setColorFilter(Color.parseColor("#32AC37"), PorterDuff.Mode.SRC_OVER);
                btnNoche.getBackground().setColorFilter(Color.parseColor("#F3F2F2"), PorterDuff.Mode.SRC_OVER);
                break;
            case R.id.btnNoche:
                mSpinnerHora.setAdapter(null);
                turno = 1;
                btnNoche.getBackground().setColorFilter(Color.parseColor("#32AC37"), PorterDuff.Mode.SRC_OVER);
                btnDia.getBackground().setColorFilter(Color.parseColor("#F3F2F2"), PorterDuff.Mode.SRC_OVER);
                break;
            case R.id.btnBuscar:
                int notReserved = buscarDisponibilidad();
                if(notReserved == 0) {
                    showDialogs();
                }
                break;
            case R.id.edtFecha:
                selectFecha();
                break;
        }
    }

}