package com.unse.bienestarestudiantil.Vistas.Activities;

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
import com.unse.bienestarestudiantil.R;

public class RecuperarPassActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRecuperar;
    RelativeLayout layoutFondo;
    EditText edtxDni, edtxNewPass, edtxRepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layoutFondo = findViewById(R.id.backgroundlogin);

        Glide.with(this).load(R.drawable.img_unse2)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        layoutFondo.setBackground(resource);
                    }
                });

        loadViews();

        loadListener();
    }

    private void loadListener() {
        btnRecuperar.setOnClickListener(this);
    }

    private void loadViews() {
        btnRecuperar = findViewById(R.id.btnRecuperar);
        edtxDni = findViewById(R.id.edtxDni);
        edtxNewPass = findViewById(R.id.edtxNewPass);
        edtxRepass = findViewById(R.id.edtxRepeatPass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRecuperar:
                //Va al servidorsito y luego se abre la activity c:
                String dni = edtxDni.getText().toString();
                String newPass = edtxNewPass.getText().toString();
                String rePass = edtxRepass.getText().toString();
                if(true) {
                    startActivity(new Intent(RecuperarPassActivity.this, ReLoginActivity.class));
                }
                break;

        }

    }

}
