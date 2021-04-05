package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Modelos.ReservaCancha;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class ReservaCanchaAdapter extends RecyclerView.Adapter<ReservaCanchaAdapter.ReservaCanchaViewHolder> {
    private ArrayList<ReservaCancha> mList;
    private ArrayList<ReservaCancha> mListCopia;
    private Context context;

    public ReservaCanchaAdapter(ArrayList<ReservaCancha> models, Context context) {
        this.mList = models;
        this.context = context;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
    }

    @NonNull
    @Override
    public ReservaCanchaAdapter.ReservaCanchaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva_cancha, parent, false);
        return new ReservaCanchaAdapter.ReservaCanchaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaCanchaAdapter.ReservaCanchaViewHolder holder, int position) {
        ReservaCancha reserva = mList.get(position);

        holder.txtDni.setText(String.valueOf(reserva.getDni()));
        holder.txtNomAp.setText("Cargar nombre");
        holder.txtHora.setText(reserva.getHora());
        holder.txtCancha.setText(reserva.getCancha());
        holder.txtFecha.setText(reserva.getFecha());
        holder.txtPrecio.setText(String.valueOf(reserva.getPrecio()));
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).getDni();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ReservaCanchaViewHolder extends RecyclerView.ViewHolder {

        TextView txtNomAp, txtDni, txtHora, txtCancha, txtFecha, txtPrecio;

        public ReservaCanchaViewHolder(View itemView) {
            super(itemView);
            txtDni = itemView.findViewById(R.id.txtDni);
            txtNomAp = itemView.findViewById(R.id.txtNomAp);
            txtHora = itemView.findViewById(R.id.txtHora);
            txtCancha = itemView.findViewById(R.id.txtCancha);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
        }

    }

    public void change(ArrayList<ReservaCancha> list) {
        mList = list;
        this.mListCopia = new ArrayList<>();
        this.mListCopia.addAll(mList);
        notifyDataSetChanged();
    }
}
