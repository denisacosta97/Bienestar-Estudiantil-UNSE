package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeportesAdapter extends RecyclerView.Adapter<DeportesAdapter.EventosViewHolder> {

    private ArrayList<Deporte> deport;
    private Context context;
    private boolean mBoolean;

    public DeportesAdapter(ArrayList<Deporte> list, Context ctx, boolean b) {
        this.deport = list;
        this.context = ctx;
        this.mBoolean = b;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (mBoolean)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes_profesor, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes, parent, false);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Deporte depo = deport.get(position);
        Glide.with(holder.imgIcon.getContext()).load(depo.getIconDeporte()).into(holder.imgIcon);
        holder.txtNombre.setText(depo.getName());
    }


    @Override
    public long getItemId(int position) {
        return deport.get(position).getIdDep();
    }

    @Override
    public int getItemCount() {
        return deport.size();
    }

    static class EventosViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        TextView txtNombre;

        EventosViewHolder(View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgvIconDeporte);
            txtNombre = itemView.findViewById(R.id.txtNameDeporte);
        }
    }
}
