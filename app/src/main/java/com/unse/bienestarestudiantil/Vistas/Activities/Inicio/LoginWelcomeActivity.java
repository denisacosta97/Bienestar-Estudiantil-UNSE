package com.unse.bienestarestudiantil.Vistas.Activities.Inicio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.unse.bienestarestudiantil.Databases.BDGestor;
import com.unse.bienestarestudiantil.Databases.DBManager;
import com.unse.bienestarestudiantil.Herramientas.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

import java.util.Calendar;

public class LoginWelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button mLogin, mInvitado;
    TextView txtWelcome;
    LinearLayout layoutFondo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        boolean isLogin = preferenceManager.getValue(Utils.IS_LOGIN);
        if(isLogin){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }else {
            setContentView(R.layout.activity_login_welcome);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            layoutFondo = findViewById(R.id.backgroundwelcome);

            Glide.with(this).load(R.drawable.img_unse2)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            layoutFondo.setBackground(resource);
                        }
                    });

            loadViews();

            createBD();

            loadListener();

            changeTextWelcome();
        }

    }

    private void createBD() {
        BDGestor gestor = new BDGestor(getApplicationContext());
        DBManager.initializeInstance(gestor);

    }

    private void loadListener() {
        mLogin.setOnClickListener(this);
        mInvitado.setOnClickListener(this);
    }

    private void loadViews() {
        mLogin = findViewById(R.id.inisesion);
        mInvitado = findViewById(R.id.visit);
        txtWelcome = findViewById(R.id.txtWelcome);
    }

    public void changeTextWelcome() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 6 && timeOfDay <= 12) {
            txtWelcome.setText("¡Buen día!");
        } else if (timeOfDay >= 13 && timeOfDay <= 19) {
            txtWelcome.setText("¡Buenas tardes!");
        } else if (timeOfDay >= 20)
            txtWelcome.setText("¡Buenas noches!");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.visit:
                startActivity(new Intent(LoginWelcomeActivity.this, MainActivity.class));
                break;
            case R.id.inisesion:
                startActivity(new Intent(LoginWelcomeActivity.this, LoginActivity.class));
                break;
        }

    }
}
