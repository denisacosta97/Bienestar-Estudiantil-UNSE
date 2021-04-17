package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionDoctores;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Doctor;
import com.unse.bienestarestudiantil.R;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilProfesionalActivity extends AppCompatActivity {

    TextView txtNombre, txtDNI, txtServicio;
    CircleImageView imgPerfil;
    ImageView imgIcono;
    Doctor mDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesional);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getParcelableExtra(Utils.USER_NAME) != null) {
            mDoctor = getIntent().getParcelableExtra(Utils.USER_NAME);
        }

        if (mDoctor != null) {

            loadView();

            loadData();

            loadListener();

            setToolbar();

        } else {

            finish();
        }

    }

    private void loadListener() {
        imgIcono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setToolbar() {
        ((TextView) findViewById(R.id.txtTitulo)).setTextColor(getResources().getColor(R.color.colorPrimary));
        ((TextView) findViewById(R.id.txtTitulo)).setText("");
        Utils.changeColorDrawable(imgIcono, getApplicationContext(), R.color.colorPrimary);
    }

    private void loadData() {
        String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, mDoctor.getIdUsuario());
        Glide.with(imgPerfil.getContext()).load(URL).apply(new RequestOptions()
                .error(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_user)).into(imgPerfil);
        txtDNI.setText(String.valueOf(mDoctor.getIdUsuario()));
        txtNombre.setText(String.format("%s %s", mDoctor.getNombre(), mDoctor.getApellido()));
        txtServicio.setText(mDoctor.getEspecialidad());


    }

    private void loadView() {
        imgIcono = findViewById(R.id.imgFlecha);
        imgPerfil = findViewById(R.id.imgUser);
        txtNombre = findViewById(R.id.txtNombre);
        txtDNI = findViewById(R.id.txtDNI);
        txtServicio = findViewById(R.id.txtDescripcion);
    }
}
