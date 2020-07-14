package com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionDeportes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.Modelos.Profesor;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

public class InfoProfesorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgIcono;
    Usuario mUsuarios;
    Profesor mProfesors;
    Deporte mDeportes;
    TextView mNameDep, mNombreApe, mDni, mFechaNac, mDomicilio, mBarrio, mTel, mFechaIng;
    ImageView mPhotoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_profesor);

        if (getIntent().getParcelableExtra(Utils.USER_NAME) != null) {
            mUsuarios = getIntent().getParcelableExtra(Utils.USER_NAME);
        }
        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME_PROF) != null)
            mProfesors = getIntent().getParcelableExtra(Utils.DEPORTE_NAME_PROF);

        if (getIntent().getParcelableExtra(Utils.DEPORTE_NAME) != null)
            mDeportes = getIntent().getParcelableExtra(Utils.DEPORTE_NAME);

        if (mUsuarios != null && mProfesors != null && mDeportes != null) {
            loadViews();

            loadListener();

            loadData();
        } else {
            Utils.showToast(getApplicationContext(), "ERROR al abrir, vuelva a intentar");
            finish();
        }

        setToolbar();

    }

    private void loadData() {
        mNameDep.setText(mDeportes.getName());
        mNombreApe.setText(String.format("%s %s", mUsuarios.getNombre(), mUsuarios.getApellido()));
        mDni.setText(Integer.toString(mProfesors.getIdProfesor()));
        mFechaNac.setText(String.format("%s", mUsuarios.getFechaNac()));
        mDomicilio.setText(mUsuarios.getDomicilio());
        mBarrio.setText(mUsuarios.getBarrio());
        mTel.setText(mUsuarios.getTelefono());
        mFechaIng.setText(String.format("%s", mProfesors.getFechaIngreso()));

        mPhotoUser.setImageResource(R.drawable.user);
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setText("Gestión de profesores");

    }


    private void loadListener() {
        imgIcono.setOnClickListener(this);

    }


    private void loadViews() {
        imgIcono = findViewById(R.id.imgFlecha);

        mPhotoUser = findViewById(R.id.imgvUser);
        mNameDep = findViewById(R.id.txtNameDep);
        mNombreApe = findViewById(R.id.txtNameApe);
        mDni = findViewById(R.id.txtDni);
        mFechaNac = findViewById(R.id.txtFechaN);
        mDomicilio = findViewById(R.id.txtDomicilio);
        mBarrio = findViewById(R.id.txtBarrio);
        mTel = findViewById(R.id.txtTel);
        mFechaIng = findViewById(R.id.txtFechaIng);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgFlecha:
                onBackPressed();
                break;
        }
    }
}
