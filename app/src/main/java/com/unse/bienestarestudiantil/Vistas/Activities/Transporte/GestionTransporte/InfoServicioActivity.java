package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Pasajero;
import com.unse.bienestarestudiantil.Modelos.Servicio;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.PasajeroAdapter;

import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InfoServicioActivity extends AppCompatActivity {


    Servicio mServicio;
    ImageView imgBack;
    ArrayList<Pasajero> mPasajeros;
    TextView txtPatente, txtHoraInicio, txtHoraFin, txtTime, txtServicio, txtNombre, txtDNI, txtPasajeros;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PasajeroAdapter mPasajeroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getParcelableExtra(Utils.SERVICIO) != null) {
            mServicio = getIntent().getParcelableExtra(Utils.SERVICIO);
        }
        if (getIntent().getSerializableExtra(Utils.PASAJERO) != null) {
            mPasajeros = (ArrayList<Pasajero>) getIntent().getSerializableExtra(Utils.PASAJERO);
        }
        if (mServicio != null) {

            setContentView(R.layout.activity_info_servicio);

            setToolbar();

            loadViews();

            loadData();

            loadListener();

        } else {
            Utils.showToast(getApplicationContext(), getString(R.string.servicioError));
            finish();
        }
    }

    private void loadListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadData() {
        txtServicio.setText(mServicio.getDescripcio());
        txtNombre.setText(String.format("%s %s", mServicio.getNombre(), mServicio.getApellido()));
        txtDNI.setText(String.valueOf(mServicio.getDniChofer()));
        txtHoraInicio.setText(Utils.getHora(Utils.getFechaDateWithHour(mServicio.getFechaInicio())));
        txtHoraFin.setText(Utils.getHora(Utils.getFechaDateWithHour(mServicio.getFechaFin())));
        txtPatente.setText(mServicio.getPatente());
        Date horaInicio = Utils.getFechaDateWithHour(mServicio.getFechaInicio());
        Date horaActual = Utils.getFechaDateWithHour(mServicio.getFechaFin());
        if (!horaInicio.after(horaActual)) {
            long diff = horaActual.getTime() - horaInicio.getTime();
            long horas = diff / (60 * 60 * 1000) % 24;
            long minutes = diff / (60 * 1000) % 60;
            long segundos = diff / 1000 % 60;
            String tiempo = String.format("%02d:%02d:%02d", horas, minutes, segundos);
            txtTime.setText(tiempo);
        } else {
            txtTime.setText("ERROR DE TIEMPO");
        }
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        if (mPasajeros.size() > 0) {
            txtPasajeros.setVisibility(View.VISIBLE);
            txtPasajeros.setText("PASAJEROS");
            mPasajeroAdapter = new PasajeroAdapter(mPasajeros, getApplicationContext(), 2);
            mRecyclerView.setAdapter(mPasajeroAdapter);
        } else {
            txtPasajeros.setVisibility(View.VISIBLE);
            txtPasajeros.setText("NO SE REGISTRARON PASAJEROS");
        }


    }

    private void loadViews() {
        imgBack = findViewById(R.id.imgFlecha);
        txtPasajeros = findViewById(R.id.txtPasajeros);
        txtServicio = findViewById(R.id.txtServicios);
        mRecyclerView = findViewById(R.id.recycler);
        txtDNI = findViewById(R.id.txtDni);
        txtNombre = findViewById(R.id.txtName);
        txtHoraFin = findViewById(R.id.txtFin);
        txtHoraInicio = findViewById(R.id.txtInicio);
        txtPatente = findViewById(R.id.txtPatente);
        txtTime = findViewById(R.id.txtTime);

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText(
                Utils.getBirthday(
                        Utils.getFechaDateWithHour(mServicio.getFechaInicio())));

    }

}
