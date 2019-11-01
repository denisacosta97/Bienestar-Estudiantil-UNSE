package com.unse.bienestarestudiantil.Vistas.Activities.Deportes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

public class RegistroDeporteActivity extends AppCompatActivity implements View.OnClickListener {

    Deporte mDeporte;
    EditText edtNombreDep;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_deporte);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null){
            mDeporte = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);
        }

        Utils.setFont(getApplicationContext(), (ViewGroup)findViewById(android.R.id.content), Utils.MONSERRAT);

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
        btnBack = findViewById(R.id.btnBack);
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
