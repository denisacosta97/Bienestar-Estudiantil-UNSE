package com.unse.bienestarestudiantil.Vistas.Activities.UAPU.GestionTurnos;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unse.bienestarestudiantil.Herramientas.Almacenamiento.PreferenceManager;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Herramientas.VolleySingleton;
import com.unse.bienestarestudiantil.Modelos.Doctor;
import com.unse.bienestarestudiantil.Modelos.ServiciosU;
import com.unse.bienestarestudiantil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResumenTurnoUAPUActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtEsp, txtHora, txtFecha, txtDoct, txtConfirmar, txtTitulo;
    ProgressBar mProgressBar;
    FrameLayout frame;
    View revealView;
    int[] mCalendar;
    String horarios;
    ServiciosU mServiciosU;
    Doctor mDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_turno_uapu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().getStringExtra(Utils.DATA_RESERVA) != null) {
            horarios = getIntent().getStringExtra(Utils.DATA_RESERVA);
        }
        if (getIntent().getIntArrayExtra(Utils.DATA_FECHA) != null) {
            mCalendar = getIntent().getIntArrayExtra(Utils.DATA_FECHA);
        }

        if (getIntent().getParcelableExtra(Utils.SERVUAPU) != null) {
            mServiciosU = getIntent().getParcelableExtra(Utils.SERVUAPU);
        }

        if (getIntent().getParcelableExtra(Utils.DOCTOR) != null) {
            mDoctor = getIntent().getParcelableExtra(Utils.DOCTOR);
        }

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {
        txtEsp.setText(mServiciosU.getTitulo());
        String name = mDoctor.getNombre() + " " + mDoctor.getApellido();
        txtDoct.setText(name);
        txtFecha.setText(String.format("%s/%s/%s", mCalendar[0], mCalendar[1], mCalendar[2]));
        txtHora.setText(horarios);

    }

    private void loadListener() {
        frame.setOnClickListener(this);
    }

    private void loadViews() {

        txtEsp = findViewById(R.id.txtEsp);
        txtHora = findViewById(R.id.txtHora);
        txtFecha = findViewById(R.id.txtFecha);
        txtDoct = findViewById(R.id.txtDoct);
        txtConfirmar = findViewById(R.id.txtLogin);
        revealView = findViewById(R.id.revealView);
        mProgressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.singBtn);
        txtTitulo = findViewById(R.id.txtTitulo);
    }


    public void animateButtonWidth(boolean is) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(frame.getMeasuredWidth(), getFinalWidth(is));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = frame.getLayoutParams();
                layoutParams.width = value;
                frame.requestLayout();
            }
        });
        valueAnimator.setDuration(350);
        valueAnimator.start();
    }

    public void fadeInOutTextProgress() {
        txtConfirmar.animate().alpha(0f).setDuration(250).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showProgressDialog(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }


    public void revealButton() {
        revealView.setVisibility(View.VISIBLE);

        int x = revealView.getWidth();
        int y = revealView.getHeight();

        int startX = (int) (getFinalWidth(true) / 2 + frame.getX());
        int startY = (int) (getFinalWidth(true) / 2 + frame.getY());

        float radius = Math.max(x, y) * 1.2f;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Animator reveal = ViewAnimationUtils.createCircularReveal(revealView, startX, startY, getFinalWidth(true), radius);
            reveal.setDuration(1000);
            reveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    showFinalText(true);

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            reveal.start();
        } else {
            revealView.setVisibility(View.VISIBLE);
            showFinalText(true);
        }
    }

    private void showFinalText(boolean is) {
        txtTitulo.setVisibility(View.VISIBLE);
        txtTitulo.setAlpha(0f);
        txtTitulo.setText(is ? "¡TURNO RESERVADO!" : "");
        txtTitulo.animate().setDuration(500).alpha(1f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SelectorFechaActivity.instance.finish();
                finish();
                //Utils.createPDF(getApplicationContext(), "COMPROBANTE_TURNO.pdf");
            }
        }, 1200);
    }

    public void fadeOutProgress() {
        mProgressBar.animate().alpha(0f).setDuration(250).start();
    }


    public void showProgressDialog(boolean is) {
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mProgressBar.setVisibility(is ? View.VISIBLE : View.GONE);
    }

    private int getFinalWidth(boolean is) {

        return (int) getResources().getDimension(is ? R.dimen.width : R.dimen.width2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singBtn:
                animateButtonWidth(true);
                fadeInOutTextProgress();
                sendServer();
                break;
        }
    }

    private void sendServer() {
        final HashMap<String, String> map = new HashMap<>();
        String URL = "";
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        final int id = preferenceManager.getValueInt(Utils.MY_ID);
        final String token = preferenceManager.getValueString(Utils.TOKEN);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                procesarRespuesta(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Utils.showToast(getApplicationContext(), getString(R.string.servidorOff));
                loadError();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                map.put("key", token);
                map.put("idU", String.valueOf(id));
                map.put("di", String.valueOf(mCalendar[0]));
                map.put("me", String.valueOf(mCalendar[1]));
                map.put("an", String.valueOf(mCalendar[2]));

                map.put("ho", horarios);
                map.put("iu", String.valueOf(id));

                return map;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void procesarRespuesta(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            int estado = jsonObject.getInt("estado");
            switch (estado) {
                case -1:
                    Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
                    loadError();
                    break;
                case 1:
                    //Exito
                    revealButton();
                    fadeOutProgress();
                    break;
                case 2:
                    loadError();
                    break;
                case 3:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInvalido));
                    loadError();
                    break;
                case 4:
                    Utils.showToast(getApplicationContext(), getString(R.string.camposInvalidos));
                    loadError();
                    break;
                case 5:
                    Utils.showToast(getApplicationContext(), "Turno ya reservado");
                    showProgressDialog(false);
                    animateButtonWidth(false);
                    fadeOutInTextProgress();
                    break;
                case 100:
                    Utils.showToast(getApplicationContext(), getString(R.string.tokenInexistente));
                    loadError();
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            loadError();
            Utils.showToast(getApplicationContext(), getString(R.string.errorInternoAdmin));
        }
    }

    private void loadError() {
        showProgressDialog(false);
        animateButtonWidth(false);
        fadeOutInTextProgress();
        Utils.showToast(getApplicationContext(), "¡Error al reservar el turno!");
    }

    public void fadeOutInTextProgress() {
        txtConfirmar.animate().alpha(1f).setDuration(250).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }
}
