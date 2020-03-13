package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;


public class OpcionesAdapter extends RecyclerView.Adapter<OpcionesAdapter.OpcionesViewHolder> {

    private ArrayList<Opciones> arrayList;
    private Context context;

    public OpcionesAdapter(ArrayList<Opciones> list, Context ctx) {
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opciones, parent, false);


        return new OpcionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpcionesViewHolder holder, int position) {

        Opciones s = arrayList.get(position);

        holder.txtTitulo.setText(s.getTitulo());
        if (s.getColorText() != 0)
            holder.txtTitulo.setTextColor(context.getResources().getColor(s.getColorText()));
        if (s.getSizeText() != 0)
            holder.txtTitulo.setTextSize(Float.parseFloat(String.valueOf(s.getSizeText())));
        Glide.with(holder.imgIcono.getContext())
                .load(s.getIcon())
                .into(holder.imgIcono);
        holder.mCardView.setCardBackgroundColor(context.getResources().getColor(s.getColor()));

        if (s.getDisponibility() == 0){
            holder.mCardView.setEnabled(false);
        }else
            holder.mCardView.setEnabled(true);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class OpcionesViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        ImageView imgIcono;
        CardView mCardView;

        OpcionesViewHolder(View itemView) {
            super(itemView);

            imgIcono = itemView.findViewById(R.id.imgIcono);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mCardView = itemView.findViewById(R.id.card);


        }
    }

}
