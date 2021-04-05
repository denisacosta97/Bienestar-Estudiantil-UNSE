package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Instalacion;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class InstalacionesAdapter extends RecyclerView.Adapter<InstalacionesAdapter.OpcionesViewHolder> {
    private ArrayList<Instalacion> arrayList;
    private Context context;

    public InstalacionesAdapter(ArrayList<Instalacion> list, Context ctx) {
        context = ctx;
        arrayList = list;
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @NonNull
    @Override
    public OpcionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inst, parent, false);
        return new OpcionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpcionesViewHolder holder, int position) {
        Instalacion s = arrayList.get(position);

        holder.txtTurno.setText(s.getNombre());
        holder.imgIcon.setImageResource(s.getIcon());
        holder.txtDesc.setText(s.getDesc());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class OpcionesViewHolder extends RecyclerView.ViewHolder {
        TextView txtTurno, txtDesc;
        ImageView imgIcon;

        OpcionesViewHolder(View itemView) {
            super(itemView);
            txtTurno = itemView.findViewById(R.id.txtTurno);
            imgIcon = itemView.findViewById(R.id.imgEstado);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}
