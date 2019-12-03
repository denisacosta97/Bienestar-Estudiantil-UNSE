package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Activities.AsistenciaActivity;
<<<<<<< Updated upstream
import com.unse.bienestarestudiantil.Vistas.Activities.Deportes.GestionProfesor.GestionProfesorActivity;
=======
>>>>>>> Stashed changes
import com.unse.bienestarestudiantil.Vistas.Adaptadores.DeportesAdapter;

import java.util.ArrayList;

public class DialogoProfesorDeportes extends DialogFragment {

    View view;
    ImageView imgIcon;
    TextView txtNombre;
    Deporte mDeporte;
    ArrayList<Deporte> mDeportes;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView reciclerDeportes;
    DeportesAdapter mDeportesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogo_profesor_deportes, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Esto es lo nuevoooooooo, evita los bordes cuadrados
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Utils.setFont(getContext(),(ViewGroup)view, Utils.MONSERRAT);

        loadViews();

        loadDataRecycler();

        //loadData();

        //loadListener();

        return view;
    }

    private void loadData() {
        if(mDeporte != null){
            txtNombre.setText(mDeporte.getName());
            imgIcon.setImageResource(mDeporte.getIconDeporte());

        }
    }


    private void loadListener() {

    }

    private void loadViews() {
        reciclerDeportes = view.findViewById(R.id.recyclerDeportes);
    }

    private void loadDataRecycler() {
        mDeportes = new ArrayList<>();

        mDeportes.add(new Deporte(3, R.drawable.ic_futbol_masc, "Fútbol 11 Masculino", "José Grecco", "lunes, miércoles y viernes","20:30hs a 23:00hs"));
        mDeportes.add(new Deporte(4, R.drawable.ic_futbol_fem, "Fútbol 11 Femenino", "José Grecco", "lunes, miércoles y viernes","18:30hs a 20:30hs"));

        mDeportesAdapter = new DeportesAdapter(mDeportes, getContext(), true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false);
        reciclerDeportes.setNestedScrollingEnabled(true);
        reciclerDeportes.setLayoutManager(mLayoutManager);
        reciclerDeportes.setAdapter(mDeportesAdapter);

        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(reciclerDeportes);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
<<<<<<< Updated upstream
                Intent i = new Intent(getContext(), GestionProfesorActivity.class);
=======
                Intent i = new Intent(getContext(), AsistenciaActivity.class);
>>>>>>> Stashed changes
                i.putExtra(Utils.DEPORTE_NAME, mDeportes.get(position));
                startActivity(i);
            }
        });
    }

}
