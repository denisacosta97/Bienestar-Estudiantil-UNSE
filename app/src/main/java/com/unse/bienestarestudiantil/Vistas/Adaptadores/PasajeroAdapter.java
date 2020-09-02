package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.Pasajero;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PasajeroAdapter extends RecyclerView.Adapter<PasajeroAdapter.PasajeroViewHolder> {

    private ArrayList<Pasajero> mList;
    private Context mContext;
    private int tipo;

    public PasajeroAdapter(ArrayList<Pasajero> list, Context context, int tipo) {
        mList = list;
        mContext = context;
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public PasajeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pasajero, parent, false);
        return new PasajeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasajeroViewHolder holder, int position) {
        Pasajero pasajero = mList.get(position);
        Date date = Utils.getFechaDateWithHour(pasajero.getFechaLocal());
        holder.txtFecha.setText(String.format("Fecha: %s", Utils.getFechaOrderOnly(date)));
        holder.txtHora.setText(String.format("Hora: %s", Utils.getHora(date)));
        holder.txtDescripcion.setText(String.format("%s %s",
                pasajero.getNombre(),
                tipo == 1 ?
                        ""
                        : pasajero.getApellido()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class PasajeroViewHolder extends RecyclerView.ViewHolder {

        TextView txtDescripcion, txtFecha, txtHora;

        public PasajeroViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDescripcion = itemView.findViewById(R.id.txtNombre);
            txtFecha = itemView.findViewById(R.id.txtFechaIni);
            txtHora = itemView.findViewById(R.id.txtHora);
        }
    }
}
