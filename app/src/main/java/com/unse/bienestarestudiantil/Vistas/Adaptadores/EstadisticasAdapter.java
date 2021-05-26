package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.Modelos.Datos;
import com.unse.bienestarestudiantil.Modelos.Lista;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasAdapter extends RecyclerView.Adapter<EstadisticasAdapter.EstadisticaViewHolder> {

    private List<Datos> mList;
    int[] colors;
    private Context context;

    public EstadisticasAdapter(List<Datos> models, Context context, int[] colors) {
        this.mList = models;
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public EstadisticasAdapter.EstadisticaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_datos_estadistica, parent, false);
        return new EstadisticasAdapter.EstadisticaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstadisticasAdapter.EstadisticaViewHolder holder, int position) {
        Datos cons = mList.get(position);

        holder.txtInfo.setText(String.valueOf(cons.getId()));
        holder.latCuadro.setBackgroundColor(colors.length == 1 ? colors[0] : colors[position]);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class EstadisticaViewHolder extends RecyclerView.ViewHolder {

        TextView txtInfo;
        LinearLayout latCuadro;

        public EstadisticaViewHolder(View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            latCuadro = itemView.findViewById(R.id.latCuadro);
        }
    }
}

