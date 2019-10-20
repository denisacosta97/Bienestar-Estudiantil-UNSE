package com.unse.bienestarestudiantil.Vistas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import java.util.Calendar;

public class ReservarActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtReserva;
    EditText date, time, txtQS;
    Button cancel, reservar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        FontChangeUtil fontChanger = new FontChangeUtil(getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)findViewById(android.R.id.content));

        Intent intent = getIntent();
        String reserva = intent.getStringExtra("nombre");
        txtReserva = findViewById(R.id.txtInfoReserva);
        txtReserva.setText("Vas a reservar el "+reserva);
        txtQS = findViewById(R.id.txtQS);
        txtQS.setText(reserva);

        cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(this);
        reservar = findViewById(R.id.btnReservaF);
        reservar.setOnClickListener(this);
        date = findViewById(R.id.txtdateR);
        date.setOnClickListener(this);
        time = findViewById(R.id.edtxTime);
        time.setOnClickListener(this);

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

    private void showTimeDialog(){
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                    time.setText(hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edtxTime:
                showTimeDialog();
                break;
            case R.id.txtdateR:
                showDateDialog();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnReservaF:
                Toast.makeText(this, "FALTA LA FUNCIÓN GG", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
