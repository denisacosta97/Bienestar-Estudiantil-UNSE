package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.Convocatoria;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ConvocatoriasAdapter extends RecyclerView.Adapter<ConvocatoriasAdapter.TransporteViewHolder> {

    private ArrayList<Convocatoria> mList;
    private ArrayList<Convocatoria> mListCopia;
    private Context context;

    public ConvocatoriasAdapter(ArrayList<Convocatoria> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public ConvocatoriasAdapter.TransporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_convocatoria, parent, false);
        return new ConvocatoriasAdapter.TransporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConvocatoriasAdapter.TransporteViewHolder holder, int position) {
        Convocatoria con = mList.get(position);
        holder.txtBeca.setText(con.getNombre());
        holder.txtFechaIni.setText(con.getFechaInicio());
        holder.txtFechaFin.setText(con.getFechaFin());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TransporteViewHolder extends RecyclerView.ViewHolder {

        TextView txtBeca, txtFechaIni, txtFechaFin;

        public TransporteViewHolder(View itemView) {
            super(itemView);
            txtBeca = itemView.findViewById(R.id.txtBeca);
            txtFechaIni = itemView.findViewById(R.id.txtFechaDesde);
            txtFechaFin = itemView.findViewById(R.id.txtFechaHasta);
        }

    }

    public void change(ArrayList<Convocatoria> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }

}
