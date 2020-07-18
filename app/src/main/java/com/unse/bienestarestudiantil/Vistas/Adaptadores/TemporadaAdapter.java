package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Modelos.TemporadaArea;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class TemporadaAdapter extends RecyclerView.Adapter<TemporadaAdapter.TemporadaViewHolder> {

    private ArrayList<TemporadaArea> mTemporadaAreas;
    private Context mContext;

    public TemporadaAdapter(ArrayList<TemporadaArea> temporadaAreas, Context context) {
        mTemporadaAreas = temporadaAreas;
        mContext = context;
    }


    @NonNull
    @Override
    public TemporadaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temporada, parent, false);
        return new TemporadaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemporadaViewHolder holder, int position) {
        TemporadaArea temporadaArea = mTemporadaAreas.get(position);
        holder.txtNombre.setText(String.format("TEMPORADA %s", temporadaArea.getAnio()));
        holder.txtFecha.setText(String.format("Fecha de Registro: %s", temporadaArea.getFechaIngreso()));
        Glide.with(holder.imgIcon.getContext()).load(temporadaArea.getValidez() == 1 ? R.drawable.ic_chek :
                R.drawable.ic_error).into(holder.imgIcon);

    }

    @Override
    public int getItemCount() {
        return mTemporadaAreas.size();
    }

    public static class TemporadaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtFecha;
        AppCompatImageView imgIcon;

        public TemporadaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
