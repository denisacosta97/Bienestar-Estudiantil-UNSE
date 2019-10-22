package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Polideportivo.ReservaEspacioActivity;

public class PoliFragment extends Fragment {

    View view;
    Button btnReservas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_poli, container, false);

        FontChangeUtil fontChanger = new FontChangeUtil(getContext().getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        btnReservas = view.findViewById(R.id.btnReservaQS);
        btnReservas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReservaEspacioActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
