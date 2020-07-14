package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Transporte;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class TransporteAdapter extends RecyclerView.Adapter<TransporteAdapter.TransporteViewHolder> {
    private ArrayList<Transporte> mList;
    private Context context;

    public TransporteAdapter(ArrayList<Transporte> models, Context context) {
        this.mList = models;
        this.context = context;
    }


    @NonNull
    @Override
    public TransporteAdapter.TransporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transporte, parent, false);
        return new TransporteAdapter.TransporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransporteAdapter.TransporteViewHolder holder, int position) {
        Transporte transporte = mList.get(position);

        holder.txtLinea.setText(transporte.getNombre());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class TransporteViewHolder extends RecyclerView.ViewHolder {

        TextView txtLinea;

        public TransporteViewHolder(View itemView) {
            super(itemView);
            txtLinea = itemView.findViewById(R.id.txtLinea);

        }
    }
}
