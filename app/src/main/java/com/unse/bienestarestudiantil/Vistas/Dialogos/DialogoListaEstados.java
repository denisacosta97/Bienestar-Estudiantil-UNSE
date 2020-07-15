package com.unse.bienestarestudiantil.Vistas.Dialogos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.unse.bienestarestudiantil.Herramientas.RecyclerListener.ItemClickSupport;
import com.unse.bienestarestudiantil.Interfaces.OnClickListenerAdapter;
import com.unse.bienestarestudiantil.Modelos.Estado;
import com.unse.bienestarestudiantil.R;
import com.unse.bienestarestudiantil.Vistas.Adaptadores.EstadoAdapter;

import java.util.ArrayList;

public class DialogoListaEstados extends DialogFragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    EstadoAdapter mOpcionesAdapter;
    ArrayList<Estado> mOpciones;
    View view;
    Button btnOk;
    int select = -1;
    OnClickListenerAdapter mListenerAdapter;
    Context mContext;

    public void setList(ArrayList<Estado> list){
        this.mOpciones = list;
    }

    public void setContextEstado(Context contextEstado){
        this.mContext = contextEstado;
    }

    public void setListener(OnClickListenerAdapter listener){
        this.mListenerAdapter = listener;
    }
    public void setId(int i){
        this.select = i;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_lista_estado, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        loadViews();

        loadData();

        loadListener();

        return view;
    }

    private void loadListener() {
        btnOk.setOnClickListener(this);
        ItemClickSupport clickSupport = ItemClickSupport.addTo(mRecyclerView);
        clickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                reset();
                select = position;
                mOpciones.get(position).setSelect(true);
                mOpcionesAdapter.notifyDataSetChanged();
            }
        });
    }

    private void reset() {
        for (Estado e: mOpciones){
            e.setSelect(false);
        }
    }

    private void loadData() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mOpcionesAdapter = new EstadoAdapter(getContext(), mOpciones);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mOpcionesAdapter);

    }

    private void loadViews() {
        btnOk = view.findViewById(R.id.btnSI);
        mRecyclerView = view.findViewById(R.id.recycler);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSI:
                if (mListenerAdapter != null){
                    mListenerAdapter.onClick(select);
                }
                break;
        }
    }
}
