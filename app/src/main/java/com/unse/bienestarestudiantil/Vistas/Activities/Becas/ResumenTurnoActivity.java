package com.unse.bienestarestudiantil.Vistas.Activities.Becas;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;

public class ResumenTurnoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtTipoTurno, txtHora, txtFecha, txtReceptor, txtConfirmar, txtTitulo;
    Button btnConfirmar;
    ProgressBar mProgressBar;
    FrameLayout frame;
    View revealView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_turno);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loadViews();

        loadData();

        loadListener();
    }

    private void loadData() {

    }

    private void loadListener() {
        frame.setOnClickListener(this);
    }

    private void loadViews() {

        txtTipoTurno = findViewById(R.id.txtTipoBeca);
        txtHora = findViewById(R.id.txtHora);
        txtFecha = findViewById(R.id.txtFecha);
        txtReceptor = findViewById(R.id.txtReceptor);
        txtConfirmar = findViewById(R.id.txtLogin);
        revealView = findViewById(R.id.revealView);
        mProgressBar = findViewById(R.id.progress);
        frame = findViewById(R.id.singBtn);
        txtTitulo = findViewById(R.id.txtTitulo);
    }

    public void load(View view) {
        animateButtonWidth();
        fadeInOutTextProgress();
        nextAction();

    }

    public void fadeInOutTextProgress() {
        txtConfirmar.animate().alpha(0f).setDuration(250).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showProgressDialog();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    public void nextAction() {
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                revealButton();
                fadeOutProgress();
            }
        }, 2000);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void revealButton() {
        frame.setElevation(0f);
        revealView.setVisibility(View.VISIBLE);

        int x = revealView.getWidth();
        int y = revealView.getHeight();

        int startX = (int) (getFinalWidth() / 2 + frame.getX());
        int startY = (int) (getFinalWidth() / 2 + frame.getY());

        float radius = Math.max(x, y) * 1.2f;

        Animator reveal = ViewAnimationUtils.createCircularReveal(revealView, startX, startY, getFinalWidth(), radius);
        reveal.setDuration(1000);
        reveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                txtTitulo.setVisibility(View.VISIBLE);
                txtTitulo.setAlpha(0f);
                txtTitulo.animate().setDuration(500).alpha(1f);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Utils.createPDF(getApplicationContext(), "COMPROBANTE_TURNO.pdf");
                    }
                }, 1200);

           }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        reveal.start();
    }

    public void fadeOutProgress() {
        mProgressBar.animate().alpha(0f).setDuration(250).start();
    }


    public void showProgressDialog() { 
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void animateButtonWidth() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(frame.getMeasuredWidth(), getFinalWidth());
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

    private int getFinalWidth() {

        return (int) getResources().getDimension(R.dimen.width);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singBtn:
                load(v);
                break;
        }
    }
}
