package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Reserva;
import com.unse.bienestarestudiantil.Modelos.Usuario;
import com.unse.bienestarestudiantil.R;

public class DialogoReservas extends DialogFragment {

    View view;
    ImageView imgFoto;
    Button btnAceptar;
    TextView txtNombre, txtDesc, txtName, txtDni;
    Reserva reserva;
    Usuario mUsuario;

    public void loadDataFromReserva(Reserva reserva){
        this.reserva = reserva;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogo_reservas, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Esto es lo nuevoooooooo, evita los bordes cuadrados
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadData() {
        if(reserva != null){
            txtNombre.setText(reserva.getTitulo());
            txtDesc.setText(String.format("%s %s", reserva.getRangoHora().getHoraInicio(), reserva.getRangoHora().getHoraFin()));
//            txtName.setText(String.format("%s %s", mUsuario.getNombre(), mUsuario.getApellido()));
//            txtDni.setText(mUsuario.getId());
        }
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
        txtDesc = view.findViewById(R.id.txtDescripcion);
        txtNombre = view.findViewById(R.id.txtTitulo);
        txtName = view.findViewById(R.id.txtNombre);
        txtDni = view.findViewById(R.id.txtDni);
    }
}
