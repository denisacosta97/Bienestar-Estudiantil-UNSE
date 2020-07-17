package com.unse.bienestarestudiantil.Vistas.Activities.Transporte.GestionTransporte;

import android.graphics.Color;
import android.graphics.PorterDuff;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.R;

public class GestionChoferActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    TextView txtEstado, scrollingText;
    Button btnOn, btnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_chofer);

        setToolbar();

        loadViews();

        loadData();

        loadListener();

    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de chofer");

    }

    private void loadListener() {
        imgIcono.setOnClickListener(this);
        btnOn.setOnClickListener(this);
        btnOff.setOnClickListener(this);

    }

    private void loadData() {
        scrollingText.setSelected(true);
    }

    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);

        txtEstado = findViewById(R.id.txtEstado);
        btnOn = findViewById(R.id.btnOn);
        btnOff = findViewById(R.id.btnOff);
        scrollingText = findViewById(R.id.scrollingtexthrs);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
            case R.id.btnOn:
                txtEstado.setText("En servicio");
                txtEstado.setTextColor(getResources().getColor(R.color.colorGreen));
                btnOff.getBackground().setColorFilter(Color.parseColor("#D32F2F"), PorterDuff.Mode.SRC_OVER);
                btnOn.getBackground().setColorFilter(Color.parseColor("#E7E7E7"), PorterDuff.Mode.SRC_OVER);
                break;
            case R.id.btnOff:
                txtEstado.setText("Fuera de servicio");
                txtEstado.setTextColor(getResources().getColor(R.color.colorGreyDark));
                btnOff.getBackground().setColorFilter(Color.parseColor("#E7E7E7"), PorterDuff.Mode.SRC_OVER);
                btnOn.getBackground().setColorFilter(Color.parseColor("#32AC37"), PorterDuff.Mode.SRC_OVER);
                break;
        }
    }

}
