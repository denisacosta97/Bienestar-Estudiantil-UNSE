package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Opciones;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class OpcionesSimpleAdapter extends RecyclerView.Adapter<OpcionesSimpleAdapter.HorarioViewHolder> {

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
    public OpcionesSimpleAdapter.HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opciones_simple, parent, false);


        return new OpcionesSimpleAdapter.HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OpcionesSimpleAdapter.HorarioViewHolder holder, int position) {

        Opciones s = arrayList.get(position);

        holder.txtTitulo.setText(s.getTitulo());
        holder.txtTitulo.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class HorarioViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        CardView mCardView;

        HorarioViewHolder(View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            mCardView = itemView.findViewById(R.id.card);


        }
    }

}
