package com.unse.bienestarestudiantil.Vistas;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.databinding.ActivityTransitionBinding;

public class TransitionActivity extends AppCompatActivity {

    private ActivityTransitionBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

    }

    public void load(View view) {
        animateButtonWidth();
        fadeInOutTextProgress();
        nextAction();

    }

    public void fadeInOutTextProgress() {
        mBinding.txtLogin.animate().alpha(0f).setDuration(250).setListener(new Animator.AnimatorListener() {
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
        mBinding.singBtn.setElevation(0f);
        mBinding.revealView.setVisibility(View.VISIBLE);

        int x = mBinding.revealView.getWidth();
        int y = mBinding.revealView.getHeight();

        int startX = (int) (getFinalWidth() / 2 + mBinding.singBtn.getX());
        int startY = (int) (getFinalWidth() / 2 + mBinding.singBtn.getY());

        float radius = Math.max(x, y) * 1.2f;

        Animator reveal = ViewAnimationUtils.createCircularReveal(mBinding.revealView, startX, startY, getFinalWidth(), radius);
        reveal.setDuration(350);
        reveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 600);
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
        mBinding.progress.animate().alpha(0f).setDuration(250).start();
    }


    public void showProgressDialog() {
        mBinding.progress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mBinding.progress.setVisibility(View.VISIBLE);
    }

    public void animateButtonWidth() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mBinding.singBtn.getMeasuredWidth(), getFinalWidth());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mBinding.singBtn.getLayoutParams();
                layoutParams.width = value;
                mBinding.singBtn.requestLayout();
            }
        });
        valueAnimator.setDuration(350);
        valueAnimator.start();
    }

    private int getFinalWidth() {

        return (int) getResources().getDimension(R.dimen.width);
    }


}
