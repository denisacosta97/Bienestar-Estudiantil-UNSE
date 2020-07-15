package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.Becas.TurnosActivity;
import com.unse.bienestarestudiantil.Vistas.Activities.Becas.InfoBecasActivity;

public class BecasFragment extends Fragment implements View.OnClickListener {

    View view;
    CardView cardTurnos, cardInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_becas, container, false);

        loadViews();

        loadListener();

        return view;
    }

    private void loadListener() {
        cardTurnos.setOnClickListener(this);
        cardInfo.setOnClickListener(this);
    }

    private void loadViews() {
        cardTurnos = view.findViewById(R.id.cardTurnos);
        cardInfo = view.findViewById(R.id.card_infoBecas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardTurnos:
                startActivity(new Intent(getContext(), TurnosActivity.class));
                break;
            case R.id.card_infoBecas:
                startActivity(new Intent(getContext(), InfoBecasActivity.class));
                break;
        }
    }

}
