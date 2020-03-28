package com.unse.bienestarestudiantil.Herramientas.Credencial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.unse.bienestarestudiantil.R;

public class CredencialView extends FrameLayout {

    private int FRONT_CARD_ID = R.id.front_card_container;
    private int BACK_CARD_ID = R.id.back_card_container;
    private int FRONT_CARD_OUTLINE_ID = R.id.front_card_outline;
    private int BACK_CARD_OUTLINE_ID = R.id.back_card_outline;


    public static final int CARD_SIDE_FRONT = 1, CARD_SIDE_BACK = 0;


    private int mCurrentDrawable;

    public CredencialView(Context context) {
        super(context);
        init();
    }

    public CredencialView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CredencialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init() {

        mCurrentDrawable = R.drawable.card_color_round_rect_default;

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_creditcard, this, true);

    }

    private void init(AttributeSet attrs) {

        init();


    }

    public int getFRONT_CARD_ID() {
        return FRONT_CARD_ID;
    }

    public void setFRONT_CARD_ID(int FRONT_CARD_ID) {
        this.FRONT_CARD_ID = FRONT_CARD_ID;
    }

    public int getBACK_CARD_ID() {
        return BACK_CARD_ID;
    }

    public void setBACK_CARD_ID(int BACK_CARD_ID) {
        this.BACK_CARD_ID = BACK_CARD_ID;
    }

    public int getCurrentDrawable() {
        return mCurrentDrawable;
    }

    public void setCurrentDrawable(int currentDrawable) {
        mCurrentDrawable = currentDrawable;
    }

    private void flip(final boolean ltr, boolean isImmediate) {

        View layoutContainer = findViewById(R.id.card_outline_container);
        View frontView = findViewById(FRONT_CARD_OUTLINE_ID);
        View backView = findViewById(BACK_CARD_OUTLINE_ID);

        final View frontContentView = findViewById(FRONT_CARD_ID);
        final View backContentView = findViewById(BACK_CARD_ID);
        View layoutContentContainer = findViewById(R.id.card_container);


        if (isImmediate) {
            frontContentView.setVisibility(ltr ? VISIBLE : GONE);
            backContentView.setVisibility(ltr ? GONE : VISIBLE);

        } else {

            int duration = 600;

            FlipAnimator flipAnimator = new FlipAnimator(frontView, backView, frontView.getWidth() / 2, backView.getHeight() / 2);
            flipAnimator.setInterpolator(new OvershootInterpolator(0.5f));
            flipAnimator.setDuration(duration);

            if (ltr) {
                flipAnimator.reverse();
            }

            flipAnimator.setTranslateDirection(FlipAnimator.DIRECTION_Z);
            flipAnimator.setRotationDirection(FlipAnimator.DIRECTION_Y);
            layoutContainer.startAnimation(flipAnimator);

            FlipAnimator flipAnimator1 = new FlipAnimator(frontContentView, backContentView, frontContentView.getWidth() / 2, backContentView.getHeight() / 2);
            flipAnimator1.setInterpolator(new OvershootInterpolator(0.5f));
            flipAnimator1.setDuration(duration);

            if (ltr) {
                flipAnimator1.reverse();
            }

            flipAnimator1.setTranslateDirection(FlipAnimator.DIRECTION_Z);
            flipAnimator1.setRotationDirection(FlipAnimator.DIRECTION_Y);

            layoutContentContainer.startAnimation(flipAnimator1);
        }

    }


    public void showFront() {
        flip(true, false);
    }

    public void showFrontImmediate() {
        flip(true, true);
    }

    public void showBack() {
        flip(false, false);
    }

    public void showBackImmediate() {
        flip(false, true);
    }


}