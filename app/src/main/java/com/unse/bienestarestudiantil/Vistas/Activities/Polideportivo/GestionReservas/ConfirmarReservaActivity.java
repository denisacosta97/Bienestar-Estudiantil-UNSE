package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Espacio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.RequisitosReservasActivity;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EspaciosAdapter;
import com.unse.bienestarestudiantil.Vistas.Fragmentos.DatePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class ConfirmarReservaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtReserva;
    EditText date, time, txtQS;
    Button cancel, reservar, requisitos;
    ImageView imgFlecha;

    Toolbar mToolbar;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    EspaciosAdapter mEspaciosAdapter;
    ArrayList<Espacio> models;

    int tipo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        if(getIntent().getIntExtra(Utils.DATA_RESERVA,-1) != -1){
            tipo = getIntent().getIntExtra(Utils.DATA_RESERVA, -1);
        }

        if(tipo != -1){

           // Utils.setFont(getApplicationContext(),(ViewGroup)findViewById(android.R.id.content),Utils.MONSERRAT);

            setToolbar();

            loadViews();

            loadData();

            loadListener();

        }

    }

    private void loadListener() {
        cancel.setOnClickListener(this);
        reservar.setOnClickListener(this);
        imgFlecha.setOnClickListener(this);
        requisitos.setOnClickListener(this);

        date.setOnClickListener(this);

        time.setOnClickListener(this);

    }

    private void loadData() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(Utils.getAppName(getApplicationContext(), getComponentName()));
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        models = new ArrayList<>();
        if(tipo == Utils.TIPO_QUINCHO) {
            models.add(new Espacio(R.drawable.imgborrar2, "Salón", "$4200", "Salón de X por Y dimensiones para tus eventos, cuenta con cosa1, cosa2, cosa3..."));
            models.add(new Espacio(R.drawable.imgborrar1, "Quincho 1", "$2000", "El quincho 1 tiene la mejor ubicación, cerca de la pileta y baños, cuenta con asador y cosa1, cosa2..."));
            models.add(new Espacio(R.drawable.imgborrar2, "Quincho 2", "$3500", "El quincho 2 tiene la mejor ubicación, cerca de la pileta y baños, cuenta con asador y cosa1, cosa2..."));
            models.add(new Espacio(R.drawable.imgborrar1, "Quincho 3", "$3200", "El quincho 1 tiene la mejor ubicación, cerca de la pileta y baños, cuenta con asador y cosa1, cosa2..."));
        }
        mEspaciosAdapter = new EspaciosAdapter(models, getApplicationContext());

        SnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mEspaciosAdapter);
        mRecyclerView.setNestedScrollingEnabled(true);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                selectItem(position);
                mEspaciosAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadViews() {
      //  txtReserva = findViewById(R.id.txtInfoReserva);
        cancel = findViewById(R.id.btnCancel);
        reservar = findViewById(R.id.btnReservaF);
        date = findViewById(R.id.txtdateR);
        time = findViewById(R.id.edtxTime);
        mRecyclerView = findViewById(R.id.recycler);
        imgFlecha = findViewById(R.id.imgFlecha);
        requisitos = findViewById(R.id.btnRequisitos);
    }

    private void setToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void selectItem(int position) {
        for (Espacio s: models){
            s.setSeleccionado(false);
        }
        models.get(position).setSeleccionado(true);
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
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.edtxTime:
                showTimeDialog();
                break;
            case R.id.txtdateR:
                showDateDialog();
                break;
            case R.id.btnCancel:
                onBackPressed();
                break;
            case R.id.btnReservaF:
                Toast.makeText(this, "FALTA LA FUNCIÓN GG", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnRequisitos:
                startActivity(new Intent(ConfirmarReservaActivity.this, RequisitosReservasActivity.class));
                break;
        }

    }

}
