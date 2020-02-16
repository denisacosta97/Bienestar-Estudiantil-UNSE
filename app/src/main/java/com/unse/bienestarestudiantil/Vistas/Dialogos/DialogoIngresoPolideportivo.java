package com.unse.bienestarestudiantil.Vistas.Dialogos;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

public class DialogoIngresoPolideportivo extends DialogFragment {

    View view;
    ImageView imgFoto;
    ImageButton btnMasMay, btnMasMen, btnMenosMay, btnMenosMen;
    Button btnAceptar;
    TextView txtCantidadMay, txtCantidadMen,txtNombre, txtDNI;
    Usuario usuario;

    int contadorMay = 0, contadorMeno = 0;

    public void loadDataFromUser(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_ingreso_poli, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Esto es lo nuevoooooooo, evita los bordes cuadrados
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //Utils.setFont(getContext(),(ViewGroup)view, Utils.MONSERRAT);

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadData() {
        contadorMeno = contadorMay = 0;
        if(usuario != null){
            txtNombre.setText(String.format("%s %s", usuario.getNombre(), usuario.getApellido()));
            txtDNI.setText(String.format("%s", usuario.getIdUsuario()));
            updateCounter(contadorMay, contadorMeno);
        }
    }

    private void updateCounter(int c1, int c2){
        txtCantidadMay.setText(c1+"");
        txtCantidadMen.setText(c2+"");
    }

    private void loadListener() {
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadViews() {
        btnAceptar = view.findViewById(R.id.btnAceptar);
        imgFoto = view.findViewById(R.id.imgIcon);
        txtCantidadMay = view.findViewById(R.id.txtCantidadMay);
        txtCantidadMen = view.findViewById(R.id.txtCantidadMen);
        btnMasMay = view.findViewById(R.id.btnAddMay);
        btnMenosMay = view.findViewById(R.id.btnRemoveMay);
        btnMasMen = view.findViewById(R.id.btnAddMen);
        btnMenosMen = view.findViewById(R.id.btnRemoveMen);
        txtDNI = view.findViewById(R.id.txtDescripcion);
        txtNombre = view.findViewById(R.id.txtTitulo);


    }

}
