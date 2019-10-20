package com.unse.bienestarestudiantil.Vistas.Fragmentos;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Modelos.Deportes;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;

import java.util.ArrayList;

public class DeportesFragment extends Fragment {

    View view;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    ArrayList<Deportes> mDeportes;
    DeportesAdapter mDeportesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_deportes, container, false);

        FontChangeUtil fontChanger = new FontChangeUtil(getContext().getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        loadViews();

        loadDataRecycler();

        return view;
    }

    private void loadViews() {
        reciclerDeportes = view.findViewById(R.id.recyclerDeportes);
    }

    private void loadDataRecycler() {
        mDeportes = new ArrayList<>();

        mDeportes.add(new Deportes(0, R.drawable.ic_becas, "Ajedréz"));
        mDeportes.add(new Deportes(1, R.drawable.ic_becas, "Básquet"));
        mDeportes.add(new Deportes(2, R.drawable.ic_becas, "Cestobol"));
        mDeportes.add(new Deportes(3, R.drawable.ic_becas, "Fútbol 11 Masculino"));
        mDeportes.add(new Deportes(4, R.drawable.ic_becas, "Fútbol 11 Femenino"));
        mDeportes.add(new Deportes(5, R.drawable.ic_becas, "Fútbol Sala Masculino"));
        mDeportes.add(new Deportes(6, R.drawable.ic_becas, "Fútbol Sala Femenino"));
        mDeportes.add(new Deportes(7, R.drawable.ic_becas, "Hockey"));
        mDeportes.add(new Deportes(8, R.drawable.ic_becas, "Natación"));
        mDeportes.add(new Deportes(9, R.drawable.ic_becas, "Rugby"));
        mDeportes.add(new Deportes(10, R.drawable.ic_becas, "Tenis de Mesa"));
        mDeportes.add(new Deportes(11, R.drawable.ic_becas, "Voleibol Masculino"));
        mDeportes.add(new Deportes(12, R.drawable.ic_becas, "Voleibol Femenino"));

        mDeportesAdapter = new DeportesAdapter(mDeportes, getContext());

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mDeportesAdapter);

    }

}