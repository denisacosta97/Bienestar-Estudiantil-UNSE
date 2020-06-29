package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Inicio.RegisterActivity;

public class AccesoDenegadoFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btnRegistrarse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_acceso_denegado, container, false);

        loadViews();


        loadListener();


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnregister:
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loadListener() {
        btnRegistrarse.setOnClickListener(this);
    }

    private void loadViews() {
        btnRegistrarse = view.findViewById(R.id.btnregister);
    }

}