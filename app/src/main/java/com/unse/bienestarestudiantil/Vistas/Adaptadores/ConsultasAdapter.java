package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.Consulta;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ConsultasAdapter extends RecyclerView.Adapter<ConsultasAdapter.TransporteViewHolder> {
    private ArrayList<Consulta> mList;
    private ArrayList<Consulta> mListCopia;
    private Context context;

    public ConsultasAdapter(ArrayList<Consulta> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public ConsultasAdapter.TransporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultas, parent, false);
        return new ConsultasAdapter.TransporteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultasAdapter.TransporteViewHolder holder, int position) {
        Consulta cons = mList.get(position);

        holder.txtDni.setText(String.valueOf(cons.getDni()));
        holder.txtFecha.setText(cons.getFecha());
        holder.txtConsulta.setText(cons.getConsulta());

    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getDni();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class TransporteViewHolder extends RecyclerView.ViewHolder {

        TextView txtDni, txtFecha, txtConsulta;

        public TransporteViewHolder(View itemView) {
            super(itemView);
            txtDni = itemView.findViewById(R.id.txtDNI);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtConsulta = itemView.findViewById(R.id.txtConsulta);
        }
    }

    public void change(ArrayList<Consulta> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
