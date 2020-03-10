package com.unse.bienestarestudiantil.Vistas.Activities.Perfil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.Validador;
import com.unse.bienestarestudiantil.R;

public class RecuperarContraseniaActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnEnviar;
    EditText edtxMail, edtDNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_missed);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadListener();
    }

    private void loadListener() {
        btnEnviar.setOnClickListener(this);
    }

    private void loadViews() {
        btnEnviar = findViewById(R.id.btnEnviar);
        edtxMail = findViewById(R.id.edtMail);
        edtDNI = findViewById(R.id.edtDNI);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEnviar:
                String email = edtxMail.getText().toString().trim();
                String dni = edtDNI.getText().toString().trim();

                Validador validador = new Validador();
                if (!validador.noVacio(email, dni)){

                    if (validador.validarMail(email)){

                        if (validador.validarNumero(dni)){

                            recuperarClave(dni, email);

                        }else
                            Utils.showToast(getApplicationContext(), "Numero de DNI inválido");

                    }else{
                        Utils.showToast(getApplicationContext(), "Campo mail inválido");
                    }

                }else{
                    Utils.showToast(getApplicationContext(), "Hay campos vacíos");
                }
                if(true) {
                    startActivity(new Intent(RecuperarContraseniaActivity.this, CambiarContraseniaActivity.class));
                }
                break;
        }

    }

    private void recuperarClave(String dni, String email) {

    }

}
