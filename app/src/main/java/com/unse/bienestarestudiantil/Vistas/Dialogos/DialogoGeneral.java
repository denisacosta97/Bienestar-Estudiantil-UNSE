package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Interfaces.YesNoDialogListener;
import com.unse.bienestarestudiantil.R;

public class DialogoGeneral extends DialogFragment implements View.OnClickListener {

    public static final int TIPO_SI_NO = 1;
    public static final int TIPO_SI = 2;
    public static final int TIPO_NO = 3;
    public static final int TIPO_ACEPTAR_CANCELAR = 4;
    public static final int TIPO_ACEPTAR = 5;
    public static final int TIPO_CANCEAR = 6;
    public static final int TIPO_LIBRE = 7;

    private Button btnSi, btnNo;
    private ImageView mImageView;
    private TextView txtTitulo, txtDescripcion;
    private LinearLayout latBackground;
    private Context mContext;

    private String titulo = null, descripcion = null, textButtonYes = null, textButtonNo = null;
    private int icono = 0, colorButtonSi = 0, colorButtonNo = 0, colorBackground = 0, tipo = 0;

    YesNoDialogListener mListener = null;
    View view, viewSeparador;

    public static class Builder {
        DialogoGeneral mDialogoGeneral;

        public Builder(Context context) {
            this.mDialogoGeneral = new DialogoGeneral();
            this.mDialogoGeneral.setContext(context);
        }

        public Builder setListener(YesNoDialogListener listener) {
            this.mDialogoGeneral.setListener(listener);
            ;
            return this;
        }

        public Builder setColorBackground(int color) {
            this.mDialogoGeneral.setColorBackground(color);
            ;
            return this;
        }

        public Builder setColorButtonSi(int colorButton) {
            this.mDialogoGeneral.setColorButtonSi(colorButton);
            ;
            return this;
        }

        public Builder setColorButtonNo(int colorButton) {
            this.mDialogoGeneral.setColorButtonSi(colorButton);
            return this;
        }

        public Builder setIcono(int icon) {
            this.mDialogoGeneral.setIcono(icon);
            return this;
        }


        public Builder setTipo(int tipo) {
            this.mDialogoGeneral.setTipo(tipo);
            ;
            return this;
        }


        public Builder setTitulo(String title) {
            this.mDialogoGeneral.setTitulo(title);
            return this;
        }

        public Builder setDescripcion(String descripcion) {
            this.mDialogoGeneral.setDescripcion(descripcion);
            return this;
        }

        public Builder setTextButtonSi(String text) {
            this.mDialogoGeneral.setTextButtonYes(text);
            return this;
        }

        public Builder setTextButtonNo(String text) {
            this.mDialogoGeneral.setTextButtonNo(text);
            return this;
        }

        public DialogoGeneral build() {
            if (mDialogoGeneral.getContextDialog() == null)
                throw new IllegalArgumentException("Debe pasarse como parámetro el contexto");
            if (mDialogoGeneral.getTitulo() == null && mDialogoGeneral.getDescripcion() == null)
                throw new IllegalArgumentException("Debe tener al menos un titulo o una descripción");
            if (mDialogoGeneral.getTipo() == 0
                    || (mDialogoGeneral.getTipo() == TIPO_LIBRE
                    && mDialogoGeneral.getTextButtonNo() == null
                    && mDialogoGeneral.getTextButtonYes() == null))
                throw new IllegalArgumentException("Debe indicar un tipo de dialogo");

            return mDialogoGeneral;
        }


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_general, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadViews() {
        mImageView = view.findViewById(R.id.imgIcon);
        btnNo = view.findViewById(R.id.btnNO);
        btnSi = view.findViewById(R.id.btnSI);
        txtTitulo = view.findViewById(R.id.txtTitulo);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        latBackground = view.findViewById(R.id.layTitulo);
        viewSeparador = view.findViewById(R.id.separador);
    }

    private void loadData() {

        if (titulo != null) {
            txtTitulo.setVisibility(View.VISIBLE);
            txtTitulo.setText(getTitulo());
        } else
            txtTitulo.setVisibility(View.GONE);

        if (icono != 0) {
            mImageView.setVisibility(View.VISIBLE);
            Glide.with(mImageView.getContext()).load(icono).into(mImageView);
        } else
            mImageView.setVisibility(View.GONE);

        if (descripcion != null)
            txtDescripcion.setText(descripcion);
        else txtDescripcion.setVisibility(View.GONE);

        if (colorBackground != 0)
            latBackground.setBackgroundColor(mContext.getResources().getColor(colorBackground));


        if (colorButtonNo != 0)
            Utils.changeColor(btnNo.getBackground(), mContext, colorButtonNo);

        if (colorButtonSi != 0)
            Utils.changeColor(btnSi.getBackground(), mContext, colorButtonSi);

        switch (tipo) {
            case TIPO_SI_NO:
                btnSi.setText("SI");
                btnNo.setText("NO");
                break;
            case TIPO_SI:
                btnSi.setText("SI");
                btnNo.setVisibility(View.GONE);
                viewSeparador.setVisibility(View.GONE);
                break;
            case TIPO_NO:
                btnNo.setText("NO");
                btnSi.setVisibility(View.GONE);
                viewSeparador.setVisibility(View.GONE);
                break;
            case TIPO_ACEPTAR_CANCELAR:
                btnSi.setText("ACEPTAR");
                btnNo.setText("CANCELAR");
                break;
            case TIPO_ACEPTAR:
                btnSi.setText("ACEPTAR");
                btnNo.setVisibility(View.GONE);
                viewSeparador.setVisibility(View.GONE);
                break;
            case TIPO_CANCEAR:
                btnNo.setText("CANCELAR");
                btnSi.setVisibility(View.GONE);
                viewSeparador.setVisibility(View.GONE);
                break;
            case TIPO_LIBRE:
                if (textButtonYes != null)
                    btnSi.setText(textButtonYes);
                else throw new NullPointerException("No se puede ser nulo el boton Si");

                if (textButtonNo != null)
                    btnNo.setText(textButtonNo);
                else throw new NullPointerException("No se puede ser nulo el boton No");
                break;
        }

    }


    private void loadListener() {
        btnNo.setOnClickListener(this);
        btnSi.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSI:
                if (mListener != null) {
                    mListener.yes();
                    dismiss();
                }
                break;
            case R.id.btnNO:
                if (mListener != null) {
                    mListener.no();
                    dismiss();
                }
                break;
        }
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Context getContextDialog() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTextButtonYes() {
        return textButtonYes;
    }

    public void setTextButtonYes(String textButtonYes) {
        this.textButtonYes = textButtonYes;
    }

    public String getTextButtonNo() {
        return textButtonNo;
    }

    public void setTextButtonNo(String textButtonNo) {
        this.textButtonNo = textButtonNo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getColorButtonSi() {
        return colorButtonSi;
    }

    public void setColorButtonSi(int colorButtonSi) {
        this.colorButtonSi = colorButtonSi;
    }

    public int getColorButtonNo() {
        return colorButtonNo;
    }

    public void setColorButtonNo(int colorButtonNo) {
        this.colorButtonNo = colorButtonNo;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public YesNoDialogListener getListener() {
        return mListener;
    }

    public void setListener(YesNoDialogListener listener) {
        mListener = listener;
    }
}
