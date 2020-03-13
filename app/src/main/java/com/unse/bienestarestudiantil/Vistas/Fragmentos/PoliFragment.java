package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.GestionReservas.EspaciosActivity;

public class PoliFragment extends Fragment {

    View view;
    Button btnReservas;
    CardView cardOne;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poli, container, false);

        btnReservas = view.findViewById(R.id.btnReserva);
        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EspaciosActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
