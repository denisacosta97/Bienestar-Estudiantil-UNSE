package com.unse.bienestarestudiantil.Vistas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unse.bienestarestudiantil.Herramientas.Utils;
import com.unse.bienestarestudiantil.Modelos.PuntoConectividad;
import com.unse.bienestarestudiantil.R;

import java.util.ArrayList;

public class PuntoConectividadAdapter extends RecyclerView.Adapter<PuntoConectividadAdapter.PuntosHolder> {

    private ArrayList<PuntoConectividad> lista;
    private Context mContext;

    public PuntoConectividadAdapter(ArrayList<PuntoConectividad> lista, Context context) {
        this.lista = lista;
        mContext = context;
    }

    @NonNull
    @Override
    public PuntosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_puntos_conectividad, parent, false);

        return new PuntosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PuntosHolder holder, int position) {

        PuntoConectividad puntoConectividad = lista.get(position);

        holder.txtNombre.setText(String.format("%s %s", puntoConectividad.getNombre(), puntoConectividad.getApellido()));
        holder.txtDNI.setText(String.valueOf(puntoConectividad.getIdUsuario()));
        holder.txtEstado.setText(puntoConectividad.getDescripcion());
        holder.txtHorario.setText(puntoConectividad.getHorario());
        switch (puntoConectividad.getDescripcion()) {
            case "PENDIENTE":
            case "RESERVADO":
                holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
                holder.txtEstado.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
            case "CONFIRMADO":
            case "RETIRADO":
                holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
                holder.txtEstado.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
            case "AUSENTE":
                holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorYellow));
                holder.txtEstado.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
            case "CANCELADO":
                holder.txtEstado.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
                holder.txtEstado.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class PuntosHolder extends RecyclerView.ViewHolder {

        TextView txtDNI, txtNombre, txtHorario, txtEstado;

        public PuntosHolder(@NonNull View itemView) {
            super(itemView);

            txtDNI = itemView.findViewById(R.id.txtDni);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtHorario = itemView.findViewById(R.id.txtHora);
            txtEstado = itemView.findViewById(R.id.txtEstado);
        }
    }
}

