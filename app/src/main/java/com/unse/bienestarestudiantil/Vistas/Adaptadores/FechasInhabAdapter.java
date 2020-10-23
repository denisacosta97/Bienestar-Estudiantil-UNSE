package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.FechasInhabilitadas;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class FechasInhabAdapter extends RecyclerView.Adapter<FechasInhabAdapter.TransporteViewHolder> {
    private ArrayList<FechasInhabilitadas> mList;
    private ArrayList<FechasInhabilitadas> mListCopia;
    private Context context;

    public FechasInhabAdapter(ArrayList<FechasInhabilitadas> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public FechasInhabAdapter.TransporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fechas_inhab, parent, false);
        return new FechasInhabAdapter.TransporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FechasInhabAdapter.TransporteViewHolder holder, int position) {
        FechasInhabilitadas cons = mList.get(position);

        holder.txtDesc.setText(cons.getDesc());
        String fecha = cons.getDia() + "/" + cons.getMes() + "/" + cons.getAnio();
        holder.txtFecha.setText(fecha);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TransporteViewHolder extends RecyclerView.ViewHolder {

        TextView txtDesc, txtFecha;

        public TransporteViewHolder(View itemView) {
            super(itemView);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtFecha = itemView.findViewById(R.id.txtFechaInhab);
        }

    }

    public void change(ArrayList<FechasInhabilitadas> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
