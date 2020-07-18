package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Interfaces.OnClickOptionListener;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.OpcionesAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DialogoOpciones extends DialogFragment {

    View view;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    OpcionesAdapter mOpcionesAdapter;
    OnClickOptionListener mListener;
    ArrayList<Opciones> mOpciones;
    Context mContext;
    LinearLayout latDatos;

    public DialogoOpciones(OnClickOptionListener listener, ArrayList<Opciones> opciones, Context context) {
        mListener = listener;
        mOpciones = opciones;
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialogo_opciones, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Esto es lo nuevoooooooo, evita los bordes cuadrados
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadViews() {
        latDatos = view.findViewById(R.id.latDatos);
        mRecyclerView = view.findViewById(R.id.recycler);
    }

    private void loadData() {
        if (mOpciones.size() > 5) {
            ViewGroup.LayoutParams layoutParams = latDatos.getLayoutParams();
            layoutParams.height = 600;
            latDatos.setLayoutParams(layoutParams);
        }
        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mOpcionesAdapter = new OpcionesAdapter(mOpciones, mContext, 2);
        mRecyclerView.setAdapter(mOpcionesAdapter);
    }


    private void loadListener() {
        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (mListener != null) {
                    dismiss();
                    mListener.onClick(position);
                }
            }
        });

    }

}
