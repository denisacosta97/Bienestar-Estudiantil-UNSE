package com.unse.bienestarestudiantil.Vistas.Adaptadores;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Sugerencia;
import com.unse.bienestarestudiantil.R;

public class OpinionesAdapter extends RecyclerView.Adapter<OpinionesAdapter.OpinionViewHolder> {

    private ArrayList<Sugerencia> mSugerencias;
    private Context mContext;

    public OpinionesAdapter(ArrayList<Sugerencia> sugerencias, Context context) {
        mSugerencias = sugerencias;
        mContext = context;
    }

    @NonNull
    @Override
    public OpinionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sugerencias, parent, false);
        return new OpinionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpinionViewHolder holder, int position) {
        Sugerencia sugerencia = mSugerencias.get(position);

        holder.txtFecha.setText(Utils.getFechaOrder(
                Utils.getFechaDateWithHour(sugerencia.getFechaRegistro())
        ));
        holder.txtDescripcion.setText(sugerencia.getDescripcion());

    }

    @Override
    public int getItemCount() {
        return mSugerencias.size();
    }

    public void change(ArrayList<Sugerencia> list) {
        mSugerencias = list;
        notifyDataSetChanged();
    }

    public static class OpinionViewHolder extends RecyclerView.ViewHolder {

        TextView txtFecha, txtDescripcion;

        public OpinionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtDescripcion = itemView.findViewById(R.id.txtDesc);
        }
    }
}
