package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Herramientas.FontChangeUtil;
import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Deporte;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class DeportesAdapter extends RecyclerView.Adapter<DeportesAdapter.EventosViewHolder> {

    private ArrayList<Deporte> deport;
    private Context context;
    private boolean mBoolean;
    View view;

    public DeportesAdapter(ArrayList<Deporte> list, Context ctx, boolean b) {
        this.deport = list;
        this.context = ctx;
        this.mBoolean = b;
    }

    @NonNull
    @Override
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mBoolean)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes_profesor, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deportes, parent, false);

        FontChangeUtil fontChanger = new FontChangeUtil(context.getAssets(), "Montserrat-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup) view);

        return new EventosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        Deporte depo = deport.get(position);
        Glide.with(holder.mIcon.getContext()).load(Utils.getIconDeporte(depo.getIdDep())).into(holder.mIcon);
        holder.mNameDeport.setText(depo.getName());
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

        ImageView mIcon;
        TextView mNameDeport;

        EventosViewHolder(View itemView) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.imgvIconDeporte);
            mNameDeport = itemView.findViewById(R.id.txtNameDeporte);
        }
    }
}
