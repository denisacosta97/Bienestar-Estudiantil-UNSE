package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.InfoBecasActivity;

public class BecasFragment extends Fragment implements View.OnClickListener {

    View view;
    CardView mInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_becas, container, false);

        loadViews();

        loadListener();

        loadDataRecycler();


        return view;
    }

    private void loadListener() {
        mInfo.setOnClickListener(this);
    }

    private void loadViews() {
        mInfo = view.findViewById(R.id.card_infoBecas);
    }

    private void loadDataRecycler() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_infoBecas:
                startActivity(new Intent(getContext(), InfoBecasActivity.class));
                break;
        }
    }
}
