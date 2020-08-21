package com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservasCanchas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.R;

public class DialogBuscarCancha extends DialogFragment {

    View view;
    Button btnBuscar, btnAceptar;
    EditText edtDni;
    TextView txtNomAp, txtDni, txtHora, txtCancha, txtFecha;
    LinearLayout linlayNo, linlayInfo, linlayEdt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_buscar_cancha, container, false);
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

    }


    private void loadListener() {
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = edtDni.getText().toString().trim();
                boolean exist = buscarReserva(dni);
                if(exist){
                    //cargar los pinshis datitos
                    linlayEdt.setVisibility(View.GONE);
                    linlayInfo.setVisibility(View.VISIBLE);
                } else {
                    linlayEdt.setVisibility(View.GONE);
                    linlayNo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean buscarReserva(String dni) {
        return false;
    }

    private void loadViews() {
        linlayEdt = view.findViewById(R.id.linlayEdt);
        linlayInfo = view.findViewById(R.id.linlayInfo);
        linlayNo = view.findViewById(R.id.linlayNo);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        txtNomAp = view.findViewById(R.id.txtNomAp);
        txtDni = view.findViewById(R.id.txtDni);
        txtHora = view.findViewById(R.id.txtHora);
        txtFecha = view.findViewById(R.id.txtFecha);
        txtCancha = view.findViewById(R.id.txtCancha);
        edtDni = view.findViewById(R.id.edtDniImput);
    }
}