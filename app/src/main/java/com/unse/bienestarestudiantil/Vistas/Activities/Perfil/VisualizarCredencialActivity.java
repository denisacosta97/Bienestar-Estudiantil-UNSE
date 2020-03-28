package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.unse.bienestarestudiantil.Databases.UsuariosRepo;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Alumno;
import com.unse.bienestarestudiantil.Modelos.Credencial;
import com.unse.bienestarestudiantil.Modelos.CredencialDeporte;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

import org.slf4j.helpers.Util;

public class VisualizarCredencialActivity extends AppCompatActivity {

    TextView mDeporte, mApellido, mNombre, mFacultad, mLegajo, mNombreEquipo, mAnio;
    ImageView mFoto;

    Credencial mCredencial;

    LinearLayout layDeporte;

    int tipo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_credencial);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getIntExtra(Utils.TIPO_CREDENCIAL, -1) != -1){
            tipo = getIntent().getIntExtra(Utils.TIPO_CREDENCIAL, -1);
        }
        if (getIntent().getParcelableExtra(Utils.TIPO_CREDENCIAL_DATO) != null){
            mCredencial = getIntent().getParcelableExtra(Utils.TIPO_CREDENCIAL_DATO);
        }

        loadViews();

        loadData();

        loadListener();

        updateView();

    }

    private void updateView() {
        switch (tipo){
            case 2:
                layDeporte.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void loadListener() {
    }

    private void loadData() {
        switch (tipo){
            case 2:
                int id = new PreferenceManager(getApplicationContext()).getValueInt(Utils.MY_ID);
                Usuario usuario = new UsuariosRepo(getApplicationContext()).get(id);
                CredencialDeporte credencialDeporte = (CredencialDeporte) mCredencial;
                mDeporte.setText(credencialDeporte.getNombre());
                mApellido.setText(usuario.getApellido());
                mNombre.setText(usuario.getNombre());
                mNombreEquipo.setText(credencialDeporte.getDescripcion());
                mAnio.setText(String.valueOf(credencialDeporte.getAnio()));
                String URL = String.format("%s%s.jpg", Utils.URL_USUARIO_IMAGE_LOAD, id);
                Glide.with(mFoto.getContext()).load(URL).apply(new RequestOptions().error(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(R.drawable.ic_user)).into(mFoto);
                break;
        }
    }

    private void loadViews() {
        layDeporte = findViewById(R.id.layDeporte);
        mDeporte = findViewById(R.id.txtDeporte);
        mApellido = findViewById(R.id.txtApellido);
        mNombre = findViewById(R.id.txtNombre);
        mFacultad = findViewById(R.id.txtFacultad);
        mLegajo = findViewById(R.id.txtLegajo);
        mNombreEquipo = findViewById(R.id.txtNombreEquipo);
        mAnio = findViewById(R.id.txtAnio);
        mFoto = findViewById(R.id.imgUser);
    }
}
