package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.ReservaEspacio;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ReservaEspacioAdapter extends RecyclerView.Adapter<ReservaEspacioAdapter.ReservaEspacioViewHolder> {

    private ArrayList<ReservaEspacio> mList;
    private ArrayList<ReservaEspacio> mListCopia;
    private Context context;

    public ReservaEspacioAdapter(ArrayList<ReservaEspacio> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public ReservaEspacioAdapter.ReservaEspacioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva_espacio, parent, false);
        return new ReservaEspacioAdapter.ReservaEspacioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaEspacioAdapter.ReservaEspacioViewHolder holder, int position) {
        ReservaEspacio reserva = mList.get(position);

        holder.txtNombre.setText(reserva.getNombre());
        holder.txtIdReserva.setText(String.valueOf(reserva.getIdReserva()));
        holder.txtHoraI.setText(reserva.getHoraI());
        holder.txtHoraF.setText(reserva.getHoraF());

    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getIdReserva();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ReservaEspacioViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdReserva, txtNombre, txtHoraI, txtHoraF;

        public ReservaEspacioViewHolder(View itemView) {
            super(itemView);
            txtIdReserva = itemView.findViewById(R.id.txtIdReserva);
            txtNombre = itemView.findViewById(R.id.txtEspacio);
            txtHoraF = itemView.findViewById(R.id.txtHoraF);
            txtHoraI = itemView.findViewById(R.id.txtHoraI);
        }

    }

    public void change(ArrayList<ReservaEspacio> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }

}
