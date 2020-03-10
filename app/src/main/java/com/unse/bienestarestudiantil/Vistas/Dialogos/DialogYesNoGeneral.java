package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.R;

public class DialogYesNoGeneral extends DialogFragment implements View.OnClickListener {

    Button btnSi, btnNo;
    TextView txtTitulo, txtDescripcion;
    LinearLayout latTitulo;
    Context mContext;

    String titulo, descripcion, botonSi, botonNo;
    int colorSi = 0, colorNo = 0, colorFondo = 0;

    YesNoDialogListener mListener;

    View view;

    public void loadData(String titulo, String descripcion, YesNoDialogListener listener, Context context){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.mListener = listener;
        this.mContext = context;
    }

    public void loadColorButton(int c1, int c2){
        this.colorSi = c1;
        this.colorNo = c2;
    }

    public void loadColorTitulo(int c1){
        this.colorFondo = c1;
    }

    public void loadTextButton(String b1, String b2){
        this.botonSi = b1;
        this.botonNo = b2;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_general, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Esto es lo nuevoooooooo, evita los bordes cuadrados
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadViews() {
        btnNo = view.findViewById(R.id.btnNO);
        btnSi = view.findViewById(R.id.btnSI);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        latTitulo = view.findViewById(R.id.layTitulo);
    }

    private void loadData() {
        if (titulo != null){
            txtTitulo.setText(titulo);
        }
        if (descripcion != null)
            txtDescripcion.setText(descripcion);
        if (colorFondo != 0)
            latTitulo.setBackgroundColor(mContext.getResources().getColor(colorFondo));
        if (colorNo != 0){
            Drawable drawable = btnNo.getBackground();
            if (drawable instanceof ShapeDrawable)
                ((ShapeDrawable)drawable).getPaint().setColor(ContextCompat.getColor(mContext, colorNo));
            else if (drawable instanceof GradientDrawable)
                ((GradientDrawable) drawable).setColor(ContextCompat.getColor(mContext, colorNo));
            else if (drawable instanceof  ColorDrawable)
                ((ColorDrawable) drawable).setColor(ContextCompat.getColor(mContext, colorNo));
        }
        if (colorSi != 0){
            Drawable drawable = btnSi.getBackground();
            if (drawable instanceof ShapeDrawable)
                ((ShapeDrawable)drawable).getPaint().setColor(ContextCompat.getColor(mContext, colorSi));
            else if (drawable instanceof GradientDrawable)
                ((GradientDrawable) drawable).setColor(ContextCompat.getColor(mContext, colorSi));
            else if (drawable instanceof  ColorDrawable)
                ((ColorDrawable) drawable).setColor(ContextCompat.getColor(mContext, colorSi));
        }
        if (botonSi != null)
            btnSi.setText(botonSi);
        if (botonNo != null)
            btnNo.setText(botonNo);


    }


    private void loadListener() {
        btnNo.setOnClickListener(this);
        btnSi.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSI:
                if (mListener != null)
                    mListener.yes();
                break;
            case R.id.btnNO:
                if (mListener != null)
                    mListener.no();
                break;
        }
    }
}
