package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unse.bienestarestudiantil.R;

public class ComedorFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_comedor, container, false);

        loadViews();

        loadListener();

        return view;
    }

    private void loadListener() {

    }

    private void loadViews() {

    }

    @Override
    public void onClick(View v) {

    }

}
