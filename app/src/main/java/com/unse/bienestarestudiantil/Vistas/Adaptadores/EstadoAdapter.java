package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Modelos.Estado;
import com.unse.bienestarestudiantil.Modelos.Turno;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class EstadoAdapter extends RecyclerView.Adapter<EstadoAdapter.EstadoHolder> {

    private ArrayList<Estado> mEstadoArrayList;
    private Context mContext;

    public EstadoAdapter(Context context, ArrayList<Estado> list){
        this.mContext = context;
        this.mEstadoArrayList = list;

    }

    @NonNull
    @Override
    public EstadoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_estado, viewGroup, false);

        return new EstadoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoHolder holder, int i) {

        Estado estado = mEstadoArrayList.get(i);
        holder.descripcion.setText(estado.getDescripcion());
        if (estado.isSelect()){
            holder.descripcion.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
        }else{
            holder.descripcion.setTextColor(mContext.getResources().getColor(R.color.colorTextDefault));
            holder.mCardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }

    }

    @Override
    public int getItemCount() {
        return mEstadoArrayList.size();
    }

     static class EstadoHolder extends RecyclerView.ViewHolder{

        private TextView descripcion;
        private CardView mCardView;

         EstadoHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.txtTitulo);
            mCardView = itemView.findViewById(R.id.card);
        }
    }
}
