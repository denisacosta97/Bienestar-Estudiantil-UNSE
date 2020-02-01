package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class OpcionesSimpleAdapter extends RecyclerView.Adapter<OpcionesSimpleAdapter.OpcionesViewHolder> {

    private ArrayList<Opciones> arrayList;
    private Context context;

    public OpcionesSimpleAdapter(ArrayList<Opciones> list, Context ctx) {
        context = ctx;
        arrayList = list;
    }


    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @NonNull
    @Override
    public OpcionesSimpleAdapter.OpcionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opciones_simple, parent, false);


        return new OpcionesSimpleAdapter.OpcionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpcionesSimpleAdapter.OpcionesViewHolder holder, int position) {

        Opciones s = arrayList.get(position);

        holder.txtTitulo.setText(s.getTitulo());
        holder.txtTitulo.setTextColor(context.getResources().getColor(R.color.colorAccent));
        //holder.mCardView.setCardBackgroundColor(context.getResources().getColor(s.getColor()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class OpcionesViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        CardView mCardView;

        OpcionesViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mCardView = itemView.findViewById(R.id.card);


        }
    }

}
